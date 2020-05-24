package me.tylerbwong.stack.ui.profile

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.tylerbwong.stack.R
import me.tylerbwong.stack.data.model.Question
import me.tylerbwong.stack.data.model.User
import me.tylerbwong.stack.data.network.service.UserService
import me.tylerbwong.stack.ui.BaseViewModel

class ProfileViewModel(private val service: UserService) : BaseViewModel() {

    internal var userId: Int? = null
    private var user: User? = null

    internal val userData: LiveData<User>
        get() = _userData
    private val _userData = MutableLiveData<User>()

    internal val questionsData: LiveData<List<Question>>
        get() = _questionsData
    private val _questionsData = MutableLiveData<List<Question>>()

    internal fun getUserQuestionsAndAnswers() {
        launchRequest {
            _userData.value = service.getUser(userId).items.firstOrNull()
            _questionsData.value = service.getUserQuestionsById(userId).items
            user = questionsData.value?.firstOrNull()?.owner
        }
    }

    internal fun startShareIntent(context: Context) {
        user?.let {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = SHARE_TEXT_TYPE
                putExtra(Intent.EXTRA_SUBJECT, it.displayName)
                putExtra(Intent.EXTRA_TEXT, it.link)
            }
            context.startActivity(Intent.createChooser(intent, context.getString(R.string.share)))
        }
    }
}
