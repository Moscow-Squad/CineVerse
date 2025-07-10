package com.example.image_viewer.component.classfier

import android.content.Context
import android.graphics.Bitmap
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import androidx.core.graphics.scale

class NSFWClassifier(context: Context) {
    private val interpreter: Interpreter

    init {
        interpreter = Interpreter(loadModelFile(context))
    }

    @Throws(IOException::class)
    private fun loadModelFile(context: Context): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd(MODEL_NAME)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    fun classifyImage(bitmap: Bitmap): Boolean {
        val input = preprocess(bitmap)
        val output = Array(1) { FloatArray(5) }  // Binary output

        interpreter.run(input, output)
        return isNSFW(output[0])
    }

    private fun preprocess(originalBitmap: Bitmap): ByteBuffer {
        val inputSize = 224
        val byteBuffer = ByteBuffer.allocateDirect(4 * inputSize * inputSize * 3)
        byteBuffer.order(ByteOrder.nativeOrder())
        val bitmap = originalBitmap.scale(inputSize, inputSize)

        val intValues = IntArray(inputSize * inputSize)
        bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        for (pixelValue in intValues) {
            val r = (pixelValue shr 16 and 0xFF) / 255.0f
            val g = (pixelValue shr 8 and 0xFF) / 255.0f
            val b = (pixelValue and 0xFF) / 255.0f
            byteBuffer.putFloat(r)
            byteBuffer.putFloat(g)
            byteBuffer.putFloat(b)
        }
        return byteBuffer
    }

    private fun isNSFW(output: FloatArray): Boolean {
        val drawings = output[0]
        val hentai = output[1]
        val neutral = output[2]
        val porn = output[3]
        val sexy = output[4]

        val sfwDrawing = drawings > hentai || hentai < 0.5
        val sfwPhoto = (neutral > porn || porn < 0.5) && (neutral > sexy || sexy < 0.5)

        return !(sfwPhoto and sfwDrawing)
    }

    companion object{
        const val MODEL_NAME = "nsfw_model.tflite"
    }
}