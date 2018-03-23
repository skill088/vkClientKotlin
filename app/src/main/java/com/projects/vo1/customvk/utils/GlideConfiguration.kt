package com.projects.vo1.customvk.utils

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

@GlideModule
class GlideConfiguration : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setDefaultRequestOptions(RequestOptions()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .dontAnimate())
        super.applyOptions(context, builder)
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}