package com.andrefpc.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.recyclerview.widget.RecyclerView
import com.andrefpc.data.RedditChild
import com.andrefpc.data.RedditData
import com.andrefpc.databinding.PostLayoutBinding
import com.andrefpc.extensions.ImageViewExtensions.loadImage
import com.andrefpc.extensions.ViewExtensions.blink
import com.andrefpc.extensions.ViewExtensions.stopBlink


class PostsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val postList: ArrayList<RedditChild> = arrayListOf()
    private var selectListener: (RedditData) -> Unit = { }
    private var removeListener: () -> Unit = { }

    /**
     * Listener to listen to post selections
     */
    fun onSelect(selectListener: (RedditData) -> Unit) {
        this.selectListener = selectListener
    }

    /**
     * Listener to listen to post removals
     */
    fun onRemove(removeListener: () -> Unit) {
        this.removeListener = removeListener
    }

    /**
     * Get the last post name of the current list to pass to api to do pagination
     * @return The name of the last post
     */
    fun getLastItemName(): String {
        return postList[postList.size - 1].data.name
    }

    /**
     * Refresh the full list of the recyclerView
     * @param list The new list to be shown
     */
    fun refreshList(list: List<RedditChild>) {
        if (postList.isNotEmpty()) {
            val oldListCount = postList.size
            postList.clear()
            notifyItemRangeRemoved(0, oldListCount)
        }
        postList.addAll(list)
        notifyItemRangeInserted(0, list.size)
    }

    /**
     * Add the list on the end of the recyclerView current list
     * @param list The new list to be added
     */
    fun addList(list: List<RedditChild>) {
        val positionStart = postList.size
        postList.addAll(list)
        notifyItemRangeInserted(positionStart, list.size)
    }

    /**
     * Remove a post from the RecyclerView
     * @param name The name of the post to be removed
     */
    private fun removePost(name: String){
        val index = postList.indexOfFirst { it.data.name == name }
        postList.removeAt(index)
        notifyItemRemoved(index)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = PostLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PostsViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val holder = viewHolder as PostsViewHolder
        val child: RedditChild = postList[position]
        val data: RedditData = child.data
        holder.binding.postAuthor.text = data.author
        holder.binding.postImage.loadImage(data.thumbnail) {
            holder.binding.postImage.visibility = View.GONE
        }
        holder.binding.postText.text = data.title
        holder.binding.postTime.text = data.getTime()
        holder.binding.postComments.text = data.getComments()
        var animation: Animation? = null
        if (!child.read) animation = holder.binding.postRead.blink()
        else holder.binding.postRead.stopBlink(animation)
        holder.binding.root.setOnClickListener {
            holder.binding.postRead.stopBlink(animation)
            child.read = true
            selectListener(data)
        }
        holder.binding.postClear.setOnClickListener {
            removeListener()
            removePost(data.name)
        }
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    /**
     * The ViewHolder to be used on the adapter
     * @param binding The layout binding
     */
    class PostsViewHolder(val binding: PostLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}