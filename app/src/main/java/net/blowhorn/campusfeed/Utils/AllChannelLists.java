package net.blowhorn.campusfeed.Utils;

import net.blowhorn.campusfeed.DTO.ChannelDTO;

import java.util.ArrayList;

/**
 * Created by chinmay on 19/6/15.
 */
public class AllChannelLists {

    private static AllChannelLists instance = new AllChannelLists();

    private static ArrayList<ChannelDTO> allChannelList = new ArrayList<ChannelDTO>();
    private static ArrayList<ChannelDTO> followedChannelList = new ArrayList<ChannelDTO>();

    private AllChannelLists(){

    }

    public static AllChannelLists getInstance(){
        return instance;
    }

    public ArrayList<ChannelDTO> getFollowedChannelList(){
        return followedChannelList;
    }

    public ArrayList<ChannelDTO> getAllChannelList(){
        return allChannelList;
    }

    public boolean isDetailAvailable(String channelId){

        for(int i=0; i<allChannelList.size();i++){
            if(allChannelList.get(i).getChannelID().equals(channelId)){
                return allChannelList.get(i).isDetailAvailable();
            }
        }

        for(int i=0; i<followedChannelList.size();i++){
            if(allChannelList.get(i).getChannelID().equals(channelId)){
                return followedChannelList.get(i).isDetailAvailable();
            }
        }

        return false;
    }
}
