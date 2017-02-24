package com.mobiowin.paalan.model;

/**
 * Created by yashika on 31/1/17.
 */

public class WhatsNewScreenModel implements Comparable{
    int sequence;
    String title;
    String imageLink;
    String message;

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int compareTo(Object object) {
        int compareage=((WhatsNewScreenModel)object).getSequence();
        /* For Ascending order*/
        return this.sequence-compareage;
    }
}
