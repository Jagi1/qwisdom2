package bandurski.s.qwisdom2.Adapters

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import bandurski.s.qwisdom2.R

class RecyclerViewAdapter(private var options: ArrayList<String>, private var context: Context) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view: View = LayoutInflater.from(p0.context).inflate(R.layout.item_recyclerview, p0,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return options.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        Log.d("Bind","$p1")
        p0.textView.text = options[p1]
        p0.itemLayout.setOnClickListener { _ -> Toast.makeText(context,options[p1],Toast.LENGTH_LONG).show() }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.option)
        var itemLayout: ConstraintLayout = itemView.findViewById(R.id.item_layout)
    }
}