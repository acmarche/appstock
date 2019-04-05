package be.marche.appstock.database

import androidx.lifecycle.LiveData
import androidx.room.*
import be.marche.appstock.entity.Categorie
import be.marche.appstock.entity.Produit


@Dao
interface StockDao {

    /**
     * CategorieListFragment
     */
    @Query("SELECT * FROM categorie ORDER BY nom ASC")
    fun getAllCategories(): LiveData<List<Categorie>>

    @Query("SELECT * FROM categorie WHERE id = :categorieId")
    fun getCagorieById(categorieId: Int): LiveData<Categorie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategories(categories: List<Categorie>)

    /**
     * Produit
     */
    @Query("SELECT * FROM produit ORDER BY nom ASC")
    fun getAllProduits(): LiveData<List<Produit>>

    @Query("SELECT * FROM produit WHERE id = :produitId")
    fun getProduitById(produitId: Int): LiveData<Produit>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduits(produits: List<Produit>)

    @Update
    fun updateProduit(produit: Produit)

    @Query("SELECT * FROM produit WHERE categorie_id = :categorieId ORDER BY nom ASC")
    fun getProduitsByCategorie(categorieId: Int): LiveData<List<Produit>>


}
