package jp.wozniak.training.kotlinspring.domain

import java.time.LocalDateTime
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

enum class UserState{
    CREATED, INITIALIZED, SIGNEDUP
}

class User(
    val id: Long
    ,var email: String
    ,var hashedPassword: String
    ,var expiresAt: LocalDateTime
    ,var firstName: String
    ,var lastName: String
    ,var adminFlag: Boolean
    ,var state: UserState
    ,var updatedUser: Long?
    ,var updatedAt: LocalDateTime?
    ,var lockVersion: Long
)

class NewUser(
     var email: String
    ,var hashedPassword: String
    ,var expiresAt: LocalDateTime
    ,var firstName: String
    ,var lastName: String
    ,var adminFlag: Boolean
)