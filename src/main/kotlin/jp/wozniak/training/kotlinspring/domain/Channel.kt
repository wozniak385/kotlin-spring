package jp.wozniak.training.kotlinspring.domain

import java.time.LocalDateTime
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class Channel (
    val id: Long = 0,
    val title: String = "",
    val overview: String = "",
    val user: User = User(),
    val lockVersion: Long = 0
)

class NewChannel (
    val title: String,
    val overview: String
){
    var generatedId: Long? = null
    var loginUser: User? = null
}

class PatchChannel (
    val id: Long,
    val title: String? = null,
    val overview: String? = null,
    var lockVersion: Long // 'var' because this is to be incremented
)