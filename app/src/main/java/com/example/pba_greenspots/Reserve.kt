package com.example.pba_greenspots

import android.os.Parcel
import android.os.Parcelable

class Reserve (    //var id: String = " ",
    var instrumentoPlanificacion: String= " ",
    var municipio: String= " ",
    var nombreUnidad: String= " ") : Parcelable {

    constructor(parcel: Parcel) : this(
        // id = parcel.readString().toString(),
        instrumentoPlanificacion = parcel.readString().toString(),
        municipio = parcel.readString().toString(),
        nombreUnidad = parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        // parcel.writeString(id)
        parcel.writeString(instrumentoPlanificacion)
        parcel.writeString(municipio)
        parcel.writeString(nombreUnidad)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Reserve> {
        override fun createFromParcel(parcel: Parcel): Reserve {
            return Reserve(parcel)
        }

        override fun newArray(size: Int): Array<Reserve?> {
            return arrayOfNulls(size)
        }
    }
}