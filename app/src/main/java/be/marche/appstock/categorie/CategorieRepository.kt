package be.marche.appstock.categorie

import androidx.lifecycle.LiveData
import be.marche.appstock.database.StockDao
import be.marche.appstock.entity.Categorie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent

class CategorieRepository(private val stockDao: StockDao) : KoinComponent {

    fun getAllCategories(): LiveData<List<Categorie>> {
        return stockDao.getAllCategories()
    }

    fun getCagorieById(ecoldeId: Int): LiveData<Categorie> {
        return stockDao.getCagorieById(ecoldeId)
    }

    suspend fun insertCategories(categories: List<Categorie>) {
        withContext(Dispatchers.IO) {
            stockDao.insertCategories(categories)
        }
    }
}