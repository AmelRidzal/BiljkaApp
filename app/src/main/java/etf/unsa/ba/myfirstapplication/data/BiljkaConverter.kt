package etf.unsa.ba.myfirstapplication.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import etf.unsa.ba.myfirstapplication.enumClass.KlimatskiTip
import etf.unsa.ba.myfirstapplication.enumClass.MedicinskaKorist
import etf.unsa.ba.myfirstapplication.enumClass.ProfilOkusaBiljke
import etf.unsa.ba.myfirstapplication.enumClass.Zemljište
import kotlinx.coroutines.CoroutineStart
import java.io.ByteArrayOutputStream



class BiljkaConverter {


    @TypeConverter
    fun toZemljiste(value: Int?): List<Zemljište> {
        var ret:List<Zemljište> =listOf()
        var x=value
        if (x != null) {
           while(x!=0) {
               val z= x.rem(10)
               x /= 10
               //if(z!=0)
               ret = if (ret.isEmpty()) {
                   listOf(enumValues<Zemljište>()[z-1])
               } else {
                   ret + listOf(enumValues<Zemljište>()[z-1])
               }
           }
        }
        return ret
    }

    @TypeConverter
    fun fromZemljiste(value: List<Zemljište>?): Int {
        var ret=0
        if (value != null) {
            for(z in value) {
                if(z!=null)
                ret=ret*10+z.ordinal+1
            }
        }
        return ret
    }
    @TypeConverter
    fun toKlimatskiTip(value: Int): List<KlimatskiTip> {

        Log.d("-1fromKli",value.toString())
        var ret:List<KlimatskiTip> =listOf()
        var x=value
        if (x != null) {
            while(x!=0) {
                val z= x.rem(10)
                x /= 10
                //if(z!=0)
                ret = if (ret.isEmpty()) {
                    listOf(enumValues<KlimatskiTip>()[z-1])
                } else {
                    ret + listOf(enumValues<KlimatskiTip>()[z-1])
                }
            }
        }
        Log.d("-1toKli",ret.toString())
        return ret
    }
    @TypeConverter
    fun fromKlimatskiTip(value: List<KlimatskiTip>?): Int {
        var ret=0
        if (value != null) {
            for(z in value) {
                if(z!=null)
                ret=ret*10+z.ordinal+1
            }
        }
        return ret
    }
    @TypeConverter
    fun toProfilOkusaBiljke(value: Int?) = listOf(enumValues<ProfilOkusaBiljke>()[value!!])

    @TypeConverter
    fun fromProfilOkusaBiljke(value: List<ProfilOkusaBiljke>?) = value?.get(0)?.ordinal
    @TypeConverter
    fun toMedicinskaKorist(value: Int?): List<MedicinskaKorist> {
        var ret:List<MedicinskaKorist> =listOf()
        var x=value
        if (x != null) {
            while(x!=0) {
                val z= x.rem(10)
                x /= 10
                if(z!=0)
                ret = if (ret.isEmpty()) {
                    listOf(enumValues<MedicinskaKorist>()[z-1])
                } else {
                    ret + listOf(enumValues<MedicinskaKorist>()[z-1])
                }
            }
        }
        return ret
    }
    @TypeConverter
    fun fromMedicinskaKorist(value: List<MedicinskaKorist>?): Int {
        var ret=0
        if (value != null) {
            for(z in value) {
                if(z!=null)
                ret=ret*10+z.ordinal+1
            }
        }
        return ret
    }
    @TypeConverter
    fun toJela(value: String?): List<String> {
        var ret:List<String> =listOf()
        var s=""
        if (value != null) {
            for(c in value){
                if(c=='.'){
                    ret = if (ret.isEmpty()) {
                        listOf(s)
                    } else {
                        ret + listOf(s)
                    }
                    s=""
                }else{
                    s += c
                }
            }
        }
        return ret
    }

    @TypeConverter
    fun fromJela(value: List<String>?): String {
        var ret=""
        if (value != null) {
            for(s in value){
                ret= "$ret.$s"
            }
        }
        return ret
    }

    @TypeConverter
    fun toSlikaBitmap(value: String?):Bitmap? {
        return try {
            val encodeByte = Base64.decode(value, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e:Exception) {
            e.printStackTrace()
            null
        }
    }
    @TypeConverter
    fun fromSlikaBitmap(value: Bitmap?): String? {
        val baos = ByteArrayOutputStream()
        value?.compress(Bitmap.CompressFormat.JPEG, 70, baos)
        val byteArray = baos.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
}