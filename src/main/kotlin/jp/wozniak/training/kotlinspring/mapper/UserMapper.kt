package jp.wozniak.training.kotlinspring.mapper

import jp.wozniak.training.kotlinspring.domain.User
import jp.wozniak.training.kotlinspring.domain.NewUser
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface UserMapper{
    fun insert(newUser: NewUser): Int
    fun select(id: Long): User
}