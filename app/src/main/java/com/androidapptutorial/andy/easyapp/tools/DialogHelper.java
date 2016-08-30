package com.androidapptutorial.andy.easyapp.tools;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;

public class DialogHelper {

	public static Activity activity;
	public static String url;
	public static DialogHelperInterface dialogHelperInterface;
	
	public static void showAlertMessage(Activity a, String msg)
	{
		activity = a;		
		Builder builder = new Builder(a);
		builder.setMessage(msg);
		builder.setCancelable(false);
		builder.setPositiveButton("是", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});		
		builder.create().show();
	}
	
	public static void showDialogPopBrowser(Activity a, String msg, String positiveOption, boolean cancelable,String link)
	{
		activity = a;
		url = link;
		Builder builder = new Builder(a);
		builder.setMessage(msg);
		builder.setCancelable(cancelable);
		builder.setPositiveButton(positiveOption, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				Intent i = new Intent(Intent.ACTION_VIEW, 
					       Uri.parse(url));
				activity.startActivity(i);
					
				dialog.dismiss();
				activity.finish();
			}
		});		
		builder.create().show();
	}
	
	public static void showDialogError(Activity a, String msg, String positiveOption, boolean cancelable)
	{
		activity = a;
		Builder builder = new Builder(a);
		builder.setMessage(msg);
		builder.setCancelable(cancelable);
		builder.setPositiveButton(positiveOption, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				activity.finish();
			}
		});		
		builder.create().show();
	}

	public static void showDialog(Activity a, String msg, String positiveOption, boolean cancelable)
	{
		activity = a;
		Builder builder = new Builder(a);
		builder.setMessage(msg);
		builder.setCancelable(cancelable);
		builder.setPositiveButton(positiveOption, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});		
		builder.create().show();
	}
	
	public static void showDialog(Activity a, String msg, boolean cancelable)
	{
		activity = a;
		Builder builder = new Builder(a);
		builder.setMessage(msg);
		builder.setCancelable(cancelable);
		builder.create().show();
	}
	
	public static void showDialog(Activity a, String title, String msg, String positiveOption, String negativeOption, boolean cancelable, DialogHelperInterface i)
	{
		activity = a;
		dialogHelperInterface = i;
		Builder builder = new Builder(a);
		builder.setMessage(msg);
		builder.setTitle(title);
		builder.setCancelable(cancelable);
		builder.setPositiveButton(positiveOption, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialogHelperInterface.positiveAction();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton(negativeOption, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialogHelperInterface.negativeAction();
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
	
}
