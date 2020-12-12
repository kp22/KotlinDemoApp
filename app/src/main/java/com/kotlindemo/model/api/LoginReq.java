package com.kotlindemo.model.api;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LoginReq{
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEmailValid(){
        if(!TextUtils.isEmpty(email) /*&& Patterns.EMAIL_ADDRESS.matcher(email).matches()*/){
            return true;
        }
        return false;
    }
    public boolean isPasswordEmpty(){
        if(!TextUtils.isEmpty(password)){
            return false;
        }
        return true;
    }
    public Map<String, RequestBody> getFormMap() {
        final Map<String, RequestBody> partMap = new HashMap<>();
        partMap.put("username",RequestBody.create(MediaType.parse("text/plain"), email));
        partMap.put("password",RequestBody.create(MediaType.parse("text/plain"), password));
        return partMap;
    }
}
