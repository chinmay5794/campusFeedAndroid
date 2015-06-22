package net.blowhorn.campusfeed.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;

import net.blowhorn.campusfeed.R;
import net.blowhorn.campusfeed.Utils.AllChannelLists;
import net.blowhorn.campusfeed.Utils.Constants;

/**
 * Created by chinmay on 22/6/15.
 */
public class ChannelDescriptionActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button recPosts = (Button) findViewById(R.id.channelDesc_btn_recPosts);
        recPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),ChannelPostsActivity.class);
                intent.putExtra(Constants.Keys.CHANNEL_ID,getIntent().getExtras().getString(Constants.Keys.CHANNEL_ID));
                intent.putExtra(Constants.Keys.USER_ID,getIntent().getExtras().getString(Constants.Keys.USER_ID));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!AllChannelLists.getInstance().isDetailAvailable(getIntent().getExtras().getString(Constants.Keys.CHANNEL_ID)));
        {
            //fetch data from API
        }
        //fill UI
    }
}
