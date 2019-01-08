package bandurski.s.qwisdom2.NavigationFragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import bandurski.s.qwisdom2.Adapters.RecyclerViewAdapterCategories
import bandurski.s.qwisdom2.R
import com.google.firebase.firestore.FirebaseFirestore

class CategoriesFragment: Fragment() {

    private val categories = ArrayList<String>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerViewAdapterCategories

    companion object {
        fun newInstance(): CategoriesFragment = CategoriesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
        = inflater.inflate(R.layout.fragment_categories, container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(context,3)

        val db = FirebaseFirestore.getInstance()
        db.collection("categories")
                .get().addOnSuccessListener {
                    it.forEach { categories.add(it.id) }
                    adapter = RecyclerViewAdapterCategories(categories,context!!)
                    recyclerView.adapter = adapter
                }
    }
}