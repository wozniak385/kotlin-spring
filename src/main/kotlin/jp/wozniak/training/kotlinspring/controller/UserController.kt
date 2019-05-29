package jp.wozniak.training.kotlinspring.controller

import jp.wozniak.training.kotlinspring.domain.User
import jp.wozniak.training.kotlinspring.repository.UserRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userRepository: UserRepository
//    private val passwordEncoder: PasswordEncoder,
//    private val randomStringGenerator: RandomStringGenerator,
//    private val messageSource: MessageSourceHelper,
//    private val authenticationConfigurationProperties: AuthenticationConfigurationProperties
) {

    @GetMapping("/list")
    fun list(): String {
        return "user/list"
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long) : User {
        return this.userRepository.get(id)
    }

    @PostMapping("/")
    fun add() {
        val newUser: User = User()
        newUser.email = "hoge@mail.com"
        newUser.hashedPassword = "qwertyuiop"
        newUser.firstName =  "Taro"
        newUser.lastName = "Yamada"
        this.userRepository.add(newUser)
    }

//    @PostMapping(path = ["/edit"])
//    fun edit(): String {
//        return "redirect:/user/list"
//    }
//
//    @GetMapping(path = ["/delete"])
//    fun delete(): String {
//        return "redirect:/user/list"
//    }
//
//    @GetMapping(path = ["/init"])
//    fun initPassword(): String {
//        return "redirect:/user/list"
//    }


}
