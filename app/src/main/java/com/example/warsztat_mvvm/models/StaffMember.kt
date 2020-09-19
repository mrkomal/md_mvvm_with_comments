package com.example.warsztat_mvvm.models


//Model danych opartych na dokumentacji API
data class StaffListResult(val staffMembers: List<StaffMember>)

data class StaffMember(
    val name: String?,
    val age: String?,
    val specialization: String?,
    val price: String?,
    val availability: Boolean?
)