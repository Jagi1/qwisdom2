package bandurski.s.qwisdom2.NavigationFragments

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import bandurski.s.qwisdom2.R
import bandurski.s.qwisdom2.Utils.FirebaseUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpFragment: Fragment(), View.OnClickListener {

    lateinit var loginInput: TextInputEditText
    lateinit var passwordInput: TextInputEditText
    lateinit var emailInput: TextInputEditText
    lateinit var register: Button

    companion object {
        fun newInstance(): SignUpFragment = SignUpFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_sign_up, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginInput = view.findViewById(R.id.login_input)
        passwordInput = view.findViewById(R.id.password_input)
        emailInput = view.findViewById(R.id.email_input)
        register = view.findViewById(R.id.button_register)
        register.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_register -> FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailInput.text.toString(),passwordInput.text.toString())
                    .addOnSuccessListener {
                        val db = FirebaseFirestore.getInstance()
                        val user = HashMap<String, Any>()
                        user["login"] = loginInput.text.toString()
                        user["email"] = emailInput.text.toString()
                        user["level"] = 1
                        db.collection("users")
                                .add(user)
                                .addOnSuccessListener {
                                    FirebaseAuth.getInstance().signInWithEmailAndPassword(emailInput.text.toString(),passwordInput.text.toString())
                                            .addOnSuccessListener {
                                                Toast.makeText(context,"You have been successfully registered!",Toast.LENGTH_LONG).show()
                                                FirebaseAuth.getInstance().currentUser?.sendEmailVerification()
                                                FirebaseAuth.getInstance().signOut()
                                            }
                                }
                                .addOnFailureListener {
                                    Toast.makeText(context,it.message,Toast.LENGTH_LONG).show()
                                    Log.d("DATABASE_FAILURE",it.message)
                                }
                    }
                    .addOnFailureListener {
                        Toast.makeText(context,it.message,Toast.LENGTH_LONG).show()
                        Log.d("REGISTER_FAILURE",it.message)
                    }
        }
    }
}