package com.projects.vo1.customvk.data.utils

import com.projects.vo1.customvk.data.network.Error
import com.projects.vo1.customvk.data.network.response.ApiError
import io.reactivex.Single
import io.reactivex.SingleTransformer

object Transformer {

    fun <T : ApiError> errorTransformer(): SingleTransformer<T, T> {
        return SingleTransformer(
            { upstream ->
                upstream
                    .map {
                        when (it.error) {
                            null -> it
                            else -> throw Error.WrongGetException()
                        }
                    }
            })
    }
}