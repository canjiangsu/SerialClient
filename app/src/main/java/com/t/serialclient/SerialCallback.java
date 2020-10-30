package com.t.serialclient;

import android.os.Parcel;
import android.os.Parcelable;

import com.t.serialserver.ISerialCallback;

public class SerialCallback implements Parcelable {

    private final ISerialCallback mCallback;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeStrongBinder(mCallback.asBinder());
    }

    SerialCallback(Parcel parcel) {
        mCallback = ISerialCallback.Stub.asInterface(parcel.readStrongBinder());
    }

    public static final Parcelable.Creator<SerialCallback> CREATOR
            = new Parcelable.Creator<SerialCallback> () {
        public SerialCallback createFromParcel(Parcel parcel) { return new SerialCallback(parcel); }
        public SerialCallback[] newArray(int size) { return new SerialCallback[size]; }
    };
}
