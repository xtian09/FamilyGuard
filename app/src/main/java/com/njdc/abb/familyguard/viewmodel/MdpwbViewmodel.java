package com.njdc.abb.familyguard.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.njdc.abb.familyguard.model.entity.data.User;
import com.njdc.abb.familyguard.util.Constants;
import com.njdc.abb.familyguard.util.SpManager;

public class MdpwbViewmodel extends ViewModel {

    MutableLiveData<String> originPwd = new MutableLiveData<>();
    MutableLiveData<String> newPwd = new MutableLiveData<>();
    MutableLiveData<String> newRepeatPwd = new MutableLiveData<>();
    MutableLiveData<Boolean> btnEnable = new MutableLiveData<>();
    private User user;

    public MdpwbViewmodel() {
        user = SpManager.INSTANCE.getUser();
        this.originPwd.setValue("");
        this.newPwd.setValue("");
        this.newRepeatPwd.setValue("");
        this.btnEnable.setValue(false);

        this.originPwd.observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!TextUtils.isEmpty(originPwd.getValue()) && !TextUtils.isEmpty(newPwd.getValue())
                        && !TextUtils.isEmpty(newRepeatPwd.getValue())) {
                    btnEnable.setValue(true);
                } else {
                    btnEnable.setValue(false);
                }
            }
        });
        this.newPwd.observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!TextUtils.isEmpty(originPwd.getValue()) && !TextUtils.isEmpty(newPwd.getValue())
                        && !TextUtils.isEmpty(newRepeatPwd.getValue())) {
                    btnEnable.setValue(true);
                } else {
                    btnEnable.setValue(false);
                }
            }
        });
        this.newRepeatPwd.observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!TextUtils.isEmpty(originPwd.getValue()) && !TextUtils.isEmpty(newPwd.getValue())
                        && !TextUtils.isEmpty(newRepeatPwd.getValue())) {
                    btnEnable.setValue(true);
                } else {
                    btnEnable.setValue(false);
                }
            }
        });
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        SpManager.INSTANCE.setUser(user);
    }

    public MutableLiveData<String> getOriginPwd() {
        return originPwd;
    }

    public MutableLiveData<String> getNewPwd() {
        return newPwd;
    }

    public MutableLiveData<String> getNewRepeatPwd() {
        return newRepeatPwd;
    }

    public MutableLiveData<Boolean> getBtnEnable() {
        return btnEnable;
    }

    public void setBtnEnable(MutableLiveData<Boolean> btnEnable) {
        this.btnEnable = btnEnable;
    }

    public String checkPattern() {
        if (!(originPwd.getValue().equals(user.getPwd()))) {

            return "默認密碼必須和原始密码一致！";
        }

        if (!Constants.INSTANCE.getUserPattern().matcher(newPwd.getValue()).matches()) {
            return "请填写6-16位字符密码！";
        }
        if (!Constants.INSTANCE.getPwdPattern().matcher(newRepeatPwd.getValue()).matches()) {
            return "请填写6-16位字符密码！";
        }
        if (!(newPwd.getValue().equals(newRepeatPwd.getValue()))) {
            return "密碼輸入需要一致";
        }
        return "success";
    }


}
