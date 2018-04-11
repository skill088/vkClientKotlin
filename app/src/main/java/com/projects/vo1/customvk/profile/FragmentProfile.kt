package com.projects.vo1.customvk.profile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projects.vo1.customvk.R
import com.projects.vo1.customvk.activities.FragmentError
import com.projects.vo1.customvk.data.api.profile.ApiProfile
import com.projects.vo1.customvk.data.profile.ProfileRepositoryImpl
import com.projects.vo1.customvk.data.network.ApiInterfaceProvider
import com.projects.vo1.customvk.utils.GlideApp
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.fragment_profile.*


class FragmentProfile : Fragment(), ProfileView {

    private var presenter: PresenterProfile? = null
    private var id: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, fragment_container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = activity as AppCompatActivity
        toolbar.title=""
        activity.setSupportActionBar(toolbar)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        id = arguments?.getString("USER_ID")
        presenter = PresenterProfile(
            ProfileRepositoryImpl(
                ApiInterfaceProvider.getApiInterface(ApiProfile::class.java),
                activity?.applicationContext!!
            ), this
        )

        presenter?.setProfile(id)
    }

    override fun onStop() {
        super.onStop()
        presenter?.clearCompositeDesposable()
    }

    override fun setProfile(profile: ProfileInfo) {
        collapsing_toolbar.title =
                "${profile.firstName} ${profile.lastName}"

        GlideApp.with(this)
            .load(profile.photoMax)
            .placeholder(R.drawable.no_avatar400)
            .into(user_avatar)

        user_bdate.text =
                activity?.resources?.getString(R.string.user_bdate,
                    profile.bdate ?: "не указано")
        user_city.text = activity?.resources?.getString(R.string.user_city,
            profile.city?.title?:"не указано")
    }

    override fun showError() {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.fragment_container, FragmentError.newInstance())
            ?.commit()
    }

    companion object {

        fun newInstance(): FragmentProfile {
            return FragmentProfile()
        }

        fun newInstance(id: String?): FragmentProfile {

            val fragment = FragmentProfile()
            val args = Bundle()
            args.putString("USER_ID", id)
            fragment.arguments = args

            return fragment
        }
    }
}