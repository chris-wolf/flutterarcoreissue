package com.example.arcorefluttertest

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.content_launcher.*

class LauncherActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_launcher)

		arButton.setOnClickListener { view ->
			startActivity(Intent(this, HelloSceneformActivity::class.java))
		}
		flutterButton.setOnClickListener { view ->
			startActivity(Intent(this, MainActivity::class.java))
		}

	}

}
