package com.exam.customcarouselrecyclerview

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.DrawableRes
import androidx.cardview.widget.CardView
import com.exam.customcarouselrecyclerview.databinding.HeaderItemBinding
import com.xwray.groupie.databinding.BindableItem

class HeaderItem(
    @DrawableRes private val resId: Int,
    private val width: Int,
    private val radius:Int
) : BindableItem<HeaderItemBinding>() {
    override fun getLayout() = R.layout.header_item

    override fun bind(viewBinding: HeaderItemBinding, position: Int) {
        viewBinding.thumbnailContainer.layoutParams.width = width
        viewBinding.image.layoutParams.width = width
        viewBinding.image.setImageResource(resId)
        makeCustomOutline(viewBinding.thumbnailContainer)
    }

    // 一番左の画像を、左上だけ角丸にするための処理
    private fun makeCustomOutline(card: CardView) {
        card.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setRoundRect(
                    0,
                    0,
                    view.width + radius,
                    view.height + radius,
                    radius.toFloat()
                )
            }
        }
        card.clipToOutline = true
    }
}
