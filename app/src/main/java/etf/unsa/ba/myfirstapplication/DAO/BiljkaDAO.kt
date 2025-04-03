package etf.unsa.ba.myfirstapplication.DAO


import android.graphics.Bitmap
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import etf.unsa.ba.myfirstapplication.data.Biljka
import etf.unsa.ba.myfirstapplication.data.BiljkaBitmap


@Dao
interface BiljkaDAO {

    @Query("SELECT * FROM Biljka")
    fun getAllBiljkasDAO(): List<Biljka>

    @Query("SELECT * FROM Biljka where id = :bid")
    fun getBiljkaDAO(bid:Int): Biljka

    @Insert
    fun addImageDAO(b:BiljkaBitmap)
    @Insert
    fun saveBiljkaDAO(vararg users: Biljka)
    @Delete
    fun clearDataDAO(b: Biljka)


    suspend fun getAllBiljkas() : List<Biljka> {
        return getAllBiljkasDAO()
    }

    suspend fun addImage(bid:Int, b:Bitmap){
        val biljkaBitmap=BiljkaBitmap(bid,b)
        addImageDAO(biljkaBitmap)

    }
    suspend fun saveBiljka(b: Biljka) : Boolean?{
        return try {
            saveBiljkaDAO(b)
            true
        }catch (e:Exception){
            false
        }

    }

    suspend fun clearData(){
            val bb = getAllBiljkasDAO()
            for(b in bb){
                clearDataDAO(b)
            }

    }

    suspend fun fixOfflineBiljka():Int{
            var ret=0
            var bb = getAllBiljkasDAO()
            for(b in bb){
                if(b.onlineChecked==false){
                    val novab=TrefleDAO().fixData(b)
                    novab.id = b.id
                    novab.onlineChecked=true
                    clearDataDAO(b)
                    saveBiljkaDAO(novab)
                    ret++

                }
            }
            return ret

    }

}

