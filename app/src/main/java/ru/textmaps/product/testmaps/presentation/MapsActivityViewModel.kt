package ru.textmaps.product.testmaps.presentation

import air.ru.obi.mobile.core.network.Error
import air.ru.obi.mobile.core.network.NotFoundError
import air.ru.obi.mobile.core.network.Success
import air.ru.obi.mobile.core.network.TokenError
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import ru.textmaps.product.testmaps.interactors.InteractorRussia

class MapsActivityViewModel(val interactorRussia: InteractorRussia) : ViewModel() {

    val TAG = "MapsActivityViewModel"

    private val _area = MutableSharedFlow<List<LatLng>>()
    val area: SharedFlow<List<LatLng>> = _area

    private val _distanse = MutableSharedFlow<Long>()
    val distanse: SharedFlow<Long> = _distanse


    var listRussiaBig1: List<List<Double?>?>? = null
    var listRussiaBig2: List<List<Double?>?>? = null

    fun requestRussia() {
        Log.i(TAG, "requestRussia")

        viewModelScope.launch {
            val result = interactorRussia.run(InteractorRussia.Param("id"))
            when (result) {
                is Success -> {

                    val listLevelZero = result.value.features?.get(0)?.geometry?.coordinates

                    listLevelZero?.map {

                        var list = it?.get(0)
                        val listPoint = arrayListOf<LatLng>()

                        //тут решение не очень конечно, но без каких либо явных признаков списков координат сложновато, приходится костылить
                        //было бы круто каждому списку координат дать поле name например, и по нему оринетироватся, например name: Sahalin
                        if (list?.size == 22923 || list?.size == 1313) {
                            if (list.size == 22923) listRussiaBig1 = list
                            if (list.size == 1313) listRussiaBig2 = list
                            if (listRussiaBig1 != null && listRussiaBig2 != null) {
                                list = listRussiaBig1!! + listRussiaBig2!!
                                combinePointsAndEmit(list, listPoint)
                            }
                        } else {
                            combinePointsAndEmit(list, listPoint)
                        }
                    }
                }
                is Error -> {
                    when (result.error) {
                        is TokenError -> Log.i(TAG, "TokenError")
                        is UnknownError -> Log.i(TAG, "UnknownError")
                        is NotFoundError -> Log.i(TAG, "NotFoundError")
                        else -> Log.i(TAG, "ElseError")
                    }
                }
            }
        }
    }

    private suspend fun combinePointsAndEmit(list: List<List<Double?>?>?, listPoint: ArrayList<LatLng>) {
        list?.map {
            listPoint.add(LatLng(it?.get(1) ?: 0.0, it?.get(0) ?: 0.0))
        }
        _area.emit(listPoint)

        val distanse = computeMeters(listPoint).toLong()
        _distanse.emit(distanse)
    }

    private fun computeMeters(listPoint: ArrayList<LatLng>): Double {

        var distance = 0.0

        listPoint.forEachIndexed { number, _ ->
            if (number < listPoint.size - 1) {
                distance += SphericalUtil.computeDistanceBetween(
                    listPoint.get(number),
                    listPoint.get(number + 1)
                )
            }
        }

        return distance
    }
}


