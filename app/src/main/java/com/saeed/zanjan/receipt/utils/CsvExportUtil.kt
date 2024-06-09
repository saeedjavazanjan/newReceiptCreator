package com.saeed.zanjan.receipt.utils

import android.content.Context
import android.database.Cursor
import android.os.Environment
import com.opencsv.CSVReader
import com.opencsv.CSVWriter
import com.saeed.zanjan.receipt.cash.ReceiptDao
import com.saeed.zanjan.receipt.cash.model.ConfectioneryEntity
import com.saeed.zanjan.receipt.cash.model.ConfectioneryEntityMapper
import com.saeed.zanjan.receipt.cash.model.JewelryEntity
import com.saeed.zanjan.receipt.cash.model.JewelryEntityMapper
import com.saeed.zanjan.receipt.cash.model.LaundryEntity
import com.saeed.zanjan.receipt.cash.model.LaundryEntityMapper
import com.saeed.zanjan.receipt.cash.model.OtherJobsEntity
import com.saeed.zanjan.receipt.cash.model.OtherJobsEntityMapper
import com.saeed.zanjan.receipt.cash.model.PhotographyEntity
import com.saeed.zanjan.receipt.cash.model.PhotographyEntityMapper
import com.saeed.zanjan.receipt.cash.model.RepairsEntity
import com.saeed.zanjan.receipt.cash.model.RepairsEntityMapper
import com.saeed.zanjan.receipt.cash.model.TailoringEntity
import com.saeed.zanjan.receipt.cash.model.TailoringEntityMapper
import com.saeed.zanjan.receipt.domain.dataState.DataState
import com.saeed.zanjan.receipt.domain.models.ConfectioneryReceipt
import com.saeed.zanjan.receipt.domain.models.JewelryReceipt
import com.saeed.zanjan.receipt.domain.models.LaundryReceipt
import com.saeed.zanjan.receipt.domain.models.OtherJobsReceipt
import com.saeed.zanjan.receipt.domain.models.PhotographyReceipt
import com.saeed.zanjan.receipt.domain.models.RepairsReceipt
import com.saeed.zanjan.receipt.domain.models.TailoringReceipt
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException

