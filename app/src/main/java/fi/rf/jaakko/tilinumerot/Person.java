package fi.rf.jaakko.tilinumerot;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by jaakko on 6.2.2016.
 */
public class Person implements Parcelable, Comparable<Person> {
    private String name;
    private String account;

    public Person(String name) {
        this.name = name;
    }

    public Person(String name, String account) {
        this.name = name;
        this.account = account;
    }

    public Person(Parcel in) {
        String[] data = new String[2];
        in.readStringArray(data);

        this.name = data[0];
        this.account = data[1];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public boolean setAccount(String account) {
        if (Person.validAccount(account)) {
            this.account = account;
            return true;
        }

        return false;
    }

    private static boolean validAccount(String account) {
        return true;
    }

    @Override
    public int describeContents() {
        return (this.name + this.account).hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        String[] data = {name, account};
        dest.writeStringArray(data);
    }

    public static final Parcelable.Creator<Person> CREATOR
            = new Parcelable.Creator<Person>() {
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public int compareTo(Person another) {
        return this.name.compareTo(another.name);
    }

    @Override
    public String toString() {
        return this.name + ";" + this.account;
    }
}
