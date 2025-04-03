package etf.unsa.ba.myfirstapplication

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import etf.unsa.ba.myfirstapplication.DAO.TrefleDAO
import etf.unsa.ba.myfirstapplication.data.Biljka
import etf.unsa.ba.myfirstapplication.enumClass.KlimatskiTip
import etf.unsa.ba.myfirstapplication.enumClass.MedicinskaKorist
import etf.unsa.ba.myfirstapplication.enumClass.ProfilOkusaBiljke
import etf.unsa.ba.myfirstapplication.enumClass.Zemljište
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NovaBiljkaActivity : AppCompatActivity(), AdapterView.OnItemClickListener {
    private lateinit var medicinskaKoristView: ListView
    private lateinit var klimatskiTipView: ListView
    private lateinit var zemljisniTipView: ListView
    private lateinit var profilOkusaView: ListView
    private lateinit var jelaView: ListView
    private lateinit var slikaBiljke: ImageView

    //liste tipa int su za id selektovan iz odredjenog viewa
    //liste enum tipova su za kreiranje nove biljke
    private lateinit var medPos: List<Int>
    private lateinit var medPosList: List<MedicinskaKorist>
    private lateinit var kliPos: List<Int>
    private lateinit var kliPosList: List<KlimatskiTip>
    private lateinit var zemPos: List<Int>
    private lateinit var zemPosList: List<Zemljište>
    //lista dodanih jela
    private lateinit var jelPosList: List<String>
    //id selektovanog jela
    private var jelSel: Int = -1
    //id selektovanog profila okusa
    private var prOkusPos: Int = -1
    private lateinit var slikaBiljkeBiitmap:Bitmap
    private lateinit var fixN:Biljka

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nova_biljka)

        //Postavka medicinske koristi view i adapter
        medicinskaKoristView = findViewById(R.id.medicinskaKoristLV)
        val izborMedKorist = MedicinskaKorist.entries
        val adapterMedKorist = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_multiple_choice, izborMedKorist
        )
        medicinskaKoristView.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        medicinskaKoristView.adapter = adapterMedKorist
        medicinskaKoristView.onItemClickListener = this

        //Postavka klimatski tip view i adapter
        klimatskiTipView = findViewById(R.id.klimatskiTipLV)
        val izborKliTip = KlimatskiTip.entries
        val adapterKliTip = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_multiple_choice, izborKliTip
        )
        klimatskiTipView.adapter = adapterKliTip
        klimatskiTipView.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        klimatskiTipView.onItemClickListener = this

        //Postavka zemljisni tip view i adapter
        zemljisniTipView = findViewById(R.id.zemljisniTipLV)
        val izborZemTip = Zemljište.entries
        val adapterZemTip = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_multiple_choice, izborZemTip
        )
        zemljisniTipView.adapter = adapterZemTip
        zemljisniTipView.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        zemljisniTipView.onItemClickListener = this


        //Postavka profil okusa view i adapter
        profilOkusaView = findViewById(R.id.profilOkusaLV)
        val izborProOkusa = ProfilOkusaBiljke.entries
        val adapterProOkusa = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_single_choice, izborProOkusa
        )
        profilOkusaView.adapter = adapterProOkusa
        profilOkusaView.choiceMode = ListView.CHOICE_MODE_SINGLE
        profilOkusaView.onItemClickListener = this



        //declaracija jela view i adaptera
        jelaView = findViewById(R.id.jelaLV)
        var adapterJela: ArrayAdapter<String>



        //Postavljane defaultne slike
        slikaBiljke = findViewById(R.id.slikaIV)
        val context: Context = this.slikaBiljke.context
        val id: Int = context.resources
            .getIdentifier("pic1", "drawable", context.packageName)
        slikaBiljke.setImageResource(id)

        //praznjenje listi
        medPos = emptyList()
        medPosList = emptyList()
        kliPos = emptyList()
        kliPosList = emptyList()
        zemPos = emptyList()
        zemPosList = emptyList()
        jelPosList = emptyList()

        //Dodaj biljku dugme
        findViewById<Button>(R.id.dodajBiljkuBtn).setOnClickListener {

            //upis edit textova u stringove
            val novaNaziv = findViewById<EditText>(R.id.nazivET).text.toString()
            val novaPorodica = findViewById<EditText>(R.id.porodicaET).text.toString()
            val novaMedUpozorenje =
                findViewById<EditText>(R.id.medicinskoUpozorenjeET).text.toString()

            //validacija naziva, porodice i upozorenja
            if (!checkEditText(novaNaziv)) {
                findViewById<EditText>(R.id.nazivET).setError("naziv $novaNaziv nije prihvatljiv")
            } else if (!checkEditText(novaPorodica)) {
                findViewById<EditText>(R.id.porodicaET).setError("porodica $novaPorodica nije prihvatljiva")
            } else if (!checkEditText(novaMedUpozorenje)) {
                findViewById<EditText>(R.id.medicinskoUpozorenjeET).setError("medicinsko upozorenje $novaMedUpozorenje nije prihvatljivo")
            }
            //provjera da li su medicinska korist, klimatski tip, zemljisni tip, profil okusa selektovani
            else if (medPos.isEmpty()) {
                Toast.makeText(
                    applicationContext,
                    "medicinska korist mora bit selectovana",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (kliPos.isEmpty()) {
                Toast.makeText(
                    applicationContext,
                    "klimatski tip mora bit selectovana",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (zemPos.isEmpty()) {
                Toast.makeText(
                    applicationContext,
                    "zemljisni tip mora bit selectovana",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (prOkusPos == -1) {
                Toast.makeText(
                    applicationContext,
                    "profil okusa mora bit selectovana",
                    Toast.LENGTH_SHORT
                ).show()
            }
            //provjera da je dodato barem jedno jelo
            else if (jelPosList.isEmpty()) {
                Toast.makeText(
                    applicationContext,
                    "trebate dodati barem jedno jelo",
                    Toast.LENGTH_SHORT
                ).show()
            }
            // kreiranje nove varijable tipa Biljka i vracanje u main
            else {
                //kreiranje list enum tipova od listi id-eva
                medPos.forEach() {
                    medPosList = medPosList + listOf(MedicinskaKorist.entries[it])
                }
                kliPos.forEach() {
                    kliPosList = kliPosList + listOf(KlimatskiTip.entries[it])
                }
                zemPos.forEach() {
                    zemPosList = zemPosList + listOf(Zemljište.entries[it])
                }

                val novaBiljka = Biljka(null,
                    novaNaziv,
                    novaPorodica,
                    novaMedUpozorenje,
                    medPosList,
                    ProfilOkusaBiljke.entries[prOkusPos],
                    jelPosList,
                    kliPosList,
                    zemPosList,
                    null
                )
                GlobalScope.launch {
                    fixN=TrefleDAO().fixData(novaBiljka)
                Log.d("nova", fixN.toString())
                val intent = Intent()
                intent.putExtra("ActivityResult", fixN)
                setResult(RESULT_OK, intent)
                finish()
                }
            }


        }

        //Dodaj jelo dugme
        findViewById<Button>(R.id.dodajJeloBtn).setOnClickListener {

            val novaJelo = findViewById<EditText>(R.id.jeloET).text.toString()
            //provjera da li je jelo validno
            var provjera = !checkEditText(novaJelo)
            if(provjera){
                Toast.makeText(
                    applicationContext,
                    "jelo $novaJelo nije prihvatljivo",
                    Toast.LENGTH_SHORT
                ).show()
            }else {
                //provjera da li je jelo vec u listi
                for (s: String in jelPosList) {
                    if (s.lowercase() == novaJelo.lowercase()) {
                        provjera = true
                        break
                    }
                }

                if (provjera) {
                    Toast.makeText(
                        applicationContext,
                        "jelo $novaJelo je vec u listi",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    //na osnovu jelSel tj id selektovanog jela i vrijednosti u edit textu dodajemo/brisemo/mijenajom jelo u listi
                    jelPosList = if (jelSel == -1) {
                        jelPosList + listOf(novaJelo)

                    } else if (novaJelo == "") {
                        jelPosList - listOf(jelPosList[jelSel])
                    } else {
                        jelPosList - listOf(jelPosList[jelSel]) + listOf(novaJelo)
                    }

                    //postavljanje/osvjezavanje jelo view i adaptera, vraca text dugmeta na "Dodaj jelo" i id na -1
                    adapterJela = ArrayAdapter(
                        this,
                        android.R.layout.simple_list_item_single_choice, jelPosList
                    )
                    jelaView.adapter = adapterJela
                    jelaView.choiceMode = ListView.CHOICE_MODE_SINGLE
                    jelaView.onItemClickListener = this
                    jelSel = -1
                    findViewById<EditText>(R.id.jeloET).text.clear()
                    findViewById<Button>(R.id.dodajJeloBtn).text = "Dodaj jelo"
                }
            }

        }

        //Nazad dugme
        findViewById<Button>(R.id.nazadBtn).setOnClickListener {
            finish()
        }

        //uslikaj biljku dugme
        findViewById<Button>(R.id.uslikajBiljkuBtn).setOnClickListener { v: View? ->
            // Create the camera_intent ACTION_IMAGE_CAPTURE it will open the camera for capture the image
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            // Start the activity with camera_intent, and request pic id
            startActivityForResult(cameraIntent, pic_id)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //Kamera Activity/Intent
        if (requestCode == pic_id) {
            // BitMap is data structure of image file which store the image in memory
            slikaBiljkeBiitmap = (data!!.extras!!["data"] as Bitmap?)!!
            // Set the image in imageview for display
            Log.d("slika", slikaBiljkeBiitmap.toString())
            slikaBiljke.setImageBitmap(slikaBiljkeBiitmap)
        }
    }

    companion object {
        // Define the pic id
        private const val pic_id = 123
    }


    //provjera duzine stringova
    private fun checkEditText(s: String): Boolean {
        return !(s.length <= 2 || s.length >= 20)
    }

    //selectovanje itema u list viewovima
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent != null && view != null) {
            //za svaki view posebno dodajemo/oduzimamo id selektovanog/deselektovanog itema
            if (parent.toString().contains("medicin")) {
                medPos = if (medPos.contains(position)) {
                    medPos - listOf(position)
                } else {
                    medPos + listOf(position)
                }
            }
            if (parent.toString().contains("klima")) {
                kliPos = if (kliPos.contains(position)) {
                    kliPos - listOf(position)
                } else {
                    kliPos + listOf(position)
                }
            }
            if (parent.toString().contains("zemljisni")) {
                zemPos = if (zemPos.contains(position)) {
                    zemPos - listOf(position)
                } else {
                    zemPos + listOf(position)
                }
            }
            //profil okusa samo mijenja vrijednost selektovanog id, ne moze se deselektovati
            if (parent.toString().contains("profil")) {
                prOkusPos = position
            }
            //nova jela view
            if (parent.toString().contains("jela")) {
                val izmjene = findViewById<Button>(R.id.dodajJeloBtn)
                //provjeravamo jel selekcija ili deselekcija jela
                if (jelSel == position) {
                    //ako je deselekcija vracamo id na -1, text dugmeta na "Dodaj jelo" i osvjezavamo jela view i adapter
                    jelSel = -1
                    izmjene.text = "Dodaj jelo"
                    jelaView.clearChoices()
                    val adapterJela = ArrayAdapter(
                        this,
                        android.R.layout.simple_list_item_single_choice, jelPosList
                    )
                    jelaView.adapter = adapterJela
                    jelaView.choiceMode = ListView.CHOICE_MODE_SINGLE
                    jelaView.onItemClickListener = this
                } else {
                    //ako je selekcija mijenjamo id na selektovani i text dugmeta na "Izmjeni jelo"
                    jelSel = position
                    izmjene.text = "Izmijeni jelo"
                }

            }

        }

    }


}





