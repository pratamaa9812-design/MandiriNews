package dev.rakamin.newsapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import dev.rakamin.newsapp.adapters.HeadlineAdapter
import dev.rakamin.newsapp.adapters.NewsAdapter
import dev.rakamin.newsapp.models.Article
import dev.rakamin.newsapp.models.NewsResponse
import dev.rakamin.newsapp.network.RetrofitClient
import dev.rakamin.newsapp.utils.EndlessRecyclerViewScrollListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var headlinesPager: ViewPager2
    private lateinit var rvNews: RecyclerView

    private lateinit var headlineAdapter: HeadlineAdapter
    private lateinit var newsAdapter: NewsAdapter

    private val headlines = mutableListOf<Article>()
    private val articles = mutableListOf<Article>()

    private var currentPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        headlinesPager = findViewById(R.id.vpHeadlines)
        rvNews = findViewById(R.id.rvNews)

        setupAdapters()
        setupEndlessScrolling()

        fetchTopHeadlines()
        fetchNews()
    }

    private fun setupAdapters() {
        headlineAdapter = HeadlineAdapter(headlines) { article -> openArticleUrl(article.url) }
        headlinesPager.adapter = headlineAdapter

        newsAdapter = NewsAdapter(articles) { article -> openArticleUrl(article.url) }
        rvNews.adapter = newsAdapter
        rvNews.layoutManager = LinearLayoutManager(this)
    }

    private fun setupEndlessScrolling() {
        val layoutManager = rvNews.layoutManager as LinearLayoutManager
        val scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                currentPage = page
                fetchNews()
            }
        }
        rvNews.addOnScrollListener(scrollListener)
    }

    private fun fetchTopHeadlines() {
        RetrofitClient.instance.getTopHeadlines(country = "id").enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    val articlesFromApi = response.body()?.articles.orEmpty()
                    headlines.clear()
                    headlines.addAll(articlesFromApi.filter { it.urlToImage != null })
                    headlineAdapter.notifyDataSetChanged()
                } else {
                    showError("Gagal mengambil headline: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                showError("Gagal mengambil headline: ${t.message}")
            }
        })
    }

    private fun fetchNews() {
        RetrofitClient.instance.getEverything(q = "android", page = currentPage).enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    val newArticles = response.body()?.articles.orEmpty()
                    newsAdapter.addData(newArticles.filter { it.urlToImage != null })
                } else {
                    showError("Gagal mengambil berita: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                showError("Gagal mengambil berita: ${t.message}")
            }
        })
    }

    private fun openArticleUrl(url: String?) {
        url?.let {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                startActivity(intent)
            } catch (e: Exception) {
                showError("Tidak dapat membuka artikel.")
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        Log.e("MainActivity", "Error: $message")
    }
}
