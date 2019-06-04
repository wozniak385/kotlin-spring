package jp.wozniak.training.kotlinspring.repository

import jp.wozniak.training.kotlinspring.domain.Channel
import jp.wozniak.training.kotlinspring.domain.NewChannel
import jp.wozniak.training.kotlinspring.domain.PatchChannel
import jp.wozniak.training.kotlinspring.mapper.ChannelMapper
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional


@Repository
class ChannelRepository(val channelMapper : ChannelMapper){

    fun findAll() : List<Channel> {
        return this.channelMapper.findAll()
    }

    fun findByUser(channelId: Long) : List<Channel> {
        return this.channelMapper.findByUser(channelId)
    }

    fun get(id: Long) : Channel {
        val channel = this.channelMapper.get(id)
        return channel ?: throw ResourceNotFoundException("No such a channel.")
    }

    fun post(channel: NewChannel) {
         this.channelMapper.post(channel)
    }

    fun patch(channel: PatchChannel) {
        val trying = channel.lockVersion
        val actual = this.get(channel.id).lockVersion
        if(trying < actual){
            throw UpdatingCollidedException("Updating attempt is collided.")
        }
        channel.lockVersion++
        this.channelMapper.patch(channel)
    }

    fun delete(id: Long) {
        val isChangeOccured = this.channelMapper.delete(id)
        if(!isChangeOccured){
            throw ResourceNotFoundException("No such a channel.")
        }
    }

    fun deleteByUser(userId: Long) {
        this.channelMapper.deleteByUser(userId)
    }

}