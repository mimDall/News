package com.mimdal.news.service;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

public class VolleySingletone {

    private static VolleySingletone volleySingletone;
    private RequestQueue requestQueue;


    private VolleySingletone(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized VolleySingletone getVolleySingletone(Context context) {

        if (volleySingletone == null) {
            volleySingletone = new VolleySingletone(context);
        }

        return volleySingletone;
    }

    public RequestQueue getRequestQueue(){
        return this.requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request){

        getRequestQueue().add(request);
    }

}
