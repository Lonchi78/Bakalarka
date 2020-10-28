package com.lonchi.andrej.lonchi_bakalarka.logic.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.exifinterface.media.ExifInterface
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

const val IMAGE_FORMAT_JPEG = ".jpeg"
const val IMAGE_COMPRESSION_LEVEL = 75

fun getTemporaryFile(): File {
    val fileName = UUID.randomUUID().toString()
    return createTempFile(fileName, IMAGE_FORMAT_JPEG)
}

fun Context.compressImageFromUri(originalImageUri: Uri): Uri? {
    var bos: ByteArrayOutputStream? = null
    var fos: FileOutputStream? = null

    return try {
        val tempCompressedFile = getTemporaryFile()
        tempCompressedFile.createNewFile()

        val bitmap = uriToBitmap(originalImageUri)
        bos = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, IMAGE_COMPRESSION_LEVEL, bos)
        Timber.d("CompressedCachedImageFromUri: Compressed image size = ${bos.size()} bytes")

        fos = FileOutputStream(tempCompressedFile)
        fos.write(bos.toByteArray())

        copyImageOrientation(originalImageUri, tempCompressedFile)
        Uri.fromFile(tempCompressedFile)
    } catch (e: Exception) {
        Timber.e(e)
        null
    } finally {
        bos?.flush()
        bos?.close()
        fos?.flush()
        fos?.close()
    }
}

fun Context.copyImageOrientation(inputUri: Uri, outputFile: File) {
    try {
        contentResolver.openInputStream(inputUri).use {
            it?.let { inputStream ->
                val oldExif = ExifInterface(inputStream)
                val newExif = ExifInterface(outputFile.absolutePath)
                val oldOrientation: String? = oldExif.getAttribute(ExifInterface.TAG_ORIENTATION)
                newExif.setAttribute(ExifInterface.TAG_ORIENTATION, oldOrientation)
                newExif.saveAttributes()
                inputStream.close()
            }
        }
    } catch (e: IOException) {
        Timber.e(e)
    } catch (e: Exception) {
        Timber.e(e)
    }
}

fun Context.uriToBitmap(uri: Uri): Bitmap? {
    try {
        val parcelFileDescriptor = this.contentResolver.openFileDescriptor(uri, "r")
        val fileDescriptor = parcelFileDescriptor?.fileDescriptor
        Timber.d("UriToBitmap: Original file size = ${parcelFileDescriptor?.statSize} bytes")

        return BitmapFactory.decodeFileDescriptor(fileDescriptor).also {
            parcelFileDescriptor?.close()
        }
    } catch (e: IOException) {
        Timber.e(e)
    }
    return null
}
