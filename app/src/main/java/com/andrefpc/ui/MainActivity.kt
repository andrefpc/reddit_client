package com.andrefpc.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.andrefpc.R
import com.andrefpc.data.UIState
import com.andrefpc.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()
        setupActionBar()
        setupViewModelObservers()
        binding.drawerLayout.open()
        viewModel.getPosts()
    }

    private fun initListeners(){
        binding.appBarMain.contentMain.emptyLayout.emptyButton.setOnClickListener {
            binding.drawerLayout.open()
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.appBarMain.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_drawer)
    }

    private fun setupViewModelObservers() {
        viewModel.uiState.observe(this, {
            when(it){
                UIState.Loading -> {
                    if (binding.postsViewFlipper.displayedChild == LOADING_LAYOUT) return@observe
                    binding.postsViewFlipper.displayedChild = LOADING_LAYOUT
                }
                UIState.Error -> {
                    if (binding.postsViewFlipper.displayedChild == ERROR_LAYOUT) return@observe
                    binding.postsViewFlipper.displayedChild = ERROR_LAYOUT
                }
                is UIState.Success -> {
                    val children = it.children
                    if (binding.postsViewFlipper.displayedChild == SUCCESS_LAYOUT) return@observe
                    binding.postsViewFlipper.displayedChild = SUCCESS_LAYOUT
                }
            }
        })
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == android.R.id.home) {
            binding.drawerLayout.open()
        }
        return super.onOptionsItemSelected(menuItem)
    }

    companion object{
        const val LOADING_LAYOUT = 0
        const val ERROR_LAYOUT = 1
        const val SUCCESS_LAYOUT = 2
    }
}