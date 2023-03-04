package edu.northeastern.a7team45.firebase.model;

public class StickerSent {
    private String strickerId;
    private String count;


    public StickerSent(String strickerId, String count) {
        this.strickerId = strickerId;
        this.count = count;
    }

    public StickerSent() {
    }

    public String getStrickerId() {
        return strickerId;
    }

    public void setStrickerId(String strickerId) {
        this.strickerId = strickerId;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
