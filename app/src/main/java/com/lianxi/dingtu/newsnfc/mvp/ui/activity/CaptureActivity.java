package com.lianxi.dingtu.newsnfc.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lianxi.dingtu.newsnfc.R;
import com.lianxi.dingtu.newsnfc.app.utils.AppOperator;
import com.lianxi.dingtu.newsnfc.app.utils.ImageUtil;
import com.lianxi.dingtu.newsnfc.di.component.DaggerCaptureComponent;
import com.lianxi.dingtu.newsnfc.mvp.contract.CaptureContract;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.EMGoodsTo;
import com.lianxi.dingtu.newsnfc.mvp.presenter.CapturePresenter;
import com.jess.arms.base.BaseActivity;
import java.io.IOException;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.lianxi.dingtu.newsnfc.app.utils.CameraUtils.setPreviewSize;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/28/2019 13:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class CaptureActivity extends BaseActivity<CapturePresenter> implements CaptureContract.View ,TextureView.SurfaceTextureListener{

    private ImageButton mCaptureButtonFirst;
    private int visitor;
    private EMGoodsTo mEMGoodsTo;
    @BindView(R.id.camera_view_first)
    TextureView cameraViewFirst;
    private Camera mCamera;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCaptureComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        // 去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_capture; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        // init mVideoSurface
        cameraViewFirst.setSurfaceTextureListener(this);

        visitor = getIntent().getIntExtra("visitor", 0);
        mEMGoodsTo = (EMGoodsTo) getIntent().getSerializableExtra("materials");
//        mUSBMonitor = new USBMonitor(this, mOnDeviceConnectListener);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    /**
     * 带有回调数据的初始化
     */
    private void resultFirstCamera() {

        //设置显示宽高
//        mUVCCameraViewFirst.setAspectRatio(UVCCamera.DEFAULT_PREVIEW_WIDTH / (float) UVCCamera.DEFAULT_PREVIEW_HEIGHT);
        mCaptureButtonFirst = findViewById(R.id.capture_button_first);
        mCaptureButtonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (mHandlerFirst != null) {
//                    if (mHandlerFirst.isOpened()) {
//                        if (checkPermissionWriteExternalStorage() && checkPermissionAudio()) {

                AppOperator.runOnThread(new Runnable() {
                    @Override
                    public void run() {

                        Bitmap cropBitmap = cameraViewFirst.getBitmap();
                        String str = ImageUtil.savePicInSD(cropBitmap);
                        if (1 == visitor) {
                            Intent intent = new Intent(CaptureActivity.this, PreviewActivity.class);
                            intent.putExtra("startWithUri", str);
                            startActivity(intent);
                            finish();
                        }
                        if (2 == visitor) {
                            Intent intent = new Intent(CaptureActivity.this, ImageActivity.class);
                            intent.putExtra("startWithUri", str);
                            intent.putExtra("materials", mEMGoodsTo);
                            startActivity(intent);
                            finish();
                        }


                    }
                });
//                        }
//                    }
//                }
            }
        });
    }
