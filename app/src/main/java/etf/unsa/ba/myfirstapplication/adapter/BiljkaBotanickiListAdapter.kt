package etf.unsa.ba.myfirstapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import etf.unsa.ba.myfirstapplication.data.Biljka
import etf.unsa.ba.myfirstapplication.R

class BiljkaBotanickiListAdapter(
    private var biljke: List<Biljka>,
    private val listener: RecyclerViewEvent
) : RecyclerView.Adapter<BiljkaBotanickiListAdapter.BiljkeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BiljkeViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.botanickimod, parent, false)
        return BiljkeViewHolder(view)
    }

    override fun getItemCount(): Int = biljke.size

    override fun onBindViewHolder(holder: BiljkeViewHolder, position: Int) {


        holder.biljkaNaziv.text = biljke[position].naziv
        holder.biljkaPorodica.text = biljke[position].porodica
        var zem = biljke[position].klimatskiTipovi?.getOrNull(0)
            ?.opis + " " + biljke[position].klimatskiTipovi?.getOrNull(1)?.opis
        holder.biljkaKlimatskiTip.text = zem
        zem = biljke[position].zemljisniTipovi?.getOrNull(0)
            ?.naziv + " " + biljke[position].zemljisniTipovi?.getOrNull(1)?.naziv
        holder.biljkaZemljisniTip.text = zem
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
        val biljkaPorodica: TextView = itemView.findViewById(R.id.porodicaItem)
        val biljkaKlimatskiTip: TextView = itemView.findViewById(R.id.klimatskiTipItem)
        val biljkaZemljisniTip: TextView = itemView.findViewById(R.id.zemljisniTipItem)


        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onBotanickaItemClick(position)
            }
        }


    }


    interface RecyclerViewEvent {
        fun onBotanickaItemClick(position: Int)
    }


}




