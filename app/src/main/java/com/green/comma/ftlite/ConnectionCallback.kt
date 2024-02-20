package com.green.comma.ftlite

import android.util.Size

interface ConnectionCallback {
    fun onPreviewSizeChosen(size: Size, cameraRotation: Int)
}