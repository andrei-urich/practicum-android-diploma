package ru.practicum.android.diploma.data.vacancydetails

sealed class ResourceDetails<T>(val data: T? = null, val error: ErrorType = Success()) {
    class Success<T>(data: T) : ResourceDetails<T>(data)
    class Error<T>(error: ErrorType) : ResourceDetails<T>(null, error)
}
