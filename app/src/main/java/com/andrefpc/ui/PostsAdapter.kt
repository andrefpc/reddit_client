package com.andrefpc.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andrefpc.data.RedditChild
import com.andrefpc.data.RedditData
import com.andrefpc.databinding.PostLayoutBinding
import com.andrefpc.extensions.ImageViewExtensions.loadImage
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.andrefpc.extensions.ViewExtensions.blink
import com.andrefpc.extensions.ViewExtensions.collapseHorizontal
import com.andrefpc.extensions.ViewExtensions.stopBlink


class PostsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val postList: ArrayList<RedditChild> = arrayListOf()
    private var selectListener: (RedditData) -> Unit = { }

    fun onSelect(selectListener: (RedditData) -> Unit) {
        this.selectListener = selectListener
    }

    fun refreshList(list: List<RedditChild>){
        if(postList.isNotEmpty()) {
            val oldListCount = postList.size
            postList.clear()
            notifyItemRangeRemoved(0, oldListCount)
        }
        postList.addAll(list)
        notifyItemRangeInserted(0, list.size)
    }

    fun addList(list: List<RedditChild>){
        val positionStart = postList.size
        postList.addAll(list)
        notifyItemRangeInserted(positionStart, list.size)
    }

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
        holder.binding.postImage.loadImage(data.thumbnail)
        holder.binding.postText.text = data.title
        holder.binding.postTime.text = data.getTime()
        holder.binding.postComments.text = data.getComments()
        if(!child.read) holder.binding.postRead.blink()
        else holder.binding.postRead.visibility = View.GONE
        holder.binding.root.setOnClickListener {
            holder.binding.postRead.stopBlink()
            holder.binding.postRead.collapseHorizontal()
            selectListener(data)
        }
        holder.binding.postClear.setOnClickListener { removePost(data.name) }
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    class PostsViewHolder(val binding: PostLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}