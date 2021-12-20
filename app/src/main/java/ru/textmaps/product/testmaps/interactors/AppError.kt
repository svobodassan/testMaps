package air.ru.obi.mobile.core.network

sealed class AppError

object UnknownError : AppError()
object NotFoundError : AppError()
object TokenError : AppError()
object ServerError : AppError()
