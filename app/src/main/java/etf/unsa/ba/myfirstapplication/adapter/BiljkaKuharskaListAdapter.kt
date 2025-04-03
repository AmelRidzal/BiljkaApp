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


class BiljkaKuharskaListAdapter(
    private var biljke: List<Biljka>,
    private val listener: RecyclerViewEvent
) : RecyclerView.Adapter<BiljkaKuharskaListAdapter.BiljkeViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BiljkeViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.kuharskimod, parent, false)
        return BiljkeViewHolder(view)
    }

    override fun getItemCount(): Int = biljke.size
    override fun onBindViewHolder(holder: BiljkeViewHolder, position: Int) {
        holder.biljkaNaziv.text = biljke[position].naziv

        holder.biljkaProfilOkusa.text = biljke[position].profilOkusa!!.opis
        holder.biljkaJelo1Tip.text = biljke[position].jela?.getOrNull(0)?.toString()
        holder.biljkaJelo2Tip.text = biljke[position].jela?.getOrNull(1)?.toString()
        holder.biljkaJelo3Tip.text = biljke[position].jela?.getOrNull(2)?.toString()
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
        val biljkaProfilOkusa: TextView = itemView.findViewById(R.id.profilOkusaItem)
        val biljkaJelo1Tip: TextView = itemView.findViewById(R.id.jelo1Item)
        val biljkaJelo2Tip: TextView = itemView.findViewById(R.id.jelo2Item)
        val biljkaJelo3Tip: TextView = itemView.findViewById(R.id.jelo3Item)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onKuharskaItemClick(position)
            }
        }


    }


    interface RecyclerViewEvent {
        fun onKuharskaItemClick(position: Int)
    }


}




