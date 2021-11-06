package com.exam.customcarouselrecyclerview

import androidx.annotation.DrawableRes
import com.exam.customcarouselrecyclerview.databinding.ColItemBinding
import com.xwray.groupie.databinding.BindableItem

class CarouselImageItem(
    @DrawableRes private val resId: Int,
    private val width: Int,
) : BindableItem<ColItemBinding>() {
    override fun getLayout() = R.layout.col_item

    override fun bind(viewBinding: ColItemBinding, position: Int) {
        viewBinding.container.layoutParams.width = width
        viewBinding.image.setImageResource(resId)
    }
}
