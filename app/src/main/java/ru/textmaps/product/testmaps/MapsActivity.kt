package ru.textmaps.product.testmaps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.coroutineScope

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.coroutines.flow.collect
import ru.textmaps.product.testmaps.databinding.ActivityMapsBinding
import ru.textmaps.product.testmaps.presentation.MapsActivityViewModel

import javax.inject.Inject


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnPolygonClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    companion object val TAG = "MapsActivity"

    @Inject
    lateinit var vmFactory: VMFactory
    private val mapsActivityViewModel: MapsActivityViewModel by viewModels {vmFactory}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MyApp.mapsDaggerComponent.inject(this)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mapsActivityViewModel.requestRussia()
        mMap.setOnPolygonClickListener(this)

        mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(63.043662773260905, 92.67342385703294)))

    }

    var counterAreas = 0
    val listPoligons = arrayListOf<Polygon>()
    var distanse = 0L

    override fun onResume() {
        super.onResume()

        listPoligons.clear()
        distanse = 0L

        lifecycle.coroutineScope.launchWhenCreated {
            mapsActivityViewModel.area.collect {
                val polygon = mMap.addPolygon(
                    PolygonOptions()
                        .strokeColor(R.color.design_default_color_primary)
                        .strokeWidth(1.5F)
                        .clickable(true)
                        .addAll(it)
                )

                polygon.tag = "area " + counterAreas
                polygon.fillColor = getColor(R.color.design_default_color_surface)

                counterAreas += 1
                listPoligons.add(polygon)
            }
        }

        lifecycle.coroutineScope.launchWhenCreated {
            mapsActivityViewModel.distanse.collect {
                distanse += it
                Toast.makeText(baseContext, "длина границ: " + distanse/1000 + " км", Toast.LENGTH_LONG).show()
            }
        }
    }

    var lastTag = ""

    override fun onPolygonClick(p0: Polygon) {
        Log.i(TAG, "tag: " + p0.tag)
        p0.fillColor = getColor(R.color.design_default_color_error)

        listPoligons.map {
            if (it.tag == lastTag)
                it.fillColor = getColor(R.color.design_default_color_surface)
        }

        lastTag = p0.tag.toString()
    }
}