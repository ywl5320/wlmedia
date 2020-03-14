package com.ywl5320.wlmedia.sample.http;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by hlwky001 on 2020/3/9.
 */
public class HttpUtil {

    private static HttpUtil instance;
    private HttpResultListener resultListener;

    public static HttpUtil getInstance() {
        synchronized (HttpUtil.class)
        {
            if(instance == null)
            {
                instance = new HttpUtil();
            }
        }
        return instance;
    }


    private HttpURLConnection getHttpURLConnection(String uri, String referer, String method) throws IOException {

        URL url = new URL(uri);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
        httpConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        if(referer != null && "".equals(referer))
        {
            httpConn.setRequestProperty("Referer", referer);
        }
        httpConn.setRequestMethod(method);
        httpConn.setDoInput(true);
        return httpConn;
    }

    private byte[] getBytesFromStream(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] kb = new byte[1024];
        int len;
        while ((len = is.read(kb)) != -1) {
            baos.write(kb, 0, len);
        }
        byte[] bytes = baos.toByteArray();
        baos.close();
        is.close();
        return bytes;
    }



    public void doHttpGet(final String uri, final String code, final String referer, HttpResultListener resultListener) {
        this.resultListener = resultListener;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    HttpURLConnection httpsConn = getHttpURLConnection(uri, referer, "GET");
                    byte[] result =  getBytesFromStream(httpsConn.getInputStream());
                    Message message = Message.obtain();
                    message.obj = new String(result, code);
                    message.what = 0;
                    handler.sendMessage(message);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Message message = Message.obtain();
                    message.what = -1;
                    handler.sendMessage(message);
                }

            }
        }).start();

    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0)
            {
                String result = (String) msg.obj;
                if(resultListener != null)
                {
                    resultListener.onSuccess(result);
                }
            }
            else if(msg.what == -1)
            {
                if(resultListener != null)
                {
                    resultListener.onFail("加载数据失败，请稍后再试");
                }
            }
        }
    };

    public interface HttpResultListener
    {
        void onSuccess(String result);

        void onFail(String msg);
    }
}
