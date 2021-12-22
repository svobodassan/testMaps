package ru.textmaps.product.testmaps

import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.jupiter.MockitoExtension
import ru.textmaps.product.testmaps.interactors.InteractorRussia
import ru.textmaps.product.testmaps.network.MapsRepository
import ru.textmaps.product.testmaps.presentation.MapsActivityViewModel

@ExtendWith(
    MockitoExtension::class
)
@RunWith(MockitoJUnitRunner::class)
class MapsActivityViewModelTest {

    lateinit var interactorRussia: InteractorRussia

    lateinit var mapsRepository: MapsRepository

    lateinit var mapsActivityViewModel: MapsActivityViewModel

    @Before
    fun setUp() {

        mapsRepository = MapsRepository()
        interactorRussia = InteractorRussia(mapsRepository)

        mapsActivityViewModel = MapsActivityViewModel(
            interactorRussia
        )
    }

    private fun getListPoints(): ArrayList<LatLng> {
        val listPoint: ArrayList<LatLng> = arrayListOf()
        listPoint.add(LatLng(48.402952383693886, 35.03518486112884))
        listPoint.add(LatLng(48.40217675075604, 35.03589798574773))
        return listPoint
    }

    private fun getListOfListOfDoubles(): List<List<Double>> {
        val listOne = listOf(35.03518486112884, 48.402952383693886)
        val listTwo = listOf(35.03589798574773, 48.40217675075604)
        val listOflistOfDoubles = listOf(listOne, listTwo)
        return listOflistOfDoubles
    }

    @Test
    fun computeMetersPositiveNegative() {
        assertEquals(mapsActivityViewModel.computeMeters(getListPoints()), 101.04386267109865)
        assertNotEquals(mapsActivityViewModel.computeMeters(getListPoints()), 0)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun combinePointsAndEmitDistansePositiveNegative() = runBlockingTest {

        val distanseValue = mutableListOf<Long>()

        val distanseJob = launch {
            mapsActivityViewModel.distanse.toList(distanseValue)
        }

        mapsActivityViewModel.combinePointsAndEmit(getListOfListOfDoubles(), getListPoints())

        distanseJob.cancel()

        assertEquals(arrayListOf(303).get(0).toLong(), distanseValue.get(0))
        assertNotEquals(arrayListOf(0).get(0).toLong(), distanseValue.get(0))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun combinePointsAndEmitAreaPositiveNegative() = runBlockingTest {

        val areaValue = mutableListOf<List<LatLng>>()

        val areaJob = launch {
            mapsActivityViewModel.area.toList(areaValue)
        }

        mapsActivityViewModel.combinePointsAndEmit(getListOfListOfDoubles(), arrayListOf())

        areaJob.cancel()

        assertEquals(getListPoints(), areaValue.get(0))
        assertNotEquals(getListPoints(), areaValue)
    }

    @Test
    fun getTagPositiveNegative() {
        assertEquals("MapsActivityViewModel", mapsActivityViewModel.TAG)
        assertNotEquals("", mapsActivityViewModel.TAG)
    }


//    @ExperimentalCoroutinesApi
//    @Test
//    fun requestRussia() = runBlocking {
//
//        whenever(
//            interactorRussia.run(InteractorRussia.Param(""))
//        ).thenAnswer {
//            Error()
//        }
//
//        mapsActivityViewModel.requestRussia()
//
//        val areaValue = mutableListOf<List<LatLng>>()
//        val areaJob = launch {
//            mapsActivityViewModel.area.toList(areaValue)
//        }
//        areaJob.cancel()
//
//        val distanseValue = mutableListOf<Long>()
//        val distanseJob = launch {
//            mapsActivityViewModel.distanse.toList(distanseValue)
//        }
//        distanseJob.cancel()
//
//
//        assertEquals(getListPoints(), 321)
//
//
//    }


}