package com.andrefpc.data

sealed class UIState {
    object Loading : UIState()
    object Error : UIState()
    class RefreshList(val children: List<RedditChild>) : UIState()
    class AddList(val children: List<RedditChild>) : UIState()
}
