package com.health.pusun.device.utils;

import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.health.pusun.device.vo.RequestCallVo;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.builder.PostStringBuilder;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;

/**
 * Created by majiangtao on 2018-03-12.
 */

public class MyHttpUtils {

    public static Handler getMainHandler() {
        return OkHttpUtils.getInstance().getDelivery();
    }

    /**
     * 打印请求信息
     *
     * @param url
     * @param map
     */
    public static void logRequest(String url, HashMap<String, String> map) {
        AppLog.e("请求：" + url + " params: " + CollectionUtil.getCollectionValue2(map));
    }


    public static void getAsAync(String url, HashMap<String, String> map, final MyJsonCallbalk callbalk) {

        MyHttpUtils.logRequest(url, map);

        if (map != null && map.size() > 0) {
            Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                String value = entry.getValue();
                if (value == null) {
                    entry.setValue("");
                }
            }
        }

        GetBuilder builder = OkHttpUtils.get().url(url).params(map);

        RequestCall call = builder.build();
        call.connTimeOut(60 * 1000);
        call.readTimeOut(60 * 1000);
        call.writeTimeOut(60 * 1000);
//        try {
//            Response response = call.execute();
////            if (response.code() == 200) {
//                AppLog.e("返回:" + response.body().toString());
//                RequestCallVo requestCallVo = JSON.parseObject(response.body().toString(), RequestCallVo.class);
//                callbalk.onResponse(requestCallVo);
////            } else if (response.code() == 407) {
////                callbalk.onError(new Exception(), 407);
////            } else {
////                callbalk.onError(new Exception(), 0);
////            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            callbalk.onError(e, 0);
//        }
        call.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                AppLog.e("返回:" + response.toString());
                RequestCallVo requestCallVo = JSON.parseObject(response.toString(), RequestCallVo.class);
                callbalk.onResponse(requestCallVo);
            }
        });
    }

    public static void postAsAync(String url, HashMap<String, String> map, final MyJsonCallbalk callbalk) {
        postFileAsAync(url, map, null, "", callbalk);
    }

    public static void postFileAsAync(final String url, final HashMap<String, String> map, File file, String fileName, final MyJsonCallbalk callbalk) {
        MyHttpUtils.logRequest(url, map);//打印请求日志
        //防止value是null,请求异常
        if (map != null && map.size() > 0) {
            Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                String value = entry.getValue();
                if (value == null) {
                    entry.setValue("");
                }
            }
        }
        PostFormBuilder builder = OkHttpUtils.post().url(url).params(map);
//        PostFormBuilder builder = OkHttpUtils.postString().url(url).content().mediaType().build().execute()
        if (file != null) {
            builder.addFile(fileName, file.getName(), file);
        }

//        builder.addHeader("userToken", App.getStringUserPreference("token"));
        RequestCall call = builder.build();
        call.connTimeOut(60 * 1000);
        call.readTimeOut(60 * 1000);
        call.writeTimeOut(60 * 1000);
