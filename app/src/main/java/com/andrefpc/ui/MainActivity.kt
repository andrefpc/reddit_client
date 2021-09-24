package com.andrefpc.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrefpc.R
import com.andrefpc.data.RedditData
import com.andrefpc.data.UIState
import com.andrefpc.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    private var postsAdapter: PostsAdapter? = null

    private var layoutManager: LinearLayoutManager? = null
    private var onScrollListener: RecyclerView.OnScrollListener? = null
    private var countRemoved = 0
    private var loading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()
        initPosts()
        setupActionBar()
        setupViewModelObservers()
        binding.drawerLayout.open()
        viewModel.getPosts(refresh = true)
    }

    private fun initPosts() {
        postsAdapter = PostsAdapter()
        layoutManager = LinearLayoutManager(this)
        binding.postsList.layoutManager = layoutManager
        binding.postsList.adapter = postsAdapter
        postsAdapter?.onSelect {
            binding.drawerLayout.close()
            openContent(it)
        }
        postsAdapter?.onRemove {
            countRemoved++
            if(countRemoved == 5){
                addItemsToList()
                countRemoved = 0
            }
        }
        listenScroll()
    }

    private fun openContent(data: RedditData) {
        binding.appBarMain.toolbar.title = data.author
        binding.appBarMain.contentMain.contentLayout.setupLayout(data)
        binding.appBarMain.contentMain.mainViewFlipper.displayedChild = CONTENT_LAYOUT
    }

    private fun initListeners() {
        binding.appBarMain.contentMain.emptyLayout.emptyButton.setOnClickListener {
            binding.drawerLayout.open()
        }
        binding.clearButton.setOnClickListener {
            val lastItemName = postsAdapter?.getLastItemName()
            viewModel.getPosts(lastItemName, true)
        }
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getPosts(refresh = true)
            binding.swipeRefresh.isRefreshing = false
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
                    if (binding.postsViewFlipper.displayedChild == LOADING_DRAWER_LAYOUT) return@observe
                    binding.postsViewFlipper.displayedChild = LOADING_DRAWER_LAYOUT
                }
                UIState.Error -> {
                    if (binding.postsViewFlipper.displayedChild == ERROR_DRAWER_LAYOUT) return@observe
                    binding.postsViewFlipper.displayedChild = ERROR_DRAWER_LAYOUT
                }
                is UIState.RefreshList -> {
                    loading = false
                    postsAdapter?.refreshList(it.children)
                    if (binding.postsViewFlipper.displayedChild == SUCCESS_DRAWER_LAYOUT) return@observe
                    binding.postsViewFlipper.displayedChild = SUCCESS_DRAWER_LAYOUT
                }
                is UIState.AddList -> {
                    loading = false
                    postsAdapter?.addList(it.children)
                    if (binding.postsViewFlipper.displayedChild == SUCCESS_DRAWER_LAYOUT) return@observe
                    binding.postsViewFlipper.displayedChild = SUCCESS_DRAWER_LAYOUT
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

    private fun listenScroll() {
        onScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                layoutManager?.let { manager ->
                    val visibleItemCount: Int = manager.childCount
                    val totalItemCount: Int = manager.itemCount
                    val pastVisibleItems: Int = manager.findFirstVisibleItemPosition()
                    if (!loading && pastVisibleItems + visibleItemCount >= totalItemCount - 5) {
                        addItemsToList()
                    }
                }
            }
        }
        onScrollListener?.let { binding.postsList.addOnScrollListener(it) }
    }

    private fun addItemsToList() {
        val lastItemName = postsAdapter?.getLastItemName()
        viewModel.getPosts(lastItemName, false)
        loading = true
    }

    companion object {
        const val LOADING_DRAWER_LAYOUT = 0
        const val ERROR_DRAWER_LAYOUT = 1
        const val SUCCESS_DRAWER_LAYOUT = 2

        const val CONTENT_LAYOUT = 1
    }
}