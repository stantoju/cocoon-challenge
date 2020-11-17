package com.cocoon.cocoon_challenge.ui.top_stories

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.cocoon.cocoon_challenge.data.model.Story
import com.cocoon.cocoon_challenge.repository.StoryRepository
import com.cocoon.cocoon_challenge.util.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class StoryViewmodel @ViewModelInject constructor(
    private val storyRepository: StoryRepository,
    @Assisted private val stateHandle: SavedStateHandle
) : ViewModel() {

    private val  _storyDataState: MutableLiveData<DataState<List<Story>>> = MutableLiveData()
    val storyDataState: LiveData<DataState<List<Story>>>
        get() = _storyDataState


    private val  _bookmarkDataState: MutableLiveData<DataState<List<Story>>> = MutableLiveData()
    val bookmarkDataState: LiveData<DataState<List<Story>>>
        get() = _bookmarkDataState


    private val  _bookmarked: MutableLiveData<Boolean> = MutableLiveData()
    val bookmarked: LiveData<Boolean>
        get() = _bookmarked

    /**
     *  Request Stories function
     *  Param: StoryStateEvent (From below sealed class)
     */
    fun setStoryStateEvent(event: StoryStateEvent){
        viewModelScope.launch {
            when(event){
                is StoryStateEvent.GetStoriesEvent -> {
                    storyRepository.getStory()
                        .onEach { dataState ->
                            _storyDataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                is StoryStateEvent.None -> {
                    // Something for none
                }
            }
        }
    }



    /**
     *  Request Bookmark function
     *  Param: StoryStateEvent (From below sealed class)
     */
    fun setBookmarkStateEvent(event: BookmarkStateEvent){
        viewModelScope.launch {
            when(event){
                is BookmarkStateEvent.GetBookmarkStateEvent -> {
                    storyRepository.getBBookMark()
                        .onEach { dataState ->
                            _bookmarkDataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                is BookmarkStateEvent.None -> {
                    // Something for none
                }
            }
        }
    }

    fun setBookmark(story_id: Int){
        viewModelScope.launch {
            storyRepository.bookMarkItem(story_id)
            _bookmarked.value = true
        }
    }

    fun reverseBookmarkLiveData(){
        _bookmarked.value = false
    }

}

sealed class StoryStateEvent{
    object GetStoriesEvent: StoryStateEvent()
    object None: StoryStateEvent()
}

sealed class BookmarkStateEvent{
    object GetBookmarkStateEvent: BookmarkStateEvent()
    object SetBookmarkStateEvent: BookmarkStateEvent()
    object None: BookmarkStateEvent()
}