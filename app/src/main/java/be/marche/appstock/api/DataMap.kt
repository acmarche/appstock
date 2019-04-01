package be.marche.appstock.api

import be.marche.appstock.entity.Categorie
import be.marche.appstock.entity.Produit

data class StockData(
    val categories: List<Categorie>,
    val produits: List<Produit>
)