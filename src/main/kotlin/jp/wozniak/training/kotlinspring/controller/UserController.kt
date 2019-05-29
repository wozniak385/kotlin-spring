package jp.wozniak.training.kotlinspring.controller

import jp.wozniak.training.kotlinspring.domain.User
import jp.wozniak.training.kotlinspring.repository.UserRepository
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/users")
class UserController(
    private val userRepository: UserRepository
//    private val passwordEncoder: PasswordEncoder,
//    private val randomStringGenerator: RandomStringGenerator,
//    private val messageSource: MessageSourceHelper,
//    private val authenticationConfigurationProperties: AuthenticationConfigurationProperties
) {

    @GetMapping("/")
    fun list(): List<User> {
        return this.userRepository.findAll()
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long) : User {
        return this.userRepository.get(id)
    }

    @PostMapping("/")
    fun add(@RequestBody user: User) {
        this.userRepository.add(user)
    }

    @PutMapping("/")
    fun put(@RequestBody user: User) {
        this.userRepository.put(user)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        this.userRepository.delete(id)
    }

//    @GetMapping(path = ["/init"])
//    fun initPassword(): String {
//        return "redirect:/user/list"
//    }


}
