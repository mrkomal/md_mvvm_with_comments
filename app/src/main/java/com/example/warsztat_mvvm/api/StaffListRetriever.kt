package com.example.warsztat_mvvm.api

import com.example.warsztat_mvvm.models.StaffListResult
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StaffListRetriever {
    private val service: MedicalListService

    companion object {
        //Companion object to nasz stary przyjaciel z wykladow - coś a'la singleton
        var BASE_URL = "https://medicalstaffapi-agh.herokuapp.com/"
    }

    init {
        val retrofit = Retrofit.Builder()
            //1
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            //2
            .build()
        //3
        service = retrofit.create(MedicalListService::class.java)
    }

    fun getRepositories(callback: Callback<StaffListResult>) {
        //4
        service.retrieveMedicalStaff().enqueue(callback)
    }
}