class CsvExportUtil(
    private val context: Context,
    private val receiptDao: ReceiptDao,
    private val confectioneryEntityMapper: ConfectioneryEntityMapper,
    private val jewelryEntityMapper: JewelryEntityMapper,
    private val laundryEntityMapper: LaundryEntityMapper,
    private val otherJobsEntityMapper: OtherJobsEntityMapper,
    private val photographyEntityMapper: PhotographyEntityMapper,
    private val repairsEntityMapper:RepairsEntityMapper,
    private val tailoringEntityMapper: TailoringEntityMapper,

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

    suspend fun confectioneryImportCsvToDatabase(csvFilePath:String) {

        val csvFile=File(csvFilePath)

        val csvReader = CSVReader(FileReader(csvFile))

        val receipts = mutableListOf<ConfectioneryReceipt>()
        csvReader.use { reader ->
            reader.readNext()
            var nextLine: Array<String>?
            while (reader.readNext().also { nextLine = it } != null) {
                val receipt = ConfectioneryReceipt(
                    id = nextLine!![0].toInt(),
                    status = nextLine!![1].toInt(),
                    name = nextLine!![2],
                    phone=nextLine!![3],
                    orderName  =nextLine!![4],
                    orderSpecification =nextLine!![5],
                    orderWeight  =nextLine!![6],
                    description =nextLine!![7],
                    deliveryTime=nextLine!![8],
                    receiptTime=nextLine!![9],
                    cost=nextLine!![10],
                    prepayment=nextLine!![11]
                )
                receipts.add(receipt)
            }
        }
        val confectioneryEntity = mutableListOf<ConfectioneryEntity>()

       receipts.forEach {
           confectioneryEntity.add(confectioneryEntityMapper.mapFromDomainModel(it))
        }
        receiptDao.insertAllConfectionery(confectioneryEntity)
    }
    suspend fun jewelryImportCsvToDatabase(csvFilePath:String) {

        val csvFile=File(csvFilePath)

        val csvReader = CSVReader(FileReader(csvFile))

        val receipts = mutableListOf<JewelryReceipt>()
        csvReader.use { reader ->
            reader.readNext()
            var nextLine: Array<String>?
            while (reader.readNext().also { nextLine = it } != null) {
                val receipt = JewelryReceipt(
                    id = nextLine!![0].toInt(),
                    status = nextLine!![1].toInt(),
                    name = nextLine!![2],
                    phone=nextLine!![3],
                    loanerName=nextLine!![4],
                    orderSpecification =nextLine!![5],
                    loanerProblems  =nextLine!![6],
                    loanerSpecification = nextLine!![7],
                    deliveryTime=nextLine!![8],
                    receiptTime=nextLine!![9],
                    cost=nextLine!![10],
                    prepayment=nextLine!![11]
                )
                receipts.add(receipt)
            }
        }
        val jewelryEntity = mutableListOf<JewelryEntity>()

       receipts.forEach {
            jewelryEntity.add(jewelryEntityMapper.mapFromDomainModel(it))
        }
        receiptDao.insertAllJewelry(jewelryEntity)
    }
    suspend fun laundryImportCsvToDatabase(csvFilePath:String) {

        val csvFile=File(csvFilePath)

        val csvReader = CSVReader(FileReader(csvFile))

        val receipts = mutableListOf<LaundryReceipt>()
        csvReader.use { reader ->
            reader.readNext() // خواندن عنوان ستون‌ها

            var nextLine: Array<String>?
            while (reader.readNext().also { nextLine = it } != null) {
                // فرض کنید ستون‌های CSV با فیلدهای کلاس Receipt تطابق دارند
                val receipt = LaundryReceipt(
                    id = nextLine!![0].toInt(),
                    status = nextLine!![1].toInt(),
                    name = nextLine!![2],
                    phone=nextLine!![3],
                    loanerName=nextLine!![4],
                    orderType =nextLine!![5],
                    description  =nextLine!![6],
                    deliveryTime=nextLine!![7],
                    receiptTime=nextLine!![8],
                    cost=nextLine!![9],
                    prepayment=nextLine!![10]
                )
                receipts.add(receipt)
            }
        }
        val laundryEntity = mutableListOf<LaundryEntity>()

       receipts.forEach {
           laundryEntity.add(laundryEntityMapper.mapFromDomainModel(it))
        }
        receiptDao.insertAllLaundry(laundryEntity)
    }
    suspend fun otherJobsImportCsvToDatabase(csvFilePath:String) {

        val csvFile=File(csvFilePath)

        val csvReader = CSVReader(FileReader(csvFile))

        val receipts = mutableListOf<OtherJobsReceipt>()
        csvReader.use { reader ->
            reader.readNext()

            var nextLine: Array<String>?
            while (reader.readNext().also { nextLine = it } != null) {
                val receipt = OtherJobsReceipt(
                    id = nextLine!![0].toInt(),
                    status = nextLine!![1].toInt(),
                    name = nextLine!![2],
                    phone=nextLine!![3],
                    orderName =nextLine!![4],
                    description =nextLine!![5],
                    orderNumber =nextLine!![6],
                    deliveryTime=nextLine!![7],
                    receiptTime=nextLine!![8],
                    cost=nextLine!![9],
                    prepayment=nextLine!![10]
                )
                receipts.add(receipt)
            }
        }
        val otherJobsEntity = mutableListOf<OtherJobsEntity>()

       receipts.forEach {
           otherJobsEntity.add(otherJobsEntityMapper.mapFromDomainModel(it))
        }
        receiptDao.insertAllOtherJobs(otherJobsEntity)
    }
    suspend fun photographyImportCsvToDatabase(csvFilePath:String) {

        val csvFile=File(csvFilePath)

        val csvReader = CSVReader(FileReader(csvFile))

        val receipts = mutableListOf<PhotographyReceipt>()
        csvReader.use { reader ->
            reader.readNext() // خواندن عنوان ستون‌ها

            var nextLine: Array<String>?
            while (reader.readNext().also { nextLine = it } != null) {
                // فرض کنید ستون‌های CSV با فیلدهای کلاس Receipt تطابق دارند
                val receipt = PhotographyReceipt(
                    id = nextLine!![0].toInt(),
                    status = nextLine!![1].toInt(),
                    name = nextLine!![2],
                    phone=nextLine!![3],
                    orderName =nextLine!![4],
                    orderSize =nextLine!![5],
                    orderNumber  =nextLine!![6],
                    deliveryTime=nextLine!![7],
                    receiptTime=nextLine!![8],
                    cost=nextLine!![9],
                    prepayment=nextLine!![10]
                )
                receipts.add(receipt)
            }
        }
        val photographyEntity = mutableListOf<PhotographyEntity>()

       receipts.forEach {
            photographyEntity.add(photographyEntityMapper.mapFromDomainModel(it))
        }
        receiptDao.insertAllPhotography(photographyEntity)
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
            repairEntity.add(repairsEntityMapper.mapFromDomainModel(it))
        }
        receiptDao.insertAllRepair(repairEntity)
    }
    suspend fun tailoringImportCsvToDatabase(csvFilePath:String) {

        val csvFile=File(csvFilePath)

        val csvReader = CSVReader(FileReader(csvFile))

        val receipts = mutableListOf<TailoringReceipt>()
        csvReader.use { reader ->
            reader.readNext()

            var nextLine: Array<String>?
            while (reader.readNext().also { nextLine = it } != null) {
                val receipt = TailoringReceipt(
                    id = nextLine!![0].toInt(),
                    status = nextLine!![1].toInt(),
                    name = nextLine!![2],
                    phone=nextLine!![3],
                    loanerName=nextLine!![4],
                    orderSpecification =nextLine!![5],
                    deliveryTime=nextLine!![6],
                    receiptTime=nextLine!![7],
                    sizes =nextLine!![8],
                    cost=nextLine!![9],
                    prepayment=nextLine!![10]
                )
                receipts.add(receipt)
            }
        }
        val tailoringEntity = mutableListOf<TailoringEntity>()

       receipts.forEach {
           tailoringEntity.add(tailoringEntityMapper.mapFromDomainModel(it))
        }
        receiptDao.insertAllTailoring(tailoringEntity)
    }
}

