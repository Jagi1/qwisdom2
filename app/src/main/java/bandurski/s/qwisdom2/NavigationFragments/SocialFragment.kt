package bandurski.s.qwisdom2.NavigationFragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bandurski.s.qwisdom2.R

class SocialFragment: Fragment() {

    companion object {
        fun newInstance(): SocialFragment = SocialFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_social, container, false)
}