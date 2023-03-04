package edu.northeastern.a7team45.firebase.model;

import java.util.List;

public class UserStickerInfo {
    private String username;
    private List<StickerSent> stickerSentList;
    private List<StickerReceived> stickerReceivedList;

    public UserStickerInfo() {
    }

    public UserStickerInfo(String username, List<StickerSent> stickerSentList, List<StickerReceived> stickerReceivedList) {
        this.username = username;
        this.stickerSentList = stickerSentList;
        this.stickerReceivedList = stickerReceivedList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<StickerSent> getStickerSentList() {
        return stickerSentList;
    }

    public void setStickerSentList(List<StickerSent> stickerSentList) {
        this.stickerSentList = stickerSentList;
    }

    public List<StickerReceived> getStickerReceivedList() {
        return stickerReceivedList;
    }

    public void setStickerReceivedList(List<StickerReceived> stickerReceivedList) {
        this.stickerReceivedList = stickerReceivedList;
    }
}
