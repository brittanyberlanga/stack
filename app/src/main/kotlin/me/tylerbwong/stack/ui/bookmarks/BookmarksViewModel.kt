package me.tylerbwong.stack.ui.bookmarks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.tylerbwong.stack.api.model.Question
import me.tylerbwong.stack.data.auth.AuthRepository
import me.tylerbwong.stack.data.repository.QuestionRepository
import me.tylerbwong.stack.data.repository.SiteRepository
import me.tylerbwong.stack.ui.BaseViewModel

class BookmarksViewModel @ViewModelInject constructor(
    private val authRepository: AuthRepository,
    private val siteRepository: SiteRepository,
    private val questionRepository: QuestionRepository
) : BaseViewModel() {

    internal val bookmarks: LiveData<List<Question>>
        get() = mutableBookmarks
    private val mutableBookmarks = MutableLiveData<List<Question>>()

    private val isAuthenticated: Boolean
        get() = authRepository.isAuthenticated

    internal val siteLiveData: LiveData<String>
        get() = siteRepository.siteLiveData

    internal fun fetchBookmarks() {
        if (isAuthenticated) {
            launchRequest {
                mutableBookmarks.value = questionRepository.getBookmarks()
            }
        }
    }
}
