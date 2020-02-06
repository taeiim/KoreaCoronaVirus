package com.god.taeiim.koreacoronavirus.ui.routemap

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.god.taeiim.koreacoronavirus.BR
import com.god.taeiim.koreacoronavirus.R
import com.god.taeiim.koreacoronavirus.api.model.Confirmations
import com.god.taeiim.koreacoronavirus.api.model.MarkersInMap
import com.god.taeiim.koreacoronavirus.data.FirebaseRepositoryImpl
import com.god.taeiim.koreacoronavirus.data.remote.FirebaseRemoteDataSourceImpl
import com.god.taeiim.koreacoronavirus.databinding.FragmentRouteMapBinding
import com.god.taeiim.koreacoronavirus.databinding.ItemConfirmationBinding
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

    private lateinit var markerView: View
    private lateinit var markerIndexTv: TextView

    lateinit var confirmationAdapter: ConfirmationRecyclerAdapter<Confirmations.ConirmationInfo, ItemConfirmationBinding>

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

        confirmationAdapter = ConfirmationRecyclerAdapter(R.layout.item_confirmation, BR.item, vm)
        binding.confirmationRecyclerView.adapter = confirmationAdapter

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        vm.setObserves()
        setCustomMarkerView()

    }

    private fun RouteMapViewModel.setObserves() {
        errorFailGetConfirmationsData.observe(viewLifecycleOwner, Observer { failToGetData() })
        confirmations.observe(viewLifecycleOwner, Observer {
            drawAllPolyLine()
        })
        currentSelectIndex.observe(viewLifecycleOwner, Observer {
            if (it == -1) setVisibleAllPolyLine() else setVisibleOnePolyLine(it)
        })
        confirmationsAddAllOption.observe(viewLifecycleOwner, Observer {
            updateSearchKeywords(it)
            it.confirmations.find { it.id == -1 }?.isSelected?.set(true)
        })
    }

    private fun failToGetData() {
        Toast.makeText(context, getString(R.string.fail_get_firebase), Toast.LENGTH_SHORT).show()
    }

    private fun drawAllPolyLine() {
        var colors = arrayOf(
            R.color.line1,
            R.color.line2,
            R.color.line3,
            R.color.line4,
            R.color.line5,
            R.color.line6,
            R.color.line7,
            R.color.line8,
            R.color.line9,
            R.color.line10,
            R.color.line11,
            R.color.line12,
            R.color.line13,
            R.color.line14,
            R.color.line15,
            R.color.line16,
            R.color.line17,
            R.color.line18,
            R.color.line19,
            R.color.line20
        ).map { ContextCompat.getColor(context ?: return, it) }

        for ((index, markers) in vm.confirmations.value?.confirmations!!.map { it.marker }.withIndex()) {
            val polylineOptions = PolylineOptions()
            for (marker in markers ?: return) {
                val latLng = LatLng(marker.latitude!!.toDouble(), marker.longitude!!.toDouble())
                polylineOptions.add(latLng)
                polylineOptions.color(colors.get(index % colors.size))

                markerIndexTv.text = (index + 1).toString()
                (markerIndexTv.background as GradientDrawable).run {
                    //                    setStroke(2, colors.get(index % colors.size))
                    setColor(ColorUtils.setAlphaComponent(colors.get(index % colors.size), 100))
                }

                val markerOptions =
                    MarkerOptions().position(latLng).title("${marker.date} ${marker.name}")
                        .icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(markerView)))
                val marker = map.addMarker(markerOptions)
                markersInMap.add(MarkersInMap(index, marker))
            }
            val polyLine = map.addPolyline(polylineOptions)
            polyLine.isClickable = true
            polyLine.tag = "${index + 1}번째 확진자"
            polyLine.width = 7f
            paths.add(polyLine)
        }

        binding.confirmationRecyclerView.visibility = View.VISIBLE

    }

    private fun setCustomMarkerView() {
        markerView = activity?.layoutInflater?.inflate(R.layout.marker_num, null) ?: return
        markerIndexTv = markerView.findViewById<TextView>(R.id.markerTv)
    }

    private fun createDrawableFromView(view: View): Bitmap {
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.buildDrawingCache()
        val bitmap = Bitmap.createBitmap(
            view.getMeasuredWidth(),
            view.getMeasuredHeight(),
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        view.draw(canvas)

        return bitmap
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
                LatLng(37.216689, 127.254584),
                map.minZoomLevel
            )
        )
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        if (googleMap != null) {
            map = googleMap
            initGoogleMaps()
            vm.getConfirmationsData()
        }
    }

    private fun initGoogleMaps() {
        map.setMinZoomPreference(8.0f)
        moveMapBasicLatLngZoom()
        binding.zoomResetBtn.setOnClickListener { moveMapBasicLatLngZoom() }
    }

    private fun updateSearchKeywords(confirmations: Confirmations) {
        confirmationAdapter.updateItems(confirmations.confirmations)
    }

    override fun onStart() {
        super.onStart()
        vm.getConfirmationsData()
    }

}