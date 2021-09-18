package com.fred.prueba.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity
data class Leagues(
    var idTeam:String?=null,
    @PrimaryKey
    var id:Int?=null,
    var strTeam:String?=null,
    var strStadium:String?=null,
    val strDescriptionES: String?=null,
    val intFormedYear: String?=null,
    val strTeamBadge: String?=null,
    val strYoutube: String?=null,
    val strFacebook: String?=null,
    val strTwitter: String?=null,
    val strInstagram: String?=null,
    val strWebsite: String?=null,
    @ColumnInfo
    var isFavorite:Boolean?=false,
    @ColumnInfo
    var isRead:Boolean?=false
):Serializable{
}
