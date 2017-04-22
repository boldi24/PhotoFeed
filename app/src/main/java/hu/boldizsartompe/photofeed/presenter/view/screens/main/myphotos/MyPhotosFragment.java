package hu.boldizsartompe.photofeed.presenter.view.screens.main.myphotos;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.boldizsartompe.photofeed.R;
import hu.boldizsartompe.photofeed.presenter.view.BaseActivity;
import hu.boldizsartompe.photofeed.presenter.view.presenter.main.myphotos.MyPhotosPresenter;


public class MyPhotosFragment extends Fragment implements MyPhotosView {

    private static final String TAG = "MyPhotosFragment";


    public static final String TMP_IMAGE_JPG = "/tmp_image.jpg";
    public static final String IMAGE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + TMP_IMAGE_JPG;

    private static final int CAPTURE_IMAGE_REQUEST_CODE = 1;

    private static final int REQUEST_CAMERA = 10;
    private static final int REQUEST_EXTERNAL_STORAGE = 20;

    private MyPhotosPresenter myPhotosPresenter;

    public MyPhotosFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myPhotosPresenter = new MyPhotosPresenter();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_photos, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        myPhotosPresenter.attachView(this);
    }

    @Override
    public void onStop() {
        myPhotosPresenter.detachView();
        super.onStop();
    }

    @Override
    public void showLoading(String message) {
        ((BaseActivity)getActivity()).showLoading(message);
    }

    @Override
    public void hideLoading() {
        ((BaseActivity)getActivity()).hideLoading();
    }

    @Override
    public void onStartActivityWithBundle(Class<?> cls, Bundle bundle) {

    }

    @Override
    public void onStartActivityWithoutBundle(Class<?> cls) {

    }

    //will not be called
    @Override
    public int getContentView() {
        return 0;
    }

    @Override
    public void showPhotoUploadProgress() {
        ((BaseActivity)getActivity()).showLoading(getString(R.string.text_photo_upload_progress));
    }

    @OnClick(R.id.btn_myphotos_newphoto)
    void onUploadBTNClicked(){

//        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
//        if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
//            requestCameraPermission();
//        }
//
//        permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
//            requestExternalStoragePermission();
//        } else {
//            takePhoto();
//        }

        takePhoto();


    }

    private void takePhoto(){
        File imageFile = new File(IMAGE_PATH);
        Uri imageFileUri = Uri.fromFile(imageFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,imageFileUri);
        startActivityForResult(intent, CAPTURE_IMAGE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == CAPTURE_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK){

            Log.d(TAG, "uplaoding phoot");

            //Uri uri = data.getData();
            myPhotosPresenter.attachViewOnPhoto(this);
            myPhotosPresenter.uploadPhoto(Uri.fromFile(new File(IMAGE_PATH)));

        }

    }

//    private void requestCameraPermission() {
//        Log.i(TAG, "CAMERA permission has NOT been granted. Requesting permission.");
//
//        // BEGIN_INCLUDE(camera_permission_request)
//        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
//            // Provide an additional rationale to the user if the permission was not granted
//            // and the user would benefit from additional context for the use of the permission.
//            // For example if the user has previously denied the permission.
//            Log.i(TAG, "Displaying camera permission rationale to provide additional context.");
//            Snackbar.make(getActivity().findViewById(android.R.id.content),
//                    "Kamera engedély",
//                    Snackbar.LENGTH_INDEFINITE)
//                    .setAction("Oké", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
//                        }
//                    })
//                    .show();
//        } else {
//            Log.i(TAG, "Reguesting immedately");
//            // Camera permission has not been granted yet. Request it directly.
//            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
//        }
//        // END_INCLUDE(camera_permission_request)
//    }
//
//    private void requestExternalStoragePermission() {
//        Log.i(TAG, "STORAGE permission has NOT been granted. Requesting permission.");
//
//        // BEGIN_INCLUDE(camera_permission_request)
//        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//            // Provide an additional rationale to the user if the permission was not granted
//            // and the user would benefit from additional context for the use of the permission.
//            // For example if the user has previously denied the permission.
//            Log.i(TAG, "Displaying camera permission rationale to provide additional context.");
//            Snackbar.make(getActivity().findViewById(android.R.id.content),
//                    "Kamera engedély",
//                    Snackbar.LENGTH_INDEFINITE)
//                    .setAction("Oké", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
//                        }
//                    })
//                    .show();
//        } else {
//            Log.i(TAG, "Reguesting immedately");
//            // Camera permission has not been granted yet. Request it directly.
//            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
//        }
//        // END_INCLUDE(camera_permission_request)
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//
//        if (requestCode == REQUEST_CAMERA) {
//            // BEGIN_INCLUDE(permission_result)
//            // Received permission result for camera permission.
//            Log.i(TAG, "Received response for Camera permission request.");
//
//            // Check if the only required permission has been granted
//            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Camera permission has been granted, preview can be displayed
//                Log.i(TAG, "CAMERA permission has now been granted. Showing preview.");
//                Snackbar.make(getActivity().findViewById(android.R.id.content), "Engedély megadva", Snackbar.LENGTH_SHORT).show();
//
//                takePhoto();
//            } else {
//                Log.i(TAG, "CAMERA permission was NOT granted.");
//                Snackbar.make(getActivity().findViewById(android.R.id.content), "Engedély tiltva", Snackbar.LENGTH_SHORT).show();
//            }
//            // END_INCLUDE(permission_result)
//
//        } else if(requestCode == REQUEST_EXTERNAL_STORAGE) {
//
//            Log.i(TAG, "Received response for STORAGE permission request.");
//
//            // Check if the only required permission has been granted
//            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Camera permission has been granted, preview can be displayed
//                Log.i(TAG, "STORAGE permission has now been granted. Showing preview.");
//                Snackbar.make(getActivity().findViewById(android.R.id.content), "Engedély megadva", Snackbar.LENGTH_SHORT).show();
//
//                takePhoto();
//            } else {
//                Log.i(TAG, "STORAGE permission was NOT granted.");
//                Snackbar.make(getActivity().findViewById(android.R.id.content), "Engedély tiltva", Snackbar.LENGTH_SHORT).show();
//            }
//            // END_INCLUDE(permission_result)
//
//        } else {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//    }
}
