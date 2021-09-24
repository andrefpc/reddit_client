package com.andrefpc.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andrefpc.data.ApiResult
import com.andrefpc.data.UIState
import com.andrefpc.repository.RedditRepository
import com.andrefpc.util.CoroutineContextProvider
import kotlinx.coroutines.launch

class MainViewModel(
    private val dispatchers: CoroutineContextProvider,
    private val redditRepository: RedditRepository
): ViewModel() {
    private val _uiState = MutableLiveData<UIState>()
    val uiState: LiveData<UIState> get() = _uiState

    fun getPosts( lastItemName: String? = null, refresh: Boolean){
        if(refresh) _uiState.value = UIState.Loading
        viewModelScope.launch(dispatchers.IO) {
            when (
                val result = redditRepository.getPosts(lastItemName)
            ) {
                is ApiResult.Error -> {
                    if(refresh) _uiState.postValue(UIState.Error)
                }
                is ApiResult.Success -> {
                    result.result?.let {
                        if(refresh) _uiState.postValue(UIState.RefreshList(it))
                        else _uiState.postValue(UIState.AddList(it))
                    } ?: kotlin.run {
                        if(refresh) _uiState.postValue(UIState.Error)
                    }
                }
            }
        }
    }
}