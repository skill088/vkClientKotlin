package com.projects.vo1.customvk.data.utils

import com.projects.vo1.customvk.data.network.Error
import com.projects.vo1.customvk.data.network.response.ApiError
import com.projects.vo1.customvk.services.LongPollBaseResponse
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

    fun <T : LongPollBaseResponse> longPollTransformer(): SingleTransformer<T, T> {
        return SingleTransformer {
            it.map {
                when (it.failed) {
                    null -> it
                    1 -> {
                        val ex = Error.OldTSException()
                        ex.ts = it.ts
                        throw ex
                    }
                    2,3 -> throw Error.ObsoletedKeyException()
                    else -> throw Exception()
                }
            }
        }
    }
}