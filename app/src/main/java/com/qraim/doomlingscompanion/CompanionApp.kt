package com.qraim.doomlingscompanion

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.IOException

class CompanionApp : AppCompatActivity() {


    lateinit var cardliste : ArrayList<Cards>
    lateinit var adapter : RecyclerViewAdapter
    private lateinit var spinnerCategory: Spinner
    private lateinit var spinnerSubcategory: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_companion_app)
        supportActionBar?.hide()

        val spinnerGameType: Spinner = findViewById(R.id.spinnerGameType)
        val gameTypes = arrayOf("Tout", "Jeux de base", "Dinolings", "Meaning of life", "Multi-Color", "Mythlings", "Techlings")
        val gameTypeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, gameTypes)
        spinnerGameType.adapter = gameTypeAdapter

        val spinnerCategoryType: Spinner = findViewById(R.id.spinnerCategoryType)
        val categoryTypes = arrayOf("Trait", "Ages")
        val categoryTypeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryTypes)
        spinnerCategoryType.adapter = categoryTypeAdapter

        val editText = findViewById<EditText>(R.id.editText)
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val searchQuery = s.toString()
                val selectedGameType = spinnerGameType.selectedItem.toString()
                val selectedCategoryType = spinnerCategoryType.selectedItem.toString()
                adapter.filterByCategoryAndTypeAndName(selectedGameType, selectedCategoryType, searchQuery)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        spinnerGameType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedGameType = parent.getItemAtPosition(position).toString()
                if (selectedGameType == "Tout") {
                    // Si "Tout" est sélectionné, videz et désactivez le second spinner
                    spinnerCategoryType.adapter = null
                    spinnerCategoryType.isEnabled = false
                    adapter.filterByCategoryAndTypeAndName(selectedGameType, "", "")
                    findViewById<Button>(R.id.buttonSearch).performClick()
                } else {
                    // Réactivez et remplissez le second spinner si ce n'est pas "Tout"
                    spinnerCategoryType.isEnabled = true
                    spinnerCategoryType.adapter = categoryTypeAdapter

                    val selectedCategoryType = spinnerCategoryType.selectedItem.toString()
                    adapter.filterByCategoryAndTypeAndName(selectedGameType, selectedCategoryType, editText.text.toString())
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }


        spinnerCategoryType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedCategoryType = parent.getItemAtPosition(position).toString()
                val selectedGameType = spinnerGameType.selectedItem.toString()
                adapter.filterByCategoryAndType(selectedGameType, selectedCategoryType)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

        val searchButton = findViewById<Button>(R.id.buttonSearch)
        val searchText = findViewById<EditText>(R.id.editText)

        searchButton.setOnClickListener {
            val query = searchText.text.toString()
            adapter.filter(query)
        }


        adapter = RecyclerViewAdapter(this)
        cardliste = createAllCards()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        for (card in cardliste) {
            adapter.ajouter(card)
        }

    }


    fun createAllCards(): ArrayList<Cards> {
        val allCards = ArrayList<Cards>()

        allCards.addAll(createBaseGameCards())

        allCards.addAll(createExtensionCards())

        return allCards
    }

    fun createBaseGameCards(): ArrayList<Cards> {
        val baseGameCards = ArrayList<Cards>()
        val basePath = "img/Jeux de base"

        try {
            val baseFolders = assets.list(basePath) ?: arrayOf()
            for (subfolder in baseFolders) {
                val files = assets.list("$basePath/$subfolder") ?: arrayOf()
                for (filename in files) {
                    if (filename.endsWith(".png")) {
                        val cardName = filename.substringBeforeLast(".")
                        val imagePath = "$basePath/$subfolder/$filename"
                        val card = Cards(
                            img = imagePath,
                            name = cardName,
                            str = "Card description here",
                            category = "Jeux de base",
                            subcategory = subfolder
                        )
                        baseGameCards.add(card)
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return baseGameCards
    }

    fun createExtensionCards(): ArrayList<Cards> {
        val extensionCards = ArrayList<Cards>()
        val extensionsPath = "img/Extensions"

        try {
            val extensionFolders = assets.list(extensionsPath) ?: arrayOf()
            for (folder in extensionFolders) {
                val subfolders = assets.list("$extensionsPath/$folder") ?: arrayOf()
                for (subfolder in subfolders) {
                    val files = assets.list("$extensionsPath/$folder/$subfolder") ?: arrayOf()
                    for (filename in files) {
                        if (filename.endsWith(".png")) {
                            val cardName = filename.substringBeforeLast(".")
                            val imagePath = "$extensionsPath/$folder/$subfolder/$filename"
                            val card = Cards(
                                img = imagePath,
                                name = cardName,
                                str = "Card description here",
                                category = folder,
                                subcategory = subfolder
                            )
                            extensionCards.add(card)
                        }
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return extensionCards
    }


}

