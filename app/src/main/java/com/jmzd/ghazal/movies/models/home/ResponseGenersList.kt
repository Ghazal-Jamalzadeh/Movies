package com.jmzd.ghazal.movies.models.home


import com.google.gson.annotations.SerializedName
import com.jmzd.ghazal.movies.models.home.ResponseGenersList.*

class ResponseGenersList : ArrayList<ResponseGenersListItem>(){
    data class ResponseGenersListItem(
        @SerializedName("id")
        val id: Int?, // 1
        @SerializedName("name")
        val name: String? // Crime
    )
}