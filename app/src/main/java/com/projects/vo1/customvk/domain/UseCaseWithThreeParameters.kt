package com.projects.vo1.customvk.domain

import io.reactivex.Single

interface UseCaseWithThreeParameters<in P1, in P2, in P3, R> {

    fun execute(param1: P1, param2: P2, param3: P3): Single<R>
}