package com.projects.vo1.customvk.domain

import io.reactivex.Single

interface UseCaseWithoutParameters<R> {

    fun execute(): Single<R>
}