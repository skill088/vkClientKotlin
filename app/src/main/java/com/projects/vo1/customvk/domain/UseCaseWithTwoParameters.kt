package com.projects.vo1.customvk.domain

import io.reactivex.Single

interface UseCaseWithTwoParameters<P1, P2, R> {

    fun execute(param1: P1, param2: P2): Single<R>
}