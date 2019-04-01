package be.marche.appstock.produit

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import be.marche.appstock.api.StockService
import be.marche.appstock.entity.Produit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class ProduitViewModel(
    val stockService: StockService,
    val produitRepository: ProduitRepository
) : ViewModel() {
    
    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        loadProduits()
    }

    private lateinit var fiches: LiveData<List<Produit>>
    var fiche: LiveData<Produit>? = null

    private fun loadProduits() {
        fiches = produitRepository.getAllProduits()
    }

    fun getProduits(): LiveData<List<Produit>> = fiches

    fun getCagorieById(ficheId: Int): LiveData<Produit> {
        return produitRepository.getProduitById(ficheId)
    }

    fun changeQuantite(produit: Produit, quantite: Int) {
        viewModelScope.launch {
            produit.quantite = quantite
            produitRepository.updateProduit(produit)
        }
    }

    fun saveReal(produit: Produit, quantite: Int) {

        var ok: Boolean = false
        viewModelScope.launch {

            val request = stockService.updateProduit(produit.id, quantite)
            request.enqueue(object : Callback<Produit> {
                override fun onFailure(call: Call<Produit>, t: Throwable) {

                }

                override fun onResponse(call: Call<Produit>, response: Response<Produit>) {
                    response.let {
                        val enfant = it.body()
                        if (enfant != null) {
                            Timber.i("zeze reponse produit ok ${response.body()}")
                            ok = true
                        } else {
                            Timber.i("zeze reponse produit ko ${response.body()}")
                        }
                    }
                }
            })
            if (ok)
                changeQuantite(produit, quantite)
        }

    }

}