package bandurski.s.qwisdom2.NavigationFragments

import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import bandurski.s.qwisdom2.R
import bandurski.s.qwisdom2.Utils.FirebaseUtils

class SignInFragment: Fragment(), View.OnClickListener {

    private lateinit var loginInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private var buttons: ArrayList<Button> = ArrayList()
    private lateinit var buttonSignIn: Button
    private lateinit var buttonSignUp: Button

    companion object {
        fun newInstance(): SignInFragment = SignInFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_sign_in, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginInput = view.findViewById(R.id.login_input)
        passwordInput = view.findViewById(R.id.password_input)
        buttonSignIn = view.findViewById(R.id.button_sign_in)
        buttonSignUp = view.findViewById(R.id.button_sign_up)
        buttons.add(buttonSignIn)
        buttons.add(buttonSignUp)
        buttons.forEach { button -> button.setOnClickListener(this) }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_sign_in -> {
                FirebaseUtils.auth.signInWithEmailAndPassword(loginInput.text.toString(), passwordInput.text.toString())
                        .addOnSuccessListener {
                            FirebaseUtils.LOGGED_IN = true
                            Toast.makeText(context,"You have been logged in!",Toast.LENGTH_LONG).show()
                            try {
                                val editor: SharedPreferences.Editor = activity!!.getSharedPreferences("signInPass",0).edit()
                                editor.putString("email", loginInput.text.toString())
                                editor.putString("password", passwordInput.text.toString())
                                editor.apply()
                            } catch (e: Exception) {
                                Toast.makeText(context,e.message,Toast.LENGTH_LONG).show()
                            }
                        }
                        .addOnFailureListener {
                            Toast.makeText(context,it.message,Toast.LENGTH_LONG).show()
                        }
            }
            R.id.button_sign_up -> openFragment(SignUpFragment.newInstance())
        }
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.container, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }
}