package net.blowhorn.campusfeed.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.blowhorn.campusfeed.R;

/**
 * Created by chinmay on 11/7/15.
 */
public class MiscFormsActivity extends Activity{
    EditText etChannelName;
    EditText etChannelDesc;
    Button btnCreateChannel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String purpose = getIntent().getExtras().getString("Purpose");
        etChannelName = (EditText) findViewById(R.id.misc_et_channelName);
        etChannelDesc = (EditText) findViewById(R.id.misc_et_channelDesc);
        btnCreateChannel = (Button) findViewById(R.id.misc_btn_create);
        btnCreateChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
