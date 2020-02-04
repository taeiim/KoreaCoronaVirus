package com.god.taeiim.koreacoronavirus.ui.routemap

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.god.taeiim.koreacoronavirus.R
import com.god.taeiim.koreacoronavirus.api.model.MarkersInMap
import com.god.taeiim.koreacoronavirus.data.FirebaseRepositoryImpl
import com.god.taeiim.koreacoronavirus.data.remote.FirebaseRemoteDataSourceImpl
import com.god.taeiim.koreacoronavirus.databinding.FragmentRouteMapBinding
import com.god.taeiim.myapplication.base.BaseFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*


class RouteMapFragment : BaseFragment<FragmentRouteMapBinding>(R.layout.fragment_route_map),
    OnMapReadyCallback {
    private lateinit var map: GoogleMap

    val paths = ArrayList<Polyline>()
    val markersInMap = ArrayList<MarkersInMap>()

    private val vm: RouteMapViewModel by lazy {
        ViewModelProvider(this@RouteMapFragment, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return RouteMapViewModel(
                    FirebaseRepositoryImpl.getInstance(FirebaseRemoteDataSourceImpl)
                ) as T
            }
        })[RouteMapViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        vm.setObserves()
        vm.getConfirmationsData()

    }

    private fun RouteMapViewModel.setObserves() {
        errorFailGetConfirmationsData.observe(viewLifecycleOwner, Observer { failToGetData() })
        confirmations.observe(viewLifecycleOwner, Observer { drawAllPolyLine() })
    }

    private fun failToGetData() {
        Toast.makeText(context, getString(R.string.fail_get_firebase), Toast.LENGTH_SHORT).show()
    }

    private fun drawAllPolyLine() {
        val colors =
            arrayOf(Color.CYAN, Color.MAGENTA, Color.GREEN, Color.RED, Color.YELLOW, Color.DKGRAY)
        for ((index, markers) in vm.confirmations.value?.confirmations!!.map { it.marker }.withIndex()) {
            val polylineOptions = PolylineOptions()
            for (marker in markers ?: return) {
                val latLng = LatLng(marker.latitude!!.toDouble(), marker.longitude!!.toDouble())
                polylineOptions.add(latLng)
                polylineOptions.color(colors.get(index))

                val markerOptions =
                    MarkerOptions().position(latLng).title("${marker.date} ${marker.name}")
                val marker = map.addMarker(markerOptions)
                markersInMap.add(MarkersInMap(index, marker))
            }
            val polyLine = map.addPolyline(polylineOptions)
            polyLine.isClickable = true
            polyLine.tag = "${index + 1}번째 확진자"
            paths.add(polyLine)
        }

    }

    private fun setVisibleOnePolyLine(showLineIndex: Int) {
        setInvisibleAllPolyLine()
        paths.get(showLineIndex).isVisible = true
        markersInMap.filter { it.index == showLineIndex }.map { it.marker.isVisible = true }
    }

    private fun setVisibleAllPolyLine() {
        paths.map { it.isVisible = true }
        markersInMap.map { it.marker.isVisible = true }
    }

    private fun setInvisibleAllPolyLine() {
        paths.map { it.isVisible = false }
        markersInMap.map { it.marker.isVisible = false }
    }

    private fun moveMapBasicLatLngZoom() {
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(36.585745, 127.887487),
                map.minZoomLevel
            )
        )
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        if (googleMap != null) {
            map = googleMap
            initGoogleMaps()
        }
    }

    private fun initGoogleMaps() {
        map.setMinZoomPreference(8.0f)
        moveMapBasicLatLngZoom()
        binding.zoomResetBtn.setOnClickListener { moveMapBasicLatLngZoom() }
    }
}