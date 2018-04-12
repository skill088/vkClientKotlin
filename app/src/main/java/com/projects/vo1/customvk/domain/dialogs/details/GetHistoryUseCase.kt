package com.projects.vo1.customvk.domain.dialogs.details

import com.projects.vo1.customvk.data.dialogs.details.Message
import com.projects.vo1.customvk.domain.UseCaseWithTwoParameters
import io.reactivex.Single

//class GetHistoryUseCase(detailRepository: DialogDetailRepository) :
//    UseCase<
//    object param{
//        val offset: Int = -1
//        val uid: Long = -1
//    },
//    List<Message>> {
//}

class GetHistoryUseCase(private val detailRepository: DialogDetailRepository) :
    UseCaseWithTwoParameters<Int, Long, List<Message>> {

    override fun execute(param1: Int, param2: Long): Single<List<Message>> {
        return detailRepository.getHistory(param1, param2)
    }
}