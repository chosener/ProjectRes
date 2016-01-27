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
 * �����ʹ�ÿ���������ϵAdView�ͷ�
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

		/* AdViewTargeting Ϊȫ�ֱ���ֻ��Ҫ����һ�� */
		/* ���ɱ��رգ��粻��Ҫ���޸�ΪSwitcherMode.DEFAULT */
		AdViewTargeting.setBannerSwitcherMode(BannerSwitcher.CANCLOSED);
		/* ��������ֻ���ڲ���,��ɺ�һ��Ҫȥ��,�ο��ĵ�˵�� */
		AdViewTargeting.setUpdateMode(UpdateMode.EVERYTIME); // ÿ�ζ��ӷ�����ȡ����
		// AdViewTargeting.setRunMode(RunMode.TEST); // ��֤����ѡ�еĹ�湫˾��Ϊ����״̬
		/* �����Ƿ����Html5��� ,Ĭ�ϲ����� */
		AdViewTargeting.setHtml5Switcher(this, Html5Switcher.NONSUPPORT);
		// ��ʼ��������� ,��Ҫ���벼���������ؿ���ҳ��
		adSpreadManager = new AdSpreadManager(this, "SDK20131308010754xy32wtwgvo7mg93",
				(RelativeLayout) findViewById(R.id.spreadlayout));
		// ���ÿ����ص��ӿ�
		adSpreadManager.setAdSpreadInterface(this);
		// ���ÿ����·�LOGO��������ø÷���
		adSpreadManager.setLogo(R.drawable.spread_logo);
		// ���ÿ���������ɫ���ɲ�����
		adSpreadManager.setBackgroundColor(Color.WHITE);
		// ���ÿ�������ʱ֪ͨ��ʽ
		adSpreadManager.setSpreadNotifyType(this, AdSpreadManager.NOTIFY_NUM);
		// ���������
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
		// waitingOnRestart ��Ҫ�Լ�����
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
	 * ��������Ŀ������ʱ����ø÷������
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
	 * �û���������µ���
	 */
	@Override
	public void onAdClosedByUser()
	{
		// TODO Auto-generated method stub
		jump();
	}

	/**
	 * �û��������� adSpreadManager.setSpreadNotifyType(this,
	 * AdSpreadManager.NOTIFY_CUSTOM); ���ɻص��÷��������򲻵���
	 * 
	 * @param view
	 *            ���ض����Զ��岼�֣�RelativeLayout��
	 * @param ruleTime
	 *            �涨����չʾʱ�� ������cpm&cpc �ڹ涨ʱ���ڲ��ɹرգ����򲻼�������
	 * @param delayTime
	 *            ����ʱʱ���ڿ������ɴ�����ʱʱ�䵽����Զ����� onAdSpreadPrepareClosed �ӿ�
	 */
	@Override
	public void onAdNotifyCustomCallback(final ViewGroup view, final int ruleTime, final int delayTime)
	{
		// �ֶ����ùرտ�������
		// adSpreadManager.notifySpreadAdStop();
	}
}
