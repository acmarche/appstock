package be.marche.appstock.api

import be.marche.appstock.entity.Categorie
import be.marche.appstock.entity.Produit
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface StockService {

    @GET("all")
    fun getAll(
    ): Deferred<StockData>

    @GET("categories")
    fun getAllCategories(
    ): Deferred<List<Categorie>>

    @GET("produits")
    fun getAllProduits(
    ): Deferred<List<Produit>>

    @POST("update/{id}")
    fun updateProduit(
        @Path("id") produitId: Int,
        @Path("quantite") quantite: Int
    ): Call<Produit>
}