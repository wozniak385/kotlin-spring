package jp.wozniak.training.kotlinspring.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
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
    val hashedPassword: String = "",
    val expiresAt: LocalDateTime = LocalDateTime.now(),
    val firstName: String = "",
    val lastName: String = "",
    val adminFlag: Boolean = false,
    val state: UserState = UserState.CREATED,
    val lockVersion: Long = 0
) : UserDetails {
    val auths : Collection<GrantedAuthority> = listOf<GrantedAuthority>()

    override fun getUsername(): String = this.email
    override fun getPassword(): String = this.hashedPassword
    override fun getAuthorities(): Collection<GrantedAuthority> = this.auths
    override fun isAccountNonLocked(): Boolean = false
    override fun isAccountNonExpired(): Boolean = false
    override fun isCredentialsNonExpired(): Boolean = false
    override fun isEnabled(): Boolean = false
}

class NewUser (
    val password: String,
    val email: String,
    val firstName: String,
    val expiresAt: LocalDateTime,
    val adminFlag: Boolean,
    val lastName: String
){
    private val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder() //NOTTODO: it should be autowired -> akirameta.
    val hashedPassword: String
        get() = BCryptPasswordEncoder().encode(this.password)

    var generatedId: Long? = null // not in constructor, because this will be assigned after MySQL access.
}

class PatchUser (
    val id: Long,
    val email: String? = null,
    val hashedPassword: String? = null, //TODO: i think this shoudn'd be here (instead of it, `password`)
    val expiresAt: LocalDateTime? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val adminFlag: Boolean? = null,
    val state: UserState? = null,
    var lockVersion: Long // `var` because this is to be incremented
)