package com.saeed.zanjan.receipt.interactor

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.saeed.zanjan.receipt.domain.dataState.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.InputStream
import java.io.OutputStream
import java.nio.charset.Charset
import java.util.UUID

class ConnectionClass {

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
                            result="پرینت موفق"
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
fun intentPrint(data:String, context: Context) :Flow<DataState<String>> = flow<DataState<String>> {
    emit(DataState.loading())

    if(printerName.trim().isNotEmpty()){
      val result= initPrinter(context)
        try {
            outputStream!!.write(data.toByteArray())
            outputStream!!.flush()
            outputStream!!.close()
            socket!!.close()

            emit(DataState.success(result))
        }catch (e:Exception){
            emit(DataState.error("ارتباط برقرار نشد"))
        }
    }else{
        emit(DataState.error("printer not found"))

    }
}.flowOn(Dispatchers.IO)


}