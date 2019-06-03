package jp.wozniak.training.kotlinspring.domain

import java.time.LocalDateTime
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

enum class UserState{
    CREATED, INITIALIZED, SIGNEDUP
}

class User (
    val id: Long = 0,
    val email: String = "",
    //val hashedPassword: String,
    val expiresAt: LocalDateTime = LocalDateTime.now(),
    val firstName: String = "",
    val lastName: String = "",
    val adminFlag: Boolean = false,
    val state: UserState = UserState.CREATED,
    val lockVersion: Long = 0
)

class NewUser (
    val email: String,
    val hashedPassword: String,
    val expiresAt: LocalDateTime,
    val firstName: String,
    val lastName: String,
    val adminFlag: Boolean
){
    var generatedId: Long? = null // not in constructor, because this will be assigned after MySQL access.
}

class PatchUser (
    val id: Long,
    val email: String? = null,
    val hashedPassword: String? = null,
    val expiresAt: LocalDateTime? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val adminFlag: Boolean? = null,
    val state: UserState? = null,
    var lockVersion: Long // 'var' because this is to be incremented
)