package com.tao.demo.rxDemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.tao.demo.R;
import com.tao.demo.net.RetrofitManager;
import com.tao.demo.net.RxSchedulers;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class RxDemoActivity extends AppCompatActivity {
    ImageView imageView;
    HttpLoggingInterceptor interceptor;
    OkHttpClient client;
    final int CODE_IMAGE = 2;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CODE_IMAGE:
                    imageView.setImageBitmap((Bitmap) msg.obj);
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_demo);
        imageView = findViewById(R.id.iv_image);
        interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        final String imgPath = "http://eplusplatform.oss-cn-beijing.aliyuncs.com/LaunchScreen/b28b46811d7cbab8eb5f24c727342387.jpg";
        //rxjava 获取图片
        Observable.just(imgPath)
                .map(new Function<String, Bitmap>() {
                    @Override
                    public Bitmap apply(String s) throws Exception {
                        Bitmap bitmap = getBitmap(new HttpBitmap(), s);
                        return bitmap;
                    }

                })
                //线程切换
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) throws Exception {
//                        imageView.setImageBitmap(bitmap);
                    }
                });
        doMockLogin();
        doLogin();
        //动态代理
        //返回接口的实例对象
        final IBitmapFunc funcc =
                (IBitmapFunc) Proxy.newProxyInstance(IBitmapFunc.class.getClassLoader(),
                        new Class[]{IBitmapFunc.class},
                        new BitmapHandler(new OkHttpBitmap()));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Bitmap b = funcc.getBitmap(imgPath);
                    Message msg = mHandler.obtainMessage();
                    msg.what = CODE_IMAGE;
                    msg.obj = b;
                    mHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    //post提交
    private void doMockLogin() {
        RequestBody body = new FormBody.Builder().add("username", "18661772251")
                .add("password", "e10adc3949ba59abbe56e057f20f883e").build();
        String url = "http://vrf.api.eplusplatform.com/manager/app/login";
        Request request = new Request.Builder().url(url).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Log.e("tag===", "response" + s);
            }
        });
    }

    private void doLogin() {
        String username = "18661772251";
        String password = "e10adc3949ba59abbe56e057f20f883e";
        RetrofitManager.getApiService().login(username, password)
                .compose(RxSchedulers.<String>applySchedulers())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e("doLogin tag===", "response" + s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("doLogin tag===", "response" + throwable.getMessage());
                    }
                });
    }

    private Bitmap getBitmap(IBitmapFunc func, String url) throws Exception {
        return func.getBitmap(url);
    }

    interface IBitmapFunc {
        Bitmap getBitmap(String s) throws Exception;
    }

    //okhttp获取图片
    class OkHttpBitmap implements IBitmapFunc {

        @Override
        public Bitmap getBitmap(String s) throws Exception {

            Request request = new Request.Builder().url(s).get().build();
            Response response = client.newCall(request).execute();
            Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
//            response.close();
            return bitmap;
        }
    }

    //httpUrlConnection 获取图片
    class HttpBitmap implements IBitmapFunc {

        @Override
        public Bitmap getBitmap(String s) throws Exception {
            URL url = null;
            try {
                url = new URL(s);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream());
                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    //动态代理
    class BitmapHandler implements InvocationHandler {
        private Object mObject;

        public BitmapHandler(Object mObject) {
            this.mObject = mObject;
        }

        //调用被代理对象的方法
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("开始受理");
            Object object = method.invoke(mObject, args);
            System.out.println("受理完毕");
            return object;
        }
    }

    public static void main(String[] params) {
        int aaa = 1200_000;
        System.out.println(aaa);
        int index = 0;
        if (2 > 1) throw new AssertionError();
        index++;
        System.out.println("index=" + index);

    }
}
