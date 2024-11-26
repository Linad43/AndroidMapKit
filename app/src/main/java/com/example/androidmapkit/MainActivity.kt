package com.example.androidmapkit

import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.androidmapkit.databinding.ActivityMainBinding
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.location.FilteringMode
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.location.LocationListener
import com.yandex.mapkit.location.LocationStatus
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CompositeIcon
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.RotationType
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.image.ImageProvider


class MainActivity : AppCompatActivity(), UserLocationObjectListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userLocationLayer: UserLocationLayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setApiKey(savedInstanceState)
        MapKitFactory.initialize(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.mapview.mapWindow.map.isRotateGesturesEnabled = false
        binding.mapview.mapWindow.map.move(
            CameraPosition(
                Point(0.0, 0.0),
                14F, 0F, 0F
            )
        )
        requestLocationPermission()
        val mapKit = MapKitFactory.getInstance()
        mapKit.resetLocationManagerToDefault()
        userLocationLayer = mapKit
            .createUserLocationLayer(
                binding.mapview.mapWindow
            )
        userLocationLayer.isVisible = true
        userLocationLayer.isHeadingEnabled = true
        userLocationLayer.setObjectListener(this)
        val traffic = mapKit.createTrafficLayer(binding.mapview.mapWindow)
        binding.trafficFAB.setOnClickListener {
            if (traffic.isTrafficVisible) {
                traffic.isTrafficVisible = false
            } else {
                traffic.isTrafficVisible = true
            }
        }

    }

    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                "android.permission.ACCESS_FINE_LOCATION"
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>("android.permission.ACCESS_FINE_LOCATION"),
                1
            )
        }
    }

    private fun setApiKey(savedInstanceState: Bundle?) {
        val haveApiKey = savedInstanceState?.getBoolean("haveApiKey") ?: false
        if (!haveApiKey) MapKitFactory.setApiKey(Untils.MAPKIT_API_KEY)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("haveApiKey", true)
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapview.onStart()
    }

    override fun onStop() {
        binding.mapview.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onObjectAdded(userLocationView: UserLocationView) {
        userLocationLayer.setAnchor(
            PointF(
                (binding.mapview.width * 0.5).toFloat(),
                (binding.mapview.height * 0.5).toFloat()
            ),
            PointF(
                (binding.mapview.width * 0.5).toFloat(),
                (binding.mapview.height * 0.83).toFloat()
            )
        )
        userLocationView.arrow.setIcon(
            ImageProvider.fromResource(
                this, com.yandex.maps.mobile.R.drawable.arrow
            )
        )
        val pinIcon: CompositeIcon = userLocationView.pin.useCompositeIcon()
        pinIcon.setIcon(
            "icon",
            ImageProvider.fromResource(
                this,
                com.yandex.maps.mobile.R.drawable.search_layer_advert_pin_icon_default
            ),
            IconStyle().setAnchor(PointF(0F, 0F))
                .setRotationType(RotationType.ROTATE)
                .setZIndex(0F)
                .setScale(1F)
        )
        pinIcon.setIcon(
            "pin",
            ImageProvider.fromResource(
                this,
                com.yandex.maps.mobile.R.drawable.search_layer_pin_icon_default
            ),
            IconStyle().setAnchor(PointF(0.5F, 0.5F))
                .setRotationType(RotationType.ROTATE)
                .setZIndex(1f)
                .setScale(0.5f)
        )
        userLocationView.accuracyCircle.fillColor = Color.BLUE and -0x66000001
    }

    override fun onObjectRemoved(p0: UserLocationView) {
    }

    override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) {
    }
}