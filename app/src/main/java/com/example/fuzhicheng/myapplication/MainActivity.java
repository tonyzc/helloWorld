package com.example.fuzhicheng.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fuzhicheng.entity.LoginRequest;
import com.example.fuzhicheng.entity.LoginResponse;
import com.example.fuzhicheng.manager.RetrofitProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends Activity {
    private static final String TAG = "MyApplication";
    TextView MHtmlTv;
    public static final String htmlTxt = "<b>企业发展事业群（CDG）：</b>作为<br>公司新业务孵化和专业支撑<br>平台，负责包括金融、支付、广告等重要领域的拓展，同时为公司各大业务提供战略、投资与公关市场等专业支持。<b>互动娱乐事业群（IEG）：</b>负责公司互动娱乐业务的运营与发展，打造游戏、文学、动漫、影视等在内的多元化、高品质的互动娱乐内容产品，助力公司在全球互动娱乐领域取得领先地位。<b>技术工程事业群（TEG）：</b>负责为公司内部及各事业群提供技术及运营平台支撑，为用户提供全线产品的客户服务，并负责研发管理和数据中心的建设与运营。<b>微信事业群（WXG）：</b>负责微信基础平台、微信开放平台，以及微信支付拓展、O2O等微信延伸业务的发展，并包括邮箱、企业微信等产品开发和运营。<b>云与智慧产业事业群（CSIG）：</b>整合腾讯云、互联网+、智慧零售、教育、医疗、安全和LBS等行业解决方案。<b>平台与内容事业群（PCG）：</b>将对原社交网络事业群（SNG）、原移动互联网事业群（MIG）、原网络媒体事业群（OMG）中，与社交平台、流量平台、数字内容、核心技术等高度关联且具有高融合性的板块，进行拆分和重组。<i>以上参考资料：</i><i></i>";
    public static final String normalText = "企业发展事业群（CDG）：作为公司\n新业务孵化和专业\n支撑平台，负责包括金融、支付";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MHtmlTv = (TextView) findViewById(R.id.main_tv);

//        MHtmlTv.setText(Html.fromHtml(normalText));

        MHtmlTv.setText(normalText);


        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.d(TAG, "Observable thread is : " + Thread.currentThread().getName());
                Log.d(TAG, "emit 1");
                e.onNext(1);
                e.onComplete();
            }
        });
        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "Observer thread is :" + Thread.currentThread().getName());
                Log.d(TAG, "onNext: " + integer);
            }
        };

//        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(consumer);

//        observable.subscribeOn(Schedulers.newThread())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnNext(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.d(TAG, "After observeOn(mainThread), current thread is: " + Thread.currentThread().getName());
//                        Log.d(TAG, "onNext: " + integer);
//                    }
//                })
//                .observeOn(Schedulers.io())
//                .doOnNext(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.d(TAG, "After observeOn(io), current thread is : " + Thread.currentThread().getName());
//                        Log.d(TAG, "onNext: " + integer);
//                    }
//                })
//                .subscribe();

//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                e.onNext(1);
//                e.onNext(2);
//                e.onNext(3);
//            }
//        }).map(new Function<Integer, String>() {
//            @Override
//            public String apply(Integer integer) throws Exception {
//                return "this is result " + integer;
//            }
//        }).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                Log.d(TAG, "accept: " + s);
//            }
//        });

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer);
                }
                return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "accept: " + s);
            }
        });

    }

    public static void loginRequest(final Context context) {
        Api api = RetrofitProvider.get().create(Api.class);
        api.login(new LoginRequest())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginResponse value) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
