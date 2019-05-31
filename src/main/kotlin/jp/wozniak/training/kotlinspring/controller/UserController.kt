package jp.wozniak.training.kotlinspring.controller

import jp.wozniak.training.kotlinspring.domain.PatchUser
import jp.wozniak.training.kotlinspring.domain.User
import jp.wozniak.training.kotlinspring.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
//    private val passwordEncoder: PasswordEncoder,
//    private val randomStringGenerator: RandomStringGenerator,
//    private val messageSource: MessageSourceHelper,
//    private val authenticationConfigurationProperties: AuthenticationConfigurationProperties
) {

    @GetMapping("/")
    fun list(): List<User> {
        return this.userService.findAll()
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long) : User {
        return this.userService.get(id)
    }

    @PostMapping("/")
    fun post(@RequestBody user: User) {
        this.userService.post(user)
    }

    @PatchMapping("/")
    fun put(@RequestBody user: PatchUser) {
        this.userService.patch(user)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        this.userService.delete(id)
    }

//    @GetMapping(path = ["/init"])
//    fun initPassword(): String {
//        return "redirect:/user/list"
//    }


}