//        call.connTimeOut(2*60 * 1000);
//        call.readTimeOut(6*60 * 1000);
//        call.writeTimeOut(6*60 * 1000);
//        try {
//            Response response = call.execute();
//            if (response != null){
//                String body = response.body().string();
//                if (response.code() == 200){
//                    RequestCallVo requestCallVo = JSON.parseObject(body,RequestCallVo.class);
//                    callbalk.onResponse(requestCallVo);
//                } else if (response.code() == 407){
//                    callbalk.onError(new Exception(), 407);
//                }else {
//                    callbalk.onError(new Exception(), 0);
//                }
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        call.execute(new StringCallback() {
            @Override
            public void onBefore(Request request) {
                callbalk.onBefore();

            }

            @Override
            public void onError(Call call, Exception e) {
                e.printStackTrace();
                callbalk.onError(e, 0);
            }

            @Override
            public void onResponse(String response) {
                AppLog.e("返回:" + response);
                RequestCallVo requestCallVo = JSON.parseObject(response, RequestCallVo.class);
//                if (requestCallVo.getInfo().equals("loginDenied")) {
//                    /**
//                     * 重新请求登录
//                     */
//                    HashMap<String, String> params = new HashMap<>();
//                    params.put("userName", App.getStringUserPreference("userNameString"));
//                    params.put("password", App.getStringUserPreference("passwordString"));
//                    params.put("loginType", 1 + "");
//                    MyHttpUtils.postAsAync(App.BASE_URL.replace("v1", "v2") + "login", params, new MyJsonCallbalk() {
//                        @Override
//                        public void onError(Exception e, int code) {
//                            AppLog.e("okHttpUtil.getAsync failed! request = " + e);
//                        }
//                        @Override
//                        public void onResponse(RequestCallVo requestCallVo) {
//                            AppLog.e("okHttpUtil.getAsync suczcess! response = " + requestCallVo.toString());
//                            if (requestCallVo.getResult().equals("true")) {
//                                App.savePreference("token", requestCallVo.getUserToken());
//                                postAsAync(url,map,callbalk);
//                            }else {
//                                if (requestCallVo.getInfo().contains("密码错误")){
//                                    callbalk.onError(new Exception(), 407);
//                                }
//                            }
//                        }
//                    });
//                } else if (requestCallVo.getInfo().equals("accessDenied")) {
//                    callbalk.onError(new Exception(), 408);
//                }
//                else {
                    callbalk.onResponse(requestCallVo);
//                }
            }

            @Override
            public void onAfter() {
                callbalk.onAfter();
            }
        });
    }

    public static void postJsonAsAync(final String url, final Object jsonObj, final MyJsonCallbalk callbalk) {
//        MyHttpUtils.logRequest(url, map);//打印请求日志
        //防止value是null,请求异常
//        if (map != null && map.size() > 0) {
//            Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
//            while (iterator.hasNext()) {
//                Map.Entry<String, String> entry = iterator.next();
//                String value = entry.getValue();
//                if (value == null) {
//                    entry.setValue("");
//                }
//            }
//        }
//        PostFormBuilder builder = OkHttpUtils.post().url(url).params(map);\
//        PostFormBuilder builder = OkHttpUtils
//                .postString()
//                .url(url)
//                .content(JSON.toJSON(jsonObj).toString())
//                .mediaType(MediaType.parse("application/json; charset=utf-8"))
//                .build()
//                .execute(new MyStringCallback());
        AppLog.e("请求：" + url + " params: " + JSON.toJSON(jsonObj).toString());
         PostStringBuilder builder = OkHttpUtils.postString().url(url).content(JSON.toJSON(jsonObj).toString()).mediaType(MediaType.parse("application/json; charset=utf-8"));


//        builder.addHeader("userToken", App.getStringUserPreference("token"));
        RequestCall call = builder.build();

        call.connTimeOut(60 * 1000);
        call.readTimeOut(60 * 1000);
        call.writeTimeOut(60 * 1000);
//        try {
//            Response response = call.execute();
//            if (response != null){
//                String body = response.body().string();
//                if (response.code() == 200){
//                    RequestCallVo requestCallVo = JSON.parseObject(body,RequestCallVo.class);
//                    callbalk.onResponse(requestCallVo);
//                } else if (response.code() == 407){
//                    callbalk.onError(new Exception(), 407);
//                }else {
//                    callbalk.onError(new Exception(), 0);
//                }
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        call.execute(new StringCallback() {
            @Override
            public void onBefore(Request request) {
                callbalk.onBefore();

            }

            @Override
            public void onError(Call call, Exception e) {
                e.printStackTrace();
                callbalk.onError(e, 0);
            }

            @Override
            public void onResponse(String response) {
                AppLog.e("返回:" + response);
                RequestCallVo requestCallVo = JSON.parseObject(response, RequestCallVo.class);
//                if (requestCallVo.getInfo().equals("loginDenied")) {
//                    /**
//                     * 重新请求登录
//                     */
//                    HashMap<String, String> params = new HashMap<>();
//                    params.put("userName", App.getStringUserPreference("userNameString"));
//                    params.put("password", App.getStringUserPreference("passwordString"));
//                    params.put("loginType", 1 + "");
//                    MyHttpUtils.postAsAync(App.BASE_URL.replace("v1", "v2") + "login", params, new MyJsonCallbalk() {
//                        @Override
//                        public void onError(Exception e, int code) {
//                            AppLog.e("okHttpUtil.getAsync failed! request = " + e);
//                        }
//                        @Override
//                        public void onResponse(RequestCallVo requestCallVo) {
//                            AppLog.e("okHttpUtil.getAsync suczcess! response = " + requestCallVo.toString());
//                            if (requestCallVo.getResult().equals("true")) {
//                                App.savePreference("token", requestCallVo.getUserToken());
//                                postAsAync(url,map,callbalk);
//                            }else {
//                                if (requestCallVo.getInfo().contains("密码错误")){
//                                    callbalk.onError(new Exception(), 407);
//                                }
//                            }
//                        }
//                    });
//                } else if (requestCallVo.getInfo().equals("accessDenied")) {
//                    callbalk.onError(new Exception(), 408);
//                }
//                else {
                callbalk.onResponse(requestCallVo);
//                }
            }

            @Override
            public void onAfter() {
                callbalk.onAfter();
            }
        });
    }


    public static void postMultiFilesAsAync(final String url, final HashMap<String, String> map, File[] files, String[] fileNames,final MyJsonCallbalk callbalk) {
        MyHttpUtils.logRequest(url, map);//打印请求日志
        //防止value是null,请求异常
        if (map != null && map.size() > 0) {
            Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                String value = entry.getValue();
                if (value == null) {
                    entry.setValue("");
                }
            }
        }
        PostFormBuilder builder = OkHttpUtils.post().url(url).params(map);
        if(files != null && files.length > 0){
            for(int i = 0; i < files.length; i++){
                builder.addFile(fileNames[i], files[i].getName(), files[i]);
            }
        }

