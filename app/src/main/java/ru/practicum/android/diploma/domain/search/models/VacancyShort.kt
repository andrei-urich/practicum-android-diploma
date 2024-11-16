package ru.practicum.android.diploma.domain.search.models

import android.os.Parcel
import android.os.Parcelable
import ru.practicum.android.diploma.util.EMPTY_STRING

data class VacancyShort(
    val vacancyId: String?,
    val name: String?,
    val employer: String?,
    val areas: String?,
    val salary: String?,
    val currency: String? = EMPTY_STRING,
    val artLink: String? = EMPTY_STRING
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(vacancyId)
        parcel.writeString(name)
        parcel.writeString(employer)
        parcel.writeString(areas)
        parcel.writeString(salary)
        parcel.writeString(currency)
        parcel.writeString(artLink)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VacancyShort> {
        override fun createFromParcel(parcel: Parcel): VacancyShort {
            return VacancyShort(parcel)
        }

        override fun newArray(size: Int): Array<VacancyShort?> {
            return arrayOfNulls(size)
        }
    }

}
