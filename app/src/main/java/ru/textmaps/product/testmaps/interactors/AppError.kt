package ru.textmaps.product.testmaps.interactors

sealed class AppError

object UnknownError : AppError()
object NotFoundError : AppError()
object TokenError : AppError()
object ServerError : AppError()
