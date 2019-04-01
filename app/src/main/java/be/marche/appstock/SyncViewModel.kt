package be.marche.appstock

import androidx.lifecycle.ViewModel
import be.marche.appstock.api.StockService
import be.marche.appstock.categorie.CategorieRepository
import be.marche.appstock.produit.ProduitRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SyncViewModel(
    val stockService: StockService,
    val produitRepository: ProduitRepository,
    val categorieRepository: CategorieRepository

) : ViewModel() {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var token: String? = null

    private suspend fun getDetail(login: String) = stockService.getAllProduits().await()

    fun refreshData() {

        viewModelScope.launch {

            val request = stockService.getAll()
            val response = request.await()

            response.let {
                categorieRepository.insertCategories(it.categories)
                produitRepository.insertProduits(it.produits)
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
    
    
}