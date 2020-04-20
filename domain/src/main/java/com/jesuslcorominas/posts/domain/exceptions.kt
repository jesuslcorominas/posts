package com.jesuslcorominas.posts.domain

open class RemoteException(message:String? = null) : Exception(message)

class InvalidResponseException : RemoteException()

class ServerException(val code: Int?, message: String?) : RemoteException(message)

class ConnectionException : RemoteException()

class DatabaseException : Exception()

