package com.desmond.squarecamera;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;


public class CameraActivity extends AppCompatActivity {

    public static final String TAG = CameraActivity.class.getSimpleName();

    /**
     * A convenient method for starting CameraActivity.
     *
     * @param activity    The caller Activity
     * @param requestCode If >= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     */
    public static void startCameraActivityForResult(Activity activity, int requestCode) {
        if (hasCameraPermission(activity)) {
            Intent intent = new Intent(activity, CameraActivity.class);
            activity.startActivityForResult(intent, requestCode);
        } else {
            Log.e(TAG, "Camera permission has not been granted!");
        }
    }

    private static boolean hasCameraPermission(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.squarecamera__CameraFullScreenTheme);
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.squarecamera__activity_camera);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, CameraFragment.newInstance(), CameraFragment.TAG)
                    .commit();
        }
    }

    public void returnPhotoUri(Uri uri) {
        Intent data = new Intent();
        data.setData(uri);

        if (getParent() == null) {
            setResult(RESULT_OK, data);
        } else {
            getParent().setResult(RESULT_OK, data);
        }

        finish();
    }

    public void onCancel(View view) {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
