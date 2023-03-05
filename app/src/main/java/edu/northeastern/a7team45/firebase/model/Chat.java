package edu.northeastern.a7team45.firebase.model;

public class Chat {
    private String sender;
    private String receiver;
    private String stickerId;
    private String timeStamp;

    public Chat() {
    }

    public Chat(String sender, String receiver, String stickerId, String timeStamp) {
        this.sender = sender;
        this.receiver = receiver;
        this.stickerId = stickerId;
        this.timeStamp = timeStamp;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getStickerId() {
        return stickerId;
    }

    public void setStickerId(String stickerId) {
        this.stickerId = stickerId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
