package be.marche.appstock.home

import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.fragment.findNavController
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import be.marche.appstock.SyncViewModel
import be.marche.appstock.api.ConnectivityLiveData
import kotlinx.android.synthetic.main.home_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    val syncViewModel: SyncViewModel by viewModel()

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(be.marche.appstock.R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        refreshDataBase()

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

                    Toast.makeText(context, "Refresh", Toast.LENGTH_LONG).show()
                    syncViewModel.refreshData()
                }
                false -> {
                    //  infoMessage.text = getString(R.string.message_no_connectivity)
                }
            }
        })
    }
}