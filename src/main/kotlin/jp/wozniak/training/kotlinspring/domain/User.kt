package jp.wozniak.training.kotlinspring.domain

import java.time.LocalDateTime
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

enum class UserState{
    CREATED, INITIALIZED, SIGNEDUP
}

class User {
    var id: Long = -1
    var email: String = ""
    var hashedPassword: String = ""
    var expiresAt: LocalDateTime = LocalDateTime.now()
    var firstName: String = ""
    var lastName: String = ""
    var adminFlag: Boolean = false
    var state: UserState = UserState.CREATED
    var lockVersion: Long = 0
}

class NewUser (
    val email: String,
    val hashedPassword: String,
    val expiresAt: LocalDateTime,
    val firstName: String,
    val lastName: String,
    val adminFlag: Boolean
){
    var generatedId: Long? = null
}

class PatchUser {
    var id: Long = -1
    var email: String? = null
    var hashedPassword: String? = null
    var expiresAt: LocalDateTime? = null
    var firstName: String? = null
    var lastName: String? = null
    var adminFlag: Boolean? = null
    var state: UserState? = null
    var lockVersion: Long = 0
}