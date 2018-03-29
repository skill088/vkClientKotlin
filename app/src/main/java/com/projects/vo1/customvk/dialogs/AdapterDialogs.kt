package com.projects.vo1.customvk.dialogs

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.projects.vo1.customvk.R
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class AdapterDialogs(private val list: MutableList<Message>) :
    RecyclerView.Adapter<AdapterDialogs.DialogsHolder>() {

    @SuppressLint("SimpleDateFormat")
    private val sdf = SimpleDateFormat()
    private val dayMillisec = TimeUnit.MILLISECONDS.convert(1L, TimeUnit.DAYS)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogsHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_dialogs, parent, false
        )
        return DialogsHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: DialogsHolder, position: Int) {

        val context = holder.itemView
        val message = list[position]

//        Glide.with(context)
//            .load(message.photo)
//            .into(holder.dialogAvatar)

//        Glide.with(context)
//            .load(message.userPhoto)
//            .into(holder.userAvatar)

//        holder.dialogName.text = message.title
//        holder.messageBody.text = message.body

//        val date = Date(message.date!! * 1000L)
//        val difference = Date().time - date.time
//        when {
//            difference < dayMillisec-1 -> {
//                sdf.applyPattern("HH:mm")
//                holder.dialogTime.text = sdf.format(date)
//            }
//            difference in dayMillisec until (dayMillisec*2) -> {
//                holder.dialogTime.text = "Вчера"
//            }
//            difference in (dayMillisec*2)..(dayMillisec*365) -> {
//                sdf.applyPattern("dd MM")
//                holder.dialogTime.text = sdf.format(date)
//            }
//            else -> {
//                sdf.applyPattern("dd MM yyyy")
//                holder.dialogTime.text = sdf.format(date)
//            }
//        }
    }

    class DialogsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dialogAvatar: CircleImageView = itemView.findViewById(R.id.dialog_avatar)
        val dialogName: TextView = itemView.findViewById(R.id.dialog_name)
        val dialogTime: TextView = itemView.findViewById(R.id.dialog_time)
        val userAvatar: CircleImageView = itemView.findViewById(R.id.interlocutor_avatar)
        val messageBody: TextView = itemView.findViewById(R.id.message_body)
    }
}