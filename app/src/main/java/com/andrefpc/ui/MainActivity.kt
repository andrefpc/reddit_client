package com.andrefpc.ui

import android.os.Bundle
import android.view.MenuItem
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
        setupActionBar()
        setupViewModelObservers()
        viewModel.getPosts()
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
                }
                UIState.Error -> {

                }
                is UIState.Success -> {
                    val children = it.children
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
}