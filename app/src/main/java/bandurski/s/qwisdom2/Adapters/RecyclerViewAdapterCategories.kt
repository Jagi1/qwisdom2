package bandurski.s.qwisdom2.Adapters

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import bandurski.s.qwisdom2.R

class RecyclerViewAdapterCategories(private var categories: ArrayList<String>, private var context: Context) : RecyclerView.Adapter<RecyclerViewAdapterCategories.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view: View = LayoutInflater.from(p0.context).inflate(R.layout.item_cardview, p0,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        Log.d("Bind","$p1")
        p0.textView.text = categories[p1]
//        p0.imageView.setOnClickListener { _ -> Toast.makeText(context, categories[p1], Toast.LENGTH_LONG).show() }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.card_title)
        var imageView: ImageView = itemView.findViewById(R.id.card_image)
    }
}