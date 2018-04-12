package com.projects.vo1.customvk.data.longPolling

import android.os.Parcel
import android.os.Parcelable

class MessageNotification (

    val msgId: Long,
    val interlocutorId: Long,
    val msgTime: Long,
    val msgBody: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readString()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeLong(msgId)
        dest?.writeLong(interlocutorId)
        dest?.writeLong(msgTime)
        dest?.writeString(msgBody)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MessageNotification> {
        override fun createFromParcel(parcel: Parcel): MessageNotification {
            return MessageNotification(parcel)
        }

        override fun newArray(size: Int): Array<MessageNotification?> {
            return arrayOfNulls(size)
        }
    }
}