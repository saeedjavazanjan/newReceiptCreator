package com.saeed.zanjan.receipt.interactor

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Picture
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import com.saeed.zanjan.receipt.domain.dataState.DataState
import com.saeed.zanjan.receipt.domain.models.GeneralReceipt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.nio.charset.Charset
import java.util.UUID
import kotlin.experimental.or

class BlueToothConnectionClass(
    sharedPreferences: SharedPreferences
) {
    val companyName=sharedPreferences.getString("COMPANY","")
    val companyPhone=sharedPreferences.getString("PHONE","")
    val receiptCategory=sharedPreferences.getInt("JOB_SUBJECT",-1)

    private var printerName=""
    var bluetoothDevice:BluetoothDevice?=null
    var socket: BluetoothSocket? = null
    var outputStream: OutputStream?=null
    var inputStream:InputStream?=null


    fun setPrinterName(name:String){
        printerName=name
    }

    fun listenForData(): Flow<DataState<String>> = flow<DataState<String>>{
        try {

            val delimiter: Byte = 10
            var readBufferPosition = 0
            var readBuffer: ByteArray = ByteArray(1024)

            val bytesAvailable = inputStream!!.available()

            if (bytesAvailable > 0) {
                val packetBytes = ByteArray(bytesAvailable)
                inputStream!!.read(packetBytes)

                for (i in 0 until bytesAvailable) {
                    val b = packetBytes[i]
                    if (b == delimiter) {
                        val encodeBytes = ByteArray(readBufferPosition)
                        System.arraycopy(
                            readBuffer, 0,
                            encodeBytes, 0,
                            encodeBytes.size
                        )

                        val data = String(encodeBytes, Charset.forName("US-ASCII"))
                        readBufferPosition = 0
                    } else {
                        readBuffer[readBufferPosition++] = b
                    }
                }

            }

        } catch (e: Exception) {
            e.printStackTrace()
        }


    }.flowOn(Dispatchers.IO)

    @SuppressLint("MissingPermission")
    fun initPrinter(context:Context):String {
        val blueToothManager: BluetoothManager = context.getSystemService(BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter?=blueToothManager.adapter
        var result=""
        try {

            val pairedDevices=bluetoothAdapter?.bondedDevices

            if(pairedDevices !=null){
                if(pairedDevices.size >0){
                    for (device in pairedDevices){
                        if(device.name==printerName){
                            bluetoothDevice=device
                            val uuid=UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
                            val m=bluetoothDevice!!.javaClass.getMethod(
                                "createRfcommSocket",*arrayOf<Class<*>?>(
                                    Int::class.javaPrimitiveType
                                )
                            )
                            socket=m.invoke(bluetoothDevice,1) as BluetoothSocket
                            bluetoothAdapter.cancelDiscovery()
                            socket!!.connect()
                            outputStream=socket!!.outputStream
                            inputStream=socket!!.inputStream
                            listenForData()
                            result="دستگاه متصل شد"
                            break

                        }


                    }
                }

            }else{
               // Toast.makeText(context,"notdevice found", Toast.LENGTH_SHORT).show()
                result="دستگاه پیدا نشد"
            }

        }catch (e:java.lang.Exception){
          //  Toast.makeText(context,"not connected", Toast.LENGTH_SHORT).show()
            result="دستگاه متصل نیست"

        }
        return result
    }

@SuppressLint("SuspiciousIndentation")
fun intentPrint(generalReceipt: GeneralReceipt, context: Context): Flow<DataState<String>> = flow {
    emit(DataState.loading())
    val data = stringOfReceipt(generalReceipt)
    val dataBytes = data.toByteArray(Charsets.UTF_8)

  /*  val escPosInit = byteArrayOf(0x1B, 0x40) // Initialize printer
    val escPosSelectCharset = byteArrayOf(0x1B, 0x74, 0x10) // Select charset UTF-8

    val escPosInit = byteArrayOf(0x1B, 0x40) // Initialize printer*/

    // تبدیل HTML به بایت‌ها

    val htmlData = htmlStringOfReceipt(generalReceipt)
   // val htmlData = "<html><body><h1>Hello, WebView!</h1></body></html>"

    if (printerName.trim().isNotEmpty()) {
        val result = initPrinter(context)
        try {
            Log.i("PRINTER",htmlData)
            outputStream!!.write(data.toByteArray())
            outputStream!!.flush()
            emit(DataState.success(result))
        } catch (e: Exception) {
            emit(DataState.error("ارتباط برقرار نشد"))
        } finally {
            try {
                outputStream?.close()
                socket?.close()
                Log.i("PRINTER", "OutputStream و Socket بسته شدند")
            } catch (e: IOException) {
                Log.e("PRINTER", "خطا در بستن OutputStream یا Socket", e)
            }
        }
    } else {
        emit(DataState.error("پرینتر یافت نشد"))
    }
}

fun stringOfReceipt(generalReceipt: GeneralReceipt):String{
    val separator = System.getProperty("line.separator")

    when (receiptCategory) {
        0 -> {
            //repair
            Log.i("PRINTER","stringcreated")
            return  StringBuilder().apply {
                append(companyName?.padStart(40) ?: "" ).append(separator)
                append("نام کالا: ${generalReceipt.orderName}".padStart(40)).append(separator)
                append("ایرادات: ${generalReceipt.repairLoanerProblems}".padStart(40)).append(separator)
                append("خطرات: ${generalReceipt.repairRisks}".padStart(40)).append(separator)
                append("موعد تحویل: ${generalReceipt.deliveryTime}".padStart(40)).append(separator)
                append("لوازم همراه: ${generalReceipt.repairAccessories}".padStart(40)).append(separator)
                append(separator)
                append("جمع هزینه: ${generalReceipt.cost} تومان".padStart(40)).append(separator)
                append("پرداخت شده: ${generalReceipt.prepayment} تومان".padStart(40)).append(separator)
                append("شماره رسید: ${generalReceipt.id}".padStart(40)).append(separator)
                append("------------------------------------".padStart(40)).append(separator)
                append("شماره تماس: ${companyPhone}".padStart(40)).append(separator)
            }.toString()


        /* companyName+ System.getProperty("line.separator") +
                    "نام کالا:" + generalReceipt.orderName + System.getProperty("line.separator") +
                    "ایرادات:" + generalReceipt.repairLoanerProblems + System.getProperty("line.separator") +
                    "خطرات:" + generalReceipt.repairRisks + System.getProperty("line.separator") +
                    "موعد تحویل:" + generalReceipt.deliveryTime + System.getProperty("line.separator")+
                    "لوازم همراه:" + generalReceipt.repairAccessories + System.getProperty("line.separator") +
                    System.getProperty("line.separator") +
                    "جمع هزینه:" + generalReceipt.cost +" تومان" + System.getProperty("line.separator") +
                    "پرداخت شده:" + generalReceipt.prepayment+" تومان" + System.getProperty("line.separator") +
                    "شماره رسید:" + generalReceipt.id+ System.getProperty("line.separator") +
                    "------------------------------------"+
                    System.getProperty("line.separator")+
                    " شماره تماس: " + companyPhone*/
        }

        1 -> {
            //repair
            return   companyName+ System.getProperty("line.separator") +
                    "نام کالا:" + generalReceipt.orderName + System.getProperty("line.separator") +
                    "ایرادات:" + generalReceipt.repairLoanerProblems + System.getProperty("line.separator") +
                    "خطرات:" + generalReceipt.repairRisks + System.getProperty("line.separator") +
                    "موعد تحویل:" + generalReceipt.deliveryTime + System.getProperty("line.separator")+
                    "لوازم همراه:" + generalReceipt.repairAccessories + System.getProperty("line.separator") +
                    System.getProperty("line.separator") +
                    "جمع هزینه:" + generalReceipt.cost +" تومان" + System.getProperty("line.separator") +
                    "پرداخت شده:" + generalReceipt.prepayment+" تومان" + System.getProperty("line.separator") +
                    "شماره رسید:" + generalReceipt.id+ System.getProperty("line.separator") +
                    "------------------------------------"+
                    System.getProperty("line.separator")+
                    " شماره تماس: " + companyPhone
        }

        2 -> {
            //  repair
            return   companyName+ System.getProperty("line.separator") +
                    "نام کالا:" + generalReceipt.orderName + System.getProperty("line.separator") +
                    "ایرادات:" + generalReceipt.repairLoanerProblems + System.getProperty("line.separator") +
                    "خطرات:" + generalReceipt.repairRisks + System.getProperty("line.separator") +
                    "موعد تحویل:" + generalReceipt.deliveryTime + System.getProperty("line.separator")+
                    "لوازم همراه:" + generalReceipt.repairAccessories + System.getProperty("line.separator") +
                    System.getProperty("line.separator") +
                    "جمع هزینه:" + generalReceipt.cost +" تومان" + System.getProperty("line.separator") +
                    "پرداخت شده:" + generalReceipt.prepayment+" تومان" + System.getProperty("line.separator") +
                    "شماره رسید:" + generalReceipt.id+ System.getProperty("line.separator") +
                    "------------------------------------"+
                    System.getProperty("line.separator")+
                    " شماره تماس: " + companyPhone
        }

        3 -> {
            //tailoring
          return  companyName+ System.getProperty("line.separator") +
                    "نام سفارش:" + generalReceipt.orderName + System.getProperty("line.separator") +
                    "مشخصات:" + generalReceipt.tailoringOrderSpecification + System.getProperty("line.separator") +
                    "موعد تحویل:" + generalReceipt.deliveryTime + System.getProperty("line.separator") +
                    System.getProperty("line.separator") +
                    "جمع هزینه:" + generalReceipt.cost+" تومان" + System.getProperty("line.separator") +
                    "پرداخت شده:" + generalReceipt.prepayment+ " تومان" +System.getProperty("line.separator") +
                    "شماره رسید:" + generalReceipt.id+ System.getProperty("line.separator") +
                  "------------------------------------"+
                  System.getProperty("line.separator")+
                  " شماره تماس: " + companyPhone

        }

        4 -> {
            //jewelry
           return   companyName+ System.getProperty("line.separator") +
                   "نام سفارش:" + generalReceipt.orderName + System.getProperty("line.separator") +
                   "مشخصات کالا:" + generalReceipt.jewelryLoanerSpecification + System.getProperty("line.separator") +
                   "مشخصات سفارش:" + generalReceipt.jewelryOrderSpecification + System.getProperty("line.separator") +
                   "مشکلات:" + generalReceipt.jewelryLoanerProblems + System.getProperty("line.separator") +
                   "موعد تحویل:" +generalReceipt.deliveryTime  + System.getProperty("line.separator") +
                   System.getProperty("line.separator") +
                   "جمع هزینه:" + generalReceipt.cost +" تومان" + System.getProperty("line.separator") +
                   "پرداخت شده:" + generalReceipt.prepayment+" تومان" + System.getProperty("line.separator") +
                   "شماره رسید:" + generalReceipt.id+ System.getProperty("line.separator") +
                   "------------------------------------"+
                   System.getProperty("line.separator")+
                   " شماره تماس: " + companyPhone
        }

        5 -> {
            //photo
            return companyName+ System.getProperty("line.separator") +
                    "عنوان سفارش:" + generalReceipt.orderName + System.getProperty("line.separator") +
                    "تعداد:" + generalReceipt.photographyOrderNumber + System.getProperty("line.separator") +
                    "اندازه :" + generalReceipt.photographyOrderSize + System.getProperty("line.separator") +
                    "موعد تحویل:" + generalReceipt.deliveryTime + System.getProperty("line.separator") +
                    System.getProperty("line.separator") +
                    "جمع هزینه:" + generalReceipt.cost +" تومان" + System.getProperty("line.separator") +
                    "پرداخت شده:"+ generalReceipt.prepayment+" تومان" + System.getProperty("line.separator") +
                    "شماره رسید:" + generalReceipt.id+ System.getProperty("line.separator") +
                    "------------------------------------"+
                    System.getProperty("line.separator")+
                    " شماره تماس: " + companyPhone
        }

        6 -> {
            //laundry
          return companyName+ System.getProperty("line.separator") +
                  "نام کالا:" + generalReceipt.orderName + System.getProperty("line.separator") +
                  "نوع سفارش:" + generalReceipt.laundryOrderType + System.getProperty("line.separator") +
                  "توضیحات:" + generalReceipt.laundryDescription + System.getProperty("line.separator") +
                  "موعد تحویل:" + generalReceipt.deliveryTime + System.getProperty("line.separator") +
                  System.getProperty("line.separator") +
                  "جمع هزینه:" + generalReceipt.cost +" تومان" + System.getProperty("line.separator") +
                  "پرداخت شده:" + generalReceipt.prepayment+" تومان" + System.getProperty("line.separator") +
                  "شماره رسید:" + generalReceipt.id+ System.getProperty("line.separator") +
                  "------------------------------------"+
                  System.getProperty("line.separator")+
                  " شماره تماس: " + companyPhone

        }

        7 -> {
            //confectionery
            return  companyName+ System.getProperty("line.separator") +
                    "نام سفارش:" + generalReceipt.orderName + System.getProperty("line.separator") +
                    "مشخصات:" + generalReceipt.confectioneryOrderSpecification + System.getProperty("line.separator") +
                    "توضیحات:" + generalReceipt.confectioneryDescription + System.getProperty("line.separator") +
                    "وزن:" + generalReceipt.confectioneryOrderWeight + System.getProperty("line.separator") +
                    "موعد تحویل:" + generalReceipt.deliveryTime + System.getProperty("line.separator") +
                    System.getProperty("line.separator") +
                    "جمع هزینه:" + generalReceipt.cost +" تومان" + System.getProperty("line.separator") +
                    "پرداخت شده:" + generalReceipt.prepayment+" تومان" + System.getProperty("line.separator") +
                    "شماره رسید:" + generalReceipt.id+ System.getProperty("line.separator") +
                    "------------------------------------"+
                    System.getProperty("line.separator")+
                    " شماره تماس: " + companyPhone
        }

        8 -> {
            //otherJobs
          return companyName+ System.getProperty("line.separator") +
                  "عنوان سفارش:" + generalReceipt.orderName + System.getProperty("line.separator") +
                  "تعداد:" + generalReceipt.otherJobsOrderNumber + System.getProperty("line.separator") +
                  "توضیحات:" + generalReceipt.otherJobsDescription + System.getProperty("line.separator") +
                  "موعد تحویل:" + generalReceipt.deliveryTime + System.getProperty("line.separator") +
                  System.getProperty("line.separator") +
                  "جمع هزینه:" + generalReceipt.cost +" تومان" + System.getProperty("line.separator") +
                  "پرداخت شده:" + generalReceipt.prepayment+" تومان" + System.getProperty("line.separator") +
                  "شماره رسید:" + generalReceipt.id+ System.getProperty("line.separator") +
                  "------------------------------------"+
                  System.getProperty("line.separator")+
                  " شماره تماس: " + companyPhone

        }
        else->{
            return "رسید یافت نشد"
        }

    }



}
    fun htmlStringOfReceipt(generalReceipt: GeneralReceipt): String {

        return """
        <html>
            <body style="text-align: right; direction: rtl;">
                <div>${companyName ?: ""}</div>
                <div>نام کالا: ${generalReceipt.orderName}</div>
                <div>ایرادات: ${generalReceipt.repairLoanerProblems}</div>
                <div>خطرات: ${generalReceipt.repairRisks}</div>
                <div>موعد تحویل: ${generalReceipt.deliveryTime}</div>
                <div>لوازم همراه: ${generalReceipt.repairAccessories}</div>
                <div>جمع هزینه: ${generalReceipt.cost} تومان</div>
                <div>پرداخت شده: ${generalReceipt.prepayment} تومان</div>
                <div>شماره رسید: ${generalReceipt.id}</div>
                <div>------------------------------------</div>
                <div>شماره تماس: ${companyPhone}</div>
            </body>
        </html>
    """.trimIndent()

    }
    suspend fun convertHtmlToBitmap(context: Context, html: String, callback: (Bitmap?) -> Unit) {
        withContext(Dispatchers.Main) {
            try {
                // Save HTML to file
                val file = saveHtmlToFile(context, html)
                val fileUrl = "file://${file.absolutePath}"

                // Create a WebView
                val webView = WebView(context)
                webView.settings.javaScriptEnabled = true
                webView.settings.loadWithOverviewMode = true
                webView.settings.useWideViewPort = true
                webView.settings.domStorageEnabled = true

                // Add WebView to a FrameLayout
                val frameLayout = FrameLayout(context)
                frameLayout.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                frameLayout.addView(webView)
                frameLayout.visibility = ViewGroup.INVISIBLE

                // Add the FrameLayout to the activity's root view
                val rootView = (context as Activity).window.decorView.rootView as ViewGroup
                rootView.addView(frameLayout)

                webView.webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            webView.measure(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                            webView.layout(0, 0, webView.measuredWidth, webView.measuredHeight)
                            webView.buildDrawingCache()

                            val width = webView.measuredWidth
                            val height = webView.measuredHeight

                            if (width > 0 && height > 0) {
                                val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                                val canvas = Canvas(bitmap)
                                webView.draw(canvas)
                                callback(bitmap)
                            } else {
                                callback(null)
                            }

                            // Remove the FrameLayout from the root view
                            rootView.removeView(frameLayout)
                        }, 1000)
                    }
                }

                // Load the HTML file
                webView.loadUrl(fileUrl)
            } catch (e: Exception) {
                Log.e("PRINTER", "Error converting HTML to bitmap: ${e.message}")
                callback(null)
            }
        }
    }
    fun printBitmap(bitmap: Bitmap) {
        try {
            // تبدیل Bitmap به بایت‌ها با استفاده از دستورات ESC/POS
            val width = bitmap.width
            val height = bitmap.height
            val widthBytes = (width + 7) / 8

            val data = ByteArray(widthBytes * height)
            for (y in 0 until height) {
                for (x in 0 until width) {
                    if (bitmap.getPixel(x, y) != 0) {
                        val byteIndex = x / 8
                        val bitIndex = x % 8
                        data[y * widthBytes + byteIndex] = (data[y * widthBytes + byteIndex] or (128 shr bitIndex).toByte())
                    }
                }
            }

            val escPosCommand = ByteArrayOutputStream()
            escPosCommand.write(byteArrayOf(0x1B, 0x40)) // Initialize printer
            escPosCommand.write(byteArrayOf(0x1B, 0x33, 0x00)) // Set line spacing to 0
            for (y in 0 until height) {
                escPosCommand.write(byteArrayOf(0x1B, 0x2A, 33.toByte(), (widthBytes % 256).toByte(), (widthBytes / 256).toByte()))
                escPosCommand.write(data, y * widthBytes, widthBytes)
                escPosCommand.write(byteArrayOf(0x0A)) // Line feed
            }
            escPosCommand.write(byteArrayOf(0x1B, 0x64, 4.toByte())) // Feed 4 lines

            outputStream!!.write(escPosCommand.toByteArray())
            outputStream!!.flush()
            Log.i("PRINTER", "Bitmap printed successfully")
        } catch (e: Exception) {
            Log.e("PRINTER", "Error printing bitmap: ${e.message}")
        }
    }
    fun saveHtmlToFile(context: Context, html: String): File {
        val fileName = "temp.html"
        val file = File(context.cacheDir, fileName)
        FileOutputStream(file).use { outputStream ->
            outputStream.write(html.toByteArray())
        }
        Log.i("PRINTER", "file Generated")

        return file
    }

    fun pictureToBitmap(picture: Picture, width: Int, height: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        picture.draw(canvas)
        return bitmap
    }

    fun printPicture(picture: Picture) {
        val bitmap = pictureToBitmap(picture, picture.width, picture.height)
        printBitmap(bitmap)
    }

}
   /* fun htmlStringOfReceipt(generalReceipt: GeneralReceipt): String {
        return """
        <!DOCTYPE html>
        <html lang="fa">
        <head>
            <meta charset="UTF-8">
            <style>
                body { direction: rtl; font-family: Arial, sans-serif; }
                .receipt-container { width: 80mm; }
                .header { text-align: center; }
                .section { margin: 10px 0; }
                .footer { text-align: center; margin-top: 20px; }
            </style>
        </head>
        <body>
            <div class="receipt-container">
                <div class="header">
                    <h2>${companyName ?: ""}</h2>
                    <p>شماره تماس: ${companyPhone ?: ""}</p>
                </div>
                <div class="section">
                    <p>نام کالا: ${generalReceipt.orderName}</p>
                    <p>ایرادات: ${generalReceipt.repairLoanerProblems}</p>
                    <p>خطرات: ${generalReceipt.repairRisks}</p>
                    <p>موعد تحویل: ${generalReceipt.deliveryTime}</p>
                    <p>لوازم همراه: ${generalReceipt.repairAccessories}</p>
                </div>
                <div class="section">
                    <p>جمع هزینه: ${generalReceipt.cost} تومان</p>
                    <p>پرداخت شده: ${generalReceipt.prepayment} تومان</p>
                    <p>شماره رسید: ${generalReceipt.id}</p>
                </div>
                <div class="footer">
                    <p>------------------------------------</p>
                </div>
            </div>
        </body>
        </html>
    """
    }*/
