package com.krito.io.oscar.model.operations;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.krito.io.oscar.model.data.Rate;
import com.krito.io.oscar.model.data.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mona Abdallh on 3/19/2018.
 */

public class UserOperation {

    private static final String baseUrl = "http://192.168.1.18:80/oscar/";
    static RegistrationCallback registrationCallback;
    static LoginCallback loginCallback;
    static RequestQueue requestQueue;


    public static void setRegistrationCallback(RegistrationCallback call){
        registrationCallback = call;
    }

    public static void setLoginCallback(LoginCallback call){
        loginCallback = call;
    }

    public static void setRequestQueue(RequestQueue queue){
        requestQueue = queue;
    }

    /**
     * create new user account
     * @param user complete user object
     */
    public static void register(final User user){
        String url = baseUrl + "register.php";

        StringRequest post = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    Log.e("api_response", response);
                    registrationCallback.onResponse(jsonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("api_error", error.toString());
                registrationCallback.onError(error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("name", user.getName());
                params.put("address", user.getAddress());
                params.put("phone", user.getPhoneNumber());
                params.put("email", user.getEmail());
                params.put("password", user.getPassword());
                return params;
            }
        };
       requestQueue.add(post);
    }

    /**
     * check if user exists
     * @param email email of user account
     * @param password password of user account
     */
    public static void login(final String email, final String password){
        String url = baseUrl + "login.php";

        StringRequest post = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    Log.e("api_response", response);
                    loginCallback.onResponse(jsonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loginCallback.onError(error);
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        requestQueue.add(post);
    }

    /**
     * change user account password
     * @param oldPass old account password
     * @param newPass old account password
     */
    public static void changePassword(String oldPass, String newPass){

    }

    /**
     * submit customer rate on a driver
     * @param rate number of starts
     */
    public static void rate(Rate rate){

    }

    public interface RegistrationCallback {
        void onResponse(JSONObject object);
        void onError(VolleyError error);
    }

    public interface LoginCallback{
        void onResponse(JSONObject object);
        void onError(VolleyError error);
    }
}
