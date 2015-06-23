package com.soi.rapidandroidapp.models;

import android.graphics.drawable.Drawable;

/**
 * This models will be used to create a custom UI element
 * like a dialog/spinner which will contains the social entities
 * which can be selected to share on each one.
 * TODO: support for multiple selection and share
 */
public class SocialShareModel {

    /**
     * The title of the social entity
     * eg.
     * Share on facebook
     */
    private String title;

    /**
     * The drawable which will be associated
     * for this social model.
     * TODO: support url as images
     */
    private Drawable image;

    private SocialType type;

    public SocialShareModel() {
    }

    public SocialShareModel(String title, Drawable image, SocialType type) {
        this.title = title;
        this.image = image;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public SocialType getType() {
        return type;
    }

    public void setType(SocialType type) {
        this.type = type;
    }
}