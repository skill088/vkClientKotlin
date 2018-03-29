package com.projects.vo1.customvk.dialogs

/**
 * Created by Admin on 29.03.2018.
 */
interface DialogsView {

    fun showMessages(messages: MutableList<Message>)
    fun showMore(messages: MutableList<Message>)
    fun showError()
}