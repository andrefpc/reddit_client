package com.andrefpc.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
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
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupPostsRecyclerView()
        setupListeners()
        setupActionBar()
        setupViewModelObservers()
        binding.drawerLayout.open()
        viewModel.getPosts(refresh = true)
    }

    /**
     * Setup the RecyclerView with the adapter
     */
    private fun setupPostsRecyclerView() {
        postsAdapter = PostsAdapter()
        layoutManager = LinearLayoutManager(this)
        binding.postsList.layoutManager = layoutManager
        binding.postsList.adapter = postsAdapter
        listenScroll()
    }

    /**
     * Setup the listeners of the activity
     */
    private fun setupListeners() {
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
        postsAdapter?.onSelect {
            binding.drawerLayout.close()
            openContent(it)
        }
        postsAdapter?.onRemove {
            countRemoved++
            if (countRemoved == 5) {
                addItemsToList()
                countRemoved = 0
            }
        }
    }

    /**
     * Open the selected post data on the main layout
     */
    private fun openContent(data: RedditData) {
        binding.appBarMain.toolbar.title = data.author
        binding.appBarMain.contentMain.contentLayout.setupLayout(data)
        binding.appBarMain.contentMain.mainViewFlipper.displayedChild = CONTENT_LAYOUT
        if (data.isImage()) {
            viewModel.currentImageUrl = data.url
            showDownloadButton()
        } else {
            viewModel.currentImageUrl = null
            hideDownloadButton()
        }
    }

    /**
     * Show the download button on the toolbar
     */
    private fun showDownloadButton() {
        menu?.findItem(R.id.download_image)?.isVisible = true
    }

    /**
     * Hide the download button on the toolbar
     */
    private fun hideDownloadButton() {
        menu?.findItem(R.id.download_image)?.isVisible = false
    }

    /**
     * Setup the action bar
     */
    private fun setupActionBar() {
        setSupportActionBar(binding.appBarMain.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_drawer)
    }

    /**
     * Setup the ViewModel Observers
     */
    private fun setupViewModelObservers() {
        viewModel.uiState.observe(this, {
            when (it) {
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

    /**
     * Create the menu itens on toolbar
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        this.menu = menu
        menu?.findItem(R.id.download_image)?.isVisible = false
        return true
    }

    /**
     * Listen for menu buttons clicks
     */
    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            android.R.id.home -> binding.drawerLayout.open()
            R.id.download_image -> {
                if (haveExternalStoragePermission()) {
                    viewModel.saveCurrentImage()
                    Toast.makeText(this, R.string.image_save_on_galery, Toast.LENGTH_SHORT).show()
                }
            }
        }
        return super.onOptionsItemSelected(menuItem)
    }

    /**
     * Listen for scroll events to do the endless scroll (pagination)
     */
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

    /**
     * Call the method to get posts from the api to add to the current list
     */
    private fun addItemsToList() {
        val lastItemName = postsAdapter?.getLastItemName()
        viewModel.getPosts(lastItemName, false)
        loading = true
    }

    /**
     * Verify if user gives the permission to write on external storage
     */
    private fun haveExternalStoragePermission(): Boolean {
        return if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                0
            )
            false
        }
    }

    /**
     * Listen to permission results
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            viewModel.saveCurrentImage()
        } else {
            Toast.makeText(this, R.string.permission_not_granted, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val LOADING_DRAWER_LAYOUT = 0
        private const val ERROR_DRAWER_LAYOUT = 1
        private const val SUCCESS_DRAWER_LAYOUT = 2

        private const val CONTENT_LAYOUT = 1
    }
}