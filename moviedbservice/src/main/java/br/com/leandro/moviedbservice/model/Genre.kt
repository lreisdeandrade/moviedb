package br.com.leandro.moviedbservice.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by leandro on 07/06/2018
 */

data class Genre(val id: Int, val name: String) : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(name)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Genre> = object : Parcelable.Creator<Genre> {
            override fun createFromParcel(source: Parcel): Genre = Genre(source)
            override fun newArray(size: Int): Array<Genre?> = arrayOfNulls(size)
        }
    }
}