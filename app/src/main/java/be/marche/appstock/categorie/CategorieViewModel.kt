package be.marche.appstock.categorie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import be.marche.appstock.api.StockService
import be.marche.appstock.entity.Categorie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class CategorieViewModel(
    val stockService: StockService,
    val categorieRepository: CategorieRepository
) : ViewModel(
) {

    init {
        loadCategories()
    }

    private lateinit var categories: LiveData<List<Categorie>>
    var categorie: Categorie? = null

    private fun loadCategories() {
        categories = categorieRepository.getAllCategories()
    }

    fun getCategories(): LiveData<List<Categorie>> = categories

    fun getCagorieById(categorieId: Int): LiveData<Categorie> {
        return categorieRepository.getCagorieById(categorieId)
    }
}