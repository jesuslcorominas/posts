package com.jesuslcorominas.posts.domain

class ServerException(val code: Int, message: String) : Exception(message)