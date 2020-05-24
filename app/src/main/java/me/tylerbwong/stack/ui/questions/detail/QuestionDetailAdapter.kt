package me.tylerbwong.stack.ui.questions.detail

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.answer_holder.view.*
import kotlinx.android.synthetic.main.question_detail_holder.view.*
import me.tylerbwong.stack.R
import me.tylerbwong.stack.ui.answers.AnswerHolder
import me.tylerbwong.stack.ui.utils.inflate
import me.tylerbwong.stack.ui.utils.noCopySpannableFactory

class QuestionDetailAdapter : ListAdapter<QuestionDetailItem, RecyclerView.ViewHolder>(
    AsyncDifferConfig.Builder(QuestionDetailItemCallback()).build()
) {
    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is QuestionItem -> ITEM_TYPE_QUESTION
        is QuestionActionItem -> ITEM_TYPE_ACTION
        is AnswerHeaderItem -> ITEM_TYPE_ANSWER_HEADER
        is AnswerItem -> ITEM_TYPE_ANSWER
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder = when (viewType) {
        ITEM_TYPE_QUESTION -> QuestionDetailHolder(
            parent.inflate<ViewGroup>(R.layout.question_detail_holder).also {
                it.questionBody.setSpannableFactory(noCopySpannableFactory)
            }
        )
        ITEM_TYPE_ACTION -> QuestionDetailActionHolder(
            parent.inflate<ViewGroup>(R.layout.question_detail_action_holder)
        )
        ITEM_TYPE_ANSWER_HEADER -> AnswerHeaderViewHolder(parent.inflate(R.layout.answer_header))
        else -> AnswerHolder(
            parent.inflate<ViewGroup>(R.layout.answer_holder).also {
                it.answerBody.setSpannableFactory(noCopySpannableFactory)
            }
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        when {
            holder is QuestionDetailHolder && item is QuestionItem -> holder.bind(item)
            holder is QuestionDetailActionHolder && item is QuestionActionItem -> holder.bind(item)
            holder is AnswerHeaderViewHolder && item is AnswerHeaderItem -> holder.bind(item)
            holder is AnswerHolder && item is AnswerItem -> holder.bind(item)
        }
    }

    companion object {
        private const val ITEM_TYPE_QUESTION = 1
        private const val ITEM_TYPE_ACTION = 2
        private const val ITEM_TYPE_ANSWER_HEADER = 3
        private const val ITEM_TYPE_ANSWER = 4
    }
}
