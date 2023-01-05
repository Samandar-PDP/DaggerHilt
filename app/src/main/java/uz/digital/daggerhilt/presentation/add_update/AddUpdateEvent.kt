package uz.digital.daggerhilt.presentation.add_update

import uz.digital.daggerhilt.model.Post

sealed class AddUpdateEvent {
    data class OnGetOnePost(val id: Int): AddUpdateEvent()
    data class OnUpdatePost(val id: Int): AddUpdateEvent()
    data class OnCreatePost(val post: Post): AddUpdateEvent()
}