package be.marche.appstock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.marche.appstock.api.StockService
import be.marche.appstock.categorie.CategorieRepository
import be.marche.appstock.produit.ProduitRepository
import kotlinx.coroutines.launch

class SyncViewModel(
    val stockService: StockService,
    val produitRepository: ProduitRepository,
    val categorieRepository: CategorieRepository

) : ViewModel() {

    fun refreshData() {

        viewModelScope.launch {
            val response = stockService.getAll()
            if (response.isSuccessful) {
                response.body()?.let {
                    categorieRepository.insertCategories(it.categories)
                    produitRepository.insertProduits(it.produits)
                }
            }
        }
    }
}