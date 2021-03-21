package com.yuxiu.myim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.yuxiu.myim.base.BaseActivity;
import com.yuxiu.myim.databinding.ActivityMainBinding;
import com.yuxiu.myim.utils.NetUtils;

public class MainActivity  extends BaseActivity<ActivityMainBinding,MainModel> {
    @Override
    protected void onPrepare() {

    }

    @Override
    protected ActivityMainBinding getBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected MainModel getModel() {
        return new MainModel(binding,this);
    }
}