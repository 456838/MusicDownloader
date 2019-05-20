package com.salton123.utils;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * User: newSalton@outlook.com
 * Date: 2019/2/27 15:45
 * ModifyTime: 15:45
 * Description:
 */
public class RequestUtil {
    private static ExecutorService sExecutorService = Executors.newCachedThreadPool();

    public static <T> void get(
            String requestUrl,
            Class<T> classOfT,
            HttpResponseCallback<T> httpResponseCallback) {
        sExecutorService.submit(() -> {
            try {
                URL url = new URL(requestUrl.trim());
                //打开连接
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (200 == urlConnection.getResponseCode()) {
                    //得到输入流
                    InputStream is = urlConnection.getInputStream();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while (-1 != (len = is.read(buffer))) {
                        baos.write(buffer, 0, len);
                        baos.flush();
                    }
                    try {
                        T t = new Gson().fromJson(baos.toString("utf-8"), classOfT);
                        httpResponseCallback.onSuccess(t);
                    } catch (Exception e) {
                        httpResponseCallback.onFailure(e.toString());
                    }
                } else {
                    httpResponseCallback.onFailure(urlConnection.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
                httpResponseCallback.onFailure(e.getMessage());
            }
        });
    }

    public interface HttpResponseCallback<T> {
        void onSuccess(T responseData);

        void onFailure(String failedReason);
    }
}
