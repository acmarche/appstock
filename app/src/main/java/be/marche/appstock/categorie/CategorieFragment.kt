package be.marche.appstock.categorie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import be.marche.appstock.R
import be.marche.appstock.entity.Categorie
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class CategorieFragment: Fragment() {

    val categorieViewModel: CategorieViewModel by sharedViewModel()
    lateinit var categorie: Categorie

     companion object {
        fun newInstance() = CategorieFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.categorie_fragment, container, false)
    }
}