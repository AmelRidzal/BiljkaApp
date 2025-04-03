package etf.unsa.ba.myfirstapplication


import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteDatabase
import etf.unsa.ba.myfirstapplication.DAO.BiljkaDAO
import etf.unsa.ba.myfirstapplication.DAO.BiljkaDatabase
import etf.unsa.ba.myfirstapplication.DAO.TrefleDAO
import etf.unsa.ba.myfirstapplication.adapter.BiljkaBotanickiListAdapter
import etf.unsa.ba.myfirstapplication.adapter.BiljkaKuharskaListAdapter
import etf.unsa.ba.myfirstapplication.adapter.BiljkaMedicinskaListAdapter
import etf.unsa.ba.myfirstapplication.data.Biljka
import etf.unsa.ba.myfirstapplication.data.getBiljke
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), BiljkaMedicinskaListAdapter.RecyclerViewEvent,
    BiljkaKuharskaListAdapter.RecyclerViewEvent, BiljkaBotanickiListAdapter.RecyclerViewEvent {

    private lateinit var biljkaView: RecyclerView
    private lateinit var biljkaMedicinskiAdapter: BiljkaMedicinskaListAdapter
    private lateinit var biljkaKuharskiAdapter: BiljkaKuharskaListAdapter
    private lateinit var biljkaBotanickiListAdapter: BiljkaBotanickiListAdapter

    //lista biljaka za prikaz
    private var biljkaLista = getBiljke()

    //lista svih biljaka
    private var sveBiljkaLista = getBiljke()

    //id za trenutni mod
    private var currentMod = 0
    private lateinit var bojaSpin: Spinner
    private lateinit var pretragaEt: EditText


    lateinit var biljkaDatabase: BiljkaDatabase
    lateinit var biljkaDao: BiljkaDAO
    private var context :Context=this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        biljkaView = findViewById(R.id.biljkeRV)
        biljkaView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        var mPref  = getPreferences(MODE_PRIVATE);
        var c: Int = mPref.getInt("numRun", 0)
        c++
        mPref.edit().putInt("numRun", c).apply()
        Log.d("here", c.toString())


            GlobalScope.launch {
                // if(c==1) {
                //biljkaDatabase =Room.inMemoryDatabaseBuilder(context, BiljkaDatabase::class.java).build()
               // }else {
                biljkaDatabase =BiljkaDatabase.getInstance(context)
                biljkaDao = biljkaDatabase.biljkaDao()
              //  }
               // if(c==2) {
                    for (b in biljkaLista) {
                        b.slikaBitmap=TrefleDAO().getImage(b)
                        biljkaDao.saveBiljka(b)
                    }
              //  }
                biljkaLista=biljkaDao.getAllBiljkas()
                for (b in biljkaLista) {
                    b.id?.let { b.slikaBitmap?.let { it1 -> biljkaDao.addImage(it, it1) } }
                }
                withContext(Dispatchers.Main) {
                    resetBiljkeRV()
                }
            }


        biljkaMedicinskiAdapter = BiljkaMedicinskaListAdapter(listOf(), this)
        biljkaView.adapter = biljkaMedicinskiAdapter
        biljkaMedicinskiAdapter.updateBiljke(biljkaLista)





        //Reset dugme
        findViewById<Button>(R.id.resetBtn).setOnClickListener {

            biljkaLista = sveBiljkaLista
            resetBiljkeRV()

        }

        //Nova biljka dugme
        findViewById<Button>(R.id.novaBiljkaBtn).setOnClickListener {
            val intent = Intent(this, NovaBiljkaActivity::class.java)
            startActivityForResult(intent, 1)
        }

        //Postavka spinera
        val izborBilja = resources.getStringArray(R.array.izborBilja)
        val spinner = findViewById<Spinner>(R.id.modSpinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, izborBilja
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    //Odabir modova za spiner
                    when (position) {
                        0 -> {
                            biljkaMedicinskiAdapter =
                                BiljkaMedicinskaListAdapter(listOf(), this@MainActivity)
                            biljkaView.adapter = biljkaMedicinskiAdapter
                            biljkaMedicinskiAdapter.updateBiljke(biljkaLista)
                            bojaSpin.setEnabled(false)
                            bojaSpin.visibility = INVISIBLE
                            pretragaEt.setEnabled(false)
                            pretragaEt.visibility = INVISIBLE

                        }

                        1 -> {
                            biljkaKuharskiAdapter =
                                BiljkaKuharskaListAdapter(listOf(), this@MainActivity)
                            biljkaView.adapter = biljkaKuharskiAdapter
                            biljkaKuharskiAdapter.updateBiljke(biljkaLista)
                            bojaSpin.setEnabled(false)
                            bojaSpin.visibility = INVISIBLE
                            pretragaEt.setEnabled(false)
                            pretragaEt.visibility = INVISIBLE
                        }

                        2 -> {
                            biljkaBotanickiListAdapter =
                                BiljkaBotanickiListAdapter(listOf(), this@MainActivity)
                            biljkaView.adapter = biljkaBotanickiListAdapter
                            biljkaBotanickiListAdapter.updateBiljke(biljkaLista)
                            bojaSpin.setEnabled(true)
                            bojaSpin.visibility = VISIBLE
                            pretragaEt.setEnabled(true)
                            pretragaEt.visibility = VISIBLE
                        }
                    }
                    currentMod = position

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

        bojaSpin = findViewById(R.id.bojaSPIN)
        bojaSpin.setEnabled(false)
        bojaSpin.visibility = GONE
        pretragaEt = findViewById(R.id.pretragaET)
        pretragaEt.setEnabled(false)
        pretragaEt.visibility = GONE
        val izborBoja = resources.getStringArray(R.array.izborBoja)
        if (bojaSpin != null) {
            val adapter2 = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, izborBoja
            )
            bojaSpin.adapter = adapter2

            bojaSpin.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    //Odabir modova za spiner


                    GlobalScope.launch {
                        withContext(Dispatchers.Main) {
                            if (currentMod == 2) {
                                Log.d("flowerSelect", pretragaEt.text.toString() + " " + position)
                                when (position) {
                                    0 -> {
                                        biljkaLista = TrefleDAO().getPlantsWithFlowerColor(
                                            "red",
                                            pretragaEt.text.toString()
                                        )
                                    }

                                    1 -> {
                                        biljkaLista = TrefleDAO().getPlantsWithFlowerColor(
                                            "blue",
                                            pretragaEt.text.toString()
                                        )
                                    }

                                    2 -> {
                                        biljkaLista = TrefleDAO().getPlantsWithFlowerColor(
                                            "yellow",
                                            pretragaEt.text.toString()
                                        )
                                    }

                                    3 -> {
                                        biljkaLista = TrefleDAO().getPlantsWithFlowerColor(
                                            "orange",
                                            pretragaEt.text.toString()
                                        )
                                    }

                                    4 -> {
                                        biljkaLista = TrefleDAO().getPlantsWithFlowerColor(
                                            "purple",
                                            pretragaEt.text.toString()
                                        )
                                    }

                                    5 -> {
                                        biljkaLista = TrefleDAO().getPlantsWithFlowerColor(
                                            "brown",
                                            pretragaEt.text.toString()
                                        )
                                    }

                                    6 -> {
                                        biljkaLista = TrefleDAO().getPlantsWithFlowerColor(
                                            "green",
                                            pretragaEt.text.toString()
                                        )
                                    }
                                }
                                Log.d("flowerSlike", biljkaLista.toString())
                               /* for (b: Biljka in biljkaLista) {
                                    b.slika = TrefleDAO.getImage(b)
                                }*/
                                Log.d("flowerReset", biljkaLista.toString())
                                resetBiljkeRV()
                            }

                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Nova biljka Activity/Intent
        if (requestCode == 1) {

            // The result code from the activity started using startActivityForResults
            if (resultCode == Activity.RESULT_OK) {
                val ret = data?.getSerializableExtra("ActivityResult") as Biljka
                Log.d("novaDodanaBiljka", ret.toString())
                GlobalScope.launch {
                    ret.slikaBitmap = TrefleDAO().getImage(ret)
                    biljkaDao.saveBiljka(ret)
                }

                sveBiljkaLista = sveBiljkaLista + listOf(ret)
                biljkaLista = biljkaLista + listOf(ret)
                resetBiljkeRV()
            }
        }
    }

    //Medicinski mod filter
    override fun onMedicinskaItemClick(position: Int) {
        var zajednicko: Boolean
        val selectovanaBiljka = biljkaLista[position]
        var zaIzbacit = mutableListOf(0)
        zaIzbacit.removeAt(0)
        for (i in biljkaLista.indices) {
            zajednicko = false
            for (j in 0..<(selectovanaBiljka.medicinskeKoristi!!.size)) {
                for (k in 0..<biljkaLista[i].medicinskeKoristi!!.size) {
                    if (selectovanaBiljka.medicinskeKoristi!![j] == biljkaLista[i].medicinskeKoristi!![k]) {
                        zajednicko = true
                        break
                    }
                }
                if (zajednicko) {
                    break
                }
            }
            if (!zajednicko) {
                zaIzbacit.add(i)
            }
        }
        zaIzbacit = zaIzbacit.reversed().toMutableList()
        for (i in zaIzbacit) {
            biljkaLista = biljkaLista.filterIndexed { index, _ ->
                index != i
            }
        }
        Log.d("izbaci", zaIzbacit.toString())
        biljkaMedicinskiAdapter.updateBiljke(biljkaLista)


    }


    //Kuharski mod filter
    override fun onKuharskaItemClick(position: Int) {
        var zajednicko: Boolean
        val selectovanaBiljka = biljkaLista[position]
        var zaIzbacit = mutableListOf(0)
        zaIzbacit.removeAt(0)
        for (i in biljkaLista.indices) {
            zajednicko = false
            for (j in 0..<selectovanaBiljka.jela!!.size) {
                for (k in 0..<biljkaLista[i].jela!!.size) {
                    if (selectovanaBiljka.jela!![j] == biljkaLista[i].jela!![k]) {
                        zajednicko = true
                        break
                    }
                }
                if (zajednicko) {
                    break
                }
            }
            if (!zajednicko) {
                if (selectovanaBiljka.profilOkusa != biljkaLista[i].profilOkusa)
                    zaIzbacit.add(i)
            }
        }
        zaIzbacit = zaIzbacit.reversed().toMutableList()
        for (i in zaIzbacit) {
            biljkaLista = biljkaLista.filterIndexed { index, _ ->
                index != i // you can also specify more interesting filters here...
            } // filter, map, etc. all return you a new list. If that list must be mutable again, just add a .toMutableList() at the end
        }
        Log.d("izbaci", zaIzbacit.toString())
        biljkaKuharskiAdapter.updateBiljke(biljkaLista)
    }

    //Botanicki mod filter
    override fun onBotanickaItemClick(position: Int) {
        var zajednicko1: Boolean
        var zajednicko2: Boolean
        val selectovanaBiljka = biljkaLista[position]
        var zaIzbacit = mutableListOf(0)
        zaIzbacit.removeAt(0)
        for (i in biljkaLista.indices) {
            zajednicko1 = false
            zajednicko2 = false
            for (j in 0..<selectovanaBiljka.zemljisniTipovi!!.size) {
                for (k in 0..<biljkaLista[i].zemljisniTipovi!!.size) {
                    if (selectovanaBiljka.zemljisniTipovi!![j] == biljkaLista[i].zemljisniTipovi!![k]) {
                        zajednicko1 = true
                        break
                    }
                }
                if (zajednicko1) {
                    break
                }
            }
            for (j in 0..<selectovanaBiljka.klimatskiTipovi!!.size) {
                for (k in 0..<biljkaLista[i].klimatskiTipovi!!.size) {
                    if (selectovanaBiljka.klimatskiTipovi!![j] == biljkaLista[i].klimatskiTipovi!![k]) {
                        zajednicko2 = true
                        break
                    }
                }
                if (zajednicko2) {
                    break
                }
            }
            if (zajednicko1 && zajednicko2) {
                if (selectovanaBiljka.porodica != biljkaLista[i].porodica)
                    zaIzbacit.add(i)
            } else {
                zaIzbacit.add(i)
            }
        }
        zaIzbacit = zaIzbacit.reversed().toMutableList()
        for (i in zaIzbacit) {
            biljkaLista = biljkaLista.filterIndexed { index, _ ->
                index != i // you can also specify more interesting filters here...
            } // filter, map, etc. all return you a new list. If that list must be mutable again, just add a .toMutableList() at the end
        }
        Log.d("izbaci", zaIzbacit.toString())
        biljkaBotanickiListAdapter.updateBiljke(biljkaLista)
    }


    private fun resetBiljkeRV() {
        when (currentMod) {
            0 -> {
                biljkaMedicinskiAdapter.updateBiljke(biljkaLista)
                bojaSpin.setEnabled(false)
                bojaSpin.visibility = INVISIBLE
                pretragaEt.setEnabled(false)
                pretragaEt.visibility = INVISIBLE
            }

            1 -> {
                biljkaKuharskiAdapter.updateBiljke(biljkaLista)
                bojaSpin.setEnabled(false)
                bojaSpin.visibility = INVISIBLE
                pretragaEt.setEnabled(false)
                pretragaEt.visibility = INVISIBLE
            }

            2 -> {
                biljkaBotanickiListAdapter.updateBiljke(biljkaLista)
                bojaSpin.setEnabled(true)
                bojaSpin.visibility = VISIBLE
                pretragaEt.setEnabled(true)
                pretragaEt.visibility = VISIBLE
            }
        }
    }



}





