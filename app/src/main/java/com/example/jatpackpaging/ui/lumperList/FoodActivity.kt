package com.example.jatpackpaging.ui.lumperList

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jatpackpaging.R
import com.example.jatpackpaging.databinding.ActivityFoodBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class FoodActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private lateinit var viewModel: FoodListViewModel
    private val factory: FoodListViewModelFactory by instance()
    private lateinit var binding: ActivityFoodBinding

    private val adapter =
        FoodAdapter { name: String -> snackBarClickedPlayer(name) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@FoodActivity, R.layout.activity_food)
        viewModel = ViewModelProvider(this, factory).get(FoodListViewModel::class.java)

        setUpAdapter()
        startSearchJob()
        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.refresh()

        }
    }


    private fun startSearchJob() {
        lifecycleScope.launch {
            viewModel.getMovieList()?.observe(this@FoodActivity, {

                adapter.submitData(this@FoodActivity.lifecycle, it)

            })
        }
    }

    private fun setUpAdapter() {
        binding.recyclerViewLumper.apply {
            layoutManager = LinearLayoutManager(this@FoodActivity)
            setHasFixedSize(true)
            val decoration =
                DividerItemDecoration(this@FoodActivity, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
        }
        binding.recyclerViewLumper.adapter = adapter.withLoadStateFooter(
            footer = FoodLoadingStateAdapter { retry() }
        )

        adapter.addLoadStateListener { loadState ->

            if (loadState.refresh is LoadState.Loading) {

                if (adapter.snapshot().isEmpty()) {
                    binding.progressBar.visibility = View.VISIBLE
                }
                binding.noRecordText.visibility = View.GONE

            } else {
                binding.progressBar.visibility = View.GONE
                binding.swipeRefreshLayout.isRefreshing = false

                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error

                    else -> null
                }
                error?.let {
                    if (adapter.snapshot().isEmpty()) {
                        binding.noRecordText.visibility = View.VISIBLE
                        binding.noRecordText.text = it.error.localizedMessage
                    }
                }

            }
        }
    }

    private fun snackBarClickedPlayer(name: String) {
        val parentLayout = findViewById<View>(android.R.id.content)
        Snackbar.make(parentLayout, name, Snackbar.LENGTH_LONG)
            .show()
    }

    private fun retry() {
        adapter.retry()
    }
}