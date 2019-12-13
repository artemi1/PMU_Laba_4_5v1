package com.example.pmu_laba_4_5v1;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.widget.Toast;

import com.example.pmu_laba_4_5v1.implementation.bodiesImplementation.StarBody;
import com.example.pmu_laba_4_5v1.implementation.verletImplementation.StarVerletMove;
import com.example.pmu_laba_4_5v1.utils.Bodies;
import com.example.pmu_laba_4_5v1.utils.OpenGLRenderer;


public class StarActivity extends Activity
{
	private GLSurfaceView glSurfaceView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (!supportES2())
		{
			Toast.makeText(this, "OpenGL ES 2.0 is not supported", Toast.LENGTH_LONG).show();
			finish();
			return;
		}

		Bodies bodies = new Bodies();
		bodies.add(new StarBody());

		glSurfaceView = new GLSurfaceView(this);
		glSurfaceView.setEGLContextClientVersion(2);
		glSurfaceView.setRenderer(new OpenGLRenderer(new StarVerletMove(bodies)));
		setContentView(glSurfaceView);
	}


	@Override
	protected void onPause()
	{
		super.onPause();
		glSurfaceView.onPause();
	}


	@Override
	protected void onResume()
	{
		super.onResume();
		glSurfaceView.onResume();
	}


	private boolean supportES2()
	{
		ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
		return (configurationInfo.reqGlEsVersion >= 0x20000);
	}
}