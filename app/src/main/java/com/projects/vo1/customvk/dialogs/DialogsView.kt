package com.projects.vo1.customvk.dialogs

interface DialogsView {

    fun showMessages(dialogs: List<Dialog>)
    fun showMore(dialogs: List<Dialog>)
    fun showError()
    fun reload(dialogs: List<Dialog>)
}