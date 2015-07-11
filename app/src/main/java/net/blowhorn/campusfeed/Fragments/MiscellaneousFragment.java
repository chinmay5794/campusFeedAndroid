package net.blowhorn.campusfeed.Fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import net.blowhorn.campusfeed.Activities.MiscFormsActivity;
import net.blowhorn.campusfeed.AsyncTasks.HTTPGetAsyncTask;
import net.blowhorn.campusfeed.Interfaces.OnHTTPCompleteListener;
import net.blowhorn.campusfeed.R;
import net.blowhorn.campusfeed.Utils.Constants;

import org.json.JSONObject;

/**
 * Created by chinmay on 17/6/15.
 */
public class MiscellaneousFragment extends Fragment {

    private String TAG = "MiscellaneousFragment";
    private String userId;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fragment_settings, container, false);
        userId = getActivity().getIntent().getExtras().getString(Constants.Keys.USER_ID);
        Bundle args = getArguments();
        Button createChannel = (Button) rootView.findViewById(R.id.misc_btn_createChannel);
        createChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MiscFormsActivity.class);
                intent.putExtra("Purpose","CreateChannel");
                intent.putExtra(Constants.Keys.USER_ID,userId);
                startActivity(intent);
            }
        });
        if(!Constants.miscellaneousDataFetched){
            fetchMiscellaneousData();
        }
        return rootView;
    }

    private void fetchMiscellaneousData() {
        JSONObject jsonObjectMiscellaneous = new JSONObject();
        HTTPGetAsyncTask httpGetAsyncTaskMiscellaneous = new HTTPGetAsyncTask(getActivity(),true);
        httpGetAsyncTaskMiscellaneous.setHTTPCompleteListener(new OnHTTPCompleteListener() {
            @Override
            public void onHTTPDataReceived(String result, String url) {
                //handleMiscellaneousData()
                Constants.miscellaneousDataFetched = true;
            }
        });
        //httpGetAsyncTaskMiscellaneous.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
        //        Constants.URL_SHOW_PENDING_REQUESTS(userId), jsonObjectMiscellaneous.toString());

    }

}
