package com.projects.vo1.customvk.dialogs.details

/**
 * Created by Admin on 30.03.2018.
 */
interface MessagesView {

    fun showMessages(messages: List<Message>)
    fun showMore(messages: List<Message>)
    fun setSended()
}