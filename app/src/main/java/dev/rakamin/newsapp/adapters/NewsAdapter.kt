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

class NewsAdapter(
    private val items: MutableList<Article>,
    private val onClick: (Article) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsVH>() {

    inner class NewsVH(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.ivThumb)
        val title: TextView = view.findViewById(R.id.tvTitle)
        val author: TextView = view.findViewById(R.id.tvAuthor)
        val date: TextView = view.findViewById(R.id.tvDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return NewsVH(view)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: NewsVH, position: Int) {
        val item = items[position]
        holder.title.text = item.title ?: ""
        holder.author.text = item.author ?: ""

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

    fun addData(newItems: List<Article>) {
        val start = items.size
        items.addAll(newItems)
        notifyItemRangeInserted(start, newItems.size)
    }
}
