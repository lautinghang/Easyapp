package com.androidapptutorial.andy.easyapp;

import android.app.Application;
import android.graphics.Bitmap.CompressFormat;

import com.androidapptutorial.andy.easyapp.image.ImageCacheManager;
import com.androidapptutorial.andy.easyapp.volley.RequestManager;

/**
 * This {@code IntentService} does the actual handling of the GCM message.
 * {@code GcmBroadcastReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class MyApplication extends Application {

	private static int DISK_IMAGECACHE_SIZE = 1024*1024*10;
	private static CompressFormat DISK_IMAGECACHE_COMPRESS_FORMAT = CompressFormat.PNG;
	private static int DISK_IMAGECACHE_QUALITY = 100;  //PNG is lossless so quality is ignored but must be provided

	/**
	 * 为了完全退出程序调用方法 MyApplication.getInstance().addActivity(this);
	 * MyApplication.getInstance().exit();
	 */
	private static MyApplication instance;

	public void onCreate()
	{
		super.onCreate();

		init();
	}
	/**
	 * Intialize the request manager and the image cache 
	 */
	private void init() {		
		RequestManager.init(this);
		createImageCache();
	}
    
	public MyApplication() {
		
	}
	// 单例模式获取唯一的MyApplication实例
	public static MyApplication getInstance() {
		if (null == instance) {
			instance = new MyApplication();
		}
		return instance;
	}
	/**
	 * Create the image cache. Uses Memory Cache by default. Change to Disk for a Disk based LRU implementation.  
	 */
	private void createImageCache(){
		ImageCacheManager.getInstance().init(this,
				this.getPackageCodePath()
				, DISK_IMAGECACHE_SIZE
				, DISK_IMAGECACHE_COMPRESS_FORMAT
				, DISK_IMAGECACHE_QUALITY
				, ImageCacheManager.CacheType.MEMORY);
	}
}
