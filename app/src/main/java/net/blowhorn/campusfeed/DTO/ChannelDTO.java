package net.blowhorn.campusfeed.DTO;

import java.util.HashMap;

/**
 * Created by chinmay on 19/6/15.
 */
public class ChannelDTO {
    //when values are obtained from mychannels, only first 4 are set
    private String channelID = null;
    private String channelName = null;
    private String channelImgUrl = null;
    private int numFollowers = 0;
    private int numViews = 0;
    private String created_time = null;
    private String description = null;
    public class Admin {
        private String firstName = null;
        private String lastName = null;
        public String getLastName() {
            return lastName;
        }
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
        public String getFirstName() {
            return firstName;
        }
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
    }
    public Admin admin = new Admin();
    public String getChannelID() {
        return channelID;
    }
    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }
    public String getChannelName() {
        return channelName;
    }
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
    public String getChannelImgUrl() {
        return channelImgUrl;
    }
    public void setChannelImgUrl(String channelImgUrl) {
        this.channelImgUrl = channelImgUrl;
    }
    public int getNumFollowers() {
        return numFollowers;
    }
    public void setNumFollowers(int numFollowers) {
        this.numFollowers = numFollowers;
    }
    public int getNumViews(){
        return numViews;
    }
    public void setNumViews(int numViews){
        this.numViews = numViews;
    }
    public String getCreated_time() {
        return created_time;
    }
    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public boolean isDetailAvailable(){
        if(created_time != null && description != null &&
                this.admin.getFirstName() !=null && this.admin.getLastName() != null){
            return true;
        }
        else{
            return false;
        }
    }
}