/*
    fun printBitmap(bitmap: Bitmap, outputStream: OutputStream) {
        try {
            saveBitmapAsImageFile(bitmap, Bitmap.CompressFormat.JPEG, outputStream)

            // تبدیل Bitmap به بایت‌ها با استفاده از دستورات ESC/POS
            val width = bitmap.width
            val height = bitmap.height
            val widthBytes = (width + 7) / 8

            val data = ByteArray(widthBytes * height)
            for (y in 0 until height) {
                for (x in 0 until width) {
                    if (bitmap.getPixel(x, y) != 0) {
                        val byteIndex = x / 8
                        val bitIndex = x % 8
                        data[y * widthBytes + byteIndex] = (data[y * widthBytes + byteIndex] or ((128 shr bitIndex).toByte())).toByte()
                    }
                }
                Log.i("PRINTER","successprintbitmap1")

            }

            val escPosCommand = ByteArrayOutputStream()
            escPosCommand.write(byteArrayOf(0x1B, 0x40)) // Initialize printer
            escPosCommand.write(byteArrayOf(0x1B, 0x33, 0x00)) // Set line spacing to 0
            for (y in 0 until height) {
                escPosCommand.write(byteArrayOf(0x1B, 0x2A, 33.toByte(), (widthBytes % 256).toByte(), (widthBytes / 256).toByte()))
                escPosCommand.write(data, y * widthBytes, widthBytes)
                escPosCommand.write(byteArrayOf(0x0A)) // Line feed
                Log.i("PRINTER","successprintbitmap2")

            }
            escPosCommand.write(byteArrayOf(0x1B, 0x64, 4.toByte())) // Feed 4 lines

            outputStream.write(escPosCommand.toByteArray())
            outputStream.flush()
            Log.i("PRINTER","successprintbitmap")

        }catch (e:Exception){
            Log.e("PRINTER",e.message.toString())
        }

    }}
*/

   /* suspend fun convertHtmlToBitmap(context: Context, html: String, callback: (Bitmap?) -> Unit) {
        try {
            withContext(Dispatchers.Main) {

            val webView = WebView(context)
            webView.settings.javaScriptEnabled = true

            webView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        val bitmap = Bitmap.createBitmap(50, 100, Bitmap.Config.ARGB_8888)
                        val canvas = Canvas(bitmap)
                        webView.draw(canvas)
                        callback(bitmap)
                    }, 1000) // تاخیر کوچک برای اطمینان از اینکه صفحه به طور کامل بارگذاری شده است
                }
            }
            webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null)
            Log.i("PRINTER","successconverttobitmap")
            }
        }catch (e:Exception){
            Log.e("PRINTER",e.message.toString())

        }

    }*/


/*
    fun printBitmap(bitmap: Bitmap, outputStream: OutputStream) {
        try {
            // تبدیل Bitmap به فرمت JPEG و ذخیره آن در OutputStream
            saveBitmapAsImageFile(bitmap, Bitmap.CompressFormat.JPEG, outputStream)

            // شما می‌توانید سایر دستورات ESC/POS را برای چاپ بیتمپ به پرینتر اضافه کنید
            Log.i("PRINTER", "Bitmap printed successfully")
        } catch (e: Exception) {
            Log.e("PRINTER", "Error printing bitmap: ${e.message}")
        }
    }
*/
   /* fun saveBitmapAsImageFile(bitmap: Bitmap, format: Bitmap.CompressFormat, output: OutputStream) {
        try {
            val success = bitmap.compress(format, 100, output)
            if (!success) {
                throw IOException("Failed to save bitmap as image file")
            }
            outputStream!!.flush()
            Log.i("PRINTER", "Image file saved successfully")
        } catch (e: IOException) {
            Log.e("PRINTER", "Error saving image file: ${e.message}")
        }
    }
}*/