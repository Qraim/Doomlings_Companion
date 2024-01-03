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
import java.util.*
import kotlin.collections.ArrayList

class RecyclerViewAdapter(val context : Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.MonViewHolder>() {

    class MonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val card_img = itemView.findViewById<ImageView>(R.id.imageViewCarte)
        val card_name = itemView.findViewById<TextView>(R.id.textViewNomCarte)
        val card_text = itemView.findViewById<TextView>(R.id.textViewTraduction)
    }

    private val cards_liste : ArrayList<Cards> = ArrayList<Cards>()
    private var filteredList: ArrayList<Cards> = ArrayList(cards_liste)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.ligne, parent, false)
        return MonViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun onBindViewHolder(holder: MonViewHolder, position: Int) {
        val card = filteredList[position]
        val imgPath = card.getimg()
        val name = card.getname()
        val texte = card.getstr()

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
        filteredList.add(carte)
        notifyItemInserted(cards_liste.size-1)
    }

    fun filter(query: String) {
        filteredList = cards_liste.filter {
            it.name.contains(query, ignoreCase = true)
        } as ArrayList<Cards>
        notifyDataSetChanged()
    }

    fun filterByCategoryAndType(gameType: String, categoryType: String) {
        filteredList = if (gameType == "Tout") {
            cards_liste.filter { card ->
                card.category.equals(categoryType, ignoreCase = true) || categoryType == "Tout"
            }
        } else {
            cards_liste.filter { card ->
                card.category.equals(gameType, ignoreCase = true) &&
                        (card.subcategory.equals(categoryType, ignoreCase = true) || categoryType == "Tout")
            }
        } as ArrayList<Cards>
        notifyDataSetChanged()
    }

    fun filterByCategoryAndTypeAndName(gameType: String, categoryType: String, searchQuery: String) {
        filteredList = cards_liste.filter { card ->
            val gameTypeMatch = gameType == "Tout" || card.category.equals(gameType, ignoreCase = true)

            val categoryTypeMatch = categoryType == "Tout" || card.subcategory.equals(categoryType, ignoreCase = true)

            val nameMatch = card.name.contains(searchQuery, ignoreCase = true)

            gameTypeMatch && categoryTypeMatch && nameMatch
        } as ArrayList<Cards>
        notifyDataSetChanged()
    }


}