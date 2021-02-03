package dev.idee.cicdandroid

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private val movieAdapter = MovieListAdapter(DiffUtilCallback())



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        setupViewModel()
        AppCenter.start(application,
                "1c90129f-e8a0-4f36-bf70-689568fdb636",
                Analytics::class.java,
                Crashes::class.java)
    }

    private fun setupRecyclerView() {
        with(movieRecyclerView) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = movieAdapter
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this)[MainActivityViewModel::class.java]
        viewModel.movieListLiveData.observe(this, Observer {
            progressBar.visibility = View.GONE
            movieAdapter.submitList(it)
        })
    }

}
