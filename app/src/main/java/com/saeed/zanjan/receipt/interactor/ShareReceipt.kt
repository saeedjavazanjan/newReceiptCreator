package com.saeed.zanjan.receipt.interactor

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Picture
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                newAndroidShareBitmap(context, uri)
            else
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
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            saveImageToMediaStore(context)
        } else {
          //  saveImageToExternalStorageLegacy()
            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "screenshot-${System.currentTimeMillis()}.png"
            )

            file.writeBitmap(this, Bitmap.CompressFormat.PNG, 100)

            return scanFilePath(context, file.path) ?: throw Exception("File could not be saved")
        }
    }

    // روش جدید برای اندروید 10 به بعد (API 29+)
    private fun Bitmap.saveImageToMediaStore(context: Context): Uri {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "screenshot-${System.currentTimeMillis()}.png")
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/Receipts")
            put(MediaStore.Images.Media.IS_PENDING, 1) // در حال ذخیره‌سازی
        }

        val resolver = context.contentResolver
        val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            ?: throw Exception("Failed to create new MediaStore record.")

        resolver.openOutputStream(imageUri)?.use { outputStream ->
            this.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        }

        // ذخیره‌سازی را کامل کن
        contentValues.clear()
        contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
        resolver.update(imageUri, contentValues, null, null)

        return imageUri
    }

    // روش قدیمی برای اندروید 9 و پایین‌تر (API 28-)
   /* private suspend fun Bitmap.saveImageToExternalStorageLegacy(): Uri {
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            "screenshot-${System.currentTimeMillis()}.png"
        )

        file.writeBitmap(this, Bitmap.CompressFormat.PNG, 100)
        return Uri.fromFile(file)

    }*/



   /* private suspend fun Bitmap.saveToDisk(context: Context): Uri {
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            "screenshot-${System.currentTimeMillis()}.png"
        )

        file.writeBitmap(this, Bitmap.CompressFormat.PNG, 100)

        return scanFilePath(context, file.path) ?: throw Exception("File could not be saved")
    }
*/
    /**
     * We call [MediaScannerConnection] to index the newly created image inside MediaStore to be visible
     * for other apps, as well as returning its [MediaStore] Uri
     */
    /*private suspend fun scanFilePath(context: Context, filePath: String): Uri? {
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
    }*/
    private suspend fun scanFilePath(context: Context, filePath: String): Uri? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            null // در اندروید 10+ نیازی به اسکن نیست
        } else {
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
   private fun newAndroidShareBitmap(context: Context, uri: Uri) {
       val shareIntent = Intent(Intent.ACTION_SEND).apply {
           type = "image/png"
           val fileUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
               uri // نیازی به FileProvider نیست چون از MediaStore می‌آید
           } else {
               FileProvider.getUriForFile(context, "${context.packageName}.provider", File(uri.path!!))
           }
           putExtra(Intent.EXTRA_STREAM, fileUri)
           addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
       }
       ContextCompat.startActivity(context, Intent.createChooser(shareIntent, "Share your image"), null)
   }








}