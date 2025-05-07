package com.ansorisan.dicevent.features.events.favorite.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ansorisan.dicevent.R
import com.ansorisan.dicevent.base.data.models.UiState
import com.ansorisan.dicevent.base.utils.adapters.EventListAdapter
import com.ansorisan.dicevent.base.utils.adapters.LoadingEventAdapter
import com.ansorisan.dicevent.base.utils.dialogues.AlertDialog
import com.ansorisan.dicevent.base.utils.listeners.OnEventClickListener
import com.ansorisan.dicevent.databinding.FragmentFavoriteBinding
import com.ansorisan.dicevent.features.events.detail.presentation.ui.DetailEventActivity
import com.ansorisan.dicevent.features.events.favorite.presentation.viewmodel.FavoriteEventViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding
    private val viewModel: FavoriteEventViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: NestedScrollView? = binding?.root

        viewModel.fetchFavoriteEvents()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = EventListAdapter(true, isFav = true)
        val loadingAdapter = LoadingEventAdapter(true)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding?.favorite?.layoutManager = layoutManager
        val alert = AlertDialog((activity as AppCompatActivity))

        lifecycleScope.launch {
            viewModel.eventsState.collect { state ->
                when (state) {
                    is UiState.Loading -> binding?.favorite?.adapter = loadingAdapter
                    is UiState.Error -> alert.errorAlert(state.message)
                    is UiState.Success -> {
                        adapter.setFavData(state.data)
                        binding?.favorite?.adapter = adapter
                    }
                }
            }
        }

        adapter.setOnEventClickListener(object: OnEventClickListener {
            override fun onEventClick(eventId: Int) {
                startActivity(
                    Intent(activity, DetailEventActivity::class.java)
                        .putExtra("ID", eventId))
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}