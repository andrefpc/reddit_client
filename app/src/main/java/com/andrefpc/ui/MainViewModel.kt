package com.andrefpc.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andrefpc.data.ApiResult
import com.andrefpc.data.UIState
import com.andrefpc.repository.RedditRepository
import com.andrefpc.util.CoroutineContextProvider
import com.andrefpc.util.image.ImageUtil
import kotlinx.coroutines.launch

class MainViewModel(
    private val dispatchers: CoroutineContextProvider,
    private val redditRepository: RedditRepository,
    private val imageUtil: ImageUtil
): ViewModel() {
    /**
     * This param will be saved when the opened post have an image to Download
     */
    var currentImageUrl: String? = null

    /**
     * LiveData to change the UiState
     */
    private val _uiState = MutableLiveData<UIState>()
    val uiState: LiveData<UIState> get() = _uiState

    /**
     * Get the list of posts from the api to replace the current list
     * or to be added in the end of the list
     * @param lastItemName The last post name from the loaded list to pagination (non-required)
     * @param refresh Param to verify if the list will replace the current one or just to be added
     */
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

    /**
     * Save the current opened image on gallery
     */
    fun saveCurrentImage(){
        viewModelScope.launch(dispatchers.IO) {
            currentImageUrl?.let {  imageUtil.saveImageOnGallery(it) }
        }
    }


}