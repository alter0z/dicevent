package com.ansorisan.dicevent.features.events.search.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ansorisan.dicevent.base.data.models.UiState
import com.ansorisan.dicevent.base.utils.adapters.EventListAdapter
import com.ansorisan.dicevent.base.utils.adapters.LoadingEventAdapter
import com.ansorisan.dicevent.base.utils.dialogues.AlertDialog
import com.ansorisan.dicevent.base.utils.listeners.OnEventClickListener
import com.ansorisan.dicevent.databinding.ActivitySearchEventBinding
import com.ansorisan.dicevent.features.events.detail.presentation.ui.DetailEventActivity
import com.ansorisan.dicevent.features.events.search.presentation.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchEventActivity : AppCompatActivity() {

    private var _binding: ActivitySearchEventBinding? = null
    private val binding get() = _binding
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySearchEventBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val adapter = EventListAdapter(true, isFav = false)
        val loadingAdapter = LoadingEventAdapter(true)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding?.events?.layoutManager = layoutManager
        val alert = AlertDialog(this)

        binding?.apply {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    viewModel.searchEvent(searchView.text.toString())
                    lifecycleScope.launch {
                        viewModel.eventsState.collect { state ->
                            when (state) {
                                is UiState.Loading -> binding?.events?.adapter = loadingAdapter
                                is UiState.Error -> alert.errorAlert(state.message)
                                is UiState.Success -> {
                                    if (state.data.data.isEmpty()) alert.warningAlert("Event not found")
                                    adapter.setData(state.data.data)
                                    binding?.events?.adapter = adapter
                                }
                            }
                        }
                    }
                    false
                }
        }

        adapter.setOnEventClickListener(object: OnEventClickListener {
            override fun onEventClick(eventId: Int) {
                startActivity(
                    Intent(this@SearchEventActivity, DetailEventActivity::class.java)
                    .putExtra("ID", eventId))
            }
        })
    }
}