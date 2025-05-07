package com.ansorisan.dicevent.base.utils.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ansorisan.dicevent.base.domain.entities.Event
import com.ansorisan.dicevent.features.events.favorite.domain.entity.Event as FavEvent
import com.ansorisan.dicevent.base.utils.common.CommonUtils
import com.ansorisan.dicevent.base.utils.listeners.OnEventClickListener
import com.ansorisan.dicevent.databinding.EventCardBinding
import com.bumptech.glide.Glide

class EventListAdapter(private val isVertical: Boolean, private val isFav: Boolean): RecyclerView.Adapter<EventListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: EventCardBinding): RecyclerView.ViewHolder(binding.root)

    private val events = ArrayList<Event>()
    private val favEvents = ArrayList<FavEvent>()
    private var onEventClickListener: OnEventClickListener? = null

    fun setOnEventClickListener(onEventClickListener: OnEventClickListener) {
        this.onEventClickListener = onEventClickListener
    }

    fun setData(list: List<Event>) {
        this.events.clear()
        this.events.addAll(list)
        notifyDataSetChanged()
    }

    fun setFavData(list: List<FavEvent>) {
        this.favEvents.clear()
        this.favEvents.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = EventCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ViewHolder(binding)
    }

    override fun getItemCount(): Int = if (isFav) favEvents.size else events.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            if (isFav) {
                with(favEvents[position]) {
                    binding.apply {
                        Glide.with(itemView).load(mediaCover).into(poster)
                        title.text = name
                        desc.text = summary
                        date.text = beginTime.let { CommonUtils.formatDateTime(it) }

                        val layoutParams = card.layoutParams as ViewGroup.MarginLayoutParams
                        val marginHorizontal = 16.dpToPx(itemView.context)
                        val marginDefault = 4.dpToPx(itemView.context)
                        val marginBottom = 100.dpToPx(itemView.context)

                        if (isVertical) {
                            layoutParams.marginStart = marginHorizontal
                            layoutParams.marginEnd = marginHorizontal
                            layoutParams.bottomMargin = if (position == events.lastIndex) marginBottom else marginDefault
                        } else {
                            layoutParams.marginStart = if (position == 0) marginHorizontal else marginDefault
                            layoutParams.marginEnd = if (position == events.lastIndex) marginHorizontal else marginDefault
                        }

                        card.layoutParams = layoutParams

                        itemView.setOnClickListener { onEventClickListener?.onEventClick(id) }
                    }
                }
            } else {
                with(events[position]) {
                    binding.apply {
                        Glide.with(itemView).load(mediaCover).into(poster)
                        title.text = name
                        desc.text = summary
                        date.text = beginTime?.let { CommonUtils.formatDateTime(it) }

                        val layoutParams = card.layoutParams as ViewGroup.MarginLayoutParams
                        val marginHorizontal = 16.dpToPx(itemView.context)
                        val marginDefault = 4.dpToPx(itemView.context)
                        val marginBottom = 100.dpToPx(itemView.context)

                        if (isVertical) {
                            layoutParams.marginStart = marginHorizontal
                            layoutParams.marginEnd = marginHorizontal
                            layoutParams.bottomMargin = if (position == events.lastIndex) marginBottom else marginDefault
                        } else {
                            layoutParams.marginStart = if (position == 0) marginHorizontal else marginDefault
                            layoutParams.marginEnd = if (position == events.lastIndex) marginHorizontal else marginDefault
                        }

                        card.layoutParams = layoutParams

                        itemView.setOnClickListener { onEventClickListener?.onEventClick(id) }
                    }
                }
            }
        }
    }

    private fun Int.dpToPx(context: Context): Int = (this * context.resources.displayMetrics.density).toInt()
}