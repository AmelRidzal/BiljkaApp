package etf.unsa.ba.myfirstapplication.DAO


import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import etf.unsa.ba.myfirstapplication.data.Biljka
import etf.unsa.ba.myfirstapplication.data.BiljkaBitmap
import etf.unsa.ba.myfirstapplication.data.BiljkaConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Database(entities = arrayOf(Biljka::class,BiljkaBitmap::class), version = 1)
@TypeConverters(BiljkaConverter::class)
abstract class BiljkaDatabase : RoomDatabase() {
    abstract fun biljkaDao(): BiljkaDAO

    companion object {
        private var INSTANCE: BiljkaDatabase? = null
        fun getInstance(context: Context): BiljkaDatabase {
            if (INSTANCE == null) {
                synchronized(BiljkaDatabase::class) {
                    INSTANCE = buildRoomDB(context)
                }
            }
            Log.d("roominst", INSTANCE.toString())
            return INSTANCE!!
        }
        private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                BiljkaDatabase::class.java,
                "biljke-db"
            ).build()


    }
}