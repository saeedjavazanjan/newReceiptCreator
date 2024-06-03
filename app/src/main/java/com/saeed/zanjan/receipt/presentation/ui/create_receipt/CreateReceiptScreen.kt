package com.saeed.zanjan.receipt.presentation.ui.create_receipt

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.saeed.zanjan.receipt.presentation.components.TopBar
import com.saeed.zanjan.receipt.ui.theme.CustomColors
import com.saeed.zanjan.receipt.ui.theme.NewReceiptCreatorTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.gmail.hamedvakhide.compose_jalali_datepicker.JalaliDatePickerDialog
import com.saeed.zanjan.receipt.R
import com.saeed.zanjan.receipt.domain.models.GeneralReceipt
import com.saeed.zanjan.receipt.presentation.components.BottomBar
import com.saeed.zanjan.receipt.presentation.components.ConfectioneryFields
import com.saeed.zanjan.receipt.presentation.components.ExitDialog
import com.saeed.zanjan.receipt.presentation.components.JewelryFields
import com.saeed.zanjan.receipt.presentation.components.LaundryFields
import com.saeed.zanjan.receipt.presentation.components.OtherJobsFields
import com.saeed.zanjan.receipt.presentation.components.PhotographyFields
import com.saeed.zanjan.receipt.presentation.components.ReceiptCard
import com.saeed.zanjan.receipt.presentation.components.RepairFields
import com.saeed.zanjan.receipt.presentation.components.StatusDialog
import com.saeed.zanjan.receipt.presentation.components.TailoringFields
import com.saeed.zanjan.receipt.presentation.navigation.Screen
import com.saeed.zanjan.receipt.utils.NumberFormat
import ir.huri.jcal.JalaliCalendar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateReceiptScreen(
    navController: NavController,
    viewModel: CreateReceiptViewModel,
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var generalReceipt by remember{mutableStateOf( GeneralReceipt())}

    val loading = viewModel.loading.value
    val dataSaveStatus = viewModel.dataSaveStatus.value
    val savedReceiptId=viewModel.savedReceiptId.value



    val openReceiveDateDialog = remember { mutableStateOf(false) }
    val openDeliveryDateDialog = remember { mutableStateOf(false) }
    val openStatusDialog = remember { mutableStateOf(false) }
    val openExitDialog = remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.restartState()
        }
    }
    var saveClicked by remember {
        mutableStateOf(false)
    }


    var status by remember {
        mutableStateOf(0)
    }

    var receiveDate by remember {
        mutableStateOf("") }

    var deliveryDate by remember {
        mutableStateOf("")
    }
    var customerName by remember {
        mutableStateOf("")
    }
    var phoneNumber by remember {
        mutableStateOf("")
    }
    var productName by remember {
        mutableStateOf("")
    }

    var totalAmount by remember { mutableStateOf( TextFieldValue("0")) }
    var payedAmount by remember { mutableStateOf( TextFieldValue("0")) }


    //repair
    var productProblem by remember {
        mutableStateOf("")
    }
    var risks by remember {
        mutableStateOf("")
    }
    var accessories by remember {
        mutableStateOf("")
    }

    //tailoring
    var details by remember {
        mutableStateOf("")
    }
    var sizes by remember {
        mutableStateOf("")
    }

    //jewelry
    var jewelryProductProblem by remember {
        mutableStateOf("")
    }
    var explainOfOrder by remember {
        mutableStateOf("")
    }
    var detailsOfJewelry by remember {
        mutableStateOf("")
    }

    //photography
    var photoNumber by remember {
        mutableStateOf("")
    }
    var photoSize by remember {
        mutableStateOf("")
    }

    //laundry
    var typeOfLaundryOrder by remember {
        mutableStateOf("")
    }
    var detailsOfLaundryOrder by remember {
        mutableStateOf("")
    }

    //confectionery
    var explainOfConfectioneryOrder by remember {
        mutableStateOf("")
    }
    var detailsOfConfectioneryOrder by remember {
        mutableStateOf("")
    }
    var weightOfOrder by remember {
        mutableStateOf("")
    }

    //otherJobs
    var countOfOrder by remember {
        mutableStateOf("")
    }
    var detailsOfOtherOrder by remember {
        mutableStateOf("")
    }

    NewReceiptCreatorTheme(
        displayProgressBar = loading,
        themColor = CustomColors.lightBlue
    ) {

        LaunchedEffect(key1 = dataSaveStatus){
            if(dataSaveStatus){
                val route = Screen.Receipt.route + "/${savedReceiptId}/${true}/${false}/${false}/${false}"
                navController.navigate(route){
                    popUpTo(Screen.Home.route){
                        inclusive=false
                    }

                }
            }

        }


        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            containerColor = CustomColors.lightBlue,
            topBar = {
                TopBar(
                    onBackClicked = {
                        if (dataSaveStatus)
                            navController.popBackStack()
                        else
                            openExitDialog.value = true
                    }
                )
            },
            bottomBar = {
                    CrateReceiptBottomBar(
                        dataSaveStatus = dataSaveStatus,
                        status = status!!,
                        saveData = {
                            saveClicked = true
                            if (phoneNumber == "" || phoneNumber!!.length < 11) {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("لطفا شماره تلفن را به درستی وارد کنید")

                                }
                            } else {
                                 generalReceipt = GeneralReceipt(
                                    status = status,
                                    name = customerName,
                                    phone = phoneNumber,
                                    orderName = productName,
                                    deliveryTime = deliveryDate,
                                    receiptTime = receiveDate,
                                    cost = totalAmount!!.text,
                                    prepayment = payedAmount!!.text,
                                    confectioneryOrderSpecification =explainOfConfectioneryOrder,
                                    confectioneryOrderWeight =weightOfOrder,
                                    confectioneryDescription =detailsOfConfectioneryOrder,
                                    jewelryOrderSpecification =explainOfOrder,
                                    jewelryLoanerProblems =jewelryProductProblem,
                                    jewelryLoanerSpecification =detailsOfJewelry,
                                    laundryOrderType =typeOfLaundryOrder,
                                    laundryDescription =detailsOfLaundryOrder,
                                    otherJobsDescription =detailsOfOtherOrder,
                                    otherJobsOrderNumber =countOfOrder,
                                    photographyOrderSize =photoSize,
                                    photographyOrderNumber =photoNumber,
                                    repairLoanerProblems =productProblem,
                                    repairRisks =risks,
                                    repairAccessories =accessories,
                                    tailoringOrderSpecification =details,
                                    tailoringSizes =sizes
                                )
                                viewModel.saveInDatabase(
                                    generalReceipt=generalReceipt,
                                    snackbarHostState = snackbarHostState,
                                )

                            }

                        },
                        openStatusDialog = {
                            openStatusDialog.value = true
                        }
                    )

            }
        ) {

            if (openExitDialog.value) {
                ExitDialog(onDismiss = {

                    openExitDialog.value = false
                },
                    onExitClicked = {
                        openExitDialog.value = false
                        navController.popBackStack()
                    }
                )

            }


            if (openStatusDialog.value) {
                StatusDialog(
                    onDismiss = {
                        openStatusDialog.value = false
                    },
                    onStatusSelected = { selectedStatus ->
                        status = selectedStatus
                        openStatusDialog.value = false

                    }
                )
            }



            JalaliDatePickerDialog(
                openDialog = openDeliveryDateDialog,
                initialDate = JalaliCalendar(1402, 1, 2),
                onSelectDay = { //it:JalaliCalendar
                },
                onConfirm = {
                    deliveryDate = "${it.day}/ ${it.month} /${it.year}"

                },
                backgroundColor = CustomColors.lightGray,
                textColor = CustomColors.darkPurple
            )
            JalaliDatePickerDialog(
                openDialog = openReceiveDateDialog,
                initialDate = JalaliCalendar(1402, 1, 2),
                onSelectDay = { //it:JalaliCalendar
                },
                onConfirm = {
                    receiveDate = "${it.day}/ ${it.month} /${it.year}"
                },
                backgroundColor = CustomColors.lightGray,
                textColor = CustomColors.darkPurple
            )
                Card(
                    modifier = Modifier
                        .padding(
                            start = 25.dp,
                            end = 25.dp,
                            top = it.calculateTopPadding(),
                            bottom = 16.dp
                        )
                        .fillMaxSize(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                        contentColor = CustomColors.lightBlue,
                    )

                ) {
                    Column(
                        modifier = Modifier
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding()
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Column {
                                Text(
                                    modifier = Modifier,
                                    text = "تاریخ دریافت",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = CustomColors.bitterDarkPurple
                                )
                                OutlinedTextField(
                                    modifier = Modifier
                                        .width(150.dp)
                                        .clickable {
                                            openReceiveDateDialog.value = true
                                        },
                                    shape = RoundedCornerShape(30.dp),
                                    readOnly = true,
                                    enabled = false,
                                    value = receiveDate!!,
                                    onValueChange = { receive ->
                                        receiveDate = receive
                                    },
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.calendar),
                                            tint = CustomColors.gray,
                                            contentDescription = null
                                        )
                                    },
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        disabledTextColor = CustomColors.darkPurple,
                                        containerColor = CustomColors.transparentLightGray,
                                        cursorColor = CustomColors.lightBlue,
                                        focusedBorderColor = CustomColors.lightBlue,
                                        unfocusedBorderColor = CustomColors.lightGray
                                    )
                                )

                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Column {
                                Text(
                                    modifier = Modifier,
                                    text = "موعد تحویل",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = CustomColors.bitterDarkPurple
                                )
                                OutlinedTextField(
                                    modifier = Modifier
                                        .width(150.dp)
                                        .clickable {
                                            openDeliveryDateDialog.value = true
                                        },
                                    shape = RoundedCornerShape(30.dp),
                                    readOnly = true,
                                    enabled = false,
                                    value = deliveryDate!!,
                                    onValueChange = { delivery ->
                                        deliveryDate = delivery
                                    },
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.calendar_checked),
                                            tint = CustomColors.gray,
                                            contentDescription = null
                                        )
                                    },
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        disabledTextColor = CustomColors.darkPurple,
                                        containerColor = CustomColors.transparentLightGray,
                                        cursorColor = CustomColors.lightBlue,
                                        focusedBorderColor = CustomColors.lightBlue,
                                        unfocusedBorderColor = CustomColors.lightGray
                                    )
                                )
                            }

                        }
                        Divider(
                            color = CustomColors.lightGray,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .padding(horizontal = 5.dp)
                        )
                        Column {
                            Text(
                                modifier = Modifier,
                                text = "نام مشتری",
                                style = MaterialTheme.typography.bodyMedium,
                                color = CustomColors.bitterDarkPurple
                            )
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                singleLine = true,
                                value = customerName,
                                shape = RoundedCornerShape(30.dp),
                                onValueChange = { name ->
                                    customerName = name
                                },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.user),
                                        tint = CustomColors.gray,
                                        contentDescription = null
                                    )
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = CustomColors.darkPurple,
                                    containerColor = CustomColors.transparentLightGray,
                                    cursorColor = CustomColors.lightBlue,
                                    focusedBorderColor = CustomColors.lightBlue,
                                    unfocusedBorderColor = CustomColors.lightGray
                                )
                            )

                        }
                        Column {
                            Text(
                                modifier = Modifier,
                                text = "شماره تلفن",
                                style = MaterialTheme.typography.bodyMedium,
                                color = CustomColors.bitterDarkPurple
                            )
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                singleLine = true,
                                isError = (phoneNumber!!.isEmpty() || phoneNumber!!.length < 11) && saveClicked,
                                shape = RoundedCornerShape(30.dp),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                value = phoneNumber!!,
                                onValueChange = { phone ->
                                    phoneNumber = phone
                                },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.call),
                                        tint = CustomColors.gray,
                                        contentDescription = null
                                    )
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = CustomColors.darkPurple,
                                    containerColor = CustomColors.transparentLightGray,
                                    cursorColor = CustomColors.lightBlue,
                                    focusedBorderColor = CustomColors.lightBlue,
                                    unfocusedBorderColor = CustomColors.lightGray
                                )
                            )

                        }
                        Column {
                            Text(
                                modifier = Modifier,
                                text = "نام سفارش",
                                style = MaterialTheme.typography.bodyMedium,
                                color = CustomColors.bitterDarkPurple
                            )
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                value = productName!!,
                                singleLine = true,
                                shape = RoundedCornerShape(30.dp),
                                onValueChange = { pName ->
                                    productName = pName
                                },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.cart_3),
                                        tint = CustomColors.gray,
                                        contentDescription = null
                                    )
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = CustomColors.darkPurple,
                                    containerColor = CustomColors.transparentLightGray,
                                    cursorColor = CustomColors.lightBlue,
                                    focusedBorderColor = CustomColors.lightBlue,
                                    unfocusedBorderColor = CustomColors.lightGray
                                )
                            )

                        }
                        Divider(
                            color = CustomColors.lightGray,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .padding(horizontal = 5.dp)
                        )
                        when (viewModel.receiptCategory) {
                            0 -> {
                                RepairFields(
                                    productProblem = productProblem!!,
                                    risks = risks!!,
                                    accessories = accessories!!,
                                    pProblemSetValue = {
                                        productProblem = it
                                    },
                                    risksSetValue = {
                                        risks = it
                                    },
                                    accessoriesSetValue = {
                                        accessories = it
                                    }

                                )
                            }

                            1 -> {
                                RepairFields(
                                    productProblem = productProblem!!,
                                    risks = risks!!,
                                    accessories = accessories!!,
                                    pProblemSetValue = {
                                        productProblem = it
                                    },
                                    risksSetValue = {
                                        risks = it
                                    },
                                    accessoriesSetValue = {
                                        accessories = it
                                    }
                                )
                            }

                            2 -> {
                                RepairFields(
                                    productProblem = productProblem!!,
                                    risks = risks!!,
                                    accessories = accessories!!,
                                    pProblemSetValue = {
                                        productProblem = it
                                    },
                                    risksSetValue = {
                                        risks = it
                                    },
                                    accessoriesSetValue = {
                                        accessories = it
                                    }
                                )

                            }

                            3 -> {
                                TailoringFields(
                                    details = details!!,
                                    sizes = sizes!!,
                                    detailsSetValue = {
                                        details = it
                                    },
                                    sizesSetValue = {
                                        sizes = it
                                    }
                                )
                            }

                            4 -> {
                                JewelryFields(
                                    jewelryProblem = jewelryProductProblem!!,
                                    explainOfOrder = explainOfOrder!!,
                                    detailsOfProduct = detailsOfJewelry!!,
                                    jewelryProblemSetValue = {
                                        jewelryProductProblem = it
                                    },
                                    explainOfOrderSetValue = {
                                        explainOfOrder = it
                                    },
                                    detailOfProductSetValue = {
                                        detailsOfJewelry = it
                                    }
                                )
                            }

                            5 -> {
                                PhotographyFields(
                                    photoNumber = photoNumber!!,
                                    photoSize = photoSize!!,
                                    photoNumberSetValue = {
                                        phoneNumber = it
                                    },
                                    photoSizeSetValue = {
                                        photoSize = it
                                    }
                                )
                            }

                            6 -> {
                                LaundryFields(
                                    typeOfLaundryOrder = typeOfLaundryOrder!!,
                                    detailsOfLaundryOrder = detailsOfLaundryOrder!!,
                                    typeOfLaundryOrderSetValue = {
                                        typeOfLaundryOrder = it
                                    },
                                    detailsOfLaundryOrderSetValue = {
                                        detailsOfLaundryOrder = it
                                    }
                                )
                            }

                            7 -> {
                                ConfectioneryFields(
                                    explainOfConfectioneryOrder = explainOfConfectioneryOrder!!,
                                    detailsOfConfectioneryOrder = detailsOfConfectioneryOrder!!,
                                    weightOfOrder = weightOfOrder!!,
                                    explainOfConfectioneryOrderSetValue = {
                                        explainOfConfectioneryOrder = it
                                    },
                                    detailsOfConfectioneryOrderSetValue = {
                                        detailsOfConfectioneryOrder = it
                                    },
                                    weightOfOrderSetValue = {
                                        weightOfOrder = it
                                    }
                                )
                            }

                            8 -> {
                                OtherJobsFields(
                                    countOfOrder = countOfOrder!!,
                                    detailsOfOtherOrder = detailsOfOtherOrder!!,
                                    countOfOrderSetValue = {
                                        countOfOrder = it
                                    },
                                    detailsOfOtherOrderSetValue = {
                                        detailsOfOtherOrder = it
                                    }
                                )
                            }

                        }

                        Divider(
                            color = CustomColors.lightGray,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .padding(horizontal = 5.dp)
                        )
                        Column {
                            Text(
                                modifier = Modifier,
                                text = "مبلغ کل اعلامی(تومان)",
                                style = MaterialTheme.typography.bodyMedium,
                                color = CustomColors.bitterDarkPurple
                            )
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {

                                    },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                value = totalAmount!!,
                                shape = RoundedCornerShape(30.dp),
                                onValueChange = { tAmount ->
                                    totalAmount = NumberFormat.formatNumber(tAmount)
                                },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.credit_card),
                                        tint = CustomColors.gray,
                                        contentDescription = null
                                    )
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = CustomColors.darkPurple,
                                    containerColor = CustomColors.transparentLightGray,
                                    cursorColor = CustomColors.lightBlue,
                                    focusedBorderColor = CustomColors.lightBlue,
                                    unfocusedBorderColor = CustomColors.lightGray
                                )
                            )

                        }
                        Column {
                            Text(
                                modifier = Modifier,
                                text = "مبلغ پرداخت شده(تومان)",
                                style = MaterialTheme.typography.bodyMedium,
                                color = CustomColors.bitterDarkPurple
                            )
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {

                                    },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                value = payedAmount!!,
                                shape = RoundedCornerShape(30.dp),
                                onValueChange = { pAmount ->
                                    payedAmount = NumberFormat.formatNumber(pAmount)
                                },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.paied_card),
                                        tint = CustomColors.gray,
                                        contentDescription = null
                                    )
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = CustomColors.darkPurple,
                                    containerColor = CustomColors.transparentLightGray,
                                    cursorColor = CustomColors.lightBlue,
                                    focusedBorderColor = CustomColors.lightBlue,
                                    unfocusedBorderColor = CustomColors.lightGray
                                )
                            )

                        }
                        Spacer(modifier = Modifier.size(90.dp))

                    }
                }


        }


    }
    BackHandler {
        if (dataSaveStatus)
            navController.popBackStack()
        else
            openExitDialog.value = true

    }
}






@Composable
fun CrateReceiptBottomBar(
    dataSaveStatus: Boolean,
    status: Int,
    saveData: () -> Unit,
    openStatusDialog: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            modifier = Modifier.padding(30.dp),
            elevation = CardDefaults.cardElevation(20.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = Color.White
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 5.dp)
                    .wrapContentWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Column {
                    Button(
                        enabled = !dataSaveStatus,
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(50.dp)
                            .padding(2.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CustomColors.lightGray
                        ),
                        onClick = {
                            saveData()
                        },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.checked),
                            tint = CustomColors.darkPurple,
                            contentDescription = null
                        )
                    }

                }


                Spacer(modifier = Modifier.size(30.dp))
                Column {
                    Button(
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(50.dp)
                            .padding(3.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = when (status) {
                                0 -> CustomColors.inProses
                                1 -> CustomColors.delivered
                                2 -> CustomColors.problem
                                3 -> CustomColors.readyForDelivery
                                else -> CustomColors.gray
                            }
                        ),
                        onClick = {
                            openStatusDialog()
                        },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.chart),
                            tint = CustomColors.darkPurple,
                            contentDescription = null
                        )
                    }

                }


            }
        }
    }

}
