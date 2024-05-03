package com.green.comma.ui.common

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import com.green.comma.R
import com.green.comma.databinding.DialogLoadingBinding

class LoadingDialog(context: Context) : Dialog(context) {

    private var binding: DialogLoadingBinding
    private var currentIndex = 0
    private val handler = Handler(Looper.getMainLooper())
    private val images = listOf(R.drawable.img_loading_1, R.drawable.img_loading_2, R.drawable.img_loading_3)

    init {
        setCanceledOnTouchOutside(false)

        binding = DialogLoadingBinding.inflate(layoutInflater)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        startImageChanging()

        setContentView(binding.root)
    }

    private fun startImageChanging() {
        // 1초마다 이미지를 변경합니다.
        handler.postDelayed(object : Runnable {
            override fun run() {
                // 현재 인덱스에 해당하는 이미지를 이미지뷰에 설정합니다.
                binding.imageView.setImageResource(images[currentIndex])

                // 다음 인덱스로 이동합니다. 인덱스가 images 리스트의 크기를 넘어가면 처음으로 돌아갑니다.
                currentIndex = (currentIndex + 1) % images.size

                // 다음 이미지를 1초 후에 변경합니다.
                handler.postDelayed(this, 600)
            }
        }, 600) // 초기 딜레이는 1초로 설정합니다.
    }
}