package com.example.warsztat_mvvm.api

import com.example.warsztat_mvvm.models.StaffListResult
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StaffListRetriever {
    private val service: MedicalListService

    //'static object' - przywiązuje się do klasy,a nie do instancji
    companion object {
        //Companion object to nasz stary przyjaciel z wykladow - coś a'la singleton
        var BASE_URL = "https://medicalstaffapi-agh.herokuapp.com/"
    }

    init {
        //Retrofit - odkoduje przechowywane dane
        val retrofit = Retrofit.Builder()
            //1
            .baseUrl(BASE_URL)
                //GsonConverterFactory, kierunek: deserializacja Json z uzyciem Gson
            // json - text format that is completely language independent
            // Gson - biblioteka do serializacji i deserializacji obiektow Java do formatu JSON
            .addConverterFactory(GsonConverterFactory.create())
            //2
            .build()
        //3
        service = retrofit.create(MedicalListService::class.java)
    }

    //Callback?
    fun getMedicalStaff(callback: Callback<StaffListResult>) {
        //4
        service.retrieveMedicalStaff().enqueue(callback)
    }
}