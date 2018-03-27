package com.projects.vo1.customvk.proffile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.projects.vo1.customvk.R
import com.projects.vo1.customvk.data.api.profile.ApiProfile
import com.projects.vo1.customvk.data.profile.ProfileRepositoryImpl
import com.projects.vo1.customvk.data.nework.ApiInterfaceProvider
import kotlinx.android.synthetic.main.fragment_profile.*



class FragmentProfile : Fragment(), ProfileView {

    private var presenter: PresenterProfile? = null
    private var id: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = activity as AppCompatActivity
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

        Glide.with(this)
            .apply { setDefaultRequestOptions(
                RequestOptions()
                    .format(DecodeFormat.PREFER_ARGB_8888)) }
//            .setDefaultRequestOptions(
//                RequestOptions()
//                    .format(DecodeFormat.PREFER_ARGB_8888)
//                    .dontAnimate()
//            )
            .load(profile.photoMax)
            .into(user_avatar)

        user_bdate.text = activity?.resources?.getString(R.string.user_bdate, profile.bdate)
        user_city.text = activity?.resources?.getString(R.string.user_city, profile.city?.title)
    }

    companion object {

        fun newInstance(): FragmentProfile {
            return FragmentProfile()
        }

        fun newInstance(id: String?) : FragmentProfile {

            var fragment = FragmentProfile()
            val args = Bundle()
            args.putString("USER_ID", id)
            fragment.arguments = args

            return fragment
        }
    }
}