package com.example.arcorefluttertest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.ar.core.AugmentedImageDatabase
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.sceneform.ux.ArFragment
import java.io.IOException

class AugmentedImageFragment : ArFragment() {
	private val TAG = "AugmentedImageFragment"
	private val SAMPLE_IMAGE_DATABASE = "sample_database.imgdb"

	override fun onCreateView(
			inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		val view = super.onCreateView(inflater, container, savedInstanceState)

		// Turn off the plane discovery since we're only looking for images
		planeDiscoveryController.hide()
		planeDiscoveryController.setInstructionView(null)
		arSceneView.planeRenderer.isEnabled = false
		return view
	}

	override fun getSessionConfiguration(session: Session): Config {
		val config = super.getSessionConfiguration(session)
		if (!setupAugmentedImageDatabase(config, session)) {
			// todo (KG) - Show error
		}
		return config
	}

	private fun setupAugmentedImageDatabase(config: Config, session: Session): Boolean {
		var augmentedImageDatabase: AugmentedImageDatabase? = null

		val assetManager = if (context != null) context?.assets else null
		if (assetManager == null) {
			Log.e(TAG, "Context is null, cannot intitialize image database.")
			return false
		}

		try {
			assetManager.open(SAMPLE_IMAGE_DATABASE).use { inputStream -> augmentedImageDatabase = AugmentedImageDatabase.deserialize(session, inputStream) }
		} catch (e: IOException) {
			Log.e(TAG, "IO exception loading augmented image database.", e)
			return false
		}

		config.augmentedImageDatabase = augmentedImageDatabase
		return true
	}
}