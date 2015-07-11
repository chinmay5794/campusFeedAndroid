package net.blowhorn.campusfeed.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import net.blowhorn.campusfeed.Utils.Constants;
import net.blowhorn.campusfeed.Interfaces.OnHTTPCompleteListener;
import net.blowhorn.campusfeed.Utils.NetworkUtil;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by chinmay on 18/6/15.
 */
//<1,2,3> => inputType of <doInBackground,setProgressUpdate,onPostExecute>

//Conventions : - params[0] = URL ,params[1] = JSON String

public class HTTPPostAsyncTask extends AsyncTask<String,String,String> {

    private String TAG = "HTTPPost";
    private Context context;
    JSONObject jResponse;
    private String url;
    private boolean setLoader;
    private OnHTTPCompleteListener listener;

    public HTTPPostAsyncTask(Context context, boolean setLoader){
        this.context = context;
        this.setLoader = setLoader;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(setLoader){
            //show loader
        }
    }

    @Override
    protected String doInBackground(String... params) {

        url = params[0];

        if(NetworkUtil.isNetworkAvailable(context)) {
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(params[0]);

                StringEntity stringEntity = new StringEntity(params[1]);

                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("token", Constants.mAuthToken);
                httpPost.setHeader("Content-type", "application/json");
                stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                httpPost.setEntity(stringEntity);

                HttpResponse httpResponse = httpClient.execute(httpPost);
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    Log.i(TAG + "URL: " + params[0], "Successfully sent POST data");
                } else {
                    Log.i(TAG + "URL: " + params[0], "HTTP Status code is " +
                            String.valueOf(httpResponse.getStatusLine().getStatusCode()));
                }

                //-- Retrieve the response from the server
                String line = "";
                StringBuilder result = new StringBuilder();
                InputStream is = httpResponse.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                return result.toString();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Log.e(TAG + "URL: " + params[0], "Unsupported Encoding Exception");
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                Log.e(TAG + "URL: " + params[0], "Client Protocol Exception");
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG + "URL: " + params[0], "IOException");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            Log.w(TAG, "No internet connection");
        }

        return null;
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        try{
            if(!response.isEmpty()) {
                Log.e(TAG + "response:", response);
                listener.onHTTPDataReceived(response, url);
            }
            else{
                Log.w(TAG, "Null response");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(!NetworkUtil.isNetworkAvailable(context)){
            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void setHTTPCompleteListener(OnHTTPCompleteListener listener){
        this.listener = listener;
    }

}
