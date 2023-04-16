package com.github.studydistractor.sdp.distraction

import android.os.Parcel
import android.os.Parcelable

/**
 * Data class that represents a Distraction
 * @param name name of the Distraction
 * @param description description of the Distraction
 * @constructor create the Distraction
 */
data class Distraction(
    val name: String? = null,
    val description: String? = null,
    val lat: Double? = null,
    val long: Double? = null,
    val iconName : String? = null) :
    Parcelable {
    // Parcelable is used to pass a Distractionfrom an activity to another one.
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString()
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
        if (iconName != null) {
            parcel.writeString(iconName)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Distraction> {
        override fun createFromParcel(parcel: Parcel): Distraction {
            return Distraction(parcel)
        }

        override fun newArray(size: Int): Array<Distraction?> {
            return arrayOfNulls(size)
        }
    }
}