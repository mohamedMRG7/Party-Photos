package com.dev.mohamed.partyphotos.data;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moham on 7/16/2018.
 */

public class PartyData implements Parcelable {

    private String partyName;
    private String partyMainPhotoLink;
    private int numOfPhotos;
    private int numOfViews;
    private ArrayList<String> listOfPhotosLinks;
    private String partyPassword;
    private boolean closedParty;

    private Uri partyMainPhotoUri;
    private List<Bitmap> listOfPhotsBitmab;

    public PartyData() {
    }

    public PartyData(String partyName, String partyPassword, boolean ClosedParty, Uri partyMainPhotoUri) {
        this.partyName = partyName;
        this.partyPassword = partyPassword;
        this.partyMainPhotoUri = partyMainPhotoUri;
        this.closedParty = ClosedParty;
    }

    public boolean isClosedParty() {
        return closedParty;
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

    public ArrayList<String> getListOfPhotosLinks() {
        return listOfPhotosLinks;
    }

    public void setListOfPhotosLinks(ArrayList<String> listOfPhotosLinks) {
        this.listOfPhotosLinks = listOfPhotosLinks;
    }

    public String getPartyPassword() {
        return partyPassword;
    }

    public void setPartyPassword(String partyPassword) {
        this.partyPassword = partyPassword;
    }

    public Uri getPartyMainPhotoUri() {
        return partyMainPhotoUri;
    }

    public void setPartyMainPhotoUri(Uri partyMainPhotoUri) {
        this.partyMainPhotoUri = partyMainPhotoUri;
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
        dest.writeByte(this.closedParty ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.partyMainPhotoUri, flags);
        dest.writeTypedList(this.listOfPhotsBitmab);
    }

    protected PartyData(Parcel in) {
        this.partyName = in.readString();
        this.partyMainPhotoLink = in.readString();
        this.numOfPhotos = in.readInt();
        this.numOfViews = in.readInt();
        this.listOfPhotosLinks = in.createStringArrayList();
        this.partyPassword = in.readString();
        this.closedParty = in.readByte() != 0;
        this.partyMainPhotoUri = in.readParcelable(Uri.class.getClassLoader());
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
