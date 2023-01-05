package uz.digital.daggerhilt.presentation.add_update

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.digital.daggerhilt.repository.PostRepository
import uz.digital.daggerhilt.util.Response
import javax.inject.Inject

@HiltViewModel
class AddUpdateViewModel @Inject constructor(
    private val repository: PostRepository
): ViewModel() {
    private val _state: MutableStateFlow<AddUpdateState> = MutableStateFlow(AddUpdateState.Idle)
    val state: StateFlow<AddUpdateState> get() = _state

    fun onEvent(event: AddUpdateEvent) {
        when(event) {
            is AddUpdateEvent.OnGetOnePost -> {
                viewModelScope.launch {
                    repository.getPostById(event.id).collect { response ->
                        when(response) {
                            is Response.Loading -> {
                                _state.update {
                                    AddUpdateState.Loading
                                }
                            }
                            is Response.Error -> {
                                _state.update {
                                    AddUpdateState.Error(response.error)
                                }
                            }
                            is Response.Success -> {
                                _state.update {
                                    AddUpdateState.SuccessPostGot(response.data)
                                }
                            }
                        }
                    }
                }
            }
            is AddUpdateEvent.OnCreatePost -> {
                viewModelScope.launch {
                    repository.createPost(event.post).collect {
                        _state.update {
                            AddUpdateState.Loading
                        }
                        delay(400L)
                        if (it) {
                            _state.update {
                                AddUpdateState.SuccessCreated
                            }
                        }
                    }
                }
            }
            is AddUpdateEvent.OnUpdatePost -> {
                viewModelScope.launch {
                    repository.updatePost(event.id).collect {
                        _state.update {
                            AddUpdateState.Loading
                        }
                        delay(400L)
                        if (it) {
                            _state.update {
                                AddUpdateState.SuccessUpdated
                            }
                        }
                    }
                }
            }
        }
    }
}