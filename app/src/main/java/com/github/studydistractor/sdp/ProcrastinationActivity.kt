package com.github.studydistractor.sdp

import android.os.Parcel
import android.os.Parcelable

data class ProcrastinationActivity(
    val name: String? = null,
    val description: String? = null) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProcrastinationActivity> {
        override fun createFromParcel(parcel: Parcel): ProcrastinationActivity {
            return ProcrastinationActivity(parcel)
        }

        override fun newArray(size: Int): Array<ProcrastinationActivity?> {
            return arrayOfNulls(size)
        }
    }
}