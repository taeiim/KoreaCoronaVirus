package com.god.taeiim.koreacoronavirus.ui.routemap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.god.taeiim.koreacoronavirus.R
import com.god.taeiim.koreacoronavirus.data.FirebaseRepositoryImpl
import com.god.taeiim.koreacoronavirus.data.remote.FirebaseRemoteDataSourceImpl
import com.god.taeiim.koreacoronavirus.databinding.FragmentRouteMapBinding
import com.god.taeiim.myapplication.base.BaseFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng


class RouteMapFragment : BaseFragment<FragmentRouteMapBinding>(R.layout.fragment_route_map),
    OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)

        vm.setObserves()
        vm.getConfirmationsData()

    }

    private fun RouteMapViewModel.setObserves() {
//        errorFailGetData.observe(viewLifecycleOwner, Observer { failToGetData() })
    }

    private fun failToGetData() {
        Toast.makeText(context, getString(R.string.fail_get_firebase), Toast.LENGTH_SHORT).show()
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