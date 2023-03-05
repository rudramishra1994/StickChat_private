package edu.northeastern.a7team45.firebase.model;

import java.util.List;

public class UserStickerInfo {
    private String sender;
    private List<StickerSent> stickerSentList;


    public UserStickerInfo() {
    }

    public UserStickerInfo(String username, List<StickerSent> stickerSentList) {
        this.sender = username;
        this.stickerSentList = stickerSentList;

    }

    public String getUsername() {
        return sender;
    }

    public void setUsername(String username) {
        this.sender = username;
    }

    public List<StickerSent> getStickerSentList() {
        return stickerSentList;
    }

    public void setStickerSentList(List<StickerSent> stickerSentList) {
        this.stickerSentList = stickerSentList;
    }


}
