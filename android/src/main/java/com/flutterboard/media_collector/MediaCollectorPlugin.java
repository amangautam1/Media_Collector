package com.flutterboard.media_collector;
import android.util.Log;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import java.util.ArrayList;
import java.util.HashSet;
import java.io.File;
import android.os.Environment;
import java.util.StringTokenizer;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** MediaCollectorPlugin */
public class MediaCollectorPlugin implements MethodCallHandler,PluginRegistry.RequestPermissionsResultListener {
  private final Activity activity;
  private static final int MY_PERMISSIONS_REQUEST_CODE = 1001;
  private Result mResult;
  
  private MediaCollectorPlugin(Activity activity) {
    this.activity = activity;
  }

  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "media_collector");
    //channel.setMethodCallHandler(new MediaCollectorPlugin(registrar.activity()));
    MediaCollectorPlugin mediaCollectorPlugin = new MediaCollectorPlugin(registrar.activity());
    channel.setMethodCallHandler(mediaCollectorPlugin);
    registrar.addRequestPermissionsResultListener(mediaCollectorPlugin);
  }

  public  boolean checkPermission(Context context) {
    return ContextCompat.checkSelfPermission((context),
        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
  }

  public void askPermission(Context context,Result result) {
    // if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
    //     Manifest.permission.READ_EXTERNAL_STORAGE))
    //      result.success("Permission Denied");
    // else
      ActivityCompat.requestPermissions((Activity) context, new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
          MY_PERMISSIONS_REQUEST_CODE);

  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    mResult = result;
    if(call.method.equals("getImages")){
        getImages((Context) activity, mResult);
    }
    else if(call.method.equals("getVideos")){
        getVideos((Context) activity, mResult);
    }
    else {
      result.notImplemented();
    }
  }

    public void getImages(Context context, Result result) {
        try {
            if (checkPermission(context)) {
                result.success(getAllImages((Activity)context));
            } else {
                askPermission(context,result);
            }
        } catch (Exception e) {
            result.success("Failed");
        }
    }
    public void getVideos(Context context, Result result) {
        try {
            if (checkPermission(context)) {
                result.success(getAllVideos((Activity) context));
            } else {
                askPermission(context,result);
            }
        } catch (Exception e) {
            result.success("Failed");
        }
    }
  
    public ArrayList<String> getAllImages(Activity activity) {
        Uri uri;
        Cursor cursor;
        int column_index;
        StringTokenizer st1;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA };

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index);
            listOfAllImages.add(absolutePathOfImage);
        }

        return listOfAllImages;
    }
    public ArrayList<String> getAllVideos(Activity activity) {
        HashSet<String> videoItemHashSet = new HashSet<>();
        String[] projection = { MediaStore.Video.VideoColumns.DATA ,MediaStore.Video.Media.DISPLAY_NAME};
        Cursor cursor = activity.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        try {
            cursor.moveToFirst();
            do{
                videoItemHashSet.add((cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))));
            }while(cursor.moveToNext());

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String> downloadedList = new ArrayList<>(videoItemHashSet);
        return downloadedList;
    }


  @Override
    public boolean onRequestPermissionsResult(int requestCode, String[] strings, int[] ints) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (ints.length > 0 && ints[0] == PackageManager.PERMISSION_GRANTED) {
                    getImages(activity, mResult);
                } else {
                    askPermission(activity,mResult);
                   // mResult.success("Permission Denied");
                    // permission denied
                }
                break;
        }
        return true;
    }

}

