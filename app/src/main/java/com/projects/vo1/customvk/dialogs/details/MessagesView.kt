package com.projects.vo1.customvk.dialogs.details

interface MessagesView {

    fun showMessages(messages: List<Message>)
    fun showMore(messages: List<Message>)
    fun setSent(sentId: Long)
}