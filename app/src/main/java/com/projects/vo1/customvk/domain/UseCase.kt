package com.projects.vo1.customvk.domain

import io.reactivex.Single

interface UseCase<in P, R> {

    fun execute(param: P): Single<R>
}