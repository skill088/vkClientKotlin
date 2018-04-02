package com.projects.vo1.customvk.dialogs.details

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.projects.vo1.customvk.R
import java.text.SimpleDateFormat
import java.util.*

class AdapterMessages(private val list: MutableList<Message>) :
    RecyclerView.Adapter<AdapterMessages.MessageHolder>() {

    private val MESSAGE_TYPE_IN = 0
    private val MESSAGE_TYPE_OUT = 1
    @SuppressLint("SimpleDateFormat")
    private val sdf = SimpleDateFormat()
    private val date = Date()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterMessages.MessageHolder {

        return when(viewType) {
            MESSAGE_TYPE_IN -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.list_item_message_in, parent, false
                )
                MessageHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.list_item_message_out, parent, false
                )
                MessageHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(list[position].out) {
            1 -> MESSAGE_TYPE_OUT
            else -> MESSAGE_TYPE_IN
        }
    }

    override fun onBindViewHolder(holder: AdapterMessages.MessageHolder, position: Int) {
        val message = list[position]

        holder.messageBody.text = message.body
        date.time = message.date * 1000L
        sdf.applyPattern("dd.MM.yyyy в HH:mm")
        holder.messageTime.text = sdf.format(date)
    }

    class MessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageBody: TextView = itemView.findViewById(R.id.message_body)
        val messageTime: TextView = itemView.findViewById(R.id.message_time)
    }
}
