package com.projects.vo1.customvk.proffile

import com.projects.vo1.customvk.data.profile.ProfileRepository
import com.projects.vo1.customvk.presenter.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PresenterProfile(private val repository: ProfileRepository,
                       private val view: ProfileView) : BasePresenter() {

    private var profile = ProfileInfo()

    fun setProfile(id: String?) {
        compositeDisposable.add(
            repository.getUserInfo(id)
                .map {
                    profile.firstName = it.firstName
                    profile.lastName = it.lastName
                    profile.bdate = it.bdate
                    profile.city = it.city
                    profile.photoMax = it.photoMax
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { view.setProfile(profile) }
                .subscribe()
        )
    }
}