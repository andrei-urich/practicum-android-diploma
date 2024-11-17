package ru.practicum.android.diploma.data.vacancydetails

abstract class ErrorType
class Success : ErrorType()
class ServerInternalError : ErrorType()
class NoInternetError : ErrorType()
class BadRequestError : ErrorType()
