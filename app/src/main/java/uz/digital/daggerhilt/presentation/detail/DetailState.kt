package uz.digital.daggerhilt.presentation.detail

import uz.digital.daggerhilt.model.Post

sealed class DetailState {
    object Loading : DetailState()
    data class Error(val message: String) : DetailState()
    object SuccessDeleted: DetailState()
    data class Success(val post: Post) : DetailState()
}