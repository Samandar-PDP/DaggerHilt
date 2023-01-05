package uz.digital.daggerhilt.presentation.detail

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
class DetailViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {
    private val _state: MutableStateFlow<DetailState> = MutableStateFlow(DetailState.Loading)
    val state: StateFlow<DetailState> get() = _state

    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.OnGetOnePost -> {
                viewModelScope.launch {
                    repository.getPostById(event.id).collect { response ->
                        when (response) {
                            is Response.Loading -> {
                                _state.update {
                                    DetailState.Loading
                                }
                            }
                            is Response.Error -> {
                                _state.update {
                                    DetailState.Error(response.error)
                                }
                            }
                            is Response.Success -> {
                                _state.update {
                                    DetailState.Success(response.data)
                                }
                            }
                        }
                    }
                }
            }
            is DetailEvent.OnDelete -> {
                viewModelScope.launch {
                    repository.deletePost(event.id).collect {
                        _state.update {
                            DetailState.Loading
                        }
                        delay(500L)
                        if (it) {
                            _state.update {
                                DetailState.SuccessDeleted
                            }
                        }
                    }
                }
            }
        }
    }
}