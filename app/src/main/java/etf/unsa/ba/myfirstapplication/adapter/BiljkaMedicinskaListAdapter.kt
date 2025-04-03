package etf.unsa.ba.myfirstapplication.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import etf.unsa.ba.myfirstapplication.data.Biljka
import etf.unsa.ba.myfirstapplication.R
import kotlinx.coroutines.android.awaitFrame


class BiljkaMedicinskaListAdapter(
    private var biljke: List<Biljka>,
    private val listener: RecyclerViewEvent
) : RecyclerView.Adapter<BiljkaMedicinskaListAdapter.BiljkeViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BiljkeViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.medicinskimod, parent, false)
        return BiljkeViewHolder(view)
    }

    override fun getItemCount(): Int = biljke.size
    override fun onBindViewHolder(holder: BiljkeViewHolder, position: Int) {
        holder.biljkaNaziv.text = biljke[position].naziv

        holder.biljkaUpozorenje.text = biljke[position].porodica
        holder.biljkaKorist1.text = biljke[position].medicinskeKoristi?.getOrNull(0)?.opis
        holder.biljkaKorist2.text = biljke[position].medicinskeKoristi?.getOrNull(1)?.opis
        holder.biljkaKorist3.text = biljke[position].medicinskeKoristi?.getOrNull(2)?.opis
        Log.d("slika",biljke[position].slika.toString())
        if(biljke[position].slika==null){
            val imageMatch: String = biljke[position].naziv.toString()
            val context: Context = holder.biljkaImage.context
            var id: Int = context.resources
                .getIdentifier(imageMatch, "drawable", context.packageName)
            if (id == 0) id = context.resources
                .getIdentifier("pic1", "drawable", context.packageName)
            holder.biljkaImage.setImageResource(id)
        }else {
            holder.biljkaImage.setImageBitmap(biljke[position].slikaBitmap)
        }
    }


    fun updateBiljke(biljke: List<Biljka>) {
        this.biljke = biljke
        notifyDataSetChanged()
    }


    inner class BiljkeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val biljkaImage: ImageView = itemView.findViewById(R.id.slikaItem)
        val biljkaNaziv: TextView = itemView.findViewById(R.id.nazivItem)
        val biljkaUpozorenje: TextView = itemView.findViewById(R.id.upozorenjeItem)
        val biljkaKorist1: TextView = itemView.findViewById(R.id.korist1Item)
        val biljkaKorist2: TextView = itemView.findViewById(R.id.korist2Item)
        val biljkaKorist3: TextView = itemView.findViewById(R.id.korist3Item)


        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onMedicinskaItemClick(position)
            }
        }


    }


    interface RecyclerViewEvent {
        fun onMedicinskaItemClick(position: Int)
    }

}




