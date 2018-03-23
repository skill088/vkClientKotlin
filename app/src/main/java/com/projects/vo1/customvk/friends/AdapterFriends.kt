package com.projects.vo1.customvk.friends

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.projects.vo1.customvk.R
import de.hdodenhof.circleimageview.CircleImageView

class AdapterFriends : RecyclerView.Adapter<AdapterFriends.FriendsHolder>() {

    private var list: List<FriendInfo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsHolder {
        val layout = R.layout.friends_list_item
        val v: View = LayoutInflater.from(parent.context).inflate(layout, parent,
                false)
        return FriendsHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(newList: List<FriendInfo>) {
        list = newList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: FriendsHolder, position: Int) {
        val context = holder.itemView
        val info = list[position]

        holder.friendName.text = context.resources.getString(R.string.friend_name,
                info.firstName, info.lastName)

        Glide.with(context)
                .setDefaultRequestOptions(RequestOptions()
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .dontAnimate())
                .load(info.photo)
                .into(holder.friendAvatar)
    }

    class FriendsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val friendName: TextView = itemView.findViewById(R.id.friend_name)
        val friendAvatar: CircleImageView = itemView.findViewById(R.id.friend_avatar)
    }
}