package com.example.fuzhicheng.myapplication;

import com.example.fuzhicheng.entity.LoginRequest;
import com.example.fuzhicheng.entity.LoginResponse;
import com.example.fuzhicheng.entity.RegisterRequest;
import com.example.fuzhicheng.entity.RegisterResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;

/**
 * Created by fuzhicheng on 2017/11/30.
 */

public interface Api {
    @GET
    Observable<LoginResponse> login(@Body LoginRequest request);

    @GET
    Observable<RegisterResponse> register(@Body RegisterRequest request);

}
