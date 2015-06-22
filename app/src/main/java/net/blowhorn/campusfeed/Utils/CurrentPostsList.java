package net.blowhorn.campusfeed.Utils;

import net.blowhorn.campusfeed.DTO.ChannelDTO;
import net.blowhorn.campusfeed.DTO.PostDTO;

import java.util.ArrayList;

/**
 * Created by chinmay on 22/6/15.
 */
public class CurrentPostsList {

    private static CurrentPostsList instance = new CurrentPostsList();

    private static ArrayList<PostDTO> currentPostsList = new ArrayList<PostDTO>();

    private CurrentPostsList() {

    }

    public static CurrentPostsList getInstance() {
        return instance;
    }

    public ArrayList<PostDTO> getCurrentPostsList() {
        return currentPostsList;
    }
}
