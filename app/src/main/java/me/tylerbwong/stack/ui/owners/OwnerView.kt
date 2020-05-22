package me.tylerbwong.stack.ui.owners

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import coil.api.load
import coil.transform.CircleCropTransformation
import kotlinx.android.synthetic.main.owner_view.view.*
import me.tylerbwong.stack.R
import me.tylerbwong.stack.data.model.User
import me.tylerbwong.stack.ui.MainActivity
import me.tylerbwong.stack.ui.utils.OnClickHandler
import me.tylerbwong.stack.ui.utils.format
import me.tylerbwong.stack.ui.utils.inflate
import me.tylerbwong.stack.ui.utils.setThrottledOnClickListener
import me.tylerbwong.stack.ui.utils.toHtml

class OwnerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyle, defStyleRes) {

    private val onClick: OnClickHandler?

    init {
        inflate<ConstraintLayout>(R.layout.owner_view, attachToRoot = true)
        onClick = context as? MainActivity
    }

    fun bind(owner: User) {
        username.text = owner.displayName.toHtml()
        userImage.load(owner.profileImage) {
            crossfade(true)
            error(R.drawable.user_image_placeholder)
            placeholder(R.drawable.user_image_placeholder)
            transformations(CircleCropTransformation())
        }
        userImage.setThrottledOnClickListener {
            onClick?.onClickOpenProfilePage(owner, it)
        }
        badgeView.badgeCounts = owner.badgeCounts
        reputation.text = owner.reputation.toLong().format()
    }
}
