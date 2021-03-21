package com.yuxiu.myim.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;


public abstract class BaseActivity<T extends ViewBinding,M extends  BaseModel> extends AppCompatActivity{
    protected T binding;
    protected M model;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        model.onKeyDown(keyCode,event);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onPrepare();
        binding=getBinding();
        setContentView(binding.getRoot());
        model=getModel();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(model!=null)
        model.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (model != null)
            model.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(model!=null)
        model.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(model!=null)
        model.onResume();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(model!=null)
        model.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(model!=null)
        model.onRestart();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(model!=null)
        model.onNewIntent(intent);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(model!=null)
            model.onKeyUp(keyCode,event);
        return super.onKeyUp(keyCode, event);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(model!=null)
            model.onStart();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(model!=null)
        model.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(model!=null)
        model.onBackPressed();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(model!=null)
            model.dispatchKeyEvent(event);
        return super.dispatchKeyEvent(event);

    }

    protected abstract void onPrepare();
    protected abstract T getBinding();
    protected abstract M getModel();

    public void setStateBar ( boolean textDark){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                if (textDark) {
                    int option =
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                    decorView.setSystemUiVisibility(option);
                } else {
                    int option =
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                    decorView.setSystemUiVisibility(option);
                }

                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                //导航栏颜色也可以正常设置
//                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation =
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
//                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }
}
