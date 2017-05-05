package hu.boldizsartompe.photofeed.presenter.view.screens.main.photos.myfeed;


import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.boldizsartompe.photofeed.R;
import hu.boldizsartompe.photofeed.domain.entity.Photo;
import hu.boldizsartompe.photofeed.presenter.rx.JobExecutor;
import hu.boldizsartompe.photofeed.presenter.utils.MessageShower;
import hu.boldizsartompe.photofeed.presenter.view.BaseActivity;
import hu.boldizsartompe.photofeed.presenter.view.presenter.main.myfeed.MyFeedPresenter;
import hu.boldizsartompe.photofeed.presenter.view.screens.main.photos.BasePhotosFragment;
import hu.boldizsartompe.photofeed.presenter.view.screens.main.photos.myphotos.MyPhotosAdapter;

public class MyFeedFragment extends BasePhotosFragment implements MyFeedView, MyFeedAdapter.IPhoto {

    public static final String IMAGE_PATH = Environment.getExternalStorageDirectory() + File.separator + "photo_feed" + File.separator;

    public static final int MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE = 110;

    @BindView(R.id.rv_myfeed)
    RecyclerView recyclerView;
    @BindView(R.id.srf_myfeed)
    SwipeRefreshLayout swipeRefreshLayout;

    private MyFeedAdapter adapter;

    private MyFeedPresenter myFeedPresenter;

    public MyFeedFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myFeedPresenter = new MyFeedPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getContentView(), container, false);
        ButterKnife.bind(this, view);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        adapter = new MyFeedAdapter(getContext(), new ArrayList<Photo>(), this);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myFeedPresenter.getMyFeedPhotos();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        myFeedPresenter.attachView(this);
        myFeedPresenter.getMyFeedPhotos();
    }

    @Override
    public void onStop() {
        myFeedPresenter.detachView();
        super.onStop();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_my_feed;
    }

    @Override
    public void showPhotos(List<Photo> photos) {
        adapter.addPhotos(photos);
    }

    @Override
    public void likePhoto(int position) {
        myFeedPresenter.likePhoto(position);
    }

    @Override
    public void commentClicked(int position) {
        myFeedPresenter.showComments(position);
    }

    private ImageView iv;
    private String title;

    @Override
    public void savePhoto(ImageView iv, String title) {
        this.iv = iv;
        this.title = title;
        showDoYouWantToSaveAlert();
    }


    //**********************************
    //TODO: külön class ami ezekt csinálja
    private void showPermissionDialog(final String permission, final int code) {
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
                        ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, code);
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    //**********************************
    //TODO: külön class ami ezekt csinálja
    public void handleExternalStoragePermission(){
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                showPermissionDialog(Manifest.permission.WRITE_EXTERNAL_STORAGE, MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE);
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE);
            }
        } else {
            saveImageView();
        }
    }

    private void showDoYouWantToSaveAlert(){
        new AlertDialog.Builder(getContext())
                .setTitle("Kép mentés")
                .setMessage("El akarod menteni ezt a képet?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MessageShower.showSnackbarToastOnContextWithMessage(iv, getContext().getString(R.string.text_photo_saving_inprogress));
                        dialogInterface.dismiss();
                        handleExternalStoragePermission();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    //Android specific so saving here
    public void saveImageView(){
        JobExecutor.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                iv.buildDrawingCache();
                Bitmap bm = iv.getDrawingCache();
                OutputStream fOut = null;
                try {
                    File root = new File(IMAGE_PATH);
                    root.mkdirs();
                    File imageMainDirectory = new File(root, title + ".jpg");
                    fOut = new FileOutputStream(imageMainDirectory);
                } catch (Exception e) {
                    MessageShower.showSnackbarToastOnContextWithMessage(iv, getContext().getString(R.string.error_while_saving_photo));
                }
                try {
                    bm.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                    MessageShower.showSnackbarToastOnContextWithMessage(iv, getContext().getString(R.string.text_saving_photo_successful));
                } catch (Exception e) {}
            }
        });
    }


    @Override
    public void stopRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
