package com.github.studydistractor.sdp.procrastinationActivity

import android.os.Parcel
import android.os.Parcelable

/**
 * Data class that represents a procrastination activity
 * @param name name of the procrastination activity
 * @param description description of the procrastination activity
 * @constructor create the procrastination activity
 */
data class ProcrastinationActivity(
    val name: String? = null,
    val description: String? = null,
    val lat: Double? = null,
    val long: Double? = null) :
    Parcelable {
    // Parcelable is used to pass a procrastination activity from an activity to another one.
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
        if (lat != null) {
            parcel.writeDouble(lat)
        }
        if (long != null) {
            parcel.writeDouble(long)
        }
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