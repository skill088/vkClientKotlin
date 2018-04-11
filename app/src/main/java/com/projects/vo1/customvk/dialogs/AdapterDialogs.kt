package com.projects.vo1.customvk.dialogs

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.projects.vo1.customvk.R
import com.projects.vo1.customvk.utils.GlideRequests
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class AdapterDialogs(private val list: MutableList<Dialog>, private val glide: GlideRequests) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    @SuppressLint("SimpleDateFormat")
    private val sdf = SimpleDateFormat()
    private val date = Date()
    private val dayMillisec = TimeUnit.MILLISECONDS.convert(1L, TimeUnit.DAYS)

    private var clickListener: OnDialogClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.list_item_dialogs, parent, false
                )
                return DialogsHolder(view)
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

        if (holder is DialogsHolder) {
            val message = list[position]

            holder.itemView.setOnClickListener({
                clickListener?.onClick(
                    if (message.chatId == null) message.userId else message.chatId + 2000000000L,
                    if (message.title != "") message.title else message.userName!!,
                    message.chatId != null
                )
            })

            glide.load(message.photo)
                .placeholder(R.drawable.no_avatar)
                .into(holder.dialogAvatar)

            glide.load(message.userPhoto)
                .placeholder(R.drawable.no_avatar)
                .into(holder.userAvatar)

            holder.dialogName.text =
                    if (!message.title.equals("")) message.title else message.userName
            holder.messageBody.text = message.body

            date.time = (message.date * 1000L)
            val difference = Date().time - date.time
            when {
                difference < dayMillisec - 1 -> {
                    sdf.applyPattern("HH:mm")
                    holder.dialogTime.text = sdf.format(date)
                }
                difference in dayMillisec until (dayMillisec * 2) -> {
                    holder.dialogTime.text = "Вчера"
                }
                difference in (dayMillisec * 2)..(dayMillisec * 365) -> {
                    sdf.applyPattern("dd.MM")
                    holder.dialogTime.text = sdf.format(date)
                }
                else -> {
                    sdf.applyPattern("dd.MM.yyyy")
                    holder.dialogTime.text = sdf.format(date)
                }
            }
        } else if (holder is LoadingViewHolder) {
            holder.progressBar.isIndeterminate = true
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position].id == -1 && position != 0) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    fun setClickListener(listener: OnDialogClickListener) {
        clickListener = listener
    }

    class DialogsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dialogAvatar: CircleImageView = itemView.findViewById(R.id.dialog_avatar)
        val dialogName: TextView = itemView.findViewById(R.id.dialog_name)
        val dialogTime: TextView = itemView.findViewById(R.id.dialog_time)
        val userAvatar: CircleImageView = itemView.findViewById(R.id.interlocutor_avatar)
        val messageBody: TextView = itemView.findViewById(R.id.message_body)
    }

    private class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var progressBar: ProgressBar = view.findViewById(R.id.listProgressBar)
    }
}