package com.example.genji.popularmovie;


import android.os.Parcel;
import android.os.Parcelable;


public class Poster implements Parcelable{
    public String image;
    public String name;
    public String releaseDate;
    public double rate;
    public String desc;

    public Poster(String image,String name,String releaseDate,double rate,String desc){
        this.image = image;
        this.name = name;
        this.releaseDate = releaseDate;
        this.rate = rate;
        this.desc = desc;

    }

    private Poster(Parcel in){
        image = in.readString();
        name = in.readString();
        releaseDate = in.readString();
        rate = in.readDouble();
        desc = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(name);
        dest.writeString(releaseDate);
        dest.writeDouble(rate);
        dest.writeString(desc);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Poster> CREATOR = new Parcelable.Creator<Poster>() {

        public Poster createFromParcel(Parcel in) {
            return new Poster(in);
        }

        public Poster[] newArray(int size) {
            return new Poster[size];
        }
    };
}
