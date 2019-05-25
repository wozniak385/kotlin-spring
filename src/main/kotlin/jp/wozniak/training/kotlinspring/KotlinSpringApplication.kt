package jp.wozniak.training.kotlinspring

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration


@MapperScan("jp.wozniak.training.kotlinspring.mapper")
@SpringBootApplication
class KotlinSpringApplication

fun main(args: Array<String>) {
	runApplication<KotlinSpringApplication>(*args)
}
