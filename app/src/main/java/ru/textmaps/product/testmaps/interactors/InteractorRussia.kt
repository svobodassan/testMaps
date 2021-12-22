package ru.textmaps.product.testmaps.interactors

import android.util.Log
import ru.textmaps.product.testmaps.models.ResponseRussia
import ru.textmaps.product.testmaps.network.MapsRepository
import javax.inject.Inject

class InteractorRussia @Inject constructor(private val mapsRepository: MapsRepository) :
    Interactor<ResponseRussia, InteractorRussia.Param> {

    val TAG = "InteractorRussia"

    data class Param(val param: String)

    override suspend fun run(param: Param): InteractorResult<ResponseRussia> {

        return try {
            Success(mapsRepository.getRussia())
        } catch (exc: Exception) {
            Log.i(TAG, "exc.message: " + exc.message)
            var code = 0
            try {
                code = exc.message.toString().replace(Regex("[^0123456789]"), "").toInt()
            } catch (e: Exception) {
                Log.e(TAG, "e: " + e.message)
            }

            when (code) {
                in 400..404 -> Error(TokenError)
                in 500..600 -> Error(ServerError)
                else -> Error(UnknownError)
            }
        }
    }
}