//        builder.addHeader("userToken", App.getStringUserPreference("token"));
        RequestCall call = builder.build();
        call.connTimeOut(60 * 1000);
        call.readTimeOut(60 * 1000);
        call.writeTimeOut(60 * 1000);
        call.execute(new StringCallback() {
            @Override
            public void onBefore(Request request) {
                callbalk.onBefore();

            }

            @Override
            public void onError(Call call, Exception e) {
                e.printStackTrace();
                callbalk.onError(e, 0);
            }

            @Override
            public void onResponse(String response) {
                AppLog.e("返回:" + response);
                RequestCallVo requestCallVo = JSON.parseObject(response, RequestCallVo.class);
//                if (requestCallVo.getInfo().equals("loginDenied")) {
//                    /**
//                     * 重新请求登录
//                     */
//                    HashMap<String, String> params = new HashMap<>();
//                    params.put("userName", App.getStringUserPreference("userNameString"));
//                    params.put("password", App.getStringUserPreference("passwordString"));
//                    params.put("loginType", 1 + "");
//                    MyHttpUtils.postAsAync(App.BASE_URL.replace("v1", "v2") + "login", params, new MyJsonCallbalk() {
//                        @Override
//                        public void onError(Exception e, int code) {
//                            AppLog.e("okHttpUtil.getAsync failed! request = " + e);
//                        }
//                        @Override
//                        public void onResponse(RequestCallVo requestCallVo) {
//                            AppLog.e("okHttpUtil.getAsync suczcess! response = " + requestCallVo.toString());
//                            if (requestCallVo.getResult().equals("true")) {
//                                App.savePreference("token", requestCallVo.getUserToken());
//                                postAsAync(url,map,callbalk);
//                            }else {
//                                if (requestCallVo.getInfo().contains("密码错误")){
//                                    callbalk.onError(new Exception(), 407);
//                                }
//                            }
//                        }
//                    });
//                } else if (requestCallVo.getInfo().equals("accessDenied")) {
//                    callbalk.onError(new Exception(), 408);
//                } else {
                    callbalk.onResponse(requestCallVo);
//                }
            }

            @Override
            public void onAfter() {
                callbalk.onAfter();
            }
        });
    }

}