//
//        mHandlerFirst = UVCCameraHandler.createHandler(this, mUVCCameraViewFirst
//                , UVCCamera.DEFAULT_PREVIEW_WIDTH, UVCCamera.DEFAULT_PREVIEW_HEIGHT
//                , BANDWIDTH_FACTORS[0], firstDataCallBack);
//
//        if (mHandlerFirst != null) {
//            if (!mHandlerFirst.isOpened()) {
//
//                Flowable.just("vans")
//                        //延时两秒，第一个参数是数值，第二个参数是事件单位
//                        .delay(2, TimeUnit.SECONDS)
//                        // Run on a background thread
//                        .subscribeOn(Schedulers.io())
//                        // Be notified on the main thread
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Consumer<String>() {
//                            @Override public void accept(String s) throws Exception {
//                                usbOpen();
//                            }
//                        });//这里的观察者依然不重要
//            } else {
//                mHandlerFirst.close();
//                //
//            }
//        }
//
//    }

//    private void usbOpen() {
//        // 取连接到设备上的USB设备集合
//        final List<DeviceFilter> filter = DeviceFilter.getDeviceFilters(this, com.serenegiant.uvccamera.R.xml.device_filter);
//        List<UsbDevice> list = mUSBMonitor.getDeviceList(filter.get(0));
//        // 没有连接设备
//        if (list.size() == 0) {
//            ArmsUtils.snackbarText("未找到任何连接设备");
//            return;
//        }
//        // 遍历集合取指定的USB设备
//        UsbDevice usbDevice = null;
//        for (UsbDevice device : list) {
//            //
//            int VendorID = device.getVendorId();
//            int ProductID = device.getProductId();
//            Log.e("usbPrint: ", "VendorID" + VendorID + "==ProductID" + ProductID);
//            if (VendorID == 7727 && ProductID == 38789) {
//                usbDevice = device;
//                break;
//            }
//        }
//        // 没有找到设备
//        if (usbDevice == null) {
//            ArmsUtils.snackbarText("未找到指定相机");
//            return;
//        }
//        Log.e("usbPrint: ", JSON.toJSONString(usbDevice));
//        mUSBMonitor.requestPermission(usbDevice);
//    }
//

    @Override
    protected void onStart() {
        super.onStart();
//        mUSBMonitor.register();
//        if (mUVCCameraViewFirst != null)
//            mUVCCameraViewFirst.onResume();

        resultFirstCamera();
    }
//
//
//    @Override
//    protected void onStop() {
//        mHandlerFirst.close();
//        if (mUVCCameraViewFirst != null)
//            mUVCCameraViewFirst.onPause();
//
//
//        mUSBMonitor.unregister();//usb管理器解绑
//        super.onStop();
//    }
//
//    UvcCameraDataCallBack firstDataCallBack = new UvcCameraDataCallBack() {
//        @Override
//        public void getData(byte[] data) {
//            if (DEBUG) Log.v(TAG, "数据回调:" + data.length);
//        }
//    };
//
//
//    private final USBMonitor.OnDeviceConnectListener mOnDeviceConnectListener = new USBMonitor.OnDeviceConnectListener() {
//        @Override
//        public void onAttach(final UsbDevice device) {
//            if (DEBUG) Log.v(TAG, "onAttach:" + device);
//            showMessage("USB_DEVICE_ATTACHED");
//        }
//
//        @Override
//        public void onConnect(final UsbDevice device, final USBMonitor.UsbControlBlock ctrlBlock, final boolean createNew) {
//            //设备连接成功
//            if (DEBUG) Log.v(TAG, "onConnect:" + device);
//            if (!mHandlerFirst.isOpened()) {
//                mHandlerFirst.open(ctrlBlock);
//                final SurfaceTexture st = mUVCCameraViewFirst.getSurfaceTexture();
//                mHandlerFirst.startPreview(new Surface(st));
//
//
//            }
//        }
//
//        @Override
//        public void onDisconnect(final UsbDevice device, final USBMonitor.UsbControlBlock ctrlBlock) {
//            if (DEBUG) Log.v(TAG, "onDisconnect:" + device);
//            if ((mHandlerFirst != null) && !mHandlerFirst.isEqual(device)) {
//                queueEvent(new Runnable() {
//                    @Override
//                    public void run() {
//                        mHandlerFirst.close();
//                        if (mFirstPreviewSurface != null) {
//                            mFirstPreviewSurface.release();
//                            mFirstPreviewSurface = null;
//                        }
//                        //
//
//                    }
//                }, 0);
//            }
//        }
//
//        @Override
//        public void onDettach(final UsbDevice device) {
//            if (DEBUG) Log.v(TAG, "onDettach:" + device);
//            showMessage("USB_DEVICE_DETACHED");
//        }
//
//        @Override
//        public void onCancel(final UsbDevice device) {
//            if (DEBUG) Log.v(TAG, "onCancel:");
//        }
//    };
//
//    @Override
//    public USBMonitor getUSBMonitor() {
//        return mUSBMonitor;
//    }
//
//    @Override
//    public void onDialogResult(boolean canceled) {
//        if (canceled) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    //
//                }
//            }, 0);
//        }
//    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        mCamera = Camera.open();
        setPreviewSize(mCamera, 1920, 1080);
        try {
            mCamera.setPreviewTexture(surface);
        } catch (IOException t) {
        }
        mCamera.startPreview();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mCamera.stopPreview();
        mCamera.release();
        return true;    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {}
}
