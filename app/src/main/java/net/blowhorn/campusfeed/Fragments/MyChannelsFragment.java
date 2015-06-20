package net.blowhorn.campusfeed.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.blowhorn.campusfeed.AsyncTasks.HTTPGetAsyncTask;
import net.blowhorn.campusfeed.ChannelDTO;
import net.blowhorn.campusfeed.Interfaces.OnHTTPCompleteListener;
import net.blowhorn.campusfeed.AllChannels;
import net.blowhorn.campusfeed.R;
import net.blowhorn.campusfeed.Utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chinmay on 17/6/15.
 */
public class MyChannelsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fragment_mychannels, container, false);
        Bundle args = getArguments();
        if(!Constants.myChannelsDataFetched) {
            fetchMyChannelsData();
        }
        return rootView;
    }

    private void fetchMyChannelsData() {
        JSONObject jsonObjectMyChannels = new JSONObject();
        HTTPGetAsyncTask httpGetAsyncTaskMyChannels = new HTTPGetAsyncTask(getActivity(),true);
        httpGetAsyncTaskMyChannels.setHTTPCompleteListener(new OnHTTPCompleteListener() {
            @Override
            public void onHTTPDataReceived(String result, String url) {
                handleMyChannelsData(result);
                Constants.myChannelsDataFetched = false;
            }
        });
        String userId = getActivity().getIntent().getExtras().getString(Constants.Keys.USER_ID);
        httpGetAsyncTaskMyChannels.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                Constants.URL_GET_MY_CHANNELS(userId,"-1","0"), jsonObjectMyChannels.toString());
        //-1 and 0, i.e limit and offset will have to be changed later according to data in DB
    }

    private void handleMyChannelsData(String result){
        Log.e("MyChannelsFragment",result);
        try {
            JSONObject jsonObject = new JSONObject(result);
            String status = jsonObject.getString(Constants.Keys.STATUS);
            if(status.equals(Constants.Status.SUCCESS)){
                JSONArray jsonArray = jsonObject.getJSONArray(Constants.Keys.USER_CHANNELS);
                for(int i=0; i< jsonArray.length();i++){
                    ChannelDTO channelDTO = new ChannelDTO();
                    JSONObject jChannel = jsonArray.getJSONObject(i);
                    channelDTO.setChannelID(jChannel.getString(Constants.Keys.CHANNEL_ID));
                    channelDTO.setChannelName(jChannel.getString(Constants.Keys.CHANNEL_NAME));
                    channelDTO.setChannelImgUrl(jChannel.getString(Constants.Keys.CHANNEL_IMAGE_URL));
                    channelDTO.setNumFollowers(Integer.valueOf(jChannel.getString(Constants.Keys.CHANNEL_NUM_FOLLOWERS)).intValue());
                    AllChannels.getInstance().myChannelList.add(i,channelDTO);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
