package net.blowhorn.campusfeed.Fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import net.blowhorn.campusfeed.Activities.ChannelPostsActivity;
import net.blowhorn.campusfeed.Activities.LoginActivity;
import net.blowhorn.campusfeed.Adapters.ChannelListAdapter;
import net.blowhorn.campusfeed.AsyncTasks.HTTPGetAsyncTask;
import net.blowhorn.campusfeed.DTO.ChannelDTO;
import net.blowhorn.campusfeed.DTO.PostDTO;
import net.blowhorn.campusfeed.Interfaces.OnHTTPCompleteListener;
import net.blowhorn.campusfeed.Utils.AllChannelLists;
import net.blowhorn.campusfeed.R;
import net.blowhorn.campusfeed.Utils.Constants;
import net.blowhorn.campusfeed.Utils.CurrentPostsList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chinmay on 17/6/15.
 */
public class FollowedChannelsFragment extends ListFragment {


    private int offset = 0;
    private ChannelListAdapter followedChannelsAdapter;
    private String TAG = "FollowedFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        followedChannelsAdapter = new ChannelListAdapter(getActivity(),R.layout.list_item_channel,
                R.id.listItem_tv_channelName, AllChannelLists.getInstance().getFollowedChannelList());
        setListAdapter(followedChannelsAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fragment_followedchannels, container, false);
        Bundle args = getArguments();
        if(offset==0) {
            //createview will get called everytime tab appears
            //so the condition will ensure that data is fetched only once
            fetchFollowedChannelsData();
        }
        Button moreBtn = (Button) rootView.findViewById(R.id.followed_btn_more);
        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchFollowedChannelsData();
            }
        });
        return rootView;
    }

    private void fetchFollowedChannelsData() {
        HTTPGetAsyncTask httpGetAsyncTaskFollowedChannels = new HTTPGetAsyncTask(getActivity(),true);
        httpGetAsyncTaskFollowedChannels.setHTTPCompleteListener(new OnHTTPCompleteListener() {
            @Override
            public void onHTTPDataReceived(String result, String url) {
                handleFollowedChannelsData(result);
            }
        });
        String userId = getActivity().getIntent().getExtras().getString(Constants.Keys.USER_ID);
        httpGetAsyncTaskFollowedChannels.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                Constants.URL_GET_FOLLOWED_CHANNELS(userId, String.valueOf(Constants.channels_fetch_limit),
                        String.valueOf(offset)));
        offset = offset + Constants.channels_fetch_limit;
    }

    private void handleFollowedChannelsData(String result){
        Log.e(TAG, result);
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray(Constants.Keys.USER_CHANNELS);
            for(int i=0; i< jsonArray.length();i++){
                ChannelDTO channelDTO = new ChannelDTO();
                JSONObject jChannel = jsonArray.getJSONObject(i);
                channelDTO.setChannelID(jChannel.getString(Constants.Keys.CHANNEL_ID));
                channelDTO.setChannelName(jChannel.getString(Constants.Keys.CHANNEL_NAME));
                channelDTO.setChannelImgUrl(jChannel.getString(Constants.Keys.CHANNEL_IMAGE_URL));
                channelDTO.setNumFollowers(Integer.valueOf(jChannel.getString(Constants.Keys.CHANNEL_NUM_FOLLOWERS)).intValue());
                AllChannelLists.getInstance().getFollowedChannelList().add(channelDTO);
            }
            followedChannelsAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getActivity(), ChannelPostsActivity.class);
        intent.putExtra(Constants.Keys.CHANNEL_ID,AllChannelLists.getInstance().getFollowedChannelList().get(position).getChannelID());
        intent.putExtra(Constants.Keys.USER_ID,getActivity().getIntent().getExtras().getString(Constants.Keys.USER_ID));
        intent.putExtra("from","FollowedChannelsFragment");
        startActivity(intent);
    }
}
