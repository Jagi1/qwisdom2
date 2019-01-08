package bandurski.s.qwisdom2.NavigationFragments

import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bandurski.s.qwisdom2.Adapters.RecyclerViewAdapter
import bandurski.s.qwisdom2.R

class SettingsFragment: Fragment() {

    var options: ArrayList<String> = ArrayList()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: RecyclerViewAdapter

    companion object {
        fun newInstance(): SettingsFragment = SettingsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        options.add("Opcja 1")
        options.add("Opcja 2")
        options.add("Opcja 3")
        options.add("Opcja 4")
        options.add("Opcja 5")
        options.add("Opcja 6")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view)
        adapter = RecyclerViewAdapter(options, context!!)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

}