package air.ru.obi.mobile.core.network

sealed class InteractorResult<T>

data class Success<T>(val value: T) : InteractorResult<T>()
data class Error<T>(val error: AppError) : InteractorResult<T>()
