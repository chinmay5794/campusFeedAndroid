package net.blowhorn.campusfeed.DTO;

/**
 * Created by chinmay on 22/6/15.
 */

//Response: Dictionary of status, posts: array of
// (post_id(generated),text, img_url, time, pending_bit, user_full_name, user_img_url, user_branch )

public class PostDTO {
    private String postId;
    private String text;
    private String postImgUrl;
    private String postTime;
    private String pendingBit;
    private String userName;
    private String userImgUrl;
    private String userBranch;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPostImgUrl() {
        return postImgUrl;
    }

    public void setPostImgUrl(String postImgUrl) {
        this.postImgUrl = postImgUrl;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getPendingBit() {
        return pendingBit;
    }

    public void setPendingBit(String pendingBit) {
        this.pendingBit = pendingBit;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImgUrl() {
        return userImgUrl;
    }

    public void setUserImgUrl(String userImgUrl) {
        this.userImgUrl = userImgUrl;
    }

    public String getUserBranch() {
        return userBranch;
    }

    public void setUserBranch(String userBranch) {
        this.userBranch = userBranch;
    }
}
