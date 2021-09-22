package com.andrefpc.data

sealed class UIState {
    object Loading : UIState()
    object Error : UIState()
    class Success(val children: List<RedditChild>) : UIState()
}
