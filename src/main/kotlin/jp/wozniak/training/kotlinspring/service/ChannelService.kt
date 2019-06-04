package jp.wozniak.training.kotlinspring.service

import jp.wozniak.training.kotlinspring.domain.Channel
import jp.wozniak.training.kotlinspring.domain.NewChannel
import jp.wozniak.training.kotlinspring.domain.PatchChannel
import jp.wozniak.training.kotlinspring.domain.User
import jp.wozniak.training.kotlinspring.repository.ChannelRepository

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChannelService(
    private val channelRepository: ChannelRepository
){

    fun findAll(): List<Channel> {
        return this.channelRepository.findAll()
    }

    fun findByUser(userId: Long): List<Channel> {
        return this.channelRepository.findByUser(userId)
    }

    fun get(id: Long): Channel {
        return this.channelRepository.get(id)
    }

    @Transactional(rollbackFor = [Throwable::class])
    fun post(channel: NewChannel) {
        this.channelRepository.post(channel)
    }

    @Transactional(rollbackFor = [Throwable::class])
    fun patch(channel: PatchChannel) {
        this.channelRepository.patch(channel)
    }

    @Transactional(rollbackFor = [Throwable::class])
    fun delete(id: Long) {
        this.channelRepository.delete(id)
    }

    @Transactional(rollbackFor = [Throwable::class])
    fun deleteByUser(userId: Long) {
        this.channelRepository.deleteByUser(userId)
    }
}