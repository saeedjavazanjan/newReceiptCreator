package com.saeed.zanjan.receipt.interactor

import android.content.Context
import android.os.Environment
import android.provider.ContactsContract.Data
import android.util.Log
import com.saeed.zanjan.receipt.cash.ReceiptDao
import com.saeed.zanjan.receipt.cash.model.ConfectioneryEntityMapper
import com.saeed.zanjan.receipt.cash.model.JewelryEntityMapper
import com.saeed.zanjan.receipt.cash.model.LaundryEntityMapper
import com.saeed.zanjan.receipt.cash.model.OtherJobsEntityMapper
import com.saeed.zanjan.receipt.cash.model.PhotographyEntityMapper
import com.saeed.zanjan.receipt.cash.model.RepairsEntityMapper
import com.saeed.zanjan.receipt.cash.model.TailoringEntityMapper
import com.saeed.zanjan.receipt.domain.dataState.DataState
import com.saeed.zanjan.receipt.domain.models.ConfectioneryReceipt
import com.saeed.zanjan.receipt.domain.models.JewelryReceipt
import com.saeed.zanjan.receipt.domain.models.LaundryReceipt
import com.saeed.zanjan.receipt.domain.models.OtherJobsReceipt
import com.saeed.zanjan.receipt.domain.models.PhotographyReceipt
import com.saeed.zanjan.receipt.domain.models.RepairsReceipt
import com.saeed.zanjan.receipt.domain.models.TailoringReceipt
import com.saeed.zanjan.receipt.presentation.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.apache.poi.ss.formula.functions.T
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ExportExcelFile(
    val context: Context,
    val receiptDao: ReceiptDao,
    val confectioneryEntityMapper: ConfectioneryEntityMapper,
    val jewelryEntityMapper: JewelryEntityMapper,
    val laundryEntityMapper: LaundryEntityMapper,
    val otherJobsEntityMapper: OtherJobsEntityMapper,
    val photographyEntityMapper: PhotographyEntityMapper,
    val repairsEntityMapper: RepairsEntityMapper,
    val tailoringEntityMapper: TailoringEntityMapper
) {


    fun databaseExport(receiptCategory: Int): Flow<DataState<String>> = flow {
        emit(DataState.loading())

        try {
            when(receiptCategory){
                0 -> {
                    //repair
                    val dataEntity = receiptDao.getAllRepairsEntity()
                    if (exportRepairsDataToExcel(repairsEntityMapper.mapToDomainList(dataEntity)) == 1)
                        emit(DataState.success("موفق"))
                    else
                        emit(DataState.error("خطا"))


                }

                1 -> {
                    //repair
                    val dataEntity = receiptDao.getAllRepairsEntity()
                    if (exportRepairsDataToExcel(repairsEntityMapper.mapToDomainList(dataEntity)) == 1)
                        emit(DataState.success("موفق"))
                    else
                        emit(DataState.error("خطا"))

                }

                2 -> {
                    //  repair
                    val dataEntity = receiptDao.getAllRepairsEntity()
                    if (exportRepairsDataToExcel(repairsEntityMapper.mapToDomainList(dataEntity)) == 1)
                        emit(DataState.success("موفق"))
                    else
                        emit(DataState.error("خطا"))
                }

                3 -> {
                    //tailoring
                    val dataEntity = receiptDao.getAllTailoringReceipts()
                    if (exportTailoringDataToExcel(tailoringEntityMapper.mapToDomainList(dataEntity)) == 1)
                        emit(DataState.success("موفق"))
                    else
                        emit(DataState.error("خطا"))
                }

                4 -> {
                    //jewelry
                    val dataEntity = receiptDao.getAllJewelryReceipts()
                    if (exportJewelryDataToExcel(jewelryEntityMapper.mapToDomainList(dataEntity)) == 1)
                        emit(DataState.success("موفق"))
                    else
                        emit(DataState.error("خطا"))
                }

                5 -> {
                    //photo
                    val dataEntity = receiptDao.getAllPhotographyReceipts()
                    if (exportPhotographyDataToExcel(photographyEntityMapper.mapToDomainList(dataEntity)) == 1)
                        emit(DataState.success("موفق"))
                    else
                        emit(DataState.error("خطا"))

                }

                6 -> {
                    //laundry
                    val dataEntity = receiptDao.getAllLaundryReceipts()
                    if (exportLaundryDataToExcel(laundryEntityMapper.mapToDomainList(dataEntity)) == 1)
                        emit(DataState.success("موفق"))
                    else
                        emit(DataState.error("خطا"))

                }

                7 -> {
                    //confectionery
                    val dataEntity = receiptDao.getAllConfectioneryReceipts()
                    if (exportConfectioneryDataToExcel(confectioneryEntityMapper.mapToDomainList(dataEntity)) == 1)
                        emit(DataState.success("موفق"))
                    else
                        emit(DataState.error("خطا"))

                }

                8 -> {
                    //otherJobs
                    val dataEntity = receiptDao.getAllOtherJobsReceipts()
                    if (exportOtherJobsDataToExcel(otherJobsEntityMapper.mapToDomainList(dataEntity)) == 1)
                        emit(DataState.success("موفق"))
                    else
                        emit(DataState.error("خطا"))

                }

                else->{
                    emit(DataState.success("خطای دسته بندی"))

                }

            }







        } catch (e: Exception) {

            emit(DataState.error(e.message.toString()))

        }

    }.flowOn(Dispatchers.IO)


    suspend fun exportConfectioneryDataToExcel(receipts: List<ConfectioneryReceipt?>): Int {
        try {
            val workbook = XSSFWorkbook()
            val sheet = workbook.createSheet("Receipts")

            // ایجاد ردیف هدر
            val headerRow = sheet.createRow(0)
            val headers = arrayOf(
                "ID",
                "وضعیت",
                "نام مشتری",
                "شماره تلفن",
                "نام سفارش",
                "توضیحات سفارش",
                "وزن",
                "مشخصات",
                "تاریخ دریافت",
                "موعد تحویل",
                "هزینه کلی",
                "مبلغ پرداخت شده",
            )
            for (i in headers.indices) {
                val cell = headerRow.createCell(i)
                cell.setCellValue(headers[i])
            }

            // نوشتن داده‌ها
            for ((index, receipt) in receipts.withIndex()) {
                val row = sheet.createRow(index + 1)
                row.createCell(0).setCellValue(receipt!!.id.toDouble())
                row.createCell(1).setCellValue(statusText(receipt.status))
                row.createCell(2).setCellValue(receipt.name)
                row.createCell(3).setCellValue(receipt.phone)
                row.createCell(4).setCellValue(receipt.orderName)
                row.createCell(5).setCellValue(receipt.orderSpecification)
                row.createCell(6).setCellValue(receipt.orderWeight)
                row.createCell(7).setCellValue(receipt.description)
                row.createCell(8).setCellValue(receipt.receiptTime)
                row.createCell(9).setCellValue(receipt.deliveryTime)
                row.createCell(10).setCellValue(receipt.cost)
                row.createCell(11).setCellValue(receipt.prepayment)
            }


            val folder =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

            if (!folder.exists()) {
                folder.mkdirs()
            }
            val dateFormat = SimpleDateFormat("MM-dd-yyyy HH-mm", Locale.getDefault())
            val dateTime = dateFormat.format(Date())
            val file = File(folder, "رسیدها${dateTime}.xlsx")
            FileOutputStream(file).use { outputStream ->
                workbook.write(outputStream)
            }
            Log.i("STORAGEDD", file.path)

            workbook.close()
            return 1

        } catch (e: Exception) {
            Log.e("STORAGEDD", e.message.toString())
            return -1
        }
    }

    suspend fun exportJewelryDataToExcel(receipts: List<JewelryReceipt?>): Int {
        try {
            val workbook = XSSFWorkbook()
            val sheet = workbook.createSheet("Receipts")

            // ایجاد ردیف هدر
            val headerRow = sheet.createRow(0)
            val headers = arrayOf(
                "ID",
                "وضعیت",
                "نام مشتری",
                "شماره تلفن",
                "نام سفارش",
                "توضیحات سفارش",
                "مشکلات سفارش",
                "مشخصات کالا",
                "تاریخ دریافت",
                "موعد تحویل",
                "هزینه کلی",
                "مبلغ پرداخت شده",
            )
            for (i in headers.indices) {
                val cell = headerRow.createCell(i)
                cell.setCellValue(headers[i])
            }

            // نوشتن داده‌ها
            for ((index, receipt) in receipts.withIndex()) {
                val row = sheet.createRow(index + 1)
                row.createCell(0).setCellValue(receipt!!.id.toDouble())
                row.createCell(1).setCellValue(statusText(receipt.status))
                row.createCell(2).setCellValue(receipt.name)
                row.createCell(3).setCellValue(receipt.phone)
                row.createCell(4).setCellValue(receipt.loanerName)
                row.createCell(5).setCellValue(receipt.orderSpecification)
                row.createCell(6).setCellValue(receipt.loanerProblems)
                row.createCell(7).setCellValue(receipt.loanerSpecification)
                row.createCell(8).setCellValue(receipt.receiptTime)
                row.createCell(9).setCellValue(receipt.deliveryTime)
                row.createCell(10).setCellValue(receipt.cost)
                row.createCell(11).setCellValue(receipt.prepayment)
            }


            val folder =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

            if (!folder.exists()) {
                folder.mkdirs()
            }
            val dateFormat = SimpleDateFormat("MM-dd-yyyy HH-mm", Locale.getDefault())
            val dateTime = dateFormat.format(Date())
            val file = File(folder, "رسیدها${dateTime}.xlsx")
            FileOutputStream(file).use { outputStream ->
                workbook.write(outputStream)
            }
            Log.i("STORAGEDD", file.path)

            workbook.close()
            return 1

        } catch (e: Exception) {
            Log.e("STORAGEDD", e.message.toString())
            return -1
        }
    }

    suspend fun exportLaundryDataToExcel(receipts: List<LaundryReceipt?>): Int {
        try {
            val workbook = XSSFWorkbook()
            val sheet = workbook.createSheet("Receipts")

            // ایجاد ردیف هدر
            val headerRow = sheet.createRow(0)
            val headers = arrayOf(
                "ID",
                "وضعیت",
                "نام مشتری",
                "شماره تلفن",
                "نام سفارش",
                "نوع سفارش",
                "توضیحات",
                "تاریخ دریافت",
                "موعد تحویل",
                "هزینه کلی",
                "مبلغ پرداخت شده",
            )
            for (i in headers.indices) {
                val cell = headerRow.createCell(i)
                cell.setCellValue(headers[i])
            }

            // نوشتن داده‌ها
            for ((index, receipt) in receipts.withIndex()) {
                val row = sheet.createRow(index + 1)
                row.createCell(0).setCellValue(receipt!!.id.toDouble())
                row.createCell(1).setCellValue(statusText(receipt.status))
                row.createCell(2).setCellValue(receipt.name)
                row.createCell(3).setCellValue(receipt.phone)
                row.createCell(4).setCellValue(receipt.loanerName)
                row.createCell(5).setCellValue(receipt.orderType)
                row.createCell(6).setCellValue(receipt.description)
                row.createCell(7).setCellValue(receipt.receiptTime)
                row.createCell(8).setCellValue(receipt.deliveryTime)
                row.createCell(9).setCellValue(receipt.cost)
                row.createCell(10).setCellValue(receipt.prepayment)
            }


            val folder =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

            if (!folder.exists()) {
                folder.mkdirs()
            }
            val dateFormat = SimpleDateFormat("MM-dd-yyyy HH-mm", Locale.getDefault())
            val dateTime = dateFormat.format(Date())
            val file = File(folder, "رسیدها${dateTime}.xlsx")
            FileOutputStream(file).use { outputStream ->
                workbook.write(outputStream)
            }
            Log.i("STORAGEDD", file.path)

            workbook.close()
            return 1

        } catch (e: Exception) {
            Log.e("STORAGEDD", e.message.toString())
            return -1
        }
    }

    suspend fun exportOtherJobsDataToExcel(receipts: List<OtherJobsReceipt?>): Int {
        try {
            val workbook = XSSFWorkbook()
            val sheet = workbook.createSheet("Receipts")

            // ایجاد ردیف هدر
            val headerRow = sheet.createRow(0)
            val headers = arrayOf(
                "ID",
                "وضعیت",
                "نام مشتری",
                "شماره تلفن",
                "نام سفارش",
                "توضیحات",
                "تعداد سفارش",
                "تاریخ دریافت",
                "موعد تحویل",
                "هزینه کلی",
                "مبلغ پرداخت شده",
            )
            for (i in headers.indices) {
                val cell = headerRow.createCell(i)
                cell.setCellValue(headers[i])
            }

            // نوشتن داده‌ها
            for ((index, receipt) in receipts.withIndex()) {
                val row = sheet.createRow(index + 1)
                row.createCell(0).setCellValue(receipt!!.id.toDouble())
                row.createCell(1).setCellValue(statusText(receipt.status))
                row.createCell(2).setCellValue(receipt.name)
                row.createCell(3).setCellValue(receipt.phone)
                row.createCell(4).setCellValue(receipt.orderName)
                row.createCell(5).setCellValue(receipt.description)
                row.createCell(6).setCellValue(receipt.orderNumber)
                row.createCell(7).setCellValue(receipt.receiptTime)
                row.createCell(8).setCellValue(receipt.deliveryTime)
                row.createCell(9).setCellValue(receipt.cost)
                row.createCell(10).setCellValue(receipt.prepayment)
            }


            val folder =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

            if (!folder.exists()) {
                folder.mkdirs()
            }
            val dateFormat = SimpleDateFormat("MM-dd-yyyy HH-mm", Locale.getDefault())
            val dateTime = dateFormat.format(Date())
            val file = File(folder, "رسیدها${dateTime}.xlsx")
            FileOutputStream(file).use { outputStream ->
                workbook.write(outputStream)
            }
            Log.i("STORAGEDD", file.path)

            workbook.close()
            return 1

        } catch (e: Exception) {
            Log.e("STORAGEDD", e.message.toString())
            return -1
        }
    }

    suspend fun exportPhotographyDataToExcel(receipts: List<PhotographyReceipt?>): Int {
        try {
            val workbook = XSSFWorkbook()
            val sheet = workbook.createSheet("Receipts")

            // ایجاد ردیف هدر
            val headerRow = sheet.createRow(0)
            val headers = arrayOf(
                "ID",
                "وضعیت",
                "نام مشتری",
                "شماره تلفن",
                "نام سفارش",
                "اندازه ها و توضیحات",
                "تعداد",
                "تاریخ دریافت",
                "موعد تحویل",
                "هزینه کلی",
                "مبلغ پرداخت شده",
            )
            for (i in headers.indices) {
                val cell = headerRow.createCell(i)
                cell.setCellValue(headers[i])
            }

            // نوشتن داده‌ها
            for ((index, receipt) in receipts.withIndex()) {
                val row = sheet.createRow(index + 1)
                row.createCell(0).setCellValue(receipt!!.id.toDouble())
                row.createCell(1).setCellValue(statusText(receipt.status))
                row.createCell(2).setCellValue(receipt.name)
                row.createCell(3).setCellValue(receipt.phone)
                row.createCell(4).setCellValue(receipt.orderName)
                row.createCell(5).setCellValue(receipt.orderSize)
                row.createCell(6).setCellValue(receipt.orderNumber)
                row.createCell(7).setCellValue(receipt.receiptTime)
                row.createCell(8).setCellValue(receipt.deliveryTime)
                row.createCell(9).setCellValue(receipt.cost)
                row.createCell(10).setCellValue(receipt.prepayment)
            }


            val folder =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

            if (!folder.exists()) {
                folder.mkdirs()
            }
            val dateFormat = SimpleDateFormat("MM-dd-yyyy HH-mm", Locale.getDefault())
            val dateTime = dateFormat.format(Date())
            val file = File(folder, "رسیدها${dateTime}.xlsx")
            FileOutputStream(file).use { outputStream ->
                workbook.write(outputStream)
            }
            Log.i("STORAGEDD", file.path)

            workbook.close()
            return 1

        } catch (e: Exception) {
            Log.e("STORAGEDD", e.message.toString())
            return -1
        }
    }

    suspend fun exportRepairsDataToExcel(receipts: List<RepairsReceipt?>): Int {
        try {
            val workbook = XSSFWorkbook()
            val sheet = workbook.createSheet("Receipts")

            // ایجاد ردیف هدر
            val headerRow = sheet.createRow(0)
            val headers = arrayOf(
                "ID",
                "وضعیت",
                "نام مشتری",
                "شماره تلفن",
                "نام سفارش",
                "مشکلات محصول",
                "خطرات احتمالی",
                "لوازم همراه",
                "تاریخ دریافت",
                "موعد تحویل",
                "هزینه کلی",
                "مبلغ پرداخت شده",
            )
            for (i in headers.indices) {
                val cell = headerRow.createCell(i)
                cell.setCellValue(headers[i])
            }

            // نوشتن داده‌ها
            for ((index, receipt) in receipts.withIndex()) {
                val row = sheet.createRow(index + 1)
                row.createCell(0).setCellValue(receipt!!.id.toDouble())
                row.createCell(1).setCellValue(statusText(receipt.status))
                row.createCell(2).setCellValue(receipt.name)
                row.createCell(3).setCellValue(receipt.phone)
                row.createCell(4).setCellValue(receipt.loanerName)
                row.createCell(5).setCellValue(receipt.loanerProblems)
                row.createCell(6).setCellValue(receipt.risks)
                row.createCell(7).setCellValue(receipt.accessories)
                row.createCell(8).setCellValue(receipt.receiptTime)
                row.createCell(9).setCellValue(receipt.deliveryTime)
                row.createCell(10).setCellValue(receipt.cost)
                row.createCell(11).setCellValue(receipt.prepayment)
            }


            val folder =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

            if (!folder.exists()) {
                folder.mkdirs()
            }
            val dateFormat = SimpleDateFormat("MM-dd-yyyy HH-mm", Locale.getDefault())
            val dateTime = dateFormat.format(Date())
            val file = File(folder, "رسیدها${dateTime}.xlsx")
            FileOutputStream(file).use { outputStream ->
                workbook.write(outputStream)
            }
            Log.i("STORAGEDD", file.path)

            workbook.close()
            return 1

        } catch (e: Exception) {
            Log.e("STORAGEDD", e.message.toString())
            return -1
        }
    }

    suspend fun exportTailoringDataToExcel(receipts: List<TailoringReceipt>): Int {
        try {
            val workbook = XSSFWorkbook()
            val sheet = workbook.createSheet("Receipts")

            // ایجاد ردیف هدر
            val headerRow = sheet.createRow(0)
            val headers = arrayOf(
                "ID",
                "وضعیت",
                "نام مشتری",
                "شماره تلفن",
                "نام سفارش",
                "توضیحات سفارش",
                "اندازه ها",
                "تاریخ دریافت",
                "موعد تحویل",
                "هزینه کلی",
                "مبلغ پرداخت شده",
            )
            for (i in headers.indices) {
                val cell = headerRow.createCell(i)
                cell.setCellValue(headers[i])
            }

            // نوشتن داده‌ها
            for ((index, receipt) in receipts.withIndex()) {
                val row = sheet.createRow(index + 1)
                row.createCell(0).setCellValue(receipt.id.toDouble())
                row.createCell(1).setCellValue(statusText(receipt.status))
                row.createCell(2).setCellValue(receipt.name)
                row.createCell(3).setCellValue(receipt.phone)
                row.createCell(4).setCellValue(receipt.loanerName)
                row.createCell(5).setCellValue(receipt.orderSpecification)
                row.createCell(6).setCellValue(receipt.sizes)
                row.createCell(7).setCellValue(receipt.receiptTime)
                row.createCell(8).setCellValue(receipt.deliveryTime)
                row.createCell(9).setCellValue(receipt.cost)
                row.createCell(10).setCellValue(receipt.prepayment)
            }


            val folder =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

            if (!folder.exists()) {
                folder.mkdirs()
            }
            val dateFormat = SimpleDateFormat("MM-dd-yyyy HH-mm", Locale.getDefault())
            val dateTime = dateFormat.format(Date())
            val file = File(folder, "رسیدها${dateTime}.xlsx")
            FileOutputStream(file).use { outputStream ->
                workbook.write(outputStream)
            }
            Log.i("STORAGEDD", file.path)

            workbook.close()
            return 1

        } catch (e: Exception) {
            Log.e("STORAGEDD", e.message.toString())
            return -1
        }
    }


    fun statusText(statusId: Int?): String {
        when (statusId) {
            0 -> {
                return "در حال انجام"
            }

            1 -> {
                return "تحویل داده شده"
            }

            2 -> {
                return "مشکل در روند سفارش"
            }

            3 -> {
                return "آماده تحویل"
            }

            else -> {
                return " "
            }


        }

    }

}