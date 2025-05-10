package com.ansorisan.dicevent.features.events.detail.presentation.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.lifecycleScope
import com.ansorisan.dicevent.R
import com.ansorisan.dicevent.base.data.models.UiState
import com.ansorisan.dicevent.base.utils.common.CommonUtils
import com.ansorisan.dicevent.base.utils.dialogues.AlertDialog
import com.ansorisan.dicevent.databinding.ActivityDetailEventBinding
import com.ansorisan.dicevent.features.events.detail.presentation.viewmodel.DetailViewModel
import com.ansorisan.dicevent.features.events.favorite.domain.entity.Event
import com.ansorisan.dicevent.features.events.favorite.presentation.viewmodel.FavoriteEventViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailEventActivity : AppCompatActivity() {

    private var _binding: ActivityDetailEventBinding? = null
    private val binding get() = _binding
    private val viewModel: DetailViewModel by viewModels()
    private val favEventViewModel: FavoriteEventViewModel by viewModels()
    private var isFav = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val alert = AlertDialog(this)
        val id = intent.getIntExtra("ID", 0)

        setSupportActionBar(binding?.toolbar)

        onBackPressedDispatcher.addCallback(this) {
            val resultIntent = Intent()
            setResult(Activity.RESULT_OK, resultIntent)
            finish() // close the activity
        }

        viewModel.fetchDetail(id)
        lifecycleScope.launch {
            viewModel.eventsState.collect { state ->
                when (state) {
                    is UiState.Loading -> binding?.contentScroll?.loading?.visibility = View.VISIBLE
                    is UiState.Error -> {
                        binding?.contentScroll?.loading?.visibility = View.GONE
                        alert.errorAlert(state.message)
                    }
                    is UiState.Success -> {
                        val data = state.data.data
                        favEventViewModel.getEventById(data.id)
                        binding?.apply {
                            contentScroll.loading?.visibility = View.GONE
                            Glide.with(this@DetailEventActivity).load(data.mediaCover).into(headerImage)
                            toolbarLayout.title = data.name
                            contentScroll.title?.text = data.name
                            contentScroll.subtitle?.text = data.summary
                            val quota = "Sisa quota ${data.quota - data.registrants}"
                            contentScroll.quota?.text = quota
                            val dateAndOwner = "${data.beginTime?.let { CommonUtils.formatDateTime(it)}} | ${data.ownerName}"
                            contentScroll.date?.text = dateAndOwner
                            contentScroll.content?.text = HtmlCompat.fromHtml(
                                data.description.toString(),
                                HtmlCompat.FROM_HTML_MODE_LEGACY
                            )

                            contentScroll.reg?.setOnClickListener {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(data.link))
                                startActivity(intent)
                            }

                            val event = Event(
                                data.id,
                                data.summary ?: "-",
                                data.mediaCover ?: "-",
                                data.name ?: "-",
                                data.beginTime ?: "-"
                            )

                            binding?.fab?.setOnClickListener {
                                binding?.fab?.setImageResource(if (isFav) R.drawable.heart_outline else R.drawable.heart_fill)
                                if (isFav) {
                                    favEventViewModel.deleteFavoriteEvent(event)
                                } else {
                                    favEventViewModel.addFavoriteEvent(event)
                                }
                            }
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            favEventViewModel.eventState.collect { state ->
                when (state) {
                    is UiState.Loading -> binding?.contentScroll?.loading?.visibility = View.VISIBLE
                    is UiState.Error -> {
                        binding?.contentScroll?.loading?.visibility = View.GONE
                        alert.errorAlert(state.message)
                    }
                    is UiState.Success -> {
                        alert.successAlert(state.data)
                    }
                }
            }
        }

        lifecycleScope.launch {
            favEventViewModel.eventDataState.collect { state ->
                when (state) {
                    is UiState.Loading -> binding?.contentScroll?.loading?.visibility = View.VISIBLE
                    is UiState.Error -> {
                        binding?.contentScroll?.loading?.visibility = View.GONE
                        alert.errorAlert(state.message)
                    }
                    is UiState.Success -> {
                        isFav = state.data != null
                        binding?.fab?.setImageResource(if (isFav) R.drawable.heart_fill else R.drawable.heart_outline)
                    }
                }
            }
        }
    }

//    override fun onBackPressed() {
//        val resultIntent = Intent()
//        setResult(Activity.RESULT_OK, resultIntent)
//        super.onBackPressed()
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}