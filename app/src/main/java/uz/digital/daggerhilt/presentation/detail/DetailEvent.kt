package uz.digital.daggerhilt.presentation.detail

sealed class DetailEvent {
    data class OnGetOnePost(val id: Int): DetailEvent()
    data class OnDelete(val id: Int): DetailEvent()
}
