package be.marche.appstock.produit

import androidx.lifecycle.LiveData
import be.marche.appstock.database.StockDao
import be.marche.appstock.entity.Produit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent

class ProduitRepository(private val stockDao: StockDao) : KoinComponent {

    fun getAllProduits(): LiveData<List<Produit>> {
        return stockDao.getAllProduits()
    }

    fun getProduitById(ficheId: Int): LiveData<Produit> {
        return stockDao.getProduitById(ficheId)
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