package com.dev.mohamed.partyphotos.data;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by moham on 7/16/2018.
 */

public class PartyData implements Parcelable {

    private String partyName;
    private String partyMainPhotoLink;
    private int numOfPhotos;
    private int numOfViews;
    private List<String> listOfPhotosLinks;
    private String partyPassword;
    private String isPrivate;

    private Bitmap partyMainPhotoBitmab;
    private List<Bitmap> listOfPhotsBitmab;

    public PartyData(String partyMainPhotoLink, int numOfPhotos, int numOfViews,String isPrivate, List<String> listOfPhotosLinks) {
        this.partyMainPhotoLink = partyMainPhotoLink;
        this.numOfPhotos = numOfPhotos;
        this.numOfViews = numOfViews;
        this.listOfPhotosLinks = listOfPhotosLinks;
        this.isPrivate=isPrivate;
    }

    public PartyData(String partyName, String partyPassword,String isPrivate, Bitmap partyMainPhotoBitmab) {
        this.partyName = partyName;
        this.partyPassword = partyPassword;
        this.partyMainPhotoBitmab = partyMainPhotoBitmab;
        this.isPrivate=isPrivate;
    }


    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPartyMainPhotoLink() {
        return partyMainPhotoLink;
    }

    public void setPartyMainPhotoLink(String partyMainPhotoLink) {
        this.partyMainPhotoLink = partyMainPhotoLink;
    }

    public int getNumOfPhotos() {
        return numOfPhotos;
    }

    public void setNumOfPhotos(int numOfPhotos) {
        this.numOfPhotos = numOfPhotos;
    }

    public int getNumOfViews() {
        return numOfViews;
    }

    public void setNumOfViews(int numOfViews) {
        this.numOfViews = numOfViews;
    }

    public List<String> getListOfPhotosLinks() {
        return listOfPhotosLinks;
    }

    public void setListOfPhotosLinks(List<String> listOfPhotosLinks) {
        this.listOfPhotosLinks = listOfPhotosLinks;
    }

    public String getPartyPassword() {
        return partyPassword;
    }

    public void setPartyPassword(String partyPassword) {
        this.partyPassword = partyPassword;
    }

    public Bitmap getPartyMainPhotoBitmab() {
        return partyMainPhotoBitmab;
    }

    public void setPartyMainPhotoBitmab(Bitmap partyMainPhotoBitmab) {
        this.partyMainPhotoBitmab = partyMainPhotoBitmab;
    }

    public List<Bitmap> getListOfPhotsBitmab() {
        return listOfPhotsBitmab;
    }

    public void setListOfPhotsBitmab(List<Bitmap> listOfPhotsBitmab) {
        this.listOfPhotsBitmab = listOfPhotsBitmab;
    }

    public static Creator<PartyData> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.partyName);
        dest.writeString(this.partyMainPhotoLink);
        dest.writeInt(this.numOfPhotos);
        dest.writeInt(this.numOfViews);
        dest.writeStringList(this.listOfPhotosLinks);
        dest.writeString(this.partyPassword);
        dest.writeString(this.isPrivate);
        dest.writeParcelable(this.partyMainPhotoBitmab, flags);
        dest.writeTypedList(this.listOfPhotsBitmab);
    }

    protected PartyData(Parcel in) {
        this.partyName = in.readString();
        this.partyMainPhotoLink = in.readString();
        this.numOfPhotos = in.readInt();
        this.numOfViews = in.readInt();
        this.listOfPhotosLinks = in.createStringArrayList();
        this.partyPassword = in.readString();
        this.isPrivate = in.readString();
        this.partyMainPhotoBitmab = in.readParcelable(Bitmap.class.getClassLoader());
        this.listOfPhotsBitmab = in.createTypedArrayList(Bitmap.CREATOR);
    }

    public static final Creator<PartyData> CREATOR = new Creator<PartyData>() {
        @Override
        public PartyData createFromParcel(Parcel source) {
            return new PartyData(source);
        }

        @Override
        public PartyData[] newArray(int size) {
            return new PartyData[size];
        }
    };
}
