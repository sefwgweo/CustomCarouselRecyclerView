package com.exam.customcarouselrecyclerview

import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.PagerSnapHelper
import com.exam.customcarouselrecyclerview.databinding.FragmentCustomCarouselListBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.databinding.BindableItem
import com.xwray.groupie.databinding.GroupieViewHolder

class CustomCarouselRecyclerFragment : Fragment() {
    companion object { fun create(): CustomCarouselRecyclerFragment = CustomCarouselRecyclerFragment() }

    private lateinit var binding: FragmentCustomCarouselListBinding
    private val groupAdapter = GroupAdapter<GroupieViewHolder<*>>()
    private val items = mutableListOf<BindableItem<*>>()
    private val carouselItemList = listOf(
        R.mipmap.item0,
        R.mipmap.item1,
        R.mipmap.item2,
        R.mipmap.item3,
        R.mipmap.item4,
        R.mipmap.item5,
        R.mipmap.item6,
        R.mipmap.item7,
        R.mipmap.item8,
        R.mipmap.item9,
    )

    private val displayRatio = 0.9 // カルーセルコンテンツ1つ1つの、端末に対する横幅表示割合

    interface OnSnapPositionChangeListener {
        fun onSnapPositionChange(position: Int)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomCarouselListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUIComponent()
    }

    private fun initUIComponent() {
        binding.list.apply {
            setHasFixedSize(true)
            adapter = groupAdapter
            PagerSnapHelper().attachToRecyclerView(this)

            // 現在見ている画像位置を表示するための処理
            addOnScrollListener(
                SnapOnScrollListener(
                    PagerSnapHelper(),
                    SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL,
                    object : OnSnapPositionChangeListener {
                        override fun onSnapPositionChange(position: Int) {
                            binding.positionText.text = (position + 1).toString()
                                .plus(" / ")
                                .plus(groupAdapter.itemCount)
                        }
                    }
                )
            )
        }

        // instagram風インジケータ追加
        binding.indicator.attachToRecyclerView(binding.list)

        // 画像を端末横幅の90%のサイズで表示するための計算
        val width = (getDisplaySize().x * displayRatio).toInt()

        // 画像の角丸値をpixel値で取得
        val radius = resources.getDimension(R.dimen.card_radius).toInt()

        carouselItemList.mapIndexed { index, resId ->
            when(index) {
                // 一番左の画像用処理
                0 -> items.add(HeaderItem(resId, width, radius))

                // 一番右の画像用処理
                carouselItemList.size - 1 -> items.add(FooterItem(resId, width, radius))

                // 端っこ以外の画像用処理
                else -> items.add(CarouselImageItem(resId, width))
            }
        }
        groupAdapter.update(items)
    }

    private fun getDisplaySize(): Point {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val display = requireActivity().display
            val size = Point()

            @Suppress("DEPRECATION")
            display?.getSize(size)
            return size
        } else {
            @Suppress("DEPRECATION")
            val display = requireActivity().windowManager.defaultDisplay
            val size = Point()

            @Suppress("DEPRECATION")
            display.getSize(size)
            return size
        }
    }
}
