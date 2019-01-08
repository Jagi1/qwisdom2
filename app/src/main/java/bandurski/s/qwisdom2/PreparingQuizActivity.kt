package bandurski.s.qwisdom2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import bandurski.s.qwisdom2.Utils.FirebaseUtils
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.HashMap

class PreparingQuizActivity : AppCompatActivity() {

    val quiz = ArrayList<HashMap<String,String>>(5)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preparing_quiz)

        quiz.forEach {
            val random = Random()
            val category = random.nextInt(FirebaseUtils.numberOfCategories-1)
            var i = 0
            FirebaseFirestore.getInstance().collection("categories")
                    .get().addOnSuccessListener {
                        it.forEach {
                            if (i == category) { // znalazlem kategorie
                                FirebaseFirestore.getInstance().collection().document("metadata")
                                        .get().addOnSuccessListener {
                                            Toast.makeText(this,it.,Toast.LENGTH_LONG).show()
                                        }

                            }
                            else ++i
                        }
                    }
        }
    }
}
