package jp.wozniak.training.kotlinspring.repository

import jp.wozniak.training.kotlinspring.domain.NewUser
import jp.wozniak.training.kotlinspring.domain.PatchUser
import jp.wozniak.training.kotlinspring.domain.User
import jp.wozniak.training.kotlinspring.mapper.UserMapper
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional


@Repository
class UserRepository(val userMapper : UserMapper){

    fun findAll() :List<User> {
        return this.userMapper.findAll()
    }

    fun get(id: Long) :User {
        val user = this.userMapper.get(id)
        return user ?: throw ResourceNotFoundException("no such a user.")
        //エルビス演算子しゅげえええええ
    }

    fun post(user: NewUser)  {
         this.userMapper.post(user)
    }

    fun patch(user: PatchUser)  {
        val trying = user.lockVersion
        val actual = this.get(user.id).lockVersion
        if(trying < actual){
            throw UpdatingCollidedException("updating attempt is collided.")
        }
        user.lockVersion++
        this.userMapper.patch(user)
    }

    fun delete(id: Long) {
        val user = this.get(id)
        /* TODO
            本当はこんなことしなくていい気がする。
            DELETE文の結果をintで受け取ったら
            成功か失敗か判定できるのでは。
         */
        return this.userMapper.delete(id)
    }

}