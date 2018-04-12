package com.projects.vo1.customvk.ui.dialogs.details

import com.projects.vo1.customvk.data.dialogs.details.Message

interface MessagesView {

    fun showMessages(messages: List<Message>)
    fun showMore(messages: List<Message>)
    fun setSent(sentId: Long)
}