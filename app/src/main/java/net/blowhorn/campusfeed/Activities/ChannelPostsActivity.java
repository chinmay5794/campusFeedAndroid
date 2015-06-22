package net.blowhorn.campusfeed.Activities;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import net.blowhorn.campusfeed.Adapters.PostsAdapter;
import net.blowhorn.campusfeed.AsyncTasks.HTTPGetAsyncTask;
import net.blowhorn.campusfeed.DTO.PostDTO;
import net.blowhorn.campusfeed.Interfaces.OnHTTPCompleteListener;
import net.blowhorn.campusfeed.R;
import net.blowhorn.campusfeed.Utils.AllChannelLists;
import net.blowhorn.campusfeed.Utils.Constants;
import net.blowhorn.campusfeed.Utils.CurrentPostsList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chinmay on 22/6/15.
 */
public class ChannelPostsActivity extends ListActivity {

    private int offset = 0;
    private PostsAdapter postsAdapter;
    private String TAG = "ChannelPosts";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postsAdapter = new PostsAdapter(this,R.layout.list_item_post,R.id.listItem_tv_postText,
                                    CurrentPostsList.getInstance().getCurrentPostsList());
        setListAdapter(postsAdapter);

        Button moreBtn = (Button) findViewById(R.id.channelPosts_btn_more);
        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchChannelPosts();
            }
        });

        Button descriptionBtn = (Button) findViewById(R.id.channelPosts_btn_description);
        descriptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ChannelDescriptionActivity.class);
                intent.putExtra(Constants.Keys.CHANNEL_ID, getIntent().getExtras().getString(Constants.Keys.CHANNEL_ID));
                intent.putExtra(Constants.Keys.USER_ID, getIntent().getExtras().getString(Constants.Keys.USER_ID));
                startActivity(intent);
            }
        });

        fetchChannelPosts();
    }

    private void fetchChannelPosts(){
        String channelId = getIntent().getExtras().getString(Constants.Keys.CHANNEL_ID);
        HTTPGetAsyncTask httpGetAsyncTaskPosts = new HTTPGetAsyncTask(this,true);
        httpGetAsyncTaskPosts.setHTTPCompleteListener(new OnHTTPCompleteListener() {
            @Override
            public void onHTTPDataReceived(String result, String url) {
                handlePostsReceivedData(result);
            }
        });
        httpGetAsyncTaskPosts.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                Constants.URL_GET_POSTS_OF_CHANNEL(channelId, String.valueOf(Constants.posts_fetch_limit),
                        String.valueOf(offset)));
        offset = offset + Constants.posts_fetch_limit;
    }

    private void handlePostsReceivedData(String result){
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray(Constants.Keys.CHANNEL_POSTS);
            int indexOfLastPost = CurrentPostsList.getInstance().getCurrentPostsList().size();
            for(int i=indexOfLastPost; i< jsonArray.length();i++){
                PostDTO postDTO = new PostDTO();
                JSONObject jPost = jsonArray.getJSONObject(i);
                postDTO.setPostId(jPost.getString(Constants.Keys.POST_ID));
                postDTO.setText(jPost.getString(Constants.Keys.POST_TEXT));
                postDTO.setPostTime(jPost.getString(Constants.Keys.POST_TIMESTAMP));
                postDTO.setPostImgUrl(jPost.getString(Constants.Keys.POST_IMAGE_URL));
                postDTO.setPendingBit(jPost.getString(Constants.Keys.POST_PENDING_BIT));
                postDTO.setUserName(jPost.getString(Constants.Keys.POST_AUTHOR_FULL_NAME));
                postDTO.setUserBranch(jPost.getString(Constants.Keys.POST_AUTHOR_BRANCH));
                postDTO.setUserImgUrl(jPost.getString(Constants.Keys.POST_AUTHOR_IMAGE_URL));
                CurrentPostsList.getInstance().getCurrentPostsList().add(postDTO);
            }
            postsAdapter.notifyDataSetChanged();
        }
        catch (JSONException e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        CurrentPostsList.getInstance().getCurrentPostsList().clear();
    }
}
