package com.jms.admobextension;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class AdmobBanner implements FREFunction {
	private AdView adView;
	private final String AD_UNIT_ID = "a14f840b733d0dc";
	
	@Override
	public FREObject call(FREContext context, FREObject[] args) {
		// TODO Auto-generated method stub
		Log.d("Tag","Version 6");
		
		
	    Activity activity = context.getActivity();
	    Context appContext = activity.getApplicationContext();
	    FrameLayout firstLayout = (FrameLayout) activity.getWindow().getDecorView().findViewById(android.R.id.content);
	    //String firstLayoutName = firstLayout.getClass().getSimpleName();
	    Log.d("Tag","Get 1st layout :" + firstLayout.getClass().getSimpleName() + " with " + firstLayout.getChildCount() + " child");
	    
	    //In Flex Air based android app, the last layout is in layer 4
	    FrameLayout secondLayerLayout = (FrameLayout) firstLayout.getChildAt(0);
	    Log.d("Tag","Get 2nd layout : " + secondLayerLayout.getClass().getSimpleName() + " with " + secondLayerLayout.getChildCount() + " child");
	    
	    
	    
	    View child1 = secondLayerLayout.getChildAt(0); //android SurfaceView, width 8, height 16
	    Log.d("Tag","Get child 1: " + child1.getClass().getSimpleName() + " with " + child1.getClass().getSuperclass().getName() + " with: " + child1.getWidth() + ":" + child1.getHeight());
	    Class<?>[] relatedClasses = child1.getClass().getClasses();
	    for (Class<?> potentialChild : relatedClasses) {
	    	Log.d("Tag","Child 1 class: " + potentialChild.getSimpleName());
	    }	    
	    
	    View child2 = secondLayerLayout.getChildAt(1); //android SurfaceView, width 600, height 951
	    Log.d("Tag","Get child 2: " + child2.getClass().getSimpleName() + " with " + child2.getClass().getSuperclass().getName() + " with: " + child2.getWidth() + ":" + child2.getHeight());
	    relatedClasses = child2.getClass().getClasses();
	    for (Class<?> potentialChild : relatedClasses) {
	    	Log.d("Tag","Child 2 class: " + potentialChild.getSimpleName());
	    }
	    
	    Log.d("Tag","Start Create AdView");
		adView = new AdView(appContext);
		Log.d("Tag","End Create AdView");
		adView.setAdSize(AdSize.BANNER);
		adView.setAdUnitId(AD_UNIT_ID);
		Log.d("Tag","Put in parent");
		firstLayout.addView(adView);
		Log.d("Tag","Successfully put in parent");
		
		AdRequest adRequest = new AdRequest.Builder().build();
		Log.d("Tag","Successfully create adrequest");
		
		adView.loadAd(adRequest);
		Log.d("Tag","Successfully Load Ad");
		
		Log.d("Tag","Get 1nd layout ver 3.1: " + firstLayout.getClass().getSimpleName() + " with " + firstLayout.getChildCount() + " child.");
		
	    return null;
	}

}
