package com.example.arcorefluttertest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.ar.core.AugmentedImage
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.ux.ArFragment

class ARActivity : AppCompatActivity() {
	private lateinit var arFragment: ArFragment

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_ar)
		arFragment = supportFragmentManager.findFragmentById(R.id.ux_fragment) as ArFragment
		arFragment.arSceneView.scene.setOnUpdateListener {
			onUpdateFrame(it)
		}
	}

	/**
	 * Registered with the Sceneform Scene object, this method is called at the start of each frame.
	 *
	 * @param frameTime - time since last frame.
	 */
	private fun onUpdateFrame(frameTime: FrameTime) {
		// Always call the fragment's onUpdate.
		arFragment.onUpdate(frameTime)

		val frame = arFragment.arSceneView?.arFrame

		// If there is no frame or ARCore is not tracking yet, just return.
		if (frame == null || frame.camera.trackingState != TrackingState.TRACKING) {
			return
		}

		val updatedAugmentedImages = frame.getUpdatedTrackables(AugmentedImage::class.java)
		for (augmentedImage in updatedAugmentedImages) {
			Log.d("ARActivity", augmentedImage.name)
		}
	}
}
