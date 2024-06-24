package com.saeed.zanjan.receipt.interactor

import android.content.Context
import android.content.SharedPreferences
import android.os.Environment
import com.saeed.zanjan.receipt.cash.ReceiptDao
import com.saeed.zanjan.receipt.cash.model.CustomerEntity
import com.saeed.zanjan.receipt.domain.dataState.DataState
import com.saeed.zanjan.receipt.network.RetrofitService
import com.saeed.zanjan.receipt.utils.CsvExportUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream

class Backup(
    val context: Context,
    val retrofitService: RetrofitService,
    private val csvExportUtil: CsvExportUtil,
    private val sharedPreferences: SharedPreferences,
    private val receiptDao: ReceiptDao

) {

    val receiptCategory = sharedPreferences.getInt("JOB_SUBJECT",-1)
    private val token = sharedPreferences.getString("JWTToken","")

    //TODO check Network State
    //todo currect token

    fun backupDb(): Flow<DataState<String>> = flow {
        emit(DataState.loading())

        try {
            val dataBasePart = getDatabaseAsPart()
            val result = retrofitService.uploadDatabase(
                token = token,
                dataBasePart
            )
            if (result.isSuccessful) {
                emit(DataState.success(result.body()!!))
            } else if (result.code() == 401) {
                emit(DataState.error("شما دسترسی لازم را ندارید"))
            } else {
                try {
                    val errMsg = result.errorBody()?.string()?.let {
                        JSONObject(it).getString("error") // or whatever your message is
                    } ?: run {
                        emit(DataState.error(result.code().toString()))
                    }
                    emit(DataState.error(errMsg.toString()))
                } catch (e: Exception) {
                    emit(DataState.error(e.message.toString()))
                }
            }

        } catch (e: Exception) {

            emit(DataState.error(e.message.toString()))

        }


    }


    private fun getDatabaseAsPart(
    ): MultipartBody.Part {
        val dbFile: File = exportDatabaseToCsv()
        val requestFile = RequestBody.create("application/octet-stream".toMediaTypeOrNull(), dbFile)
        return MultipartBody.Part.createFormData("DatabaseFile", dbFile.name, requestFile)

    }

    fun downloadDatabase(): Flow<DataState<String>> = flow {
        emit(DataState.loading())
        try {
            val response = retrofitService.downloadDatabase(
                token = token
            )
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    val success = saveFile(body)
                    if (success) {
                        val filePath = getCsvFilePath()
                        when (receiptCategory) {
                            0 -> {
                                //repair
                                csvExportUtil.repairsImportCsvToDatabase(filePath)
                                emit(DataState.success("دریافت موفق اطلاعات"))
                            }

                            1 -> {
                                //repair
                                csvExportUtil.repairsImportCsvToDatabase(filePath)
                                emit(DataState.success("دریافت موفق اطلاعات"))

                            }

                            2 -> {
                                //  repair
                                csvExportUtil.repairsImportCsvToDatabase(filePath)
                                emit(DataState.success("دریافت موفق اطلاعات"))
                            }

                            3 -> {
                                //tailoring
                                csvExportUtil.tailoringImportCsvToDatabase(filePath)
                                emit(DataState.success("دریافت موفق اطلاعات"))
                            }

                            4 -> {
                                //jewelry
                                csvExportUtil.jewelryImportCsvToDatabase(filePath)
                                emit(DataState.success("دریافت موفق اطلاعات"))
                            }

                            5 -> {
                                //photo
                                csvExportUtil.photographyImportCsvToDatabase(filePath)
                                emit(DataState.success("دریافت موفق اطلاعات"))

                            }

                            6 -> {
                                //laundry
                                csvExportUtil.laundryImportCsvToDatabase(filePath)
                                emit(DataState.success("دریافت موفق اطلاعات"))

                            }

                            7 -> {
                                //confectionery
                                csvExportUtil.confectioneryImportCsvToDatabase(filePath)
                                emit(DataState.success("دریافت موفق اطلاعات"))

                            }

                            8 -> {
                                //otherJobs
                                csvExportUtil.otherJobsImportCsvToDatabase(filePath)
                                emit(DataState.success("دریافت موفق اطلاعات"))

                            }

                            else -> {
                                emit(DataState.success("خطای دسته بندی"))

                            }

                        }


                    } else {
                        emit(DataState.error("خطا در ذخیره دیتا بیس"))

                    }
                }
            } else if (response.code() == 401) {
                emit(DataState.error("شما دسترسی لازم را ندارید"))
            } else {
                try {
                    val errMsg = response.errorBody()?.string()?.let {
                        JSONObject(it).getString("error") // or whatever your message is
                    } ?: run {
                        emit(DataState.error(response.code().toString()))
                    }
                    emit(DataState.error(errMsg.toString()))
                } catch (e: Exception) {
                    emit(DataState.error(e.message.toString()))
                }
            }


        } catch (e: Exception) {
            emit(DataState.error(e.message.toString()))

        }

    }


    fun fillCustomerTable(): Flow<DataState<Boolean>> = flow {
        emit(DataState.loading())
        val transferResult = transferData()
        if (transferResult == 1) {
            emit(DataState.success(true))
        } else {
            emit(DataState.error("خطای انتقال اطلاعات"))
        }

    }

    fun saveFile(body: ResponseBody): Boolean {
        return try {
            val file = File(context.filesDir, "downloaded_receipt.csv")
            var inputStream: InputStream? = null
            var outputStream: FileOutputStream? = null
            try {
                inputStream = body.byteStream()
                outputStream = FileOutputStream(file)
                val buffer = ByteArray(4096)
                var bytesRead: Int
                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                }
                outputStream.flush()
                true
            } finally {
                inputStream?.close()
                outputStream?.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun exportDatabaseToCsv(): File {
        return csvExportUtil.exportDatabaseToCsv(receiptCategory)
    }

    fun getCsvFilePath(): String {
        return File(context.filesDir, "downloaded_receipt.csv").absolutePath
    }

    fun transferData(): Int {
        try {
            when (receiptCategory) {
                0 -> {
                    //repair
                    val contacts = receiptDao.getRepairsContacts()

                    contacts.forEach { temporary ->
                        val entity = CustomerEntity(
                            id = 0,
                            name = temporary.name,
                            phoneNumber = temporary.phone,
                            payedAmount = temporary.prepayment,
                            totalAmount = temporary.cost
                        )
                        receiptDao.insertCustomer(entity)
                    }
                }

                1 -> {
                    //repair
                    val contacts = receiptDao.getRepairsContacts()

                    contacts.forEach { temporary ->
                        val entity = CustomerEntity(
                            id = 0,
                            name = temporary.name,
                            phoneNumber = temporary.phone,
                            payedAmount = temporary.prepayment,
                            totalAmount = temporary.cost
                        )
                        receiptDao.insertCustomer(entity)
                    }
                }

                2 -> {
                    //  repair
                    val contacts = receiptDao.getRepairsContacts()

                    contacts.forEach { temporary ->
                        val entity = CustomerEntity(
                            id = 0,
                            name = temporary.name,
                            phoneNumber = temporary.phone,
                            payedAmount = temporary.prepayment,
                            totalAmount = temporary.cost
                        )
                        receiptDao.insertCustomer(entity)
                    }
                }

                3 -> {
                    //tailoring
                    val contacts = receiptDao.getTailoringContacts()

                    contacts.forEach { temporary ->
                        val entity = CustomerEntity(
                            id = 0,
                            name = temporary.name,
                            phoneNumber = temporary.phone,
                            payedAmount = temporary.prepayment,
                            totalAmount = temporary.cost
                        )
                        receiptDao.insertCustomer(entity)
                    }
                }

                4 -> {
                    //jewelry
                    val contacts = receiptDao.getJewelryContacts()

                    contacts.forEach { temporary ->
                        val entity = CustomerEntity(
                            id = 0,
                            name = temporary.name,
                            phoneNumber = temporary.phone,
                            payedAmount = temporary.prepayment,
                            totalAmount = temporary.cost
                        )
                        receiptDao.insertCustomer(entity)
                    }
                }

                5 -> {
                    //photo
                    val contacts = receiptDao.getPhotographyContacts()

                    contacts.forEach { temporary ->
                        val entity = CustomerEntity(
                            id = 0,
                            name = temporary.name,
                            phoneNumber = temporary.phone,
                            payedAmount = temporary.prepayment,
                            totalAmount = temporary.cost
                        )
                        receiptDao.insertCustomer(entity)
                    }
                }

                6 -> {
                    //laundry
                    val contacts = receiptDao.getLaundryContacts()

                    contacts.forEach { temporary ->
                        val entity = CustomerEntity(
                            id = 0,
                            name = temporary.name,
                            phoneNumber = temporary.phone,
                            payedAmount = temporary.prepayment,
                            totalAmount = temporary.cost
                        )
                        receiptDao.insertCustomer(entity)
                    }
                }

                7 -> {
                    //confectionery
                    val contacts = receiptDao.getConfectioneryContacts()

                    contacts.forEach { temporary ->
                        val entity = CustomerEntity(
                            id = 0,
                            name = temporary.name,
                            phoneNumber = temporary.phone,
                            payedAmount = temporary.prepayment,
                            totalAmount = temporary.cost
                        )
                        receiptDao.insertCustomer(entity)
                    }
                }

                8 -> {
                    //otherJobs
                    val contacts = receiptDao.getOtherJobsContacts()

                    contacts.forEach { temporary ->
                        val entity = CustomerEntity(
                            id = 0,
                            name = temporary.name,
                            phoneNumber = temporary.phone,
                            payedAmount = temporary.prepayment,
                            totalAmount = temporary.cost
                        )
                        receiptDao.insertCustomer(entity)
                    }
                }

                else -> {
                    return -1
                }

            }

            return 1
        } catch (e: Exception) {
            return -1
        }

    }




}