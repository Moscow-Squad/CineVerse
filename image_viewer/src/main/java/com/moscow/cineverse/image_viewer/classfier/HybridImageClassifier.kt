package com.moscow.cineverse.image_viewer.classfier

import android.content.Context
import android.graphics.Bitmap
import androidx.core.graphics.scale
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

internal class HybridImageClassifier(context: Context, nsfwThreshold: Float = 0.2f) {

    private var interpreter: Interpreter? = null
    private var nfsw : Float = nsfwThreshold

    init {
        interpreter = Interpreter(loadModelFile(context))
    }

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
        val output = Array(1) { FloatArray(OUTPUT_CLASSES) }
        interpreter?.run(input, output)
        return isNSFW(output[0])
    }

    private fun preprocess(originalBitmap: Bitmap): ByteBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(4 * INPUT_SIZE * INPUT_SIZE * PIXEL_SIZE)
        byteBuffer.order(ByteOrder.nativeOrder())

        val scaledBitmap = originalBitmap.scale(INPUT_SIZE, INPUT_SIZE)

        val bitmap = if (scaledBitmap.config != Bitmap.Config.ARGB_8888) {
            scaledBitmap.copy(Bitmap.Config.ARGB_8888, false)
        } else {
            scaledBitmap
        }

        val intValues = IntArray(INPUT_SIZE * INPUT_SIZE)
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

        val sfwDrawing = drawings > hentai || hentai < NSFW_THRESHOLD
        val sfwPhoto = (neutral > porn || porn < NSFW_THRESHOLD) && (neutral > sexy || sexy < NSFW_THRESHOLD)
        return !(sfwPhoto && sfwDrawing)
    }

    companion object {
        private const val MODEL_NAME = "nsfw_model.tflite"
        private const val INPUT_SIZE = 224
        private const val PIXEL_SIZE = 3
        private const val OUTPUT_CLASSES = 5
        private const val NSFW_THRESHOLD = 0.2f
    }
}
