package bandurski.s.qwisdom2.Utils

import android.content.SharedPreferences
import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

object FirebaseUtils {
    val storage: FirebaseStorage = FirebaseStorage.getInstance()
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var avatar: Bitmap
    var LOGGED_IN = false
    var numberOfCategories = 2
    var categories = ArrayList<String>()
}