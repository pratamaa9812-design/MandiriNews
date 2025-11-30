package dev.rakamin.newsapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.rakamin.newsapp.R
import dev.rakamin.newsapp.models.Article
import java.text.SimpleDateFormat
import java.util.Locale

class HeadlineAdapter(
    private val items: List<Article>,
    private val onClick: (Article) -> Unit
) : RecyclerView.Adapter<HeadlineAdapter.HeadlineVH>() {

    inner class HeadlineVH(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.ivHeadlineImg)
        val title: TextView = view.findViewById(R.id.tvHeadlineTitle)
        val source: TextView = view.findViewById(R.id.tvHeadlineSource)
        val date: TextView = view.findViewById(R.id.tvHeadlineDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlineVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_headline, parent, false)
        return HeadlineVH(view)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: HeadlineVH, position: Int) {
        val item = items[position]
        holder.title.text = item.title ?: ""
        holder.source.text = item.source?.name ?: ""

        item.publishedAt?.let {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
            try {
                val date = inputFormat.parse(it)
                holder.date.text = date?.let { d -> outputFormat.format(d) } ?: ""
            } catch (e: Exception) {
                holder.date.text = ""
            }
        }

        Glide.with(holder.itemView.context)
            .load(item.urlToImage)
            .centerCrop()
            .into(holder.img)

        holder.itemView.setOnClickListener { onClick(item) }
    }

    override fun getItemCount(): Int = items.size
}
