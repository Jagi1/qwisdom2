package bandurski.s.qwisdom2

import android.graphics.BitmapFactory
import android.graphics.PorterDuff
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import android.hardware.fingerprint.FingerprintManager
import android.widget.Toast
import bandurski.s.qwisdom2.NavigationFragments.*
import bandurski.s.qwisdom2.Utils.BiometricUtils
import bandurski.s.qwisdom2.Utils.FirebaseUtils


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, BiometricUtils.OnAuthenticationResult {

    private lateinit var bnView: BottomNavigationView
    private lateinit var biometricUtils: BiometricUtils
    private lateinit var firebaseUtils: FirebaseUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bnView = findViewById(R.id.navigation_bar)
        bnView.setOnNavigationItemSelectedListener(this)

        biometricUtils = BiometricUtils(this,listener = this)
        firebaseUtils = FirebaseUtils

        if (biometricUtils.fingerprint() and !firebaseUtils.LOGGED_IN and getSharedPreferences("signInPass",0).contains("email")) {
            biometricUtils.generateKey()
            Toast.makeText(this,"Place finger of scanner",Toast.LENGTH_SHORT).show()
            if (biometricUtils.cipherInit()) {
                val cryptoObject: FingerprintManager.CryptoObject = FingerprintManager.CryptoObject(biometricUtils.cipher)
                biometricUtils.startAuth(cryptoObject)
            }
        }

        FirebaseUtils.categories.add("matematyka")
        FirebaseUtils.categories.add("angielski")
    }

    override fun onAuthenticationResult(b: Boolean) {
        firebaseUtils.auth.signInWithEmailAndPassword(
                getSharedPreferences("signInPass",0).getString("email",""),
                getSharedPreferences("signInPass",0).getString("password",""))
                .addOnSuccessListener {
//                    Toast.makeText(this,firebaseUtils.auth.currentUser?.uid,Toast.LENGTH_LONG).show()
                    firebaseUtils.storage.reference.child("users").child(firebaseUtils.auth.currentUser!!.uid).child("avatar.jpg")
                            .getBytes(1024*1024*5)
                            .addOnSuccessListener {
                                Toast.makeText(this,"You have been logged in!",Toast.LENGTH_LONG).show()
                                firebaseUtils.avatar = BitmapFactory.decodeByteArray(it,0,it.size)
                                firebaseUtils.LOGGED_IN = true
                                bnView.itemBackground = getDrawable(R.drawable.shape_green_bar)
                            }
                            .addOnFailureListener {
                                Toast.makeText(this,it.message,Toast.LENGTH_LONG).show()
                            }
                }
    }

    /**
     * Listener dla elementow BottomNavigationView.
     */
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.play -> openFragment(PlayFragment.newInstance())
            R.id.account -> if (firebaseUtils.LOGGED_IN) openFragment(AccountFragment.newInstance()) else openFragment(SignInFragment.newInstance())
            R.id.social -> openFragment(SocialFragment.newInstance())
            R.id.settings -> openFragment(SettingsFragment.newInstance())
        }
        return true
    }

    /**
     * Otwiera nowy fragment z BottomNavigationView.
     */
    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
