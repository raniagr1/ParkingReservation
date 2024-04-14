import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.trying.data.database.AppDatabase
import com.example.trying.data.model.Parking
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray

class PrepopulateRoomCallback(private val context: Context) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        // Prepopulate the database on first creation
        CoroutineScope(Dispatchers.IO).launch {
            val parkingList = loadParkingDataFromJson(context)
            val database = AppDatabase.getDatabase(context)
            database.parkingDao().insertAll(parkingList)
        }
    }

    private fun loadParkingDataFromJson(context: Context): List<Parking> {
        val jsonFile: String = context.assets.open("parkings.json").bufferedReader().use { it.readText() }
        val jsonArray = JSONArray(jsonFile)
        val parkingList = mutableListOf<Parking>()

        for (i in 0 until jsonArray.length()) {
            val jsonObj = jsonArray.getJSONObject(i)
            val id = jsonObj.getInt("id")
            val name = jsonObj.getString("name")
            val commune = jsonObj.getString("commune")
            val imageResId = jsonObj.getInt("imageResId")
            val capacity = jsonObj.getInt("capacity")
            val pricePerHour = jsonObj.getDouble("pricePerHour")
            val description = jsonObj.getString("description")
            val rating = jsonObj.getDouble("rating").toFloat()
            val longitude = jsonObj.getDouble("longitude").toFloat()
            val latitude = jsonObj.getDouble("latitude").toFloat()

            val parking = Parking(id, name, commune, imageResId, capacity, pricePerHour, description, rating, longitude, latitude)
            parkingList.add(parking)
        }

        return parkingList
    }
}
