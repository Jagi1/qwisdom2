package bandurski.s.qwisdom2.NavigationFragments

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import bandurski.s.qwisdom2.R
import bandurski.s.qwisdom2.Utils.FirebaseUtils
import de.hdodenhof.circleimageview.CircleImageView

class AccountFragment: Fragment() {

    private lateinit var avatar: CircleImageView
    private lateinit var username: TextView

    companion object {
        fun newInstance(): AccountFragment = AccountFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_account, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        avatar = view.findViewById(R.id.avatar)
        username = view.findViewById(R.id.username)

        avatar.setImageBitmap(FirebaseUtils.avatar)

    }

}