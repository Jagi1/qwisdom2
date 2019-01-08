package bandurski.s.qwisdom2.NavigationFragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bandurski.s.qwisdom2.PlayActivity
import bandurski.s.qwisdom2.PreparingQuizActivity
import bandurski.s.qwisdom2.R

class PlayFragment: Fragment(), View.OnClickListener {

    lateinit var cardViewCategories: CardView

    companion object {
        fun newInstance(): PlayFragment = PlayFragment()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
        = inflater.inflate(R.layout.fragment_play, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardViewCategories = view.findViewById(R.id.card_view_categories)
        cardViewCategories.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.card_view_play -> startActivity(Intent(context,PreparingQuizActivity::class.java))
            R.id.card_view_categories -> openFragment(CategoriesFragment.newInstance())
        }
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.container, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }
}