package com.example.pmu_laba_4_5v1.utils;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;


public class VerletView extends View
{
	protected VerletIntegrationSystem system;


	public VerletView(Context context, VerletIntegrationSystem system)
	{
		super(context);
		this.system = system;

		ViewRefreshHandler viewRefreshHandler = new ViewRefreshHandler();
		viewRefreshHandler.executePeriodically(new UpdateViewTask(this, null), 0);
	}

	public void setGravity(float x, float y)
	{
		system.setGravity(x, y);
	}

	public void setFixedPoint(float pX, float pY)
	{
		system.setFixedPoint(pX, pY);
	}

	public class UpdateViewTask extends ViewRefreshHandler.ViewRunnable<VerletView>
	{
		public UpdateViewTask(VerletView view, @Nullable Bundle args)
		{
			super(view, args);
		}

		@Override
		protected void run(VerletView view, Bundle args)
		{
			system.Update();
			view.invalidate();
		}
	}
}
