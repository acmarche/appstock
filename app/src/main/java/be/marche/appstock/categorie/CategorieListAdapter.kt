package be.marche.appstock.categorie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import be.marche.appstock.R
import be.marche.appstock.entity.Categorie

class CategorieListAdapter(
    private val categories: List<Categorie>,
    private val listener: CategorieListAdapterListener?
) : RecyclerView.Adapter<CategorieViewHolder>(), View.OnClickListener {

    interface CategorieListAdapterListener {
        fun onCategorieSelected(categorie: Categorie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategorieViewHolder {
        val viewItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.categorie_item, parent, false)
        return CategorieViewHolder(viewItem)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategorieViewHolder, position: Int) {
        val categorie = categories[position]

        with(holder) {
            cardView.setOnClickListener(this@CategorieListAdapter)
            cardView.tag = categorie
            categorieNom.text = categorie.nom
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.cardViewCategorie -> listener?.onCategorieSelected(view.tag as Categorie)
        }
    }

}