package com.stego.lytbf.stegoim;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.getExternalStorageDirectory;

public class SendActivity extends AppCompatActivity
{
	static final boolean LOGGING = false;
	static final String LOGTAG = "SendActivity";
	
	private static final int READ_EXTERNAL_STORAGE_PERMISSION_REQUEST = 1;
	private static final int CAPTURE_IMAGE_REQUEST = 2;
	private static final int SHARE_REQUEST = 3;
	private static final int SELECT_FROM_ALBUMS_REQUEST = 4;
	
	private static final String CAMERA_CAPTURE_FILENAME = "cameracapture";
	
	private String mMessage = "Hello";
	private String mPassword = "12345";
	
	private StegoEncryptionJob mLastSharedJob;
	private File mSelectedImageFile;
	private String mSelectedImageName = "good.JPG";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mSelectedImageFile = new File(getApplicationContext().getCacheDir().getAbsolutePath() +
				"/good.JPG");
		
//		AsyncTask task1 = new AsyncTask()
//		{
//			@Override
//			protected Void doInBackground(Object... params)
//			{
				try
				{
					Log.d("YangxiaoLog", mSelectedImageFile.exists() + "-----------" +
							mSelectedImageFile.getAbsolutePath());
					Log.d("YangxiaoLog", "------------------- THIS IS ONLY " +
							"TEST-------------------------------------------\nTEST!!!\nTEST" +
							"!!\nTEST!!\nTEST!!\n");
					Log.d("YangxiaoLog", getApplicationContext().getCacheDir().getAbsolutePath()
							+ "\n");
					Log.d("YangxiaoLog", getExternalStorageDirectory().getAbsolutePath()
							+ "\n");
					
					doCreateEncryptionJob();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
		//				return null;
		//			}
		//		};
		//		task1.execute();
	}
	
	private void createEncryptionJob() {
		
	}
	
	private void doCreateEncryptionJob() {
		String imageName = mSelectedImageName;
		if (TextUtils.isEmpty(imageName) || CAMERA_CAPTURE_FILENAME.contentEquals(imageName)) {
			imageName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg";
		}
		StegoEncryptionJob job = new StegoEncryptionJob(App.getInstance(), mSelectedImageFile,
				imageName, mMessage, mPassword);
		App.getInstance().storeJob(job);
		
		reset();
//		animateImageToOutboxJob(job);
	}
	
	private void reset() {
		mPassword = "";
		mMessage = "";
		mSelectedImageName = null;
		if (mSelectedImageFile != null) {
			if (mSelectedImageFile.exists())
				mSelectedImageFile.delete();
			mSelectedImageFile = null;
		}
	}
	
	private void setCurrentMode() {
		int permissionCheck = ContextCompat.checkSelfPermission(this,
				Manifest.permission.READ_EXTERNAL_STORAGE);
		if (Build.VERSION.SDK_INT <= 18)
			permissionCheck = PackageManager.PERMISSION_GRANTED; // For old devices we ask in the manifest!
		if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
//			AskForPermissionAdapter adapter = new AskForPermissionAdapter(this);
		} else {
			applyCurrentMode();
		}
	}
	
	private void applyCurrentMode() {
//		mLayoutGalleryInfo.setVisibility(App.getInstance().getSettings().skipGalleryInfo() ? View.GONE : View.VISIBLE);
//		setPhotosAdapter(null, true, true);
	}
}
