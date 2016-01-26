package com.kyview.demo;

import com.kyview.demo.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_activity);

		// 跳转横幅广告
		findViewById(R.id.banner).setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, AdBannerActivity.class);
				startActivity(intent);
			}
		});
		// 跳转全/插屏广告
		findViewById(R.id.instl).setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, AdInstlActivity.class);
				startActivity(intent);
			}
		});
		// 版本更新
		findViewById(R.id.release).setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
				View view = inflater.inflate(R.layout.version_layout, null);
				final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).create();
				dialog.setView(view);
				dialog.show();
				Button ok = (Button) view.findViewById(R.id.close);
				ok.setOnClickListener(new OnClickListener()
				{

					@Override
					public void onClick(View v)
					{
						// TODO Auto-generated method stub
						if (null != dialog)
							dialog.dismiss();
					}
				});

			}
		});
	}

}