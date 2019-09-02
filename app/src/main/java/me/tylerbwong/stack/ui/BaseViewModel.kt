package me.tylerbwong.stack.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.tylerbwong.stack.ui.utils.SingleLiveEvent
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {
    val refreshing: LiveData<Boolean>
        get() = _refreshing
    private val _refreshing = MutableLiveData<Boolean>()

    val snackbar: LiveData<Unit?>
        get() = _snackbar
    protected val _snackbar = SingleLiveEvent<Unit?>()

    protected fun launchRequest(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch {
            try {
                _refreshing.value = true
                _snackbar.value = null
                block()
            } catch (exception: Exception) {
                Timber.e(exception)
                _snackbar.value = Unit
            } finally {
                _refreshing.value = false
            }
        }
    }

    companion object {
        const val SHARE_TEXT_TYPE = "text/plain"
    }
}
