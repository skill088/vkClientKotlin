//package com.projects.vo1.customvk.domain.longPolling
//
//import com.projects.vo1.customvk.domain.UseCaseWithoutParameters
//import io.reactivex.Single
//
//class GetLongPollServerUseCase(private val longPollRepository: LongPollRepository) :
//    UseCaseWithoutParameters<String> {
//
//    override fun execute(): Single<String> {
//        return longPollRepository.getLongPollServer()
//            .map { "https://${it.server}?act=a_check&wait=10&mode=2&version=2" +
//                    "&key=${it.key}" +
//                    "&ts=${it.ts}" }
//    }
//}