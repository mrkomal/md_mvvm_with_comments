package com.example.warsztat_mvvm

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.warsztat_mvvm.adapters.MedicalStaffListAdapter
import com.example.warsztat_mvvm.models.StaffListResult
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ViewModelCallbackSupporting {

    val viewModel = MainActivityViewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTitle(viewModel.title)

        if (isNetworkConnected()) {
            viewModel.getMedicalStaffData()
        } else {
           showInternetErrorDialog()
        }
        medicalStaffList.layoutManager = LinearLayoutManager(this)
    }

    override fun onListFetchedSuccessful(data: StaffListResult?) {
        val resultList = StaffListResult(data?.staffMembers ?: emptyList())
        medicalStaffList.adapter = MedicalStaffListAdapter(resultList)
    }

    private fun showInternetErrorDialog() {
        AlertDialog.Builder(this).setTitle("No Internet Connection")
            .setMessage("Please check your internet connection and try again")
            .setPositiveButton(android.R.string.ok) { _, _ -> }
            .setIcon(android.R.drawable.ic_dialog_alert).show()
    }

    //This will go to the Manager later
    private fun isNetworkConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager //1
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}