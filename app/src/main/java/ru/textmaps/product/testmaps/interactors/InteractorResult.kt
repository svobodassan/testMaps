package ru.textmaps.product.testmaps.interactors

sealed class InteractorResult<T>

data class Success<T>(val value: T) : InteractorResult<T>()
data class Error<T>(val error: AppError) : InteractorResult<T>()
