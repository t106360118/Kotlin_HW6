package com.example.kotlin_hw6_la10

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions


class MainActivity : AppCompatActivity(),OnMapReadyCallback{
    private  var REQUEST_PERMISSIONS=1;
    override fun onRequestPermissionsResult(requestCode:Int,permissions:Array<String>,grantResult:IntArray)
    {
        when(requestCode)
        {
            REQUEST_PERMISSIONS -> for (result:Int in grantResult){
            if (result!=PackageManager.PERMISSION_GRANTED)
            {
                finish();
            }
            else
            {
                val map:SupportMapFragment= (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment)
                map.getMapAsync(this)
            }
        }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),REQUEST_PERMISSIONS)
        }
        else
        {
            val map:SupportMapFragment =( supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment)
            map.getMapAsync(this)
        }
    }

    override fun onMapReady(map: GoogleMap) {
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
            return
        map.isMyLocationEnabled=true
        val m1:MarkerOptions= MarkerOptions()
        val m2:MarkerOptions=MarkerOptions()
        m1.position(LatLng(25.033611, 121.565000))
        m1.title("台北101")
        m1.draggable(true)
        map.addMarker(m1)
        m2.position(LatLng(25.047924, 121.517081))
        m2.title("台北車站")
        m2.draggable(true)
        map.addMarker(m2)
        val polylineOpt= PolylineOptions()
        polylineOpt.add(LatLng(25.033611, 121.565000))
        polylineOpt.add(LatLng(25.032728, 121.565137))
        polylineOpt.add(LatLng(25.047924, 121.517081))
        val polyline = map.addPolyline(polylineOpt)
        polyline.width = 10f
        polyline.color=Color.BLUE
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(25.034,121.545),13f))
    }
}
