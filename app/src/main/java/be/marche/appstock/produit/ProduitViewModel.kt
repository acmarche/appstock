package be.marche.appstock.produit

import androidx.lifecycle.*
import be.marche.appstock.api.StockService
import be.marche.appstock.entity.Categorie
import be.marche.appstock.entity.Produit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class ProduitViewModel(
    val stockService: StockService,
    val produitRepository: ProduitRepository
) : ViewModel() {

    // from livedata-2.2.0-alpha01
    var produits = liveData(Dispatchers.IO) {
        val emps = produitRepository.getAllProduits2()
        emit(emps)
    }

    var employee3 = liveData(Dispatchers.IO) {
        emitSource(produitRepository.getAllProduits())
        val lastProduits = stockService.getAllProduits()
        produitRepository.insertProduits(lastProduits)
    }

    fun getProduitById(produitId: Int): LiveData<Produit> {
        return produitRepository.getProduitById(produitId)
    }

    fun getProduitsByCategorie(categorie: Categorie): LiveData<List<Produit>> {
        return produitRepository.getProduitsByCategorie(categorie)
    }

    fun changeQuantite(produit: Produit, quantite: Int) {
        viewModelScope.launch {
            produit.quantite = quantite
            produitRepository.updateProduit(produit)
        }
    }

    fun saveAsync(produit: Produit, quantite: Int) {

        var ok = false
        viewModelScope.launch {

            val request = stockService.updateProduit(produit.id, quantite)
            request.enqueue(object : Callback<Produit> {
                override fun onFailure(call: Call<Produit>, t: Throwable) {

                }

                override fun onResponse(call: Call<Produit>, response: Response<Produit>) {
                    response.let {
                        val quantiteResponse = it.body()
                        if (quantiteResponse != null) {
                            Timber.i("zeze reponse produit ok ${response.body()}")
                            ok = true
                        } else {
                            Timber.i("zeze reponse produit ko ${response.body()}")
                        }
                    }
                    if (ok)
                        changeQuantite(produit, quantite)
                }
            })
        }
    }
}