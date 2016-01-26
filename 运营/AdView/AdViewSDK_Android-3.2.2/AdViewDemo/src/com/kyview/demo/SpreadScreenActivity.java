package com.kyview.demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kyview.AdViewTargeting;
import com.kyview.AdViewTargeting.BannerSwitcher;
import com.kyview.AdViewTargeting.Html5Switcher;
import com.kyview.AdViewTargeting.RunMode;
import com.kyview.AdViewTargeting.UpdateMode;
import com.kyview.interfaces.AdSpreadInterface;
import com.kyview.screen.spreadscreen.AdSpreadManager;

/**
 * 
 * 如果想使用开屏，请联系AdView客服
 * 
 */
public class SpreadScreenActivity extends Activity implements AdSpreadInterface
{

	private AdSpreadManager adSpreadManager = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.spread_layout);

		/* AdViewTargeting 为全局变量只需要声明一次 */
		/* 广告可被关闭，如不需要可修改为SwitcherMode.DEFAULT */
		AdViewTargeting.setBannerSwitcherMode(BannerSwitcher.CANCLOSED);
		/* 下面两行只用于测试,完成后一定要去掉,参考文挡说明 */
		AdViewTargeting.setUpdateMode(UpdateMode.EVERYTIME); // 每次都从服务器取配置
		// AdViewTargeting.setRunMode(RunMode.TEST); // 保证所有选中的广告公司都为测试状态
		/* 设置是否接受Html5广告 ,默认不接收 */
		AdViewTargeting.setHtml5Switcher(this, Html5Switcher.NONSUPPORT);
		// 初始化开屏广告 ,需要传入布局用来加载开屏页面
		adSpreadManager = new AdSpreadManager(this, "SDK20131308010754xy32wtwgvo7mg93",
				(RelativeLayout) findViewById(R.id.spreadlayout));
		// 设置开屏回调接口
		adSpreadManager.setAdSpreadInterface(this);
		// 设置开屏下方LOGO，必须调用该方法
		adSpreadManager.setLogo(R.drawable.spread_logo);
		// 设置开屏背景颜色，可不设置
		adSpreadManager.setBackgroundColor(Color.WHITE);
		// 设置开屏倒计时通知方式
		adSpreadManager.setSpreadNotifyType(this, AdSpreadManager.NOTIFY_NUM);
		// 请求开屏广告
		adSpreadManager.requestAd();
		AdViewTargeting.setSecConfirm(true);
	}

	@Override
	public void onAdClosedAd()
	{
		// TODO Auto-generated method stub
		// Toast.makeText(this, "onAdClosedAd", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onAdDisplayAd()
	{
		// TODO Auto-generated method stub
		// Toast.makeText(this, "onAdDisplayAd", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onAdReceiveFailed(String arg0)
	{
		// Toast.makeText(this, "onAdReceiveFailed "+arg0,
		// Toast.LENGTH_SHORT).show();
		jump();
	}

	@Override
	public void onAdReceived(View arg0)
	{
		// TODO Auto-generated method stub
		Toast.makeText(this, "onAdRecieved", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onAdSpreadPrepareClosed()
	{
		jump();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK)
			return false;
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onRestart()
	{
		// TODO Auto-generated method stub
		super.onRestart();
		// waitingOnRestart 需要自己控制
		waitingOnRestart = true;
		jumpWhenCanClick();
	}

	public boolean waitingOnRestart = false;

	private void jump()
	{
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent);
		if (null != adSpreadManager)
			adSpreadManager.setAdSpreadInterface(null);
		this.finish();
	}

	/*
	 * 包含点击的开屏广告时会调用该方法广告
	 */
	private void jumpWhenCanClick()
	{
		if (this.hasWindowFocus() || waitingOnRestart)
		{
			this.startActivity(new Intent(this, MainActivity.class));
			adSpreadManager.setAdSpreadInterface(null);
			this.finish();
		}
		else
		{
			waitingOnRestart = true;
		}

	}

	/**
	 * 用户点击跳过事调用
	 */
	@Override
	public void onAdClosedByUser()
	{
		// TODO Auto-generated method stub
		jump();
	}

	/**
	 * 用户必须设置 adSpreadManager.setSpreadNotifyType(this,
	 * AdSpreadManager.NOTIFY_CUSTOM); 方可回调该方法，否则不调用
	 * 
	 * @param view
	 *            返回顶部自定义布局（RelativeLayout）
	 * @param ruleTime
	 *            规定必须展示时间 适用于cpm&cpc 在规定时间内不可关闭，否则不计入数据
	 * @param delayTime
	 *            在延时时间内可以自由处理，延时时间到达后自动调用 onAdSpreadPrepareClosed 接口
	 */
	@Override
	public void onAdNotifyCustomCallback(final ViewGroup view, final int ruleTime, final int delayTime)
	{
		// 手动调用关闭开屏方法
		// adSpreadManager.notifySpreadAdStop();
	}
}
