package com.example.arcorefluttertest

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import com.google.ar.core.ArCoreApk

import io.flutter.app.FlutterActivity
import io.flutter.plugins.GeneratedPluginRegistrant
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.view.FlutterView

class MainActivity(): FlutterActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    GeneratedPluginRegistrant.registerWith(this)
    MethodChannel(flutterView, "launchar").setMethodCallHandler { methodCall, result ->
      val minOpenGLVersion = 3.1
      val openGlVersionString = (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
              .deviceConfigurationInfo
              .glEsVersion
      val openGlVersion = java.lang.Double.parseDouble(openGlVersionString)
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N || openGlVersion < minOpenGLVersion
              || !ArCoreApk.getInstance().checkAvailability(this).isSupported) {
        result.success(false)
      } else {
        result.success(true)
        val intent = Intent(this, ARActivity::class.java)
        startActivity(intent)
      }
    }
  }
}
