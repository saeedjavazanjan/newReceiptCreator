package com.saeed.zanjan.receipt.presentation.ui.home

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
   @Inject constructor(
       sharedPreferences: SharedPreferences
   ) :ViewModel() {

       val receiptCategory=sharedPreferences.getInt("JOB_SUBJECT",-1)



    fun getListOfReceipts(){




    }

   }
