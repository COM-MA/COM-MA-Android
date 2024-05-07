package com.green.comma.ui.camera

import android.Manifest
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.FallbackStrategy
import androidx.camera.video.MediaStoreOutputOptions
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.green.comma.R
import com.green.comma.databinding.ActivityCameraBinding
import com.green.comma.ui.card.CardSearchDetailActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding

    private val aiViewModel: AIViewModel by viewModels { AIViewModelFactory(applicationContext) }

    private var videoCapture: VideoCapture<Recorder>? = null
    private var recording: Recording? = null
    private var videoPath: Uri? = null

    private lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        // Set up the listeners for take photo and video capture buttons
        binding.btnStartRecording.setOnClickListener { captureVideo() }

        aiViewModel.prediction.observe(this) {
            moveToDetailActivity(it.prediction)
            if(videoPath != null) deleteVideo()
        }

        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun captureVideo() {
        val videoCapture = this.videoCapture ?: return
        binding.btnStartRecording.isEnabled = false

        // 진행 중인 활성 녹화 세션이 있다면 중지
        val curRecording = recording
        if(curRecording != null){
            curRecording.stop()
            recording = null
            return
        }

        // 비디오 녹화를 위한 파일 이름 생성
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.KOREA)
            .format(System.currentTimeMillis())

        // 비디오 파일의 메타데이터를 설정한다.
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/CameraX-Video")
            }
        }

        // 콘텐츠의 외부 저장 위치를 옵션으로 설정하기 위해 빌더를 만들고 인스턴스를 빌드
        val mediaStoreOutputOptions = MediaStoreOutputOptions
            .Builder(contentResolver, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
            .setContentValues(contentValues)
            .build()

        recording = videoCapture.output
            .prepareRecording(this, mediaStoreOutputOptions)
            .start(ContextCompat.getMainExecutor(this)) { recordEvent ->
                when(recordEvent) {
                    is VideoRecordEvent.Start -> {
                        binding.btnStartRecording.apply {
                            text = "녹화 종료"
                            isEnabled = true
                        }
                    }
                    is VideoRecordEvent.Finalize -> {
                        if(!recordEvent.hasError()) {
                            val msg = "녹화 성공" +
                                    "${recordEvent.outputResults.outputUri}"
                            val file = getFileFromContentUri(recordEvent.outputResults.outputUri)
                            if(file != null){
                                videoPath = recordEvent.outputResults.outputUri
                                postVideoToServer(getVideoBody(file))
                            }
                            Log.d(TAG, msg)
                        } else {
                            recording?.close()
                            recording = null
                            Log.e(TAG, "Video capture ends with error: " +
                                    "${recordEvent.error}")
                            Toast.makeText(baseContext, "촬영에 실패했어요.", Toast.LENGTH_SHORT)
                                .show()
                            finish()
                        }
                        binding.btnStartRecording.apply {
                            text = "녹화 성공"
                            isEnabled = true
                        }
                    }
                }
            }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(Runnable {
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            val recorder = Recorder.Builder()
                .setQualitySelector(QualitySelector.from(Quality.HIGHEST,
                    FallbackStrategy.higherQualityOrLowerThan(Quality.SD)))
                .build()

            videoCapture = VideoCapture.withOutput(recorder)

            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, videoCapture)

                binding.btnStartRecording.performClick()
                android.os.Handler(Looper.getMainLooper()).postDelayed({
                    binding.btnStartRecording.performClick()
                }, 4000)
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf (
                Manifest.permission.CAMERA,
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun getVideoBody(file: File): MultipartBody.Part {
        return MultipartBody.Part
            .createFormData(
                name = getString(R.string.form_data_video_key),
                filename = file.name,
                body = file.asRequestBody("video/mp4".toMediaType())
            )
    }

    private fun postVideoToServer(videoData: MultipartBody.Part){
        val result = aiViewModel.postPrediction(videoData, this)
        println(result)
    }

    private fun getFileFromContentUri(contentUri: Uri): File? {
        val filePathColumn = arrayOf(MediaStore.Video.Media.DATA)
        val cursor = contentResolver.query(contentUri, filePathColumn, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndex(filePathColumn[0])
                val filePath = it.getString(columnIndex)
                return File(filePath)
            }
        }
        return null
    }

    private fun moveToDetailActivity(resultWord: String) {
        val intent = Intent(this, CardSearchDetailActivity::class.java)
        intent.putExtra(getString(R.string.search_word), resultWord)
        startActivity(intent)
        finish()
    }

    private fun deleteVideo() {
        // 컨텐트 프로바이더를 통해 동영상 삭제
        val contentResolver: ContentResolver = applicationContext.contentResolver
        val rowsDeleted: Int = contentResolver.delete(videoPath!!, null, null)
        if (rowsDeleted > 0) {
            // 삭제 성공
            println("동영상이 성공적으로 삭제되었습니다.")
        } else {
            // 삭제 실패
            println("동영상 삭제에 실패했습니다.")
        }
    }
}