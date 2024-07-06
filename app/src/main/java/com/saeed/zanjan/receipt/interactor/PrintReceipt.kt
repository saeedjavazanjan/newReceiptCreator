package com.saeed.zanjan.receipt.interactor

import android.content.Context
import android.graphics.Bitmap
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import com.saeed.zanjan.receipt.domain.dataState.DataState
import com.saeed.zanjan.receipt.domain.models.GeneralReceipt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class PrintReceipt {



    fun print(
        context:Context,
        avatar:String,
        companyName:String,
        companyPhone:String,
        receiptCategory:Int,
        generalReceipt: GeneralReceipt
    ):Flow<DataState<String>> = flow {
        emit(DataState.loading())

        try {
            val printer = EscPosPrinter(BluetoothPrintersConnections.selectFirstPaired(), 203, 48f, 32)
            printer
                .printFormattedText(
                    """
       
        [L]
        [C]<u><font size='big'>${companyName}</font></u>
        [C]<u><font size='big'> ${companyName}</font></u>
        [L]
        [C]================================
        [L]
        [L]<b>BEAUTIFUL SHIRT</b>[R]9.99e
        [L]  + Size : S
        [L]
        [L]<b>AWESOME HAT</b>[R]24.99e
        [L]  + Size : 57/58
        [L]
        [C]--------------------------------
        [R]TOTAL PRICE :[R]34.98e
        [R]TAX :[R]4.23e
        [L]
        [C]================================
        [L]
        [L]<font size='tall'>Customer :</font>
        [L]Raymond DUPONT
        [L]5 rue des girafes
        [L]31547 PERPETES
        [L]Tel : +33801201456
        [L]
        [C]<barcode type='ean13' height='10'>831254784551</barcode>
        [C]<qrcode size='20'>https://dantsu.com/</qrcode>
        """.trimIndent()
                )
        }catch (e:Exception){

            emit(DataState.error(e.message.toString()))

        }

    }

}