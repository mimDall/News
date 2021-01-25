package com.mimdal.news.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mimdal.news.Interfaces.GetDataListener;
import com.mimdal.news.Model.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetDataFromAPI {
    private static final String TAG = "GetDataFromAPI";

    Context context;

    public GetDataFromAPI(Context context) {
        this.context = context;
    }

    public void getData(GetDataListener getDataListener, String URL) {
        List<NewsItem> newsList = new ArrayList<>();

        Log.d(TAG, URL);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("articles");
//                    Log.d(TAG,""+jsonArray);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String title = jsonObject.getString("title");
                        String description = jsonObject.getString("description");
                        String url = jsonObject.getString("url");
                        String image = jsonObject.getString("image");
                        String publishedAt = jsonObject.getString("publishedAt");
                        String sourceName = jsonObject.getJSONObject("source").getString("name");

                        NewsItem newsItem = new NewsItem(title,
                                description,
                                url,
                                image,
                                publishedAt,
                                sourceName);

                        newsList.add(newsItem);

                    }

                    getDataListener.onSuccess(newsList);

                } catch (JSONException e) {

                    getDataListener.onError(e.getMessage().toString());
                }

            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                Log.d(TAG,"status code: "+error.networkResponse.statusCode);

                getDataListener.onError(error.getMessage());

            }
        }) { //no semicolon or coma
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }

        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(4000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                1.5F));

        VolleySingletone.getVolleySingletone(this.context).addToRequestQueue(jsonObjectRequest);

    }

}
