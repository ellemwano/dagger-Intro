package com.moducode.daggerexample.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "fav_episodes")
@Parcelize
@JsonClass(generateAdapter = true)
data class EpisodeData(
        @field:PrimaryKey
        @field:Json(name = "id")
        val id: Int,
        @field:ColumnInfo(name = "url")
        @field:Json(name = "url")
        val url: String,
        @field:ColumnInfo(name = "name")
        @field:Json(name = "name")
        val name: String,
        @field:ColumnInfo(name = "season")
        @field:Json(name = "season")
        val season: Int,
        @field:ColumnInfo(name = "number")
        @field:Json(name = "number")
        val number: Int,
        @field:ColumnInfo(name = "airdate")
        @field:Json(name = "airdate")
        val airdate: String,
        @field:ColumnInfo(name = "airtime")
        @field:Json(name = "airtime")
        val airtime: String,
        @field:ColumnInfo(name = "airstamp")
        @field:Json(name = "airstamp")
        val airstamp: String,
        @field:ColumnInfo(name = "runtime")
        @field:Json(name = "runtime")
        val runtime: Int,
        @field:Embedded
        @field:Json(name = "image")
        val image: Image,
        @field:ColumnInfo(name = "summary")
        @field:Json(name = "summary")
        val summary: String,
        @field:Embedded
        @field:Json(name = "_links")
        val links: Links
) : Parcelable


@Entity(tableName = "links")
@Parcelize
data class Links(
        @field:Embedded
        @field:Json(name = "self")
        val self: Self
) : Parcelable

@Entity(tableName = "self")
@Parcelize
data class Self(
        @field:Json(name = "href")
        val href: String
) : Parcelable


@Entity(tableName = "image")
@Parcelize
data class Image(
        @field:Json(name = "medium")
        val medium: String,
        @field:Json(name = "original")
        val original: String
) : Parcelable