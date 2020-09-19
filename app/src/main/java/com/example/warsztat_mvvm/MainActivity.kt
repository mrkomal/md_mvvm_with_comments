package com.example.warsztat_mvvm

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.warsztat_mvvm.adapters.MedicalStaffListAdapter
import com.example.warsztat_mvvm.api.StaffListRetriever
import com.example.warsztat_mvvm.models.StaffListResult
import kotlinx.android.synthetic.main.activity_main.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val staffRetriever = StaffListRetriever()

    private val callback = object : Callback<StaffListResult> {
        override fun onFailure(call: Call<StaffListResult>?, t: Throwable?) {
            Log.e("MainActivity", "Problem calling MedStaff API ${t?.message}")
        }

        override fun onResponse(call: Call<StaffListResult>?, response: Response<StaffListResult>?) {
            response?.isSuccessful.let {
                val resultList = StaffListResult(response?.body()?.staffMembers ?: emptyList())
                medicalStaffList.adapter = MedicalStaffListAdapter(resultList)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTitle("Lista")

        if (isNetworkConnected()) {
            staffRetriever.getRepositories(callback)
        } else {
            AlertDialog.Builder(this).setTitle("No Internet Connection")
                .setMessage("Please check your internet connection and try again")
                .setPositiveButton(android.R.string.ok) { _, _ -> }
                .setIcon(android.R.drawable.ic_dialog_alert).show()
        }

        medicalStaffList.layoutManager = LinearLayoutManager(this)
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager //1
        val networkInfo = connectivityManager.activeNetworkInfo //2
        return networkInfo != null && networkInfo.isConnected //3
    }
}