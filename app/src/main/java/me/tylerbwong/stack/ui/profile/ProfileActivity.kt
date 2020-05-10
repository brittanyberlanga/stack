package me.tylerbwong.stack.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import coil.api.load
import coil.transform.CircleCropTransformation
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.profile_header.*
import me.tylerbwong.stack.R
import me.tylerbwong.stack.ui.ApplicationWrapper
import me.tylerbwong.stack.ui.BaseActivity
import me.tylerbwong.stack.ui.di.DaggerUiComponent
import me.tylerbwong.stack.ui.questions.QuestionAdapter
import me.tylerbwong.stack.ui.utils.format
import me.tylerbwong.stack.ui.utils.launchCustomTab
import me.tylerbwong.stack.ui.utils.setThrottledOnClickListener
import me.tylerbwong.stack.ui.utils.showSnackbar
import me.tylerbwong.stack.ui.utils.toHtml
import javax.inject.Inject

class ProfileActivity : BaseActivity(R.layout.activity_profile) {

    @Inject
    lateinit var viewModelFactory: ProfileViewModelFactory

    private val viewModel by viewModels<ProfileViewModel> { viewModelFactory }
    private val adapter = QuestionAdapter()
    private var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ApplicationWrapper.uiComponent.inject(this)
        setSupportActionBar(toolbar)

        viewModel.userId = intent.getIntExtra(USER_ID, 0)
        viewModel.refreshing.observe(this) {
            refreshLayout.isRefreshing = it
        }
        viewModel.snackbar.observe(this) {
            if (it != null) {
                snackbar = rootLayout.showSnackbar(R.string.network_error, R.string.retry) {
                    viewModel.getUserQuestionsAndAnswers()
                }
            } else {
                snackbar?.dismiss()
            }
        }
        viewModel.userData.observe(this) {
            userImage.load(it.profileImage) {
                crossfade(true)
                error(R.drawable.user_image_placeholder)
                placeholder(R.drawable.user_image_placeholder)
                transformations(CircleCropTransformation())
            }
            collapsingToolbarLayout.title = it.displayName.toHtml()
            if (it.location != null) {
                location.text = it.location.toHtml()
                location.isVisible = true
            } else {
                location.isGone = true
            }
            reputation.text = it.reputation.toLong().format()
            badgeView.badgeCounts = it.badgeCounts

            it.link?.let { link ->
                userImage.setThrottledOnClickListener {
                    launchCustomTab(this, link)
                }
            }
        }
        viewModel.questionsData.observe(this) {
            adapter.submitList(it)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        recyclerView.apply {
            adapter = this@ProfileActivity.adapter
            layoutManager = LinearLayoutManager(this@ProfileActivity)
        }

        refreshLayout.setOnRefreshListener { viewModel.getUserQuestionsAndAnswers() }

        viewModel.getUserQuestionsAndAnswers()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_share, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.share -> viewModel.startShareIntent(this)
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val USER_ID = "userId"
        private const val IS_FROM_DEEP_LINK = "isFromDeepLink"

        fun startActivity(
            context: Context,
            userId: Int,
            isFromDeepLink: Boolean = false
        ) {
            val intent = Intent(context, ProfileActivity::class.java).apply {
                putExtra(USER_ID, userId)
                putExtra(IS_FROM_DEEP_LINK, isFromDeepLink)
            }
            context.startActivity(intent)
        }
    }
}
