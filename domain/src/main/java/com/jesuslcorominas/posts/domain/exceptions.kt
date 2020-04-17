package com.jesuslcorominas.posts.domain

class InvalidResponseException : Exception()

class ServerException(val code: Int?, message: String?) : Exception(message)

class ConnectionException : Exception()

class DatabaseException : Exception()

