package com.projects.vo1.customvk.ui.dialogs.details

import android.annotation.SuppressLint
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.projects.vo1.customvk.R
import com.projects.vo1.customvk.data.dialogs.details.Message
import com.projects.vo1.customvk.utils.GlideApp
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*

class AdapterMessages(private val list: MutableList<Message>,
                      private val longClickListener: OnLongClickShare
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val MESSAGE_TYPE_IN = 0
    private val MESSAGE_TYPE_OUT = 1
    private val MESSAGE_TYPE_SENDING = 2
    private val VIEW_TYPE_LOADING = -1
    @SuppressLint("SimpleDateFormat")
    private val sdf = SimpleDateFormat()
    private val date = Date()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        return when (viewType) {
            MESSAGE_TYPE_IN -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.list_item_message_in, parent, false
                )
                MessageHolder(view)
            }
            MESSAGE_TYPE_OUT, MESSAGE_TYPE_SENDING -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.list_item_message_out, parent, false
                )
                MessageHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_loading,
                    parent, false
                )
                LoadingViewHolder(
                    view
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position].out) {
            0 -> MESSAGE_TYPE_IN
            1 -> MESSAGE_TYPE_OUT
            2 -> MESSAGE_TYPE_SENDING
            else -> VIEW_TYPE_LOADING
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is MessageHolder) {

            val message = list[position]

            holder.itemView.setOnLongClickListener {
                longClickListener.onLongLcick(message.body)
            true
            }

            if (getItemViewType(position) == MESSAGE_TYPE_SENDING)
                holder.itemView.setBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.grey
                    )
                )

            if (message.photo != null && getItemViewType(position) == MESSAGE_TYPE_IN)
                GlideApp.with(holder.itemView.context)
                    .load(message.photo)
                    .placeholder(R.drawable.no_avatar)
                    .into(holder.avatar!!)

            holder.messageBody.text = message.body
            date.time = message.date * 1000L
            sdf.applyPattern("dd.MM.yyyy в HH:mm")
            holder.messageTime.text = sdf.format(date)
        } else if (holder is LoadingViewHolder) {
            holder.progressBar.isIndeterminate = true
        }
    }

    private class MessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageBody: TextView = itemView.findViewById(R.id.message_body)
        val messageTime: TextView = itemView.findViewById(R.id.message_time)
        val avatar: CircleImageView? = itemView.findViewById(R.id.avatar)
    }

    private class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var progressBar: ProgressBar = view.findViewById(R.id.listProgressBar)
    }
}
