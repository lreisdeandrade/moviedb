package br.com.leandro.moviedb.genre

import android.arch.lifecycle.Observer
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import br.com.leandro.moviedb.R
import br.com.leandro.moviedb.util.*
import br.com.leandro.moviedbservice.model.Genre
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import kotlinx.android.synthetic.main.activity_genre.*
import org.jetbrains.anko.startActivity
import timber.log.Timber
import android.content.Intent
import android.net.Uri

class GenreActivity : AppCompatActivity() {

    private lateinit var viewModel: GenreViewModel

    companion object {
        @JvmStatic
        fun createIntent(context: Context) {
            context.startActivity<GenreActivity>()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        initData()
        initViews()
        initViewModel()
    }

    private fun init() {
        //UNUSED
    }

    private fun initData() {
        //UNUSED
    }

    private fun initViews() {
        setContentView(R.layout.activity_genre)

        createDrawer()
    }

    private fun createDrawer() {
        val headerResult = AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        ProfileDrawerItem().withName("Leandro Andrade")
                                .withEmail("lreisdeandrade@gmail.com")
                                .withIcon(ContextCompat.getDrawable(this, R.drawable.profile)))

                .withOnAccountHeaderListener({ view, profile, currentProfile ->
                    val url = "https://github.com/lreisdeandrade"
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)

                    false
                })
                .build()

        toolbar.title = "The MoviesDB"
        DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withSliderBackgroundColor(ContextCompat.getColor(this, R.color.grey_dark))
                .withActionBarDrawerToggle(true)
                .withAccountHeader(headerResult)
                .withActionBarDrawerToggleAnimated(true)
                .build()
    }

    private fun initViewModel() {
        viewModel = obtainViewModel(GenreViewModel::class.java)

        viewModel.apply {
            hasErrorLive.observe(this@GenreActivity, Observer {
                it?.let {
                    if (it) showError()
                }
            })

            isLoadingLive.observe(this@GenreActivity, Observer {
                it?.let {
                    if(it){
                        genresLoadingView.visible()
                    }else{
                        genresLoadingView.gone()
                    }
                }
            })

            genresLive.observe(this@GenreActivity, Observer { genreReponse ->
                genreReponse?.let {
                    setupContainerGenre(it.genres)
                }
            })

            viewModel.loadGenres()
        }
    }

    private fun setupContainerGenre(genresList: List<Genre>) {
        for (item: Genre in genresList) {
            addFragmentToActivity(GenreFragment.newInstance(item), R.id.genreView)
        }
    }

    private fun showError() {
        Timber.d("ERROR")
    }
}
