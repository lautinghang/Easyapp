package com.androidapptutorial.andy.easyapp.tools;

import android.app.ProgressDialog;
import android.content.Context;

import com.androidapptutorial.andy.easyapp.R;

public class LoadingView {

	public static ProgressDialog progDailog;

	public static void showLoading(Context context)
	{
		if (progDailog == null)
			progDailog = ProgressDialog.show(context, null, context.getString(R.string.loading));
	}
	
	public static void hideLoading()
	{
		if (progDailog!= null)
		{
			progDailog.dismiss();
			progDailog = null;
		}
	}

    
}
