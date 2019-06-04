package jp.wozniak.training.kotlinspring.mapper

import jp.wozniak.training.kotlinspring.domain.Channel
import jp.wozniak.training.kotlinspring.domain.NewChannel
import jp.wozniak.training.kotlinspring.domain.PatchChannel
import org.apache.ibatis.annotations.Mapper

@Mapper
interface ChannelMapper{
    fun findAll(): List<Channel>
    fun findByUser(userId: Long): List<Channel>
    fun get(id: Long): Channel?
    fun post(user: NewChannel): Boolean
    fun patch(user: PatchChannel): Boolean
    fun delete(id: Long): Boolean
    fun deleteByUser(userId: Long): Boolean
}