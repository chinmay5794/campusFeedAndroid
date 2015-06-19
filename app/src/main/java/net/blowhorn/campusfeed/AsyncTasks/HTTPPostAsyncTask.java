package net.blowhorn.campusfeed.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import net.blowhorn.campusfeed.Utils.Constants;
import net.blowhorn.campusfeed.Interfaces.OnHTTPCompleteListener;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
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

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(params[0]);

            StringEntity stringEntity = new StringEntity(params[1]);

            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("token",Constants.mAuthToken);
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(stringEntity);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            if(httpResponse.getStatusLine().getStatusCode() == 200) {
                Log.i(TAG + "URL: " + params[0], "Successfully sent POST data");
            }
            else {
                Log.i(TAG + "URL: " + params[0],"Improper server response");
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
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.i(TAG + "URL: " + params[0], "Unsupported Encoding Exception");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            Log.i(TAG + "URL: " + params[0], "Client Protocol Exception");
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG + "URL: " + params[0], "IOException");
        }


        return null;
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        listener.onHTTPDataReceived(response, url);
    }

    public void setHTTPCompleteListener(OnHTTPCompleteListener listener){
        this.listener = listener;
    }

}
