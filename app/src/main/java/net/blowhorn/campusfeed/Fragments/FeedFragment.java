package net.blowhorn.campusfeed.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.blowhorn.campusfeed.AsyncTasks.HTTPGetAsyncTask;
import net.blowhorn.campusfeed.Interfaces.OnHTTPCompleteListener;
import net.blowhorn.campusfeed.R;
import net.blowhorn.campusfeed.Utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chinmay on 17/6/15.
 */
public class FeedFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);
        Bundle args = getArguments();
        if(!Constants.feedDataFetched) {
            fetchFeedData();
        }
        return rootView;
    }

    private void fetchFeedData(){
        JSONObject jsonObjectFeed = new JSONObject();
        String userId = getActivity().getIntent().getExtras().getString(Constants.Keys.USER_ID);
        try {
            jsonObjectFeed.put(Constants.Keys.USER_ID,userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HTTPGetAsyncTask httpGetAsyncTaskFeed = new HTTPGetAsyncTask(getActivity(),true);
        httpGetAsyncTaskFeed.setHTTPCompleteListener(new OnHTTPCompleteListener() {
            @Override
            public void onHTTPDataReceived(String result, String url) {
                //handleFeedData()
                Constants.feedDataFetched = true;
            }
        });
        httpGetAsyncTaskFeed.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                Constants.URL_GET_FEED, jsonObjectFeed.toString());
    }
}
