package etf.unsa.ba.myfirstapplication.DAO

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import etf.unsa.ba.myfirstapplication.data.Biljka
import etf.unsa.ba.myfirstapplication.enumClass.KlimatskiTip
import etf.unsa.ba.myfirstapplication.enumClass.Zemljište
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

class TrefleDAO {




        val apiServis = ApiClient.getInstance().create(ApiServis::class.java)
        fun getLatinName(s: String?): String {
            var ns = ""
            var zagrada = false
            if (s != null && s.contains("(")) {
                for (c: Char in s) {
                    if (c == ')') {
                        zagrada = false
                    }
                    if (zagrada) {
                        ns += c
                    }
                    if (c == '(') {
                        zagrada = true
                    }
                }
            }else{
                if (s != null) {
                    ns=s
                }
            }
            return ns
        }


        suspend fun getImage(b: Biljka): Bitmap? {
            val ns = getLatinName(b.naziv)
            val imageId = apiServis.getBiljkaByName(q = ns)
            if(imageId.body()?.data.toString()!="[]") {
                val imageData = apiServis.getImage(id = imageId.body()?.data?.get(0)?.id!!)
                val imageUrl = URL(imageData.body()?.data?.image_url)
                val image = BitmapFactory.decodeStream(withContext(Dispatchers.IO) {
                    imageUrl.openConnection().getInputStream()
                })
                return image
            }
            return null
        }

        suspend fun fixData(b: Biljka): Biljka {
            val ns = getLatinName(b.naziv)
            val fixId = apiServis.getBiljkaByName(q = ns)
            //Log.d("ffixId: ", fixId.body()?.data.toString())
            if( fixId.body()?.data.toString()!="[]") {
                //Log.d("ffixId: ", fixId.body().toString())
                val fixB = apiServis.getBiljkaById(id = fixId.body()?.data?.get(0)?.id!!)
                //Log.d("ffixBiljka: ", fixB.body().toString())
                if (fixB.body() != null) {
                    b.porodica = fixB.body()?.data?.family?.name.toString()
                    if (fixB.body()?.data?.main_species?.edible == false) {
                        b.jela = null
                        b.medicinskoUpozorenje += ", NIJE JESTIVO"
                    }
                    if (fixB.body()?.data?.main_species?.specifications?.toxicity != "none") {
                        b.medicinskoUpozorenje += ", TOKSICNO"
                    }
                    if (fixB.body()?.data?.main_species?.growth?.soil_texture != null) {
                        when (fixB.body()?.data?.main_species?.growth?.soil_texture) {
                            10 -> {
                                b.zemljisniTipovi = listOf(Zemljište.KRECNJACKO)
                            }

                            9 -> {
                                b.zemljisniTipovi = listOf(Zemljište.SLJUNOVITO)
                            }

                            8, 7 -> {
                                b.zemljisniTipovi = listOf(Zemljište.CRNICA)
                            }

                            6, 5 -> {
                                b.zemljisniTipovi = listOf(Zemljište.ILOVACA)
                            }

                            4, 3 -> {
                                b.zemljisniTipovi = listOf(Zemljište.PJESKOVITO)
                            }

                            2, 1 -> {
                                b.zemljisniTipovi = listOf(Zemljište.GLINENO)
                            }
                        }
                    }
                    if (fixB.body()?.data?.main_species?.growth?.light != null && fixB.body()?.data?.main_species?.growth?.atmospheric_humidity != null) {
                        b.klimatskiTipovi = null
                        var dodano=false
                        if (fixB.body()?.data?.main_species?.growth?.light!! in 6..9 && fixB.body()?.data?.main_species?.growth?.atmospheric_humidity!! in 1..5) {
                            if(!dodano) {
                                b.klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA)
                                dodano=true
                            }
                        }
                        if (fixB.body()?.data?.main_species?.growth?.light!! in 8..10 && fixB.body()?.data?.main_species?.growth?.atmospheric_humidity!! in 7..10) {
                            if(!dodano) {
                                b.klimatskiTipovi = listOf(KlimatskiTip.TROPSKA)
                                dodano=true
                            }else{
                                b.klimatskiTipovi = b.klimatskiTipovi?.plus(listOf(KlimatskiTip.TROPSKA))
                            }
                        }
                        if (fixB.body()?.data?.main_species?.growth?.light!! in 6..9 && fixB.body()?.data?.main_species?.growth?.atmospheric_humidity!! in 5..8) {
                            if(!dodano) {
                                b.klimatskiTipovi = listOf(KlimatskiTip.SUBTROPSKA)
                                dodano=true
                            }else{
                                b.klimatskiTipovi = b.klimatskiTipovi?.plus(listOf(KlimatskiTip.SUBTROPSKA))
                            }
                        }
                        if (fixB.body()?.data?.main_species?.growth?.light!! in 4..7 && fixB.body()?.data?.main_species?.growth?.atmospheric_humidity!! in 3..7) {
                            if(!dodano) {
                                b.klimatskiTipovi = listOf(KlimatskiTip.UMJERENA)
                                dodano=true
                            }else{
                                b.klimatskiTipovi = b.klimatskiTipovi?.plus(listOf(KlimatskiTip.UMJERENA))
                            }
                        }
                        if (fixB.body()?.data?.main_species?.growth?.light!! in 7..9 && fixB.body()?.data?.main_species?.growth?.atmospheric_humidity!! in 1..2) {
                            if(!dodano) {
                                b.klimatskiTipovi = listOf(KlimatskiTip.SUHA)
                                dodano=true
                            }else{
                                b.klimatskiTipovi = b.klimatskiTipovi?.plus(listOf(KlimatskiTip.SUHA))
                            }
                        }
                        if (fixB.body()?.data?.main_species?.growth?.light!! in 0..5 && fixB.body()?.data?.main_species?.growth?.atmospheric_humidity!! in 3..7) {
                            if(!dodano) {
                                b.klimatskiTipovi = listOf(KlimatskiTip.PLANINSKA)
                                dodano=true
                            }else{
                                b.klimatskiTipovi = b.klimatskiTipovi?.plus(listOf(KlimatskiTip.PLANINSKA))
                            }
                        }
                    }

                }
            }


            //Log.d("ffixId2: ", fixId.body()?.data.toString())

            b.onlineChecked=true
            return b
        }

        suspend fun getPlantsWithFlowerColor(flower_color:String,substr:String):List<Biljka> {
            //val res = apiServis.getBiljkaByColor(color = flower_color)
            val biljkeSub=apiServis.getBiljkaByName(q=substr)
            //Log.d("color",res.body().toString())
            var ret:List<Biljka> = listOf(Biljka())
            var dodano=false
            for(i in biljkeSub.body()?.data!!){
                val boja=apiServis.getBiljkaFlowerColor(id= i.id!!).body()?.data?.main_species?.flower?.color
                if (boja != null) {
                    for(bojaI in boja) {
                        if (flower_color == bojaI) {
                            val temp1 = Biljka(null,
                                "(" + i.scientific_name + ")",
                            )
                            val temp2 = fixData(temp1)
                            temp2.naziv = i.scientific_name.toString()
                            if (dodano) {
                                ret = ret + listOf(temp2)
                            } else {
                                dodano = true
                                ret = listOf(temp2)
                            }
                        }
                    }
                }
            }

            return ret
        }

}