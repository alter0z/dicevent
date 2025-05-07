package com.ansorisan.dicevent.features.home.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ansorisan.dicevent.base.data.models.UiState
import com.ansorisan.dicevent.base.utils.adapters.EventListAdapter
import com.ansorisan.dicevent.base.utils.adapters.LoadingEventAdapter
import com.ansorisan.dicevent.base.utils.common.CommonUtils
import com.ansorisan.dicevent.base.utils.dialogues.AlertDialog
import com.ansorisan.dicevent.base.utils.listeners.OnEventClickListener
import com.ansorisan.dicevent.databinding.FragmentHomeBinding
import com.ansorisan.dicevent.features.events.detail.presentation.ui.DetailEventActivity
import com.ansorisan.dicevent.features.events.search.presentation.ui.SearchEventActivity
import com.ansorisan.dicevent.features.home.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private val viewModel: HomeViewModel by viewModels()
    private var _alert: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): NestedScrollView? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: NestedScrollView? = binding?.root
        _alert = AlertDialog((activity as AppCompatActivity))

        if (CommonUtils.isConnected(requireContext())) {
            viewModel.fetchUpcomingEvents()
            viewModel.fetchFinishedEvents()
        } else {
            _alert?.errorAlert("No internet connection!")
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val upcomingAdapter = EventListAdapter(false, isFav = false)
        val finishedAdapter = EventListAdapter(true, isFav = false)
        val upcomingLoadingAdapter = LoadingEventAdapter(false)
        val finishedLoadingAdapter = LoadingEventAdapter(true)
        val upcomingLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val finishedLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding?.upcoming?.layoutManager = upcomingLayoutManager
        binding?.finished?.layoutManager = finishedLayoutManager

        lifecycleScope.launch {
            viewModel.upcomingEventsState.collect { state ->
                when (state) {
                    is UiState.Loading -> binding?.upcoming?.adapter = upcomingLoadingAdapter
                    is UiState.Error -> _alert?.errorAlert(state.message)
                    is UiState.Success -> {
                        upcomingAdapter.setData(state.data.data.take(5))
                        binding?.upcoming?.adapter = upcomingAdapter
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.finishedEventsState.collect { state ->
                when (state) {
                    is UiState.Loading -> binding?.finished?.adapter = finishedLoadingAdapter
                    is UiState.Error -> _alert?.errorAlert(state.message)
                    is UiState.Success -> {
                        finishedAdapter.setData(state.data.data.take(5))
                        binding?.finished?.adapter = finishedAdapter
                    }
                }
            }
        }

        upcomingAdapter.setOnEventClickListener(object: OnEventClickListener {
            override fun onEventClick(eventId: Int) {
                startActivity(Intent(activity, DetailEventActivity::class.java)
                    .putExtra("ID", eventId))
            }
        })

        finishedAdapter.setOnEventClickListener(object: OnEventClickListener {
            override fun onEventClick(eventId: Int) {
                startActivity(Intent(activity, DetailEventActivity::class.java)
                    .putExtra("ID", eventId))
            }
        })

        binding?.search?.setOnClickListener {
            startActivity(Intent(activity, SearchEventActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}