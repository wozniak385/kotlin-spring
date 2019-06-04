package jp.wozniak.training.kotlinspring.service

import jp.wozniak.training.kotlinspring.domain.NewUser
import jp.wozniak.training.kotlinspring.domain.User
import jp.wozniak.training.kotlinspring.domain.PatchUser
import jp.wozniak.training.kotlinspring.repository.ChannelRepository
import jp.wozniak.training.kotlinspring.repository.UserRepository

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository
//    ,private val knowledgeRepository: KnowledgeRepository
    ,private val channelRepository: ChannelRepository
){

    fun findAll(): List<User> {
        return this.userRepository.findAll()
    }

    fun get(id: Long): User {
        return this.userRepository.get(id)
    }

    @Transactional(rollbackFor = [Throwable::class])
    fun post(user: NewUser) {
        this.userRepository.post(user)
    }

    @Transactional(rollbackFor = [Throwable::class])
    fun patch(user: PatchUser) {
        this.userRepository.patch(user)
    }

    @Transactional(rollbackFor = [Throwable::class])
    fun delete(id: Long) {
        this.userRepository.delete(id)
        this.channelRepository.deleteByUser(id)
    }
}