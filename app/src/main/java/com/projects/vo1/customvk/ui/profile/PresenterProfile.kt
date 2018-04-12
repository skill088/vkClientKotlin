package com.projects.vo1.customvk.ui.profile

import android.util.Log
import com.projects.vo1.customvk.data.data.network.Error
import com.projects.vo1.customvk.domain.profile.GetUserProfileUseCase
import com.projects.vo1.customvk.ui.views.presenter.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException

class PresenterProfile(
    private val getUserProfile: GetUserProfileUseCase,
    private val view: ProfileView
) : BasePresenter() {

    fun setProfile(id: String?) {
        compositeDisposable.add(
            getUserProfile.execute(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( {
                    view.setProfile(it)
                }, {
                    view.showError()
                    when (it) {
                        is Error.WrongGetException -> Log.e("error: ", it.message)
                        is UnknownHostException -> view.showError()
                        else -> {
                            Log.e("error: ", it.message + "\n")
                            it.printStackTrace()
                        }
                    }
                })
        )
    }
}