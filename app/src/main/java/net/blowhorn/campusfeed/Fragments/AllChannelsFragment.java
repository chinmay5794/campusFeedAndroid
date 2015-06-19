package net.blowhorn.campusfeed.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class AllChannelsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fragment_allchannels, container, false);
        Bundle args = getArguments();
        if(!Constants.allChannelsDataFetched) {
            fetchAllChannelsData();
        }

        return rootView;
    }

    private void fetchAllChannelsData() {
        JSONObject jsonObjectAllChannels = new JSONObject();
        HTTPGetAsyncTask httpGetAsyncTaskAllChannels = new HTTPGetAsyncTask(getActivity(),true);
        httpGetAsyncTaskAllChannels.setHTTPCompleteListener(new OnHTTPCompleteListener() {
            @Override
            public void onHTTPDataReceived(String result, String url) {
                handleAllChannelsData(result);
                Constants.allChannelsDataFetched = true;
            }
        });
        httpGetAsyncTaskAllChannels.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                Constants.URL_GET_ALL_CHANNELS, jsonObjectAllChannels.toString());
    }

    private void handleAllChannelsData(String result){
        try {
            JSONObject jsonObject = new JSONObject(result);
            String status = jsonObject.getString(Constants.Keys.STATUS);
            if(status.equals(Constants.Status.SUCCESS)){
                JSONArray jsonArray = jsonObject.getJSONArray(Constants.Keys.ALL_CHANNELS);
                for(int i=0; i< jsonArray.length();i++){
                    ChannelDTO channelDTO = new ChannelDTO();
                    JSONObject jChannel = jsonArray.getJSONObject(i);
                    channelDTO.setChannelID(jChannel.getString(Constants.Keys.CHANNEL_ID));
                    channelDTO.setChannelName(jChannel.getString(Constants.Keys.CHANNEL_NAME));
                    channelDTO.setChannelImgUrl(jChannel.getString(Constants.Keys.CHANNEL_IMAGE_URL));
                    channelDTO.setNumFollowers(Integer.valueOf(jChannel.getString(Constants.Keys.CHANNEL_NUM_FOLLOWERS)).intValue());
                    AllChannels.getInstance().allChannelList.add(i,channelDTO);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
