package com.kyview.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.amazon.device.ads.AdError;
import com.amazon.device.ads.AdLayout;
import com.amazon.device.ads.AdLayout.AdSize;
import com.amazon.device.ads.AdListener;
import com.amazon.device.ads.AdProperties;
import com.amazon.device.ads.AdRegistration;
import com.amazon.device.ads.AdTargetingOptions;
import com.kyview.AdViewStream;
import com.kyview.AdViewTargeting;
import com.kyview.AdViewTargeting.UpdateMode;
import com.kyview.interfaces.AdViewInterface;

public class AdBannerActivity extends Activity implements AdViewInterface
{
	/** Called when the activity is first created. */
	private AdViewStream	adStream;
	private LinearLayout	layout, layoutXml, layoutCustom;
	private AdLayout		adView	= null;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_adbanner);
		AdViewTargeting.setUpdateMode(UpdateMode.EVERYTIME); // 每次都从服务器取配置
		AdViewTargeting.setAdSize(AdViewTargeting.AdSize.BANNER_SMART);
		layoutXml = (LinearLayout) findViewById(R.id.adLayoutXml);
		if (layoutXml == null)
			return;
		layoutXml.setVisibility(View.GONE);
		// Code布局Button处理
		findViewById(R.id.btnCode).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				CodeLayout();
			}
		});

		// 纯Code布局Button处理
		findViewById(R.id.btnCode2).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				btnCode2();
			}
		});

		// XML布局Button
		findViewById(R.id.btnXml).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				btnXml();
			}
		});

		// 自定义广告平台Button
		findViewById(R.id.btnCustom).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				btnCustom();
			}
		});

	}

	public void CodeLayout()
	{
		layout = (LinearLayout) findViewById(R.id.adLayout);
		if (layout == null)
			return;
		if (adStream != null)
			adStream.setClosed(true);
		layout.removeAllViews();
		adStream = new AdViewStream(this, "SDK20111812070129bb9oj4n571faaka");
		adStream.setAdViewInterface(this);
		layout.addView(adStream);
		layout.invalidate();
	}

	public void btnCode2()
	{
		if (adStream != null)
			adStream.setClosed(true);
		if (null != adStream)
		{
			ViewGroup rootView = (ViewGroup) findViewById(android.R.id.content);
			for (int i = 0; i < rootView.getChildCount(); i++)
			{
				if (rootView.getChildAt(i) == adStream)
				{
					rootView.removeView(adStream);
				}
			}
		}
		if (layout != null)
			layout.removeAllViews();
		adStream = new AdViewStream(this, "SDK20111812070129bb9oj4n571faaka");
		adStream.setAdViewInterface(this);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.BOTTOM;
		this.addContentView(adStream, params);
	}

	public void btnXml()
	{
		if (adStream != null)
			adStream.setClosed(true);
		adStream = (AdViewStream) findViewById(R.id.adview_ayout);
		adStream.setAdViewInterface(this);
		layoutXml.setVisibility(View.VISIBLE);
	}

	public void btnCustom()
	{
		layoutCustom = (LinearLayout) findViewById(R.id.adLayoutCustom);
		if (layoutCustom == null)
			return;
		if (adStream != null)
			adStream.setClosed(true);
		if (null != adStream)
		{
			ViewGroup rootView = (ViewGroup) findViewById(android.R.id.content);
			for (int i = 0; i < rootView.getChildCount(); i++)
			{
				if (rootView.getChildAt(i) == adStream)
				{
					rootView.removeView(adStream);
				}
			}
		}
		layoutCustom.removeAllViews();
		adStream = new AdViewStream(this, "SDK2015141602014343br3teyj7otuyk");
		adStream.setAdViewInterface(this);
		layoutCustom.addView(adStream);
		layoutCustom.invalidate();
	}

	@Override
	public void onClickAd()
	{
		// TODO Auto-generated method stub
		Log.i("AdBannerActivity", "onClickAd");
	}

	@Override
	public void onClosedAd()
	{
		// TODO Auto-generated method stub
		// 如果想立即关闭直接调用：
		// adStream.setClosed(true);

		// 弹出对话框，要求二次确认
		Dialog dialog = new AlertDialog.Builder(this).setTitle("确定要关闭广告？")
				.setNegativeButton("取消", new DialogInterface.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// 无论是否关闭广告，请务必调用下一行方法，否则广告将停止切换
						// 传入false，广告将不会关闭
						adStream.setClosed(false);
					}
				}).setPositiveButton("确定", new DialogInterface.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// 无论是否关闭广告，请务必调用下一行方法，否则广告将停止切换
						// 传入true，广告将关闭
						adStream.setClosed(true);
					}
				}).show();
		// 防止误点击关闭对话框，可能使 adStream.setClosed(boolean);不被调用
		dialog.setCanceledOnTouchOutside(false);
	}

	@Override
	public void onDisplayAd()
	{
		// TODO Auto-generated method stub
		Log.i("AdBannerActivity", "onDisplayAd");
	}

	public void amazon_proc()
	{
		Log.d("AdViewSample", "Into azmazon");
		try
		{
			// 测试模式
			AdRegistration.enableLogging(this, true);
			AdRegistration.enableTesting(this, true);
			AdRegistration.setAppKey(this, "sample-app-v1_pub-2");

			// 创建amazon的adview实例
			adView = new AdLayout(this, AdSize.AD_SIZE_320x50);
			// 指定侦听接口
			adView.setListener(new AdListener()
			{

				@Override
				public void onAdFailedToLoad(AdLayout view, AdError error)
				{
					Log.w("AdViewSample", "Ad failed to load. Code: " + error.getResponseCode() + ", Message: "
							+ error.getResponseMessage());

					// 失败之后开始请求下一个广告
					adStream.rotateThreadedPri(0);
				}

				@Override
				public void onAdLoaded(AdLayout view, AdProperties adProperties)
				{
					Log.d("AdViewSample", adProperties.getAdType().toString() + " Ad loaded successfully.");

					// 取消侦听接口
					view.setListener(null);

					// 广告请求成功之后，启动定时器，到时后请求下一个广告
					adStream.reportImpression();
					adStream.adViewManager.resetRollover();
					adStream.rotateThreadedDelayed();
				}

				@Override
				public void onAdExpanded(AdLayout arg0)
				{
					// TODO Auto-generated method stub
				}

				@Override
				public void onAdCollapsed(AdLayout arg0)
				{
					// TODO Auto-generated method stub
				}
			});
			// 清除当前的view
			adStream.removeAllViews();
			RelativeLayout.LayoutParams layoutParams;
			layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			// 添加amazon的view到当前的adlayout中
			adStream.addView(adView, layoutParams);
			AdTargetingOptions adOptions = new AdTargetingOptions();
			adView.loadAd(adOptions);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}