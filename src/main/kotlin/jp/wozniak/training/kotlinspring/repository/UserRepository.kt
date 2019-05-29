package jp.wozniak.training.kotlinspring.repository

import jp.wozniak.training.kotlinspring.domain.NewUser
import jp.wozniak.training.kotlinspring.domain.User
import jp.wozniak.training.kotlinspring.mapper.UserMapper
import org.mybatis.spring.SqlSessionTemplate
import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Repository


@Repository
class UserRepository(val userMapper : UserMapper, val sqlSessionTemplate: SqlSessionTemplate){

    fun add(newUser: User)  {
         this.userMapper.insert(newUser)
    }

    fun get(id: Long) :User {
        return this.sqlSessionTemplate.getMapper(UserMapper::class.java).select(id)
    }
}