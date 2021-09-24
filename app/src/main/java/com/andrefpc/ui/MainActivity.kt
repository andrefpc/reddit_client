package com.andrefpc.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.andrefpc.R
import com.andrefpc.data.RedditData
import com.andrefpc.data.UIState
import com.andrefpc.databinding.ActivityMainBinding
import com.andrefpc.extensions.ImageViewExtensions.loadImage
import com.andrefpc.extensions.VideoViewExtensions.loadVideo
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.andrefpc.extensions.WebViewExtensions.loadVideo
import android.util.DisplayMetrics





class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    private var postsAdapter: PostsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()
        initPosts()
        setupActionBar()
        setupViewModelObservers()
        binding.drawerLayout.open()
        viewModel.getPosts()
    }

    private fun initPosts() {
        postsAdapter = PostsAdapter()
        binding.postsList.layoutManager = LinearLayoutManager(this)
        binding.postsList.adapter = postsAdapter
        postsAdapter?.onSelect {
            binding.drawerLayout.close()
            openContent(it)
        }
    }

    private fun openContent(data: RedditData) {
        binding.appBarMain.toolbar.title = data.author
        val contentLayout = binding.appBarMain.contentMain.contentLayout
        contentLayout.contentSubreddit.text = data.getPostedBy()
        contentLayout.contentTitle.text = data.title
        data.media?.video?.let {
            contentLayout.contentVideo.loadVideo(it.url, it.width, it.height)
            contentLayout.contentImage.visibility = View.GONE
            contentLayout.contentVideo.visibility = View.VISIBLE
            contentLayout.contentEmbed.visibility = View.GONE
        } ?: kotlin.run {
            data.media?.embed?.let {
                contentLayout.contentEmbed.loadVideo(it)
                contentLayout.contentImage.visibility = View.GONE
                contentLayout.contentVideo.visibility = View.GONE
                contentLayout.contentEmbed.visibility = View.VISIBLE
            } ?: kotlin.run {
                data.imageUrl?.let {
                    contentLayout.contentImage.loadImage(it)
                } ?: kotlin.run {
                    contentLayout.contentImage.loadImage(data.thumbnail)
                }
                contentLayout.contentImage.visibility = View.VISIBLE
                contentLayout.contentVideo.visibility = View.GONE
                contentLayout.contentEmbed.visibility = View.GONE
            }

        }
        binding.appBarMain.contentMain.mainViewFlipper.displayedChild = CONTENT_LAYOUT
    }

    private fun initListeners() {
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
                    if (binding.postsViewFlipper.displayedChild == LOADING_DRAWER_LAYOUT) return@observe
                    binding.postsViewFlipper.displayedChild = LOADING_DRAWER_LAYOUT
                }
                UIState.Error -> {
                    if (binding.postsViewFlipper.displayedChild == ERROR_DRAWER_LAYOUT) return@observe
                    binding.postsViewFlipper.displayedChild = ERROR_DRAWER_LAYOUT
                }
                is UIState.Success -> {
                    postsAdapter?.refreshList(it.children)
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

    companion object {
        const val LOADING_DRAWER_LAYOUT = 0
        const val ERROR_DRAWER_LAYOUT = 1
        const val SUCCESS_DRAWER_LAYOUT = 2

        const val CONTENT_LAYOUT = 1
    }
}