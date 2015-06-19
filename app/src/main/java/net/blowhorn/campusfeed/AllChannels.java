package net.blowhorn.campusfeed;

import java.util.ArrayList;

/**
 * Created by chinmay on 19/6/15.
 */
public class AllChannels {

    private static AllChannels instance = new AllChannels();

    public static ArrayList<ChannelDTO> allChannelList = new ArrayList<ChannelDTO>();
    public static ArrayList<ChannelDTO> myChannelList = new ArrayList<ChannelDTO>();

    private AllChannels(){

    }

    public static AllChannels getInstance(){
        return instance;
    }
}
