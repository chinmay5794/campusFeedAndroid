package net.blowhorn.campusfeed.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.blowhorn.campusfeed.Utils.Constants;
import net.blowhorn.campusfeed.AsyncTasks.HTTPPostAsyncTask;
import net.blowhorn.campusfeed.Interfaces.OnHTTPCompleteListener;
import net.blowhorn.campusfeed.R;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends Activity implements View.OnClickListener, OnHTTPCompleteListener {

    TextView etUserId;
    TextView etPassword;
    Button btnLogin;
    SharedPreferences mPrefs;
    private String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkAndRestore();
        
        etUserId = (TextView) findViewById(R.id.login_et_userid);
        etPassword = (TextView) findViewById(R.id.login_et_password);
        btnLogin = (Button) findViewById(R.id.login_btn_login);

        btnLogin.setOnClickListener(this);
    }

    

    private boolean isFormValid(){
        return (etUserId.getText().toString().trim().length()!=0 &&
                etPassword.getText().toString().trim().length()!=0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    
    private void checkAndRestore(){
        mPrefs = getSharedPreferences(Constants.SharedPrefs.USER_CREDENTIALS,Context.MODE_PRIVATE);
        boolean loggedIn = mPrefs.getBoolean(Constants.Keys.IS_LOGGED_IN,false);
        if(loggedIn){
            String userId = mPrefs.getString(Constants.Keys.USER_ID,"");
            loginToHome(userId);
        }
    }

    private String createLoginJsonString(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.Keys.USER_ID,etUserId.getText().toString());
            jsonObject.put(Constants.Keys.PASSWORD,etPassword.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(TAG,jsonObject.toString());
        return jsonObject.toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn_login:
                if(isFormValid()) {
                    String loginJson = createLoginJsonString();
                    HTTPPostAsyncTask httpPostAsyncTask = new HTTPPostAsyncTask(this,true);
                    httpPostAsyncTask.setHTTPCompleteListener(this);
                    httpPostAsyncTask.execute(Constants.URL_LOGIN, loginJson);
                }
                //loginToHome("14037");
                break;
        }
    }

    @Override
    public void onHTTPDataReceived(String response, String url) {
        JSONObject jResponse = null;
        if(response != null) {
            try {
                jResponse = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.i(TAG + "URL: " + url, "JSON Exception");
            }
            switch (url) {
                case Constants.URL_LOGIN:
                    handleLoginResponse(jResponse, url);
                    break;
            }
        }
        else {
            Log.i(TAG + "URL: " + url, "Null response");
        }

    }

    private void saveUserInSharedPrefs(JSONObject jResponse){
        SharedPreferences mPrefs = getSharedPreferences(Constants.SharedPrefs.USER_CREDENTIALS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPrefs.edit();
        try {
            editor.putString(Constants.Keys.FIRST_NAME, jResponse.getString(Constants.Keys.FIRST_NAME));
            editor.putString(Constants.Keys.LAST_NAME, jResponse.getString(Constants.Keys.LAST_NAME));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        editor.putString(Constants.Keys.USER_ID, etUserId.getText().toString());
        editor.putBoolean(Constants.Keys.IS_LOGGED_IN, true);
        editor.commit();
    }

    private void loginToHome(String userId){
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constants.Keys.USER_ID, userId);
        startActivity(intent);
    }

    private void handleLoginResponse(JSONObject jResponse, String url){
        String status = null;
        String mAuthToken = null;
        try {
            //status = jResponse.getString(Constants.Keys.STATUS);
            mAuthToken = jResponse.getString(Constants.Keys.AUTH_TOKEN);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i(TAG + "URL: " + url, "JSON Exception, probably no status key");
        }
        if(true) {
            saveUserInSharedPrefs(jResponse);
            Constants.mAuthToken = mAuthToken; //saving token, use in calls later
            Log.i(TAG,"Token: " + Constants.mAuthToken);
            String userId = etUserId.getText().toString();
            loginToHome(userId);
        }
        else {
            Log.i(TAG + "URL: " + url, "Status: " + status);
        }
    }

}
