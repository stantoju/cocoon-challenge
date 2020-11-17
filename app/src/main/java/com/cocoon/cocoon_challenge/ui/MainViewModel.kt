package com.cocoon.cocoon_challenge.ui

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.cocoon.cocoon_challenge.data.model.Story
import com.cocoon.cocoon_challenge.repository.StoryRepository
import com.cocoon.cocoon_challenge.util.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class MainViewModel
@ViewModelInject
constructor(
    private val storyRepository: StoryRepository,
    @Assisted private val stateHandle: SavedStateHandle
): ViewModel() {

    private val  _dataState: MutableLiveData<DataState<List<Story>>> = MutableLiveData()
    val dataState: LiveData<DataState<List<Story>>>
        get() = _dataState

    fun setStateEvent(mainStateEvent: MainStateEvent){
        viewModelScope.launch {
            when(mainStateEvent){
                is MainStateEvent.GetBlogEvent -> {
                    storyRepository.getStory()
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                is MainStateEvent.None -> {
                    // Something for none
                }
            }
        }
    }

}

sealed class MainStateEvent{
    object GetBlogEvent: MainStateEvent()

    object None: MainStateEvent()
}