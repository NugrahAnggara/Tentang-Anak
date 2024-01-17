package com.example.tentanganak.model

import android.os.Parcel
import android.os.Parcelable

data class ModelModul(
    val judul: String?,
    val desc: String?,
    val avatar: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(judul)
        parcel.writeString(desc)
        parcel.writeString(avatar)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ModelModul> {
        override fun createFromParcel(parcel: Parcel): ModelModul {
            return ModelModul(parcel)
        }

        override fun newArray(size: Int): Array<ModelModul?> {
            return arrayOfNulls(size)
        }
    }
}