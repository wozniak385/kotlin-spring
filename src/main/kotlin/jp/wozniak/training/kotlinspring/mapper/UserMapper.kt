package jp.wozniak.training.kotlinspring.mapper

import jp.wozniak.training.kotlinspring.domain.NewUser
import jp.wozniak.training.kotlinspring.domain.PatchUser
import jp.wozniak.training.kotlinspring.domain.User
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserMapper{
    fun findAll(): List<User>
    fun get(id: Long): User?
    fun post(user: NewUser): Boolean
    fun getByEmail(email: String): User
    fun patch(user: PatchUser): Boolean
    fun delete(id: Long): Boolean
}