package edu.northeastern.a7team45.firebase.model;

public class StickerReceived {
    String StickerId;
    String senderUsername;
    String timeStamp;

    public StickerReceived(String stickerId, String senderUsername, String timeStamp) {
        StickerId = stickerId;
        this.senderUsername = senderUsername;
        this.timeStamp = timeStamp;
    }

    public StickerReceived() {
    }

    public String getStickerId() {
        return StickerId;
    }

    public void setStickerId(String stickerId) {
        StickerId = stickerId;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
