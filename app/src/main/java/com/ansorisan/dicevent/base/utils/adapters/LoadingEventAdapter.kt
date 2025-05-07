package com.ansorisan.dicevent.base.utils.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ansorisan.dicevent.databinding.EventLoadingCardBinding

class LoadingEventAdapter(private val isVertical: Boolean): RecyclerView.Adapter<LoadingEventAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: EventLoadingCardBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = EventLoadingCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = 5

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            binding.apply {
                val layoutParams = card.layoutParams as ViewGroup.MarginLayoutParams
                val marginHorizontal = 16.dpToPx(itemView.context)
                val marginDefault = 4.dpToPx(itemView.context)
                val marginBottom = 50.dpToPx(itemView.context)

                if (isVertical) {
                    layoutParams.marginStart = marginHorizontal
                    layoutParams.marginEnd = marginHorizontal
                    layoutParams.bottomMargin = if (position == 4) marginBottom else marginDefault
                } else {
                    layoutParams.marginStart = if (position == 0) marginHorizontal else marginDefault
                    layoutParams.marginEnd = if (position == 4) marginHorizontal else marginDefault
                }

                card.layoutParams = layoutParams
            }
        }
    }

    private fun Int.dpToPx(context: Context): Int = (this * context.resources.displayMetrics.density).toInt()
}