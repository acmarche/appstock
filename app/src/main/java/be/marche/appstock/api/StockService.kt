package be.marche.appstock.api

import be.marche.appstock.entity.Categorie
import be.marche.appstock.entity.Produit
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface StockService {

    @GET("all")
    suspend fun getAll(
    ): Response<StockData>

    @GET("categories")
    suspend fun getAllCategories(
    ): Response<List<Categorie>>

    @GET("produits")
    suspend fun getAllProduits(
    ): List<Produit>

    @POST("update/{id}/{quantite}")
    suspend fun updateProduit(
        @Path("id") produitId: Int,
        @Path("quantite") quantite: Int
    ): Response<Produit>
}