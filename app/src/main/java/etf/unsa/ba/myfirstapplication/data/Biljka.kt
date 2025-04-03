package etf.unsa.ba.myfirstapplication.data

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import etf.unsa.ba.myfirstapplication.enumClass.KlimatskiTip
import etf.unsa.ba.myfirstapplication.enumClass.MedicinskaKorist
import etf.unsa.ba.myfirstapplication.enumClass.ProfilOkusaBiljke
import etf.unsa.ba.myfirstapplication.enumClass.Zemljište
import java.io.Serializable

@Entity(tableName = "Biljka")
data class Biljka(

    @PrimaryKey(autoGenerate = true) var id: Int? =null,
    @ColumnInfo(name = "naziv") var naziv: String="",
    @ColumnInfo(name = "family") var porodica: String="",
    @ColumnInfo(name = "medicinskoUpozorenje") var medicinskoUpozorenje: String?="",
    @ColumnInfo(name = "medicinskeKoristi") var medicinskeKoristi: List<MedicinskaKorist>? = null,
    @ColumnInfo(name = "profilOkusa") var profilOkusa: ProfilOkusaBiljke? = null,
    @ColumnInfo(name = "jela") var jela: List<String>? = null,
    @ColumnInfo(name = "klimatskiTipovi") var klimatskiTipovi: List<KlimatskiTip>? = null,
    @ColumnInfo(name = "zemljisniTipovi") var zemljisniTipovi: List<Zemljište>? = null,
    @ColumnInfo(name = "slika") var slika: String?=naziv,
    @ColumnInfo(name = "slikaBitmap") var slikaBitmap: Bitmap?=null,
    @ColumnInfo(name = "onlineChecked") var onlineChecked: Boolean?=false,
    ):Serializable{}


@Entity(foreignKeys = arrayOf(ForeignKey(entity = Biljka::class,
parentColumns = arrayOf("id"),
childColumns = arrayOf("idBiljke"),
onDelete = ForeignKey.CASCADE)), tableName = "BiljkaBitmap")
data class BiljkaBitmap(
    @PrimaryKey(autoGenerate = false)@ColumnInfo(name = "idBiljke")var id: Int? =null,
    @ColumnInfo(name = "bitmap") var slikaBitmap: Bitmap?=null,
)