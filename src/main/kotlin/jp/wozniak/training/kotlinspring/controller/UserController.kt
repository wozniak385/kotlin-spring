package jp.wozniak.training.kotlinspring.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
//    private val userDao: UserDao,
//    private val passwordEncoder: PasswordEncoder,
//    private val randomStringGenerator: RandomStringGenerator,
//    private val messageSource: MessageSourceHelper,
//    private val authenticationConfigurationProperties: AuthenticationConfigurationProperties
) {

    @GetMapping(path = ["/list"])
    fun list(): String {
        return "user/list"
    }

    @PostMapping(path = ["/add"])
    fun add(): String {
        return "redirect:/user/list"
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
