package hu.boldizsartompe.photofeed.presenter.view.screens.main.myphotos;


import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.boldizsartompe.photofeed.BuildConfig;
import hu.boldizsartompe.photofeed.R;
import hu.boldizsartompe.photofeed.domain.entity.Photo;
import hu.boldizsartompe.photofeed.presenter.utils.MessageShower;
import hu.boldizsartompe.photofeed.presenter.view.BaseActivity;
import hu.boldizsartompe.photofeed.presenter.view.presenter.main.myphotos.MyPhotosPresenter;
import hu.boldizsartompe.photofeed.presenter.view.screens.main.MainActivity;


public class MyPhotosFragment extends Fragment implements MyPhotosView {

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final int MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE = 101;

    private static final String TAG = "MyPhotosFragment";

    public static final String TMP_IMAGE_JPG = "/tmp_image.jpg";
    public static final String IMAGE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + TMP_IMAGE_JPG;

    private static final int CAPTURE_IMAGE_REQUEST_CODE = 1;

    @BindView(R.id.rv_myphotos)
    RecyclerView recyclerView;

    private MyPhotosPresenter myPhotosPresenter;
    private MyPhotosAdapter adapter;

    public MyPhotosFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myPhotosPresenter = new MyPhotosPresenter();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(getContentView(), container, false);
        ButterKnife.bind(this, view);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        adapter = new MyPhotosAdapter(getContext(), new ArrayList<Photo>());
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        myPhotosPresenter.attachView(this);
        myPhotosPresenter.getMyPhotos();
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
        return R.layout.fragment_my_photos;
    }

    @Override
    public void showPhotoUploadProgress() {
        MessageShower.showSnackbarToastOnContextWithMessage(
                getActivity().findViewById(android.R.id.content),
                getString(R.string.text_photo_upload_inprogress));
    }

    @Override
    public void showPhotoUploadComplete() {
        MessageShower.showSnackbarToastOnContextWithMessage(
                getActivity().findViewById(android.R.id.content),
                getString(R.string.text_photo_upload_complete));
    }

    @Override
    public void showPhotoUploadError() {
        MessageShower.showSnackbarToastOnContextWithMessage(
                getActivity().findViewById(android.R.id.content),
                getString(R.string.text_photo_upload_error));
    }

    @Override
    public void showNewPhoto(Photo photo) {
        adapter.addPhoto(photo);
    }

    @Override
    public void showNewPhotos(List<Photo> photos) {
        adapter.addPhotos(photos);
    }

    @OnClick(R.id.fab_myphotos_addphoto)
    void onUploadBTNClicked(){
        handleCameraPermission();
    }

    private void handleCameraPermission(){
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setTitle(R.string.dialogtitle_permission);
                alertDialogBuilder
                        .setMessage(R.string.explanation_camera_permission)
                        .setCancelable(false)
                        .setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                MessageShower.showSnackbarToastOnContextWithMessage(getView(), "Az engedély nélkül nem lehet képet készíteni!");
                            }
                        })
                        .setPositiveButton(R.string.forward, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
            }
        } else {
            handleExternalStoragePermission();
        }
    }

    public void handleExternalStoragePermission(){
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setTitle(R.string.dialogtitle_permission);
                alertDialogBuilder
                        .setMessage(R.string.explanation_camera_permission)
                        .setCancelable(false)
                        .setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                MessageShower.showSnackbarToastOnContextWithMessage(getView(), "Az engedély nélkül nem lehet képet készíteni!");
                            }
                        })
                        .setPositiveButton(R.string.forward, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE);
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE);
            }
        } else {
            takePhoto();
        }
    }

    public void takePhoto(){
        File imageFile = new File(IMAGE_PATH);
        //Uri imageFileUri = Uri.fromFile(imageFile);

        //For ANDROID N support
        Uri imageFileUri = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".provider", imageFile);
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

}
