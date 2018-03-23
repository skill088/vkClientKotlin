package com.projects.vo1.customvk.friends

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.projects.vo1.customvk.R
import de.hdodenhof.circleimageview.CircleImageView
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import android.support.v7.widget.LinearLayoutManager
import com.projects.vo1.customvk.utils.OnLoadMoreListener

class AdapterFriendsTemp(recyclerView: RecyclerView) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    private var list: List<FriendInfo> = emptyList()
    private var onLoadMoreListener: OnLoadMoreListener? = null
    private var isLoading = false
    private val visibleThreshold = 5
    private var lastVisibleItem = 0
    private var totalItemCount = 0

    init {
        val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = linearLayoutManager.itemCount
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
                    onLoadMoreListener?.onLoadMore()
                    isLoading = true
                }
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.friends_list_item,
                        parent, false)
                FriendHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_loading,
                    parent, false)
                LoadingViewHolder(view)}
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val context = holder.itemView
        if (holder is FriendHolder) {
            val friend = list[position]
//            val userViewHolder = holder as FriendHolder
            holder.friendName.text = context.resources.getString(R.string.friend_name,
                    friend.firstName, friend.lastName)
            Glide.with(context)
                    .setDefaultRequestOptions(RequestOptions()
                            .format(DecodeFormat.PREFER_ARGB_8888)
                            .dontAnimate())
                    .load(friend.photo)
                    .into(holder.friendAvatar)
        } else if (holder is LoadingViewHolder) {
            holder.progressBar.isIndeterminate = true
        }
    }

    fun setList(newList: List<FriendInfo>) {
        list = newList
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    fun setOnLoadMoreListener(mOnLoadMoreListener: OnLoadMoreListener) {
        onLoadMoreListener = mOnLoadMoreListener
    }

    fun setLoaded() {
        isLoading = false
    }

    private class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var progressBar: ProgressBar = view.findViewById(R.id.listProgressBar)
    }

    private class FriendHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val friendName: TextView = itemView.findViewById(R.id.friend_name)
        val friendAvatar: CircleImageView = itemView.findViewById(R.id.friend_avatar)
    }
}