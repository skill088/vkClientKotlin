package com.projects.vo1.customvk.dialogs

/**
 * Created by Admin on 29.03.2018.
 */
interface DialogsView {

    fun showMessages(dialogs: List<Dialog>)
    fun showMore(dialogs: List<Dialog>)
    fun showError()
    fun reload(dialogs: List<Dialog>)
}