package com.yuxiu.myim.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.NonNull;

import androidx.viewbinding.ViewBinding;

import com.yuxiu.myim.R;

/**
 * @author : yx^_^
 * @e-mail : 565749553@qq.com
 * @date : 2020/3/6 10:59
 * @desc : better than before
 */
public abstract class MyBaseModel<T extends ViewBinding> extends BaseModel<T>{
    protected ProgressDialog mProgressDialog;
    protected String TAG;
    public MyBaseModel(T binding, Activity context) {
        super(binding, context);
        TAG=binding.getClass().getName();

    }
    public int getStateBarColor() {
        return R.color.white;
    }

    public MyBaseModel(T binding) {
        super(binding);
        TAG=binding.getClass().getName();
    }
    @Override
    public void onDestroy() {
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onKeyDown(int keyCode, KeyEvent event) {
    }

    @Override
    protected void toLoginView() {

//        Intent intent = new Intent(context, LoginActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);

    }

    @Override
    public void onNewIntent(Intent intent) {

    }

    @Override
    public void onBackPressed() {

    }

    /**
     * 取消进度条
     */
    public void cancelProgressDialog() {
        if (mProgressDialog == null) {
            return;
        } else {
            mProgressDialog.cancel();
        }
    }

    @Override
    public void onKeyUp(int keyCode, KeyEvent event) {

    }

    @Override
    public void dispatchKeyEvent(KeyEvent event) {

    }

    /**
     * 显示进度条
     */
    public void showProgressDialog(String msg) {
        if(mProgressDialog!=null&&mProgressDialog.isShowing()){
            return;
        }
//        if (null == mProgressDialog) {

//        } else {
//            mProgressDialog.setMessage(msg);
//        }
        mProgressDialog.show();
//
//        if (null != mProgressDialog) {
//            if (!mProgressDialog.isShowing()) {
//                mProgressDialog.show();
//            }
////
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }
}
