package com.kyview.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import com.kyview.AdViewTargeting;
import com.kyview.AdViewTargeting.InstlSwitcher;
import com.kyview.interfaces.AdInstlInterface;
import com.kyview.screen.interstitial.AdInstlManager;

public class AdInstlActivity extends Activity implements OnClickListener, AdInstlInterface
{

	private Button	requestAd, showAd, requestShow;
	AdInstlManager	adInstlManager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_adinstl);
		requestAd = (Button) findViewById(R.id.requestad);
		requestAd.setOnClickListener(this);
		showAd = (Button) findViewById(R.id.showad);
		showAd.setOnClickListener(this);
		requestShow = (Button) findViewById(R.id.requestshow);
		requestShow.setOnClickListener(this);
		// 设置插屏可关闭
		AdViewTargeting.setInstlSwitcherMode(InstlSwitcher.CANCLOSED);
		AdViewTargeting.setInstlDisplayMode(AdViewTargeting.InstlDisplayMode.DIALOG_MODE);
		// 设置大小只支持多盟
		// AdViewTargeting.setAdWidthHeight(300, 250);
		// 设置易传媒是可用，0代表全屏，1代表插屏,默认为0。
		AdViewTargeting.setAdChinaView(0);
		adInstlManager = new AdInstlManager(this, "SDK20141529031208v5avw087171hioq");
		adInstlManager.setAdInstlInterface(this);
	}

	@Override
	public void onClick(View v)
	{

		if (v.getId() == R.id.requestad)
		{
			// 只请求广告，适用于预加载
			adInstlManager.requestAd();
		}
		else if (v.getId() == R.id.showad)
		{
			// 需要请求广告成功后调用该方法，与requestAd()配合使用
			adInstlManager.showInstal(this);
		}
		else if (v.getId() == R.id.requestshow)
		{
			// 请求与展示广告,单独使用
			adInstlManager.requestAndshow();
		}
	}

	@Override
	public void onAdDismiss()
	{
	}

	@Override
	public void onClickAd()
	{

	}

	@Override
	public void onDisplayAd()
	{

	}

	@Override
	public void onReceivedAd(int arg0, View arg1)
	{
		// TODO Auto-generated method stub
		runOnUiThread(new Runnable()
		{

			@Override
			public void run()
			{
				// TODO Auto-generated method stub
				Toast.makeText(AdInstlActivity.this, "ReceivedAd", Toast.LENGTH_SHORT).show();
			}
		});

	}

	@Override
	public void onReceivedAdFailed(String error)
	{
		// TODO Auto-generated method stub
		runOnUiThread(new Runnable()
		{

			@Override
			public void run()
			{
				// TODO Auto-generated method stub
				Toast.makeText(AdInstlActivity.this, "onReceiveAdFailed", Toast.LENGTH_SHORT).show();
			}
		});
	}

}
