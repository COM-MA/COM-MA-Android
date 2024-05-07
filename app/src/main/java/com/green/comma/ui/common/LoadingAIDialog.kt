package com.green.comma.ui.common

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.green.comma.databinding.DialogAiLoadingBinding

class LoadingAIDialog(context: Context) : Dialog(context) {

    private var binding: DialogAiLoadingBinding

    init {
        setCanceledOnTouchOutside(false)

        binding = DialogAiLoadingBinding.inflate(layoutInflater)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setContentView(binding.root)
    }
}