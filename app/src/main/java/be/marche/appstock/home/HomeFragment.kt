package be.marche.appstock.home

import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.fragment.findNavController
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import be.marche.appstock.R
import be.marche.appstock.SyncViewModel
import be.marche.appstock.api.ConnectivityLiveData
import be.marche.appstock.categorie.CategorieViewModel
import kotlinx.android.synthetic.main.home_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    val syncViewModel: SyncViewModel by viewModel()
    val categorieViewModel: CategorieViewModel by sharedViewModel()

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(be.marche.appstock.R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        refreshDataBase()
        categorieViewModel.categorie = null

        btnCategorieView.setOnClickListener {
            findNavController().navigate(be.marche.appstock.R.id.action_homeFragment_to_categorieListFragment)
        }
        btnProduitView.setOnClickListener {
            findNavController().navigate(be.marche.appstock.R.id.action_homeFragment_to_produitListFragment)
        }

    }

    private fun refreshDataBase() {
        ConnectivityLiveData(activity?.application).observe(this, Observer { connected ->
            when (connected) {
                true -> {
                    messageView.visibility = View.INVISIBLE
                    btnProduitView.visibility = View.VISIBLE
                    btnCategorieView.visibility = View.VISIBLE
                    syncViewModel.refreshData()
                }
                false -> {
                    messageView.visibility = View.VISIBLE
                    btnProduitView.visibility = View.INVISIBLE
                    btnCategorieView.visibility = View.INVISIBLE
                    messageView.text = getString(R.string.message_no_connectivity)
                }
            }
        })
    }
}