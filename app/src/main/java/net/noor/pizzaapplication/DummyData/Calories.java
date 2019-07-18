package net.noor.pizzaapplication.DummyData;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nsamir on 7/2/2019.
 */
public class Calories implements Parcelable {
    private String tittle;
    private int calGram;
    private int calPercentage;

    Calories(String tittle, int calGram, int calPercentage) {
        this.tittle = tittle;
        this.calGram = calGram;
        this.calPercentage = calPercentage;
    }

    protected Calories(Parcel in) {
        tittle = in.readString();
        calGram = in.readInt();
        calPercentage = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tittle);
        dest.writeInt(calGram);
        dest.writeInt(calPercentage);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Calories> CREATOR = new Creator<Calories>() {
        @Override
        public Calories createFromParcel(Parcel in) {
            return new Calories(in);
        }

        @Override
        public Calories[] newArray(int size) {
            return new Calories[size];
        }
    };

    public String getTittle() {
        return tittle;
    }

    public int getCalGram() {
        return calGram;
    }

    public int getCalPercentage() {
        return calPercentage;
    }
}

