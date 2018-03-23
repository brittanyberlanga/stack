package me.tylerbwong.stack.presentation.questions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
import android.support.design.chip.Chip
import android.support.design.chip.ChipGroup
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import me.tylerbwong.stack.R
import me.tylerbwong.stack.data.model.Question
import me.tylerbwong.stack.presentation.owners.BadgeView
import me.tylerbwong.stack.presentation.utils.CustomTabsLinkResolver
import me.tylerbwong.stack.presentation.utils.toHtml
import ru.noties.markwon.Markwon
import ru.noties.markwon.SpannableConfiguration

class QuestionHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val questionTitle: TextView = ViewCompat.requireViewById(itemView, R.id.questionTitle)
    private val questionBody: TextView = ViewCompat.requireViewById(itemView, R.id.questionBody)
    private val username: TextView = ViewCompat.requireViewById(itemView, R.id.username)
    private val badgeView: BadgeView = ViewCompat.requireViewById(itemView, R.id.badgeView)
    private val expandCollapseArrow: ImageView = ViewCompat.requireViewById(itemView, R.id.expandCollapseButton)
    private val expandedView: LinearLayout = ViewCompat.requireViewById(itemView, R.id.expandedView)
    private val tagsChipGroup: ChipGroup = ViewCompat.requireViewById(itemView, R.id.tags)

    private val spannableConfiguration = SpannableConfiguration.builder(itemView.context)
        .linkResolver(CustomTabsLinkResolver())
        .build()

    fun bind(question: Question) {
        this.questionTitle.text = question.title.toHtml()
        this.username.text = itemView.context.getString(R.string.by, question.owner.displayName).toHtml()
        this.badgeView.badgeCounts = question.owner.badgeCounts
        Markwon.setMarkdown(questionBody, spannableConfiguration, question.bodyMarkdown)

        setExpanded(itemView.context, question.isExpanded, false)

        itemView.setOnLongClickListener {
            val contentManager = it.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            contentManager.primaryClip = ClipData.newPlainText("linkText", question.shareLink)
            Toast.makeText(it.context, "Link copied to clipboard", Toast.LENGTH_SHORT).show()
            true
        }

        itemView.setOnClickListener {
            question.isExpanded = !question.isExpanded
            setExpanded(it.context, question.isExpanded)
        }

        question.tags.forEach {
            with (Chip(itemView.context)) {
                chipText = it
                tagsChipGroup.addView(this)
            }
        }
    }

    private fun setExpanded(context: Context, isExpanded: Boolean, animate: Boolean = true) {
        if (!animate) {
            if (!isExpanded) {
                expandCollapseArrow.setImageResource(R.drawable.down_arrow)
            } else {
                expandCollapseArrow.setImageResource(R.drawable.up_arrow)
            }
        } else {
            val vectorDrawable: AnimatedVectorDrawable?

            if (!isExpanded) {
                vectorDrawable = context.getDrawable(R.drawable.up_to_down_arrow) as AnimatedVectorDrawable
                expandCollapseArrow.setImageDrawable(vectorDrawable)
            }
            else {
                vectorDrawable = context.getDrawable(R.drawable.down_to_up_arrow) as AnimatedVectorDrawable
                expandCollapseArrow.setImageDrawable(vectorDrawable)
            }

            vectorDrawable.start()
        }

        expandedView.visibility = if (isExpanded) View.VISIBLE else View.GONE
    }
}