package jp.wozniak.training.kotlinspring.controller

import jp.wozniak.training.kotlinspring.domain.Channel
import jp.wozniak.training.kotlinspring.domain.NewChannel
import jp.wozniak.training.kotlinspring.domain.PatchChannel
import jp.wozniak.training.kotlinspring.service.ChannelService
import jp.wozniak.training.kotlinspring.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/channels")
class ChannelController(
    private val channelService: ChannelService,
    private val userService: UserService
//    private val authenticationConfigurationProperties: AuthenticationConfigurationProperties
) {

    @GetMapping("/")
    fun list(): List<Channel> {
        return this.channelService.findAll()
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long) : Channel {
        return this.channelService.get(id)
    }

    @PostMapping("/")
    fun post(@RequestBody channel: NewChannel) : NewChannel {
        channel.loginUser = this.userService.get(1) //TODO: get logging-in user
        this.channelService.post(channel)
        return channel
    }

    @PatchMapping("/")
    fun put(@RequestBody channel: PatchChannel) : PatchChannel {
        this.channelService.patch(channel)
        return channel
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        this.channelService.delete(id)
    }



}
