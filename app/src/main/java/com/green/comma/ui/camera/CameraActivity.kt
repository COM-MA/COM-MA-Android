package com.green.comma.ui.camera

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.media.ImageReader
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Size
import android.view.Surface
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.green.comma.ftlite.Classifier
import com.green.comma.ftlite.ConnectionCallback
import com.green.comma.ftlite.YuvToRgbConverter
import com.green.comma.R
import com.green.comma.databinding.ActivityCameraBinding
import java.io.IOException

class CameraActivity : AppCompatActivity() {
    private val binding by lazy { ActivityCameraBinding.inflate(layoutInflater, null, false) }
    private lateinit var classifier: Classifier
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                setFragment()
            } else {
                Toast.makeText(
                    this,
                    "permission denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    private var previewWidth = 0
    private var previewHeight = 0
    private var sensorOrientation = 0
    private var rgbFrameBitmap: Bitmap? = null
    private var isProcessingFrame = false
    private var handlerThread: HandlerThread? = null
    private var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        initClassifier()
        checkPermission()

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        handlerThread = HandlerThread("InferenceThread")
        handlerThread?.start()
        handler = Handler(handlerThread!!.looper)
    }

    override fun onPause() {
        handlerThread?.quitSafely()
        try {
            handlerThread?.join()
            handlerThread = null
            handler = null
        } catch (e: InterruptedException) {
            Toast.makeText(this, "activity onPause InterruptedException", Toast.LENGTH_SHORT).show()
        }
        super.onPause()
    }

    override fun onDestroy() {
        classifier.finish()
        super.onDestroy()
    }

    private fun initClassifier() {
        classifier = Classifier(this, Classifier.IMAGENET_CLASSIFY_MODEL)
        try {
            classifier.init()
        } catch (exception: IOException) {
            Toast.makeText(this, "Can not init Classifier!!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                setFragment()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                Toast.makeText(
                    this,
                    "This app need camera permission to classify realtime camera image",
                    Toast.LENGTH_SHORT
                ).show()
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
            else -> requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun setFragment() {
        val inputSize = classifier.getModelInputSize()
        val cameraId = chooseCamera()
        if (inputSize.width > 0 && inputSize.height > 0 && cameraId != null) {
            val fragment = CameraFragment.newInstance(object : ConnectionCallback {
                override fun onPreviewSizeChosen(size: Size, cameraRotation: Int) {
                    previewWidth = size.width
                    previewHeight = size.height
                    sensorOrientation = cameraRotation - getScreenOrientation()
                }
            }, {
                processImage(it)
            },
                inputSize,
                cameraId
            )
            supportFragmentManager.beginTransaction().replace(R.id.frame_camera, fragment).commit()
        } else {
            Toast.makeText(this, "Can not find camera", Toast.LENGTH_SHORT).show()
        }
    }

    private fun chooseCamera(): String? {
        val manager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            manager.cameraIdList.forEach { cameraId ->
                val characteristics = manager.getCameraCharacteristics(cameraId)
                val facing = characteristics.get(CameraCharacteristics.LENS_FACING)
                if (facing != null && facing == CameraCharacteristics.LENS_FACING_FRONT) {
                    return cameraId
                }
            }
        } catch (e: CameraAccessException) {
            Toast.makeText(this, "CameraAccessException", Toast.LENGTH_SHORT).show()
        }
        return null
    }

    private fun getScreenOrientation(): Int {
        val display = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.display
        } else {
            windowManager.defaultDisplay
        } ?: return 0
        return when (display.rotation) {
            Surface.ROTATION_270 -> 270
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_90 -> 90
            else -> 0
        }
    }

    private var count = 0
    private var recogCount = RecogCount()

    private fun processImage(reader: ImageReader) {
        if (previewWidth == 0 || previewHeight == 0) return
        if (rgbFrameBitmap == null) {
            rgbFrameBitmap = Bitmap.createBitmap(previewWidth, previewHeight, Bitmap.Config.ARGB_8888)
        }
        if (isProcessingFrame) return
        isProcessingFrame = true
        val image = reader.acquireLatestImage()
        if (image == null) {
            isProcessingFrame = false
            return
        }

        YuvToRgbConverter.yuvToRgb(this, image, rgbFrameBitmap!!)

        handler?.post {
            if (::classifier.isInitialized && classifier.isInitialized()) {
                val output = classifier.classify(rgbFrameBitmap!!, sensorOrientation)
                runOnUiThread {
                    count++
                    //binding.textResult.text = String.format(Locale.ENGLISH, "class : %s, prob : %.2f%%", output.first, output.second * 100)
                    recogCount.addCount(output.first)

                    if(count > 90){
                        println(count)
                        var intent = Intent(this, CameraLoadingDialogActivity::class.java)
                        startActivity(intent)
                        startActivityForResult(intent, 1)
                    }
                }
            }
            image.close()
            isProcessingFrame = false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK){
            var intent = Intent(this, CameraResultActivity::class.java)
            intent.putExtra("result", recogCount.maxCount())
            startActivity(intent)
            finish()
        }
    }

    class RecogCount {
        private var bear: Int = 0
        private var bee: Int = 0
        private var bridge: Int = 0
        private var sea: Int = 0
        private var hospital: Int = 0
        private var teacher: Int = 0
        private var kindergarten: Int = 0
        private var mom: Int = 0
        private var school: Int = 0
        private var playground: Int = 0

        private var maxCount = bear
        private var maxName: String = "곰"

        fun addCount(name: String){
            when(name) {
                "곰" -> {
                    bear++
                    compareMax(bear, "곰")
                }
                "벌" -> {
                    bee++
                    compareMax(bee, "벌")
                }
                "다리" -> {
                    bridge++
                    compareMax(bridge, "다리")
                }
                "바다" -> {
                    sea++
                    compareMax(sea, "바다")
                }
                "병원" -> {
                    hospital++
                    compareMax(hospital, "병원")
                }
                "선생님" -> {
                    teacher++
                    compareMax(teacher, "선생님")
                }
                "유치원" -> {
                    kindergarten++
                    compareMax(kindergarten, "유치원")
                }
                "엄마" -> {
                    mom++
                    compareMax(mom, "엄마")
                }
                "학교" -> {
                    school++
                    compareMax(school, "학교")
                }
                "놀이터" -> {
                    playground++
                    compareMax(playground, "놀이터")
                }
                else -> false
            }
        }

        fun maxCount(): String {
            return maxName
        }

        fun compareMax(count: Int, name: String){
            if(maxCount < count) {
                maxCount = count
                maxName = name
            }
        }
    }
}