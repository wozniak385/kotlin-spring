package jp.wozniak.training.kotlinspring

import java.time.LocalDateTime

import jp.wozniak.training.kotlinspring.domain.NewUser
import jp.wozniak.training.kotlinspring.domain.User
import jp.wozniak.training.kotlinspring.mapper.UserMapper
import jp.wozniak.training.kotlinspring.repository.UserRepository
import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class HelloController (
    val userMapper: UserMapper,
    val userRepository: UserRepository
) {

    @RequestMapping("/")
    fun index() : String{
        return "Hello Spring Boot";
    }


    @RequestMapping(path=["/add"], produces=["application/json"])
    fun add() : Int {
        val now = LocalDateTime.now()
        val newUser: NewUser = NewUser(
            "hoge@mail.com",
            "qwertyuiop",
            now, "Taro","Yamada",
            false
        )
        val result = this.userMapper.insert(newUser)
        return result
    }

    @RequestMapping(path=["/get/{id}"], produces=["application/json"])
    fun get(@PathVariable id: Long) : User {
        return this.userRepository.get(id)
    }
}