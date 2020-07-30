package me.tylerbwong.stack.ui.questions.create

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.compose.Recomposer
import androidx.fragment.app.viewModels
import androidx.ui.core.setContent
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import me.tylerbwong.stack.R
import me.tylerbwong.stack.databinding.CreateQuestionFragmentBinding
import me.tylerbwong.stack.ui.BaseFragment
import me.tylerbwong.stack.ui.questions.create.CreateQuestionActivity.Companion.DRAFT_ID
import me.tylerbwong.stack.ui.questions.detail.QuestionDetailActivity

@AndroidEntryPoint
class CreateQuestionFragment : BaseFragment<CreateQuestionFragmentBinding>(
    CreateQuestionFragmentBinding::inflate
) {
    private val viewModel by viewModels<CreateQuestionViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.composeContent.setContent(Recomposer.current()) {
            CreateQuestionLayout(
                draftLiveData = viewModel.questionDraft,
                createQuestion = viewModel::createQuestion,
                saveDraft = viewModel::saveDraft,
                deleteDraft = viewModel::deleteDraft,
                onBackPressed = { requireActivity().onBackPressed() }
            )
        }
        viewModel.createQuestionState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is CreateQuestionSuccessPreview -> showSnackbar(getString(R.string.preview_success))
                is CreateQuestionSuccess -> {
                    QuestionDetailActivity.startActivity(requireContext(), state.questionId)
                    requireActivity().finish()
                }
                is CreateQuestionError -> showSnackbar(state.errorMessage)
            }
        }
        viewModel.fetchDraft(arguments?.getInt(DRAFT_ID) ?: -1)
    }

    private fun showSnackbar(message: String?) {
        with(
            Snackbar.make(
                binding.root,
                message ?: getString(R.string.network_error),
                Snackbar.LENGTH_INDEFINITE
            )
        ) {
            this@with.view.findViewById<TextView>(
                com.google.android.material.R.id.snackbar_text
            )?.apply {
                maxLines = 4
            }
            setAction(R.string.dismiss) { dismiss() }
            show()
        }
    }

    companion object {
        fun newInstance(id: Int): CreateQuestionFragment {
            val fragment = CreateQuestionFragment()
            val bundle = Bundle().apply { putInt(DRAFT_ID, id) }
            fragment.arguments = bundle
            return fragment
        }
    }
}
