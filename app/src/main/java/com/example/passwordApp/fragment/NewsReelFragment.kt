package com.example.passwordApp.fragment

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordApp.R
import com.example.passwordApp.adapter.ArticlesAdapter
import com.example.passwordApp.utils.Utils
import com.google.android.material.snackbar.Snackbar
import org.newsapi.NewsApi
import org.newsapi.listener.OnArticlesListener
import org.newsapi.model.ApiError
import org.newsapi.model.response.ArticlesResponse

class NewsReelFragment : Fragment(), OnArticlesListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var language: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_reel, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        language = resources.configuration.locales[0].language
        recyclerView = view.findViewById(R.id.topHeadlinesRecyclerView)

        NewsApi.getTopHeadlines(onArticlesListener = this, language=language)
    }

    override fun onArticlesResponse(articlesResponse: ArticlesResponse) {
        val adapter = ArticlesAdapter(articlesResponse.articles, requireContext())
        recyclerView.setHasFixedSize(true)
        val orientation = resources.configuration.orientation
        if (Utils.largeScreen(requireContext())) {
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                recyclerView.layoutManager = GridLayoutManager(context, 3)
            } else {
                recyclerView.layoutManager = GridLayoutManager(context, 2)
            }
        } else {
            recyclerView.layoutManager = GridLayoutManager(context, 1)
        }
        recyclerView.adapter = adapter
    }

    override fun onError(apiError: ApiError?) {
        Snackbar.make(requireView(), apiError?.message.toString(), Snackbar.LENGTH_SHORT)
            .show()
    }
}