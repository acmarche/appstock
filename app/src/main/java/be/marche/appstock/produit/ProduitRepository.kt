package be.marche.appstock.produit

import androidx.lifecycle.LiveData
import be.marche.appstock.database.StockDao
import be.marche.appstock.entity.Categorie
import be.marche.appstock.entity.Produit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent

class ProduitRepository(private val stockDao: StockDao) : KoinComponent {

    suspend fun getAllProduits(): LiveData<List<Produit>> {
        return stockDao.getAllProduits()
    }

    fun getAllProduits2(): List<Produit> {
        return stockDao.getAllProduits2()
    }

    fun getProduitById(ficheId: Int): LiveData<Produit> {
        return stockDao.getProduitById(ficheId)
    }

    fun getProduitsByCategorie(categorie: Categorie): LiveData<List<Produit>> {
        return stockDao.getProduitsByCategorie(categorie.id)
    }

    suspend fun insertProduits(fiches: List<Produit>) {
        withContext(Dispatchers.IO) {
            stockDao.insertProduits(fiches)
        }
    }

    suspend fun updateProduit(produit: Produit) {
        withContext(Dispatchers.IO) {
            stockDao.updateProduit(produit)
        }
    }
}