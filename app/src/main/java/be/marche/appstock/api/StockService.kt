package be.marche.appstock.api

import androidx.lifecycle.LiveData
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
    suspend fun getAllProduits(
    ): List<Produit>

    @POST("update/{id}/{quantite}")
    fun updateProduit(
        @Path("id") produitId: Int,
        @Path("quantite") quantite: Int
    ): Call<Produit>
}