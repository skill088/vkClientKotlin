package com.projects.vo1.customvk.ui.friends

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
import com.projects.vo1.customvk.data.friends.FriendInfo

class AdapterFriends(recyclerView: RecyclerView, private val list: MutableList<FriendInfo>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    private var onLoadMoreListener: OnLoadMoreListener? = null
    private var isLoading = false
    private val visibleThreshold = 3
    private var lastVisibleItem = 0
    private var totalItemCount = 0

    private var callback: FriendInfoCallback? = null

    init {
        val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = linearLayoutManager.itemCount
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
                    if (lastVisibleItem == -1)
                        return
                    onLoadMoreListener?.onLoadMore()
                    isLoading = true
                }
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.list_item_friends,
                    parent, false
                )
                FriendHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_loading,
                    parent, false
                )
                LoadingViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val context = holder.itemView

        if (holder is FriendHolder) {
            val friend = list[position]

            holder.itemView.setOnClickListener(View.OnClickListener {
                callback?.onClick(friend.id.toString())
            })

            holder.friendName.text = context.resources.getString(
                R.string.friend_name,
                friend.firstName, friend.lastName
            )
            Glide.with(context)
                .setDefaultRequestOptions(
                    RequestOptions()
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .dontAnimate()
                )
                .load(friend.photo)
                .into(holder.friendAvatar)
        } else if (holder is LoadingViewHolder) {
            holder.progressBar.isIndeterminate = true
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position].id == -1L && position != 0) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    fun setOnLoadMoreListener(mOnLoadMoreListener: OnLoadMoreListener) {
        onLoadMoreListener = mOnLoadMoreListener
    }

    fun setLoaded() {
        isLoading = false
    }

    fun setOnClickListener(listener: FriendInfoCallback) {
        callback = listener
    }

    private class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var progressBar: ProgressBar = view.findViewById(R.id.listProgressBar)
    }

    private class FriendHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val friendName: TextView = itemView.findViewById(R.id.friend_name)
        val friendAvatar: CircleImageView = itemView.findViewById(R.id.friend_avatar)
    }
}