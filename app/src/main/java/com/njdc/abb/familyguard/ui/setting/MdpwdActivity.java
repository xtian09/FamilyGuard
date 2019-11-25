package com.njdc.abb.familyguard.ui.setting;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.lxj.xpopup.XPopup;
import com.njdc.abb.familyguard.R;
import com.njdc.abb.familyguard.databinding.AtyMdfPwdBinding;
import com.njdc.abb.familyguard.model.entity.data.User;
import com.njdc.abb.familyguard.model.entity.http.BaseResponse;
import com.njdc.abb.familyguard.ui.base.BaseActivity;
import com.njdc.abb.familyguard.util.ExtensKt;
import com.njdc.abb.familyguard.viewmodel.MdpwbViewmodel;
import com.njdc.abb.familyguard.viewmodel.UserViewModel;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class MdpwdActivity extends BaseActivity<AtyMdfPwdBinding> {

    private MdpwbViewmodel mdpwbViewmodel;
    private UserViewModel userViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.aty_mdf_pwd;
    }

    @Override
    public void loadData() {
        Log.d("ycy", "loaddata");

        mdpwbViewmodel = new ViewModelProvider(this).get(MdpwbViewmodel.class);
        userViewModel = new ViewModelProvider(this, getFactory()).get(UserViewModel.class);
        mBinding.setVm(mdpwbViewmodel);
        mBinding.setLifecycleOwner(this);
        mdpwbViewmodel.getBtnEnable().observe(this, new Observer<Boolean>() {

            @Override
            public void onChanged(Boolean aBoolean) {

                mBinding.tbPwd.setRightBtnEnable(aBoolean);
            }
        });
        mBinding.tbPwd.setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    Log.d("ycy", "rightclick");
                    String msg = mdpwbViewmodel.checkPattern();
                    if (msg != "success") {
                        new XPopup.Builder(MdpwdActivity.this).asConfirm("", msg, null).show();
                    } else {

                        //請求網絡

                        userViewModel.alterPwd(mdpwbViewmodel.getUser().getUsr(), mdpwbViewmodel.getUser().getPwd(), mdpwbViewmodel.getNewPwd().getValue()).subscribeOn(Schedulers.io()).observeOn(
                                AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(
                                MdpwdActivity.this,
                                Lifecycle.Event.ON_DESTROY))).subscribe(new Consumer<BaseResponse<String>>() {

                            @Override
                            public void accept(BaseResponse<String> stringBaseResponse) throws Exception {
                                ExtensKt.dialog(MdpwdActivity.this, "修改成功");
                                User user = new User(mdpwbViewmodel.getUser().getUsr(), mdpwbViewmodel.getNewPwd().getValue(), mdpwbViewmodel.getUser().getPhone());
                                mdpwbViewmodel.setUser(user);

                                // MdpwdActivity.this.finish();

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                ExtensKt.dialog(MdpwdActivity.this, "异常");
                            }
                        });


                    }
                }
            }
        });

        mBinding.tbPwd.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ycy", "leftclick");
                MdpwdActivity.this.finish();
            }
        });

    }
}