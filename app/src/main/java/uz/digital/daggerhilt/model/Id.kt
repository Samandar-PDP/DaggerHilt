package uz.digital.daggerhilt.model

import com.google.gson.annotations.SerializedName

data class Id(
    @SerializedName("id")
    val code: Int
)