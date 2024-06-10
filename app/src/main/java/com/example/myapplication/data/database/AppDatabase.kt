package com.example.myapplication.data.database
/*
import PrepopulateRoomCallback
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication.data.dao.ParkingDao
import com.example.myapplication.data.dao.ReservationDao
import com.example.myapplication.data.dao.UserDao
import com.example.myapplication.data.model.Floor
import com.example.myapplication.data.model.Parking
import com.example.myapplication.data.model.Place
import com.example.myapplication.data.model.Reservation
import com.example.myapplication.data.model.User

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
*/
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapplication.data.dao.ReservationDao
import com.example.myapplication.data.model.Reservation
import java.text.SimpleDateFormat
import java.util.Date

class Converters {
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy")

    @TypeConverter
    fun toTimestamp(date: Date?): String? {
        return date?.let { dateFormat.format(it) }
    }

    @TypeConverter
    fun fromTimestamp(dateString: String?): Date {
        return dateString.let { dateFormat.parse(it) }
    }
}

@Database(entities = [ Reservation::class], version = 2)
@TypeConverters(Converters::class)
abstract class AppDatabase:RoomDatabase() {

    abstract fun getReservationDao(): ReservationDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance =
                        Room.databaseBuilder(context, AppDatabase::class.java,
                            "db_projet").addMigrations(MIGRATION_1_2).build()
                    INSTANCE = instance
                }

                return instance
            }
        }
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE reservations ADD COLUMN placeNum INTEGER")
        }}
    }

}

