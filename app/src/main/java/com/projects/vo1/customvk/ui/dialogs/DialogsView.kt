package com.projects.vo1.customvk.ui.dialogs

import com.projects.vo1.customvk.data.dialogs.Dialog

interface DialogsView {

    fun showMessages(dialogs: List<Dialog>)
    fun showMore(dialogs: List<Dialog>)
    fun showError()
    fun reload(dialogs: List<Dialog>)
}