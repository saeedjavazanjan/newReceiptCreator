package com.saeed.zanjan.receipt.presentation.ui.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultRegistry
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saeed.zanjan.receipt.domain.models.GeneralReceipt
import com.saeed.zanjan.receipt.interactor.Backup
import com.saeed.zanjan.receipt.bazar.BazarInAppBill
import com.saeed.zanjan.receipt.bazar.UserPurchaseInfo
import com.saeed.zanjan.receipt.interactor.ExportExcelFile
import com.saeed.zanjan.receipt.interactor.ListOfReceipts
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.cafebazaar.poolakey.Connection
import ir.cafebazaar.poolakey.Payment
import ir.cafebazaar.poolakey.config.PaymentConfiguration
import ir.cafebazaar.poolakey.config.SecurityCheck
import ir.cafebazaar.poolakey.entity.PurchaseInfo
import ir.cafebazaar.poolakey.request.PurchaseRequest
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class HomeViewModel
@Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val lisOfReceipts: ListOfReceipts,
    private val backup: Backup,
    private val exportExcelFile: ExportExcelFile,
    private val bazarInAppBill: BazarInAppBill
) : ViewModel() {



    val receiptCategory = 1//sharedPreferences.getInt("JOB_SUBJECT",-1)
    val loading = mutableStateOf(false)
    val receiptList: MutableState<List<GeneralReceipt>> = mutableStateOf(ArrayList())
    val databaseSaved = mutableStateOf(false)
    val bazarConnectionState= mutableStateOf(false)
    val purchaseBuyState= mutableStateOf(false)

    //bazar
    val localSecurityCheck = SecurityCheck.Enable(
        rsaPublicKey = "MIHNMA0GCSqGSIb3DQEBAQUAA4G7ADCBtwKBrwDMktQ+9MJj6kJYY+pf+X4RPcvdLOjy3HG0BZH8Pkqj8on5yc+k2xzpvRg71fR4pzoNcNXHmVIbqefoRxraTu9IIWJ/YQauSix9v2VrKqdiYPCYlGbyD8NnryJXykHtRYeAeuJVseWLwPS/SAlkkVTY5ooQLXX+O7ZJS1ungbi/lkd3AkeRzYi9YZ1oI392T3hxPkoFhaWq4w/TcV7lpOS/f1PCfsy1Z2IKyvZOx88CAwEAAQ=="
    )
    val paymentConfiguration = PaymentConfiguration(
        localSecurityCheck = localSecurityCheck
    )
    lateinit var payment: Payment
    lateinit var paymentConnection:Connection
    val expireTime= mutableStateOf(0L)
    var userPurchaseInfo= mutableListOf<UserPurchaseInfo>()

    fun getListOfReceipts(
        snackbarHostState: SnackbarHostState
    ) {
        lisOfReceipts.getListOfReceipts(
            receiptCategory
        ).onEach { dataState ->

            dataState.loading.let {
                loading.value = it
            }
            dataState.data?.let {
                receiptList.value = it
            }
            dataState.error?.let {

                snackbarHostState.showSnackbar(it)
            }

        }.catch {

            loading.value = false
            snackbarHostState.showSnackbar(it.message.toString())

        }.launchIn(viewModelScope)


    }


    fun searchReceipt(query: String, snackbarHostState: SnackbarHostState) {
        lisOfReceipts.searchReceipt(query, receiptCategory).onEach { dataState ->

            dataState.loading.let {
                loading.value = it
            }
            dataState.data?.let {
                receiptList.value = it
            }
            dataState.error?.let {
                snackbarHostState.showSnackbar(it)

            }


        }.launchIn(viewModelScope)
    }

    fun filterReceipt(status: Int, snackbarHostState: SnackbarHostState) {
        lisOfReceipts.filterReceipts(status, receiptCategory).onEach { dataState ->

            dataState.loading.let {
                loading.value = it
            }
            dataState.data?.let {
                receiptList.value = it
            }
            dataState.error?.let {
                snackbarHostState.showSnackbar(it)

            }


        }.launchIn(viewModelScope)

    }

    fun commentOnApp(context: Context) {
        val intent = Intent(Intent.ACTION_EDIT)
        intent.data =
            Uri.parse("bazaar://details?id=" + "com.saeed.zanjan.receipt")
        intent.setPackage("com.farsitel.bazaar")
        context.startActivity(intent)
    }

    //TODO storage permissin
    fun uploadBackUpOfDatabase(snackbarHostState: SnackbarHostState) {
        backup.backupDb().onEach { dataState ->
            dataState.loading.let {
                loading.value = it
            }
            dataState.data?.let {
                snackbarHostState.showSnackbar("done")
            }
            dataState.error?.let {
                snackbarHostState.showSnackbar(it)

            }
        }.launchIn(viewModelScope)

    }

    //TODO storage permissin
    fun exportExcel(snackbarHostState: SnackbarHostState) {
        exportExcelFile.databaseExport(receiptCategory).onEach { dataState ->
            dataState.loading.let {
                loading.value = it
            }
            dataState.data?.let {
                snackbarHostState.showSnackbar(it)
            }
            dataState.error?.let {
                snackbarHostState.showSnackbar(it)
            }

        }.launchIn(viewModelScope)

    }


    fun connectToBazar(context: Context){
        payment = Payment(context = context, config = paymentConfiguration)
         paymentConnection = payment.connect {
            connectionSucceed {
                bazarConnectionState.value=true
            }
            connectionFailed { throwable ->
                Toast.makeText(context,throwable.message.toString(), Toast.LENGTH_SHORT).show()
                bazarConnectionState.value=false

            }
            disconnected {
                bazarConnectionState.value=false


            }
        }


    }

    fun buySubscribe(
        activityResultRegistry:ActivityResultRegistry,
        productID:String,
        payload:String,
        context: Context
    ){
        val purchaseRequest = PurchaseRequest(
            productId =productID,
            payload = payload
        )

        payment.subscribeProduct(
            registry = activityResultRegistry,
            request = purchaseRequest
        ) {
            purchaseFlowBegan {
                Toast.makeText(context,"ارتباط با بازار...", Toast.LENGTH_SHORT).show()

            }
            failedToBeginFlow { throwable ->
                Toast.makeText(context,throwable.message.toString(), Toast.LENGTH_SHORT).show()

            }
            purchaseSucceed { purchaseEntity ->
                Toast.makeText(context,"خرید با موفقیت انجام شد", Toast.LENGTH_SHORT).show()
                purchaseBuyState.value=true
            }
            purchaseCanceled {
                Toast.makeText(context,"خرید لغو شد", Toast.LENGTH_SHORT).show()
            }
            purchaseFailed { throwable ->
                Toast.makeText(context,throwable.message.toString(), Toast.LENGTH_SHORT).show()

            }
        }

    }



    fun getUserSubscribes(context: Context) {
        payment.getSubscribedProducts {
            querySucceed { purchasedProducts ->

                if(purchasedProducts.isNotEmpty()){
                    calculateExpireTime(purchasedProducts.last())
                /*    purchasedProducts.forEach {pInfo->
                        userPurchaseInfo.add(
                            UserPurchaseInfo(
                                orderId = pInfo.orderId,
                                purchaseToken = pInfo.purchaseToken,
                                payload = pInfo.payload,
                                packageName = pInfo.packageName,
                                purchaseState = pInfo.purchaseState,
                                purchaseTime = pInfo.purchaseTime,
                                productId = pInfo.productId,
                                originalJson = pInfo.originalJson,
                                dataSignature = pInfo.dataSignature
                            )
                        )
                    }*/

                }




            }
            queryFailed { throwable ->
                Toast.makeText(context,throwable.message.toString(), Toast.LENGTH_SHORT).show()
                Log.i("BAZAR","faild")

            }
        }
    }

    fun calculateExpireTime(purchaseInfo: PurchaseInfo) {
        val currentTime = System.currentTimeMillis()
             val leftTime =
                if (purchaseInfo.productId == "1month") {
                    (purchaseInfo.purchaseTime + 2592000000L - currentTime) / 86400000
                }else if (purchaseInfo.productId == "3month") {
                    (purchaseInfo.purchaseTime + 2592000000L * 3 - currentTime) / 86400000
                } else if (purchaseInfo.productId == "6month") {
                    (purchaseInfo.purchaseTime + 2592000000L * 6 - currentTime) / 86400000
                }  else {
                    (purchaseInfo.purchaseTime + 300000 - currentTime)/1000
                }
        if(leftTime>=0){
            expireTime.value=leftTime
        }else{
            expireTime.value=0
        }
    }


    fun restartState(){
        bazarConnectionState.value=false
        purchaseBuyState.value=false
        paymentConnection.disconnect()

    }
}
