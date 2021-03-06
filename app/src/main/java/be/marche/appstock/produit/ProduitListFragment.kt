package be.marche.appstock.produit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import be.marche.appstock.R
import be.marche.appstock.api.ConnectivityLiveData
import be.marche.appstock.categorie.CategorieViewModel
import be.marche.appstock.entity.Categorie
import be.marche.appstock.entity.Produit
import kotlinx.android.synthetic.main.produit_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProduitListFragment : Fragment(), ProduitListAdapter.ProduitListAdapterListener {

    val produitViewModel: ProduitViewModel by viewModel()
    val categorieViewModel: CategorieViewModel by sharedViewModel()
    var categorie: Categorie? = null

    private var listener: ProduitListAdapter.ProduitListAdapterListener? = null
    private lateinit var produitListAdapter: ProduitListAdapter
    private lateinit var produits: MutableList<Produit>

    companion object {
        fun newInstance() = ProduitListFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(be.marche.appstock.R.layout.produit_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (!::produits.isInitialized) {
            produits = mutableListOf<Produit>()
        }

        listener = this
        produitListAdapter = ProduitListAdapter(produits, listener)

        recyclerViewProduitList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = produitListAdapter
        }

        categorie = categorieViewModel.categorie
        if (categorie != null) {
            activity?.title = categorie!!.nom
            produitViewModel.getProduitsByCategorie(categorie!!).observe(viewLifecycleOwner, Observer { produits ->
                UpdateUi(produits)
            })
        } else {
           produitViewModel.produits.observe(viewLifecycleOwner, Observer { UpdateUi(it) })
        }
    }

    private fun UpdateUi(newproduits: List<Produit>) {
        produits.clear()
        produits.addAll(newproduits)
        produitListAdapter.notifyDataSetChanged()
    }

    override fun onProduitSelected(produit: Produit) {
        Toast.makeText(getActivity(), getString(R.string.help_text), Toast.LENGTH_SHORT).show()
    }

    override fun onBtnLessSelected(produit: Produit) {
        checkInternet(produit, 1)
    }

    override fun onBtnPlusSelected(produit: Produit) {
        checkInternet(produit, 2)
    }

    private fun checkInternet(produit: Produit, action: Int) {

        ConnectivityLiveData(activity?.application).observe(viewLifecycleOwner, Observer { connected ->

            when (connected) {
                true -> {
                    when (action) {
                        1 -> if (produit.quantite > 0) {
                            produitViewModel.saveAsync(produit, produit.quantite - 1)
                        }
                        2 -> produitViewModel.saveAsync(produit, produit.quantite + 1)
                    }
                }
                false -> {
                    Toast.makeText(getActivity(), getString(R.string.message_no_connectivity), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

}