package jp.wozniak.training.kotlinspring.repository

import java.lang.RuntimeException

class UpdatingCollidedException : RuntimeException {

    constructor(message: String) : super(message){}

    constructor(message: String, cause: Throwable) : super(message, cause){}
}
