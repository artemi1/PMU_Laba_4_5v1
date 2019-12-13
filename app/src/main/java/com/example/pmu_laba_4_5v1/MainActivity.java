package com.example.pmu_laba_4_5v1;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends ListActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        String[] examples = new String[] { "Rope", "Cube", "Fixed Cube", "Star"};
        setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, examples));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        Intent intent;
        switch (position)
        {
            default:
            case 0:
                intent = new Intent(this, RopeActivity.class);
                break;

            case 1:
                intent = new Intent(this, CubeActivity.class);
                break;

            case 2:
                intent = new Intent(this, FixedCubeActivity.class);
                break;

            case 3:
                intent = new Intent(this, StarActivity.class);
                break;

        }
        startActivity(intent);
    }
}
