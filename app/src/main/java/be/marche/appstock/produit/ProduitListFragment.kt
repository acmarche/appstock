package be.marche.appstock.produit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import be.marche.appstock.entity.Produit
import kotlinx.android.synthetic.main.produit_item.*
import kotlinx.android.synthetic.main.produit_item.view.*
import kotlinx.android.synthetic.main.produit_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ProduitListFragment : Fragment(), ProduitListAdapter.ProduitListAdapterListener {

    val produitViewModel: ProduitViewModel by viewModel()

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
            produits = mutableListOf()
        }

        listener = this
        produitListAdapter = ProduitListAdapter(produits, listener)

        recyclerViewProduitList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = produitListAdapter
        }
        //viewLifecycleOwner au lieu de this
        // LiveData supprime les observateurs chaque fois que la vue du fragment est détruite:
        produitViewModel.getProduits().observe(viewLifecycleOwner, Observer { UpdateUi(it) })
    }

    private fun UpdateUi(newproduits: List<Produit>) {
        produits.clear()
        produits.addAll(newproduits)
        produitListAdapter.notifyDataSetChanged()
    }

    override fun onProduitSelected(produit: Produit) {
        Toast.makeText(getActivity(), "produit " + produit.nom, Toast.LENGTH_SHORT).show()
    }

    override fun onBtnLessSelected(produit: Produit) {
        if (produit.quantite > 0)
            produitViewModel.saveReal(produit, produit.quantite - 1)
    }

    override fun onBtnPlusSelected(produit: Produit) {
        produitViewModel.saveReal(produit, produit.quantite + 1)
    }

}