package com.example.warsztat_mvvm

import android.util.Log
import com.example.warsztat_mvvm.adapters.MedicalStaffListAdapter
import com.example.warsztat_mvvm.api.StaffListRetriever
import com.example.warsztat_mvvm.models.StaffListResult
import com.example.warsztat_mvvm.repository.MainRepository
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call

import retrofit2.Callback
import retrofit2.Response

interface ViewModelCallbackSupporting {
    fun onListFetchedSuccessful(data: StaffListResult?)
}
//klasa MainActivityViewModel przyjmuje obiekt typu ViewModelCallbackSupporting
class MainActivityViewModel(delegate: ViewModelCallbackSupporting) {

    //stworzenie nowego retrivera z package api
    private val apiRetriever = StaffListRetriever()
    var title = "Medical Staff Available"

    //Callback?
    private val onDataRetrieved = object : Callback<StaffListResult> {
        //Call?
        override fun onFailure(call: Call<StaffListResult>?, t: Throwable?) {
            Log.e("MainActivity", "Problem calling MedStaff API ${t?.message}")
        }

        override fun onResponse(
            //Call?
            call: Call<StaffListResult>?,
            response: Response<StaffListResult>?
        ) {
            response?.isSuccessful.let {
                val responseData = response?.body()
               delegate.onListFetchedSuccessful(responseData)
            }
        }
    }

    fun getMedicalStaffData() {
        MainRepository(apiRetriever).getMedicalStaff(onDataRetrieved)
    }
}