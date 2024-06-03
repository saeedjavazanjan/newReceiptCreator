package com.saeed.zanjan.receipt.interactor

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Picture
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import androidx.core.content.ContextCompat
import com.saeed.zanjan.receipt.domain.dataState.DataState
import com.saeed.zanjan.receipt.ui.theme.CustomColors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import kotlin.coroutines.resume

class ShareReceipt {




    fun shareReceipt(
        picture: Picture,
        context: Context
    ):Flow<DataState<String>> = flow <DataState<String>> {
        emit(DataState.loading())

        try{
            val bitmap = createBitmapFromPicture(picture)
            val uri = bitmap.saveToDisk(context)
            shareBitmap(context, uri)
            emit(DataState.success("ارسال موفق"))
        }catch (e:Exception){
            emit(DataState.error("خطا در اشتراک گذاری"))
        }

    }.flowOn(Dispatchers.IO)







    private fun createBitmapFromPicture(picture: Picture): Bitmap {
        val bitmap = Bitmap.createBitmap(
            picture.width,
            picture.height,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        canvas.drawColor(CustomColors.lightBlue.hashCode())
        canvas.drawPicture(picture)
        return bitmap
    }

    private suspend fun Bitmap.saveToDisk(context: Context): Uri {
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            "screenshot-${System.currentTimeMillis()}.png"
        )

        file.writeBitmap(this, Bitmap.CompressFormat.PNG, 100)

        return scanFilePath(context, file.path) ?: throw Exception("File could not be saved")
    }

    /**
     * We call [MediaScannerConnection] to index the newly created image inside MediaStore to be visible
     * for other apps, as well as returning its [MediaStore] Uri
     */
    private suspend fun scanFilePath(context: Context, filePath: String): Uri? {
        return suspendCancellableCoroutine { continuation ->
            MediaScannerConnection.scanFile(
                context,
                arrayOf(filePath),
                arrayOf("image/png")
            ) { _, scannedUri ->
                if (scannedUri == null) {
                    continuation.cancel(Exception("File $filePath could not be scanned"))
                } else {
                    continuation.resume(scannedUri)
                }
            }
        }
    }

    private fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
        outputStream().use { out ->
            bitmap.compress(format, quality, out)
            out.flush()
        }
    }

    private fun shareBitmap(context: Context, uri: Uri) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        ContextCompat.startActivity(context, Intent.createChooser(intent, "Share your image"), null)
    }








}