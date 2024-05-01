package com.saeed.zanjan.receipt.presentation.ui.registration

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saeed.zanjan.receipt.domain.models.RegistrationInfo
import com.saeed.zanjan.receipt.interactor.UserRegistration
import com.saeed.zanjan.receipt.network.RetrofitService
import com.saeed.zanjan.receipt.utils.ConnectivityManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel
@Inject
constructor(
    private val userRegistration: UserRegistration,
    private val connectivityManager: ConnectivityManager,

    ):ViewModel() {
    val loading = mutableStateOf(false)
    var onError: ((String) -> Unit)? = null
    private val snackbarQueue = mutableListOf<String>()
    private val _snackbarEvent = MutableStateFlow<String?>(null)
    val snackbarEvent: StateFlow<String?> = _snackbarEvent

    fun enqueueSnackbar(message: String) {
        snackbarQueue.add(message)
        if (_snackbarEvent.value == null) {
            showNextSnackbar()
        }
    }

    private fun showNextSnackbar() {
        if (snackbarQueue.isNotEmpty()) {
            _snackbarEvent.value = snackbarQueue.removeAt(0)
        }
    }

    fun onSnackbarDismissed() {
        _snackbarEvent.value = null
        showNextSnackbar()
    }
    fun registerRequest(
        registrationInfo: RegistrationInfo,
    ){
       userRegistration.sendRegistrationInfoToServer(
           registrationInfo,
           isNetworkAvailable = connectivityManager.isNetworkAvailable.value

       ).onEach { dataState ->
           dataState.loading.let {

           }
           dataState.data?.let {

           }
           dataState.error?.let {
               onError?.invoke(it) // Invoke the lambda function in case of an error

           }
       }.catch {
           onError?.invoke("خطای ناشناخته") // Invoke the lambda function in case of an error

       }.launchIn(viewModelScope)
    }
}