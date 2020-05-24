package me.tylerbwong.stack.ui.home

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.tylerbwong.stack.R
import me.tylerbwong.stack.ui.HeaderViewHolder
import me.tylerbwong.stack.ui.drafts.AnswerDraftHolder
import me.tylerbwong.stack.ui.questions.QuestionViewHolder
import me.tylerbwong.stack.ui.search.filters.FilterInputHolder
import me.tylerbwong.stack.ui.search.SearchHistoryItemHolder
import me.tylerbwong.stack.ui.search.SearchInputHolder
import me.tylerbwong.stack.ui.search.tags.TagsHolder
import me.tylerbwong.stack.ui.utils.inflate

class HomeAdapter : ListAdapter<HomeItem, RecyclerView.ViewHolder>(
    AsyncDifferConfig.Builder(HomeItemDiffCallback()).build()
) {
    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is HeaderItem -> ITEM_TYPE_HEADER
        is QuestionItem -> ITEM_TYPE_QUESTION
        is AnswerDraftItem -> ITEM_TYPE_ANSWER_DRAFT
        is SearchInputItem -> ITEM_TYPE_BASIC_SEARCH_INPUT
        is FilterInputItem -> ITEM_TYPE_ADVANCED_SEARCH_INPUT
        is TagsItem -> ITEM_TYPE_TAGS
        is SectionHeaderItem -> ITEM_TYPE_SECTION_HEADER
        is SearchHistoryItem -> ITEM_TYPE_SEARCH_HISTORY
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder = when (viewType) {
        ITEM_TYPE_HEADER -> HeaderViewHolder(parent.inflate(R.layout.header_holder))
        ITEM_TYPE_QUESTION -> QuestionViewHolder(parent.inflate(R.layout.question_holder))
        ITEM_TYPE_ANSWER_DRAFT -> AnswerDraftHolder(parent.inflate(R.layout.answer_draft_holder))
        ITEM_TYPE_BASIC_SEARCH_INPUT -> SearchInputHolder(parent.inflate(R.layout.search_input_holder))
        ITEM_TYPE_ADVANCED_SEARCH_INPUT ->
            FilterInputHolder(
                parent.inflate(
                    R.layout.filter_input_holder
                )
            )
        ITEM_TYPE_TAGS -> TagsHolder(parent.inflate(R.layout.tags_holder))
        ITEM_TYPE_SECTION_HEADER -> SectionHeaderHolder(parent.inflate(R.layout.section_header_holder))
        else -> SearchHistoryItemHolder(parent.inflate(R.layout.search_history_item_holder))
    }

    @Suppress("ComplexMethod")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        when {
            holder is HeaderViewHolder && item is HeaderItem -> holder.bind(item)
            holder is QuestionViewHolder && item is QuestionItem -> holder.bind(item.question)
            holder is AnswerDraftHolder && item is AnswerDraftItem -> holder.bind(item)
            holder is SearchInputHolder && item is SearchInputItem -> holder.bind(item)
            holder is FilterInputHolder && item is FilterInputItem -> holder.bind(item)
            holder is TagsHolder && item is TagsItem -> holder.bind(item)
            holder is SectionHeaderHolder && item is SectionHeaderItem -> holder.bind(item)
            holder is SearchHistoryItemHolder && item is SearchHistoryItem -> holder.bind(item)
        }
    }

    companion object {
        private const val ITEM_TYPE_HEADER = 1
        private const val ITEM_TYPE_QUESTION = 2
        private const val ITEM_TYPE_ANSWER_DRAFT = 3
        private const val ITEM_TYPE_BASIC_SEARCH_INPUT = 4
        private const val ITEM_TYPE_ADVANCED_SEARCH_INPUT = 5
        private const val ITEM_TYPE_TAGS = 6
        private const val ITEM_TYPE_SECTION_HEADER = 7
        private const val ITEM_TYPE_SEARCH_HISTORY = 8
    }
}
