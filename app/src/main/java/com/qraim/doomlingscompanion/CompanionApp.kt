package com.qraim.doomlingscompanion

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException

class CompanionApp : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_companion_app)
        supportActionBar?.hide()

        val adapter = RecyclerViewAdapter(this)
        val cardliste = createcards()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        for (card in cardliste) {
            adapter.ajouter(card)
        }

    }


    fun createcards(): ArrayList<Cards> {
        val cartes = ArrayList<Cards>()
        try {
            val assetsPath = "images" // Le chemin vers le sous-dossier dans 'assets'
            val assetManager = assets
            val fichiers = assetManager.list(assetsPath) ?: arrayOf()

            fichiers.forEach { nomFichier ->
                if (nomFichier.endsWith(".png")) {
                    val nomCarte = nomFichier.substringBeforeLast(".")
                    val cheminImage = "$assetsPath/$nomFichier"
                    val carte = Cards(img = cheminImage, name = nomCarte, str = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur")
                    cartes.add(carte)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return cartes
    }

}

