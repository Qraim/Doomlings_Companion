package com.qraim.doomlingscompanion

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.IOException

class RecyclerViewAdapter(val context : Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.MonViewHolder>() {

    class MonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val card_img = itemView.findViewById<ImageView>(R.id.imageViewCarte)
        val card_name = itemView.findViewById<TextView>(R.id.textViewNomCarte)
        val card_text = itemView.findViewById<TextView>(R.id.textViewTraduction)
    }

    private val cards_liste : ArrayList<Cards> = ArrayList<Cards>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.ligne, parent, false)
        return MonViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cards_liste.size
    }

    override fun onBindViewHolder(holder: MonViewHolder, position: Int) {
        val imgPath = cards_liste[position].getimg()
        val name = cards_liste[position].getname()
        val texte = cards_liste[position].getstr()

        try {
            val assetManager = holder.itemView.context.assets
            val inputStream = assetManager.open(imgPath)
            val cardBitmap = BitmapFactory.decodeStream(inputStream)
            holder.card_img.setImageBitmap(cardBitmap)
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        holder.card_name.text = name
        holder.card_text.text = texte
    }


    fun ajouter(carte : Cards){
        cards_liste.add(carte)
        notifyItemInserted(cards_liste.size-1)
    }

}