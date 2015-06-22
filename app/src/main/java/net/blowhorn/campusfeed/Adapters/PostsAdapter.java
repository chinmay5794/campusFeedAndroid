package net.blowhorn.campusfeed.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.blowhorn.campusfeed.DTO.PostDTO;
import net.blowhorn.campusfeed.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chinmay on 22/6/15.
 */
public class PostsAdapter extends ArrayAdapter<PostDTO> {
    private final Context context;
    public PostsAdapter(Context context, int resource, int textViewResourceId, ArrayList<PostDTO> postsList) {
        super(context, resource, textViewResourceId, postsList);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        super.getView(position, convertView, parent);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item_post, parent, false);

        //setup data in the row

        return rowView;
    }
}
