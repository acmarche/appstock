package be.marche.appstock.categorie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import be.marche.appstock.R
import be.marche.appstock.entity.Categorie
import kotlinx.android.synthetic.main.categorie_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategorieListFragment : Fragment(), CategorieListAdapter.CategorieListAdapterListener {

    val categorieViewModel: CategorieViewModel by sharedViewModel()

    private var listener: CategorieListAdapter.CategorieListAdapterListener? = null
    private lateinit var categorieListAdapter: CategorieListAdapter
    private lateinit var categories: MutableList<Categorie>

    companion object {
        fun newInstance() = CategorieListFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(be.marche.appstock.R.layout.categorie_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (!::categories.isInitialized) {
            categories = mutableListOf()
        }

        activity?.title = getString(R.string.app_name)
        listener = this
        categorieListAdapter = CategorieListAdapter(categories, listener)

        recyclerViewCategorieList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = categorieListAdapter
        }
        //viewLifecycleOwner au lieu de this
        // LiveData supprime les observateurs chaque fois que la vue du fragment est détruite:
        categorieViewModel.getCategories().observe(viewLifecycleOwner, Observer { UpdateUi(it) })
    }

    private fun UpdateUi(newcategories: List<Categorie>) {
        categories.clear()
        categories.addAll(newcategories)
        categorieListAdapter.notifyDataSetChanged()
    }

    override fun onCategorieSelected(categorie: Categorie) {
        categorieViewModel.categorie = categorieViewModel.getCagorieById(categorie.id)
        findNavController().navigate(be.marche.appstock.R.id.action_categorieListFragment_to_produitListFragment)
    }

}