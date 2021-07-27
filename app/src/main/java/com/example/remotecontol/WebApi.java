package com.example.remotecontol;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.protocol.HTTP;

public class WebApi {
    private static final String BASE_URL = "http://192.168.0.58:8080/api";
    private static final WebResponseHandler responseHandler = new WebResponseHandler();


    public static void onJoystickMove(Context context, float x, float y) {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            JSONObject postObj = new JSONObject();
            client.removeAllHeaders();
            client.addHeader(HTTP.CONTENT_TYPE, "application/json");
            int abs = (int) x;
            int ord = (int) y;
            postObj.put("abs", String.valueOf(abs));
            postObj.put("ord", String.valueOf(ord));
            //Log.i(String.valueOf(Log.INFO), "Joystick incremented to {" + x + "," + y + "}");
            StringEntity entity = new StringEntity(postObj.toString());
            client.post(context, getAbsoluteUrl("mousemove"), entity, "application/json", responseHandler);
        } catch (Exception e) {
            //Log.w(String.valueOf(Log.INFO), "Caught exception :" + e.getMessage());
        }
    }

    public static void onClickJoyStick(Context context, boolean singleClick) {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            JsonHttpResponseHandler responseHandler = new JsonHttpResponseHandler();
            JSONObject postObj = new JSONObject();
            client.removeAllHeaders();
            client.addHeader(HTTP.CONTENT_TYPE, "application/json");
            postObj.put("singleClick", String.valueOf(singleClick));
            //Log.i(String.valueOf(Log.INFO), "Clicked once :" + singleClick);
            StringEntity entity = new StringEntity(postObj.toString());
            client.post(context, getAbsoluteUrl("mouseclick"), entity, "application/json", responseHandler);
        } catch (Exception e) {
            //Log.w(String.valueOf(Log.INFO), "Caught exception :" + e.getMessage());
        }
    }

    public static void onKeyClick(Context context, int key) {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            JsonHttpResponseHandler responseHandler = new JsonHttpResponseHandler();
            JSONObject postObj = new JSONObject();
            client.removeAllHeaders();
            client.addHeader(HTTP.CONTENT_TYPE, "application/json");
            postObj.put("keyPressed", String.valueOf(key));
            //Log.i(String.valueOf(Log.INFO), "Pressed key :" + key);
            StringEntity entity = new StringEntity(postObj.toString());
            client.post(context, getAbsoluteUrl("keyboard"), entity, "application/json", responseHandler);
        } catch (Exception e) {
            //Log.w(String.valueOf(Log.INFO), "Caught exception :" + e.getMessage());
        }
    }

    private static String getAbsoluteUrl(String url) {
        return BASE_URL + "/" + url;
    }

    private static class WebResponseHandler extends AsyncHttpResponseHandler {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        }
    }
}
