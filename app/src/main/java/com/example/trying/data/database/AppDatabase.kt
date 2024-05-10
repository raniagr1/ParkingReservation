package com.example.trying.data.database

import PrepopulateRoomCallback
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.trying.data.dao.ParkingDao
import com.example.trying.data.dao.ReservationDao
import com.example.trying.data.dao.UserDao
import com.example.trying.data.model.Floor
import com.example.trying.data.model.Parking
import com.example.trying.data.model.Place
import com.example.trying.data.model.Reservation
import com.example.trying.data.model.User

@Database(entities = [User::class, Parking::class, Reservation::class, Floor::class, Place::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun parkingDao(): ParkingDao
    abstract fun userDao(): UserDao
    abstract fun reservationDao(): ReservationDao

    companion object {
        private const val DATABASE_NAME = "parking_app_database"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).fallbackToDestructiveMigration().createFromAsset("database/newparks.db").build();


                INSTANCE = instance
                return instance
            }
        }
    }
}
