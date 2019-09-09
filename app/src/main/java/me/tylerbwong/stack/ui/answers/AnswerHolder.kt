package me.tylerbwong.stack.ui.answers

import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.answer_holder.*
import kotlinx.android.synthetic.main.user_view.*
import me.saket.bettermovementmethod.BetterLinkMovementMethod
import me.tylerbwong.stack.R
import me.tylerbwong.stack.ui.utils.DynamicViewHolder
import me.tylerbwong.stack.ui.utils.GlideApp
import me.tylerbwong.stack.ui.utils.inflateWithoutAttaching
import me.tylerbwong.stack.ui.utils.markdown.setMarkdown
import me.tylerbwong.stack.ui.utils.setThrottledOnClickListener
import java.text.DateFormat
import java.util.*

class AnswerHolder(parent: ViewGroup) : DynamicViewHolder(
        parent.inflateWithoutAttaching(R.layout.answer_holder)
) {
    override fun bind(data: Any) {
        (data as? AnswerDataModel)?.let { dataModel ->
            val voteCount = dataModel.voteCount
            votes.text = itemView.context.resources.getQuantityString(R.plurals.votes, voteCount, voteCount)
            acceptedAnswerCheck.visibility = if (dataModel.isAccepted) View.VISIBLE else View.GONE

            answerBody.apply {
                setMarkdown(dataModel.answerBody)
                setTextIsSelectable(true)
                movementMethod = BetterLinkMovementMethod.getInstance()
            }

            creationDate.text = containerView.context.getString(R.string.answered_date, getAnswerDateFormatted(data.creationDate))

            username.text = dataModel.username
            GlideApp.with(itemView)
                    .load(dataModel.userImage)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .placeholder(R.drawable.user_image_placeholder)
                    .apply(RequestOptions.circleCropTransform())
                    .into(userImage)
            userImage.setThrottledOnClickListener { dataModel.onProfilePictureClicked(it.context) }
            reputation.text = dataModel.reputation
            badgeView.badgeCounts = dataModel.badgeCounts
        }
    }

    private fun getAnswerDateFormatted(creationDate: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = creationDate * 1000
        return DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.time)
    }
}
