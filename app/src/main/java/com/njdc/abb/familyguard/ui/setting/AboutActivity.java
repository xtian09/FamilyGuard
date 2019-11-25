package com.njdc.abb.familyguard.ui.setting;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;

import com.njdc.abb.familyguard.R;
import com.njdc.abb.familyguard.databinding.AtySetAboutBinding;
import com.njdc.abb.familyguard.ui.base.BaseActivity;

public class AboutActivity extends BaseActivity<AtySetAboutBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.aty_set_about;
    }

    @Override
    public void loadData() {
        try {
            PackageManager packageManager = this.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(this.getPackageName(), 0);
            int appVersion = packageInfo.versionCode;
            mBinding.tvVersion.setText("Version: " + appVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        mBinding.tbAbb.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutActivity.this.finish();
            }
        });
    }
}
