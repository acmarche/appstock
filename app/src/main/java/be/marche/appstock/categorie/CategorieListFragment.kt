package be.marche.appstock.categorie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import be.marche.appstock.entity.Categorie
import kotlinx.android.synthetic.main.categorie_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategorieListFragment : Fragment(), CategorieListAdapter.CategorieListAdapterListener {

    val categorieViewModel: CategorieViewModel by viewModel()

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

    }
}