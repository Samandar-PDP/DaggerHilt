package uz.digital.daggerhilt.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    val userId: Int = 0,
    val id: Int = 0,
    val title: String,
    val body: String
): Parcelable
