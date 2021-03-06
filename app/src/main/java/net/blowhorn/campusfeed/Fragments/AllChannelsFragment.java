package net.blowhorn.campusfeed.Fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import net.blowhorn.campusfeed.Activities.ChannelDescriptionActivity;
import net.blowhorn.campusfeed.Activities.ChannelPostsActivity;
import net.blowhorn.campusfeed.Adapters.ChannelListAdapter;
import net.blowhorn.campusfeed.AsyncTasks.HTTPGetAsyncTask;
import net.blowhorn.campusfeed.DTO.ChannelDTO;
import net.blowhorn.campusfeed.Interfaces.OnHTTPCompleteListener;
import net.blowhorn.campusfeed.Utils.AllChannelLists;
import net.blowhorn.campusfeed.R;
import net.blowhorn.campusfeed.Utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chinmay on 17/6/15.
 */
public class AllChannelsFragment extends ListFragment {

    private int offset = 0;
    private ChannelListAdapter allChannelsAdapter;
    private String TAG = "AllChannelsFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allChannelsAdapter = new ChannelListAdapter(getActivity(),R.layout.list_item_channel,
                R.id.listItem_tv_channelName, AllChannelLists.getInstance().getAllChannelList());
        setListAdapter(allChannelsAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fragment_allchannels, container, false);
        Bundle args = getArguments();
        Button moreButton = (Button) rootView.findViewById(R.id.allChannels_btn_more);
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchAllChannelsData();
            }
        });

        fetchAllChannelsData();

        return rootView;
    }

    private void fetchAllChannelsData() {
        HTTPGetAsyncTask httpGetAsyncTaskAllChannels = new HTTPGetAsyncTask(getActivity(),true);
        httpGetAsyncTaskAllChannels.setHTTPCompleteListener(new OnHTTPCompleteListener() {
            @Override
            public void onHTTPDataReceived(String result, String url) {
                handleAllChannelsData(result);
                Constants.allChannelsDataFetched = true;
            }
        });
        httpGetAsyncTaskAllChannels.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                Constants.URL_GET_ALL_CHANNELS);
        offset = offset + Constants.channels_fetch_limit;
    }

    private void handleAllChannelsData(String result){
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray(Constants.Keys.ALL_CHANNELS);
            for(int i=0; i< jsonArray.length();i++){
                ChannelDTO channelDTO = new ChannelDTO();
                JSONObject jChannel = jsonArray.getJSONObject(i);
                channelDTO.setChannelID(jChannel.getString(Constants.Keys.CHANNEL_ID));
                channelDTO.setChannelName(jChannel.getString(Constants.Keys.CHANNEL_NAME));
                channelDTO.setChannelImgUrl(jChannel.getString(Constants.Keys.CHANNEL_IMAGE_URL));
                channelDTO.setNumFollowers(Integer.valueOf(jChannel.getString(Constants.Keys.CHANNEL_NUM_FOLLOWERS)).intValue());
                AllChannelLists.getInstance().getAllChannelList().add(channelDTO);
            }
            allChannelsAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getActivity(),ChannelDescriptionActivity.class);
        intent.putExtra(Constants.Keys.CHANNEL_ID, AllChannelLists.getInstance().getAllChannelList().get(position).getChannelID());
        intent.putExtra(Constants.Keys.USER_ID,getActivity().getIntent().getExtras().getString(Constants.Keys.USER_ID));
        startActivity(intent);
    }

}
