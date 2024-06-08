package com.saeed.zanjan.receipt.utils

import android.content.Context
import android.database.Cursor
import android.os.Environment
import com.opencsv.CSVReader
import com.opencsv.CSVWriter
import com.saeed.zanjan.receipt.cash.ReceiptDao
import com.saeed.zanjan.receipt.cash.model.RepairsEntity
import com.saeed.zanjan.receipt.cash.model.RepairsEntityMapper
import com.saeed.zanjan.receipt.domain.dataState.DataState
import com.saeed.zanjan.receipt.domain.models.RepairsReceipt
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException

class CsvExportUtil(
    private val context: Context,
    val receiptDao: ReceiptDao,
    val mapper:RepairsEntityMapper,
) {

    fun exportDatabaseToCsv(receiptCategory: Int):File {

        val csvFile = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "receipts.csv")

        var csvWriter: CSVWriter? = null

        try {
            csvWriter = CSVWriter(FileWriter(csvFile))
            var cursor: Cursor? =null  // فرض کنید این متد تمام ردیف‌های جدول را باز می‌گرداند

            when(receiptCategory){
                0 -> {
                    //repair
                  cursor=receiptDao.getAllRepairsCursor()
                }

                1 -> {
                    //repair
                    cursor=receiptDao.getAllRepairsCursor()

                }

                2 -> {
                    //  repair
                    cursor=receiptDao.getAllRepairsCursor()
                }

                3 -> {
                    //tailoring
                    cursor=receiptDao.getAllTailoringCursor()

                }

                4 -> {
                    //jewelry
                    cursor=receiptDao.getAllJewelryCursor()

                }

                5 -> {
                    //photo
                    cursor=receiptDao.getAllPhotographyCursor()

                }

                6 -> {
                    //laundry
                    cursor=receiptDao.getAllLaundryCursor()

                }

                7 -> {
                    //confectionery
                    cursor=receiptDao.getAllConfectioneryCursor()

                }

                8 -> {
                    //otherJobs
                    cursor=receiptDao.getAllOtherJobsCursor()

                }

            }

            // نوشتن سطر عنوان‌ها
            csvWriter.writeNext(cursor!!.columnNames)

            // نوشتن داده‌های جدول
            while (cursor.moveToNext()) {
                val row = Array(cursor.columnCount) { i -> cursor.getString(i) }
                csvWriter.writeNext(row)
            }

            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                csvWriter?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return csvFile
    }

    suspend fun repairsImportCsvToDatabase(csvFilePath:String) {

        val csvFile=File(csvFilePath)

        val csvReader = CSVReader(FileReader(csvFile))

        val receipts = mutableListOf<RepairsReceipt>()
        csvReader.use { reader ->
            reader.readNext() // خواندن عنوان ستون‌ها

            var nextLine: Array<String>?
            while (reader.readNext().also { nextLine = it } != null) {
                // فرض کنید ستون‌های CSV با فیلدهای کلاس Receipt تطابق دارند
                val receipt = RepairsReceipt(
                    id = nextLine!![0].toInt(),
                    status = nextLine!![1].toInt(),
                    name = nextLine!![2],
                    phone=nextLine!![3],
                    loanerName=nextLine!![4],
                    loanerProblems=nextLine!![5],
                    risks =nextLine!![6],
                    deliveryTime=nextLine!![7],
                    receiptTime=nextLine!![8],
                    accessories=nextLine!![9],
                    cost=nextLine!![10],
                    prepayment=nextLine!![11]
                )
                receipts.add(receipt)
            }
        }
        val repairEntity = mutableListOf<RepairsEntity>()

       receipts.forEach {
            repairEntity.add(mapper.mapFromDomainModel(it))
        }
        receiptDao.insertAll(repairEntity)
    }
}
