package com.example.trying.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")

data class User(
    @PrimaryKey(autoGenerate = true)
    var userId: Int = 0,

    var firstName: String,

    var lastName: String,

    var userName: String,

    var passwrd: String
)
