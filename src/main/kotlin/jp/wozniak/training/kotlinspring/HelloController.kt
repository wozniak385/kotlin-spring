package jp.wozniak.training.kotlinspring

import java.time.LocalDateTime

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
    val userRepository: UserRepository
) {

    @RequestMapping("/")
    fun index() : String{
        return "Hello Spring Boot"
    }
}