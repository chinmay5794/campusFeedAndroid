package net.blowhorn.campusfeed.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.blowhorn.campusfeed.DTO.ChannelDTO;
import net.blowhorn.campusfeed.R;

import java.util.ArrayList;

/**
 * Created by chinmay on 22/6/15.
 */
public class ChannelListAdapter extends ArrayAdapter<ChannelDTO> {
    private final Context context;
    private ArrayList<ChannelDTO> channelList;
    public ChannelListAdapter(Context context, int resource, int textViewResourceId, ArrayList<ChannelDTO> channelList) {
        super(context, resource, textViewResourceId, channelList);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        super.getView(position, convertView, parent);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item_channel, parent, false);

        //setup data in row
        /*TextView tvChannelName = (TextView) rowView.findViewById(R.id.listItem_tv_channelName);
        tvChannelName.setText(channelList.get(position).getChannelName());
        TextView tvChannelFollowCount = (TextView) rowView.findViewById(R.id.listItem_tv_followCount);
        tvChannelFollowCount.setText(channelList.get(position).getNumFollowers());
        TextView tvChannelViewCount = (TextView) rowView.findViewById(R.id.listItem_tv_viewsCount);
        tvChannelViewCount.setText(channelList.get(position).getNumViews());
        ImageView ivChannelImage = (ImageView) rowView.findViewById(R.id.listItem_iv_channelImage);
        Picasso.with(getContext()).load(channelList.get(position).getChannelImgUrl()).fit().centerCrop().into(ivChannelImage);
        */
        return rowView;
    }
}
