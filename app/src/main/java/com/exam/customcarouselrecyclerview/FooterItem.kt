package com.exam.customcarouselrecyclerview

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.DrawableRes
import androidx.cardview.widget.CardView
import com.exam.customcarouselrecyclerview.databinding.FooterItemBinding
import com.xwray.groupie.databinding.BindableItem

class FooterItem(
    @DrawableRes private val resId: Int,
    private val width: Int,
    private val radius: Int,
) : BindableItem<FooterItemBinding>() {
    override fun getLayout() = R.layout.footer_item

    override fun bind(viewBinding: FooterItemBinding, position: Int) {
        viewBinding.thumbnailContainer.layoutParams.width = width
        viewBinding.image.layoutParams.width = width
        viewBinding.image.setImageResource(resId)
        makeCustomOutline(viewBinding.thumbnailContainer)
    }

    // 一番右の画像を、右上だけ角丸にするための処理
    private fun makeCustomOutline(card: CardView) {
        card.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setRoundRect(
                    -radius,
                    0,
                    view.width,
                    view.height + radius,
                    radius.toFloat()
                )
            }
        }
        card.clipToOutline = true
    }
}
