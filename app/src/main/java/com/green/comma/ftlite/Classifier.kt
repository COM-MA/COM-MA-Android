package com.green.comma.ftlite

import android.content.Context
import android.graphics.Bitmap
import android.util.Size
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.image.ops.Rot90Op
import org.tensorflow.lite.support.label.TensorLabel
import org.tensorflow.lite.support.model.Model
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class Classifier(private var context: Context, private val modelName: String) {
    private lateinit var model: Model
    private lateinit var inputImage: TensorImage
    private lateinit var outputBuffer: TensorBuffer
    private var modelInputChannel = 0
    private var modelInputWidth = 0
    private var modelInputHeight = 0
    private val labels = mutableListOf<String>()
    private var isInitialized = false

    fun init() {
        model = Model.createModel(context, modelName)
        initModelShape()
        labels.addAll(FileUtil.loadLabels(context, LABEL_FILE))
        println(labels)
        isInitialized = true
    }

    private fun initModelShape() {
        val inputTensor = model.getInputTensor(0)
        val inputShape = inputTensor.shape()
        modelInputChannel = inputShape[0]
        modelInputWidth = inputShape[1]
        modelInputHeight = inputShape[2]

        inputImage = TensorImage(inputTensor.dataType())
        println(inputTensor.dataType())

        val outputTensor = model.getOutputTensor(0)
        outputBuffer = TensorBuffer.createFixedSize(outputTensor.shape(), outputTensor.dataType())
    }

    fun classify(image: Bitmap, sensorOrientation: Int): Pair<String, Float> {
        inputImage = loadImage(image, sensorOrientation)
        val inputs = arrayOf<Any>(inputImage.buffer)
        val outputs = mutableMapOf<Int, Any>()
        outputs[0] = outputBuffer.buffer.rewind()
        model.run(inputs, outputs)
        val output = TensorLabel(labels, outputBuffer).mapWithFloatValue
        return argmax(output)
    }

    fun classify(image: Bitmap) = classify(image, 0)

    private fun loadImage(bitmap: Bitmap, sensorOrientation: Int): TensorImage {
        if (bitmap.config != Bitmap.Config.ARGB_8888) {
            inputImage.load(convertBitmapToARGB8888(bitmap))
        } else {
            inputImage.load(bitmap)
        }

        val cropSize = Math.min(bitmap.width, bitmap.height)
        val numRotation = sensorOrientation / 90

        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(2*modelInputHeight/3, modelInputWidth/2, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
            .add(Rot90Op(numRotation))
            .add(NormalizeOp(0.0f, 255.0f))
            .build()

        return imageProcessor.process(inputImage)
    }

    private fun convertBitmapToARGB8888(bitmap: Bitmap) = bitmap.copy(Bitmap.Config.ARGB_8888, true)

    private fun argmax(map: Map<String, Float>) =
        map.entries.maxByOrNull { it.value }?.let {
            it.key to it.value
        } ?: ("" to 0f)

    fun finish() {
        if (::model.isInitialized) model.close()
        if (isInitialized) isInitialized = false
    }

    fun isInitialized() = isInitialized

    fun getModelInputSize(): Size = if (isInitialized.not()) Size(0, 0) else Size(modelInputWidth, modelInputHeight)

    companion object {
        const val IMAGENET_CLASSIFY_MODEL = "model_3.tflite"
        const val LABEL_FILE = "labels33.txt"
    }
}
