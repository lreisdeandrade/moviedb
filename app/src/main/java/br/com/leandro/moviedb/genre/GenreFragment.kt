package br.com.leandro.moviedb.genre

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.leandro.moviedb.AppContext
import br.com.leandro.moviedb.R
import br.com.leandro.moviedb.util.requiredBundleNotFound
import br.com.leandro.moviedbservice.model.Genre
import kotlinx.android.synthetic.main.fragment_genre.*
import timber.log.Timber

/**
 * Created by leandro on 08/06/2018
 */

private const val EXTRA_GENRE = "EXTRA_GENRE"

class GenreFragment : Fragment() {

    private lateinit var genre: Genre

    companion object {
        fun newInstance(genre: Genre): GenreFragment {
            val fragment = GenreFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(EXTRA_GENRE, genre)
            }
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_genre, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initViewModel()
        initViews()
    }

    private fun initData() {

        arguments?.getParcelable<Genre>(EXTRA_GENRE)?.let {
            genre = it
        } ?: run {
            activity?.requiredBundleNotFound(EXTRA_GENRE)
        }
    }

    private fun initViewModel() {
//        viewModel = obtainViewModel(AppContext.getInstance(), BankSlipViewModel::class.java)
//
//        with(viewModel) {
//            hasError.observe(this@BankSlipFragment, Observer {
//                when (it) {
//                    true -> {
//                        activity?.rootView?.showSnackBar(getString(R.string.generic_error), Snackbar.LENGTH_INDEFINITE,
//                                getString(R.string.try_again), {
//                            viewModel.start(spotName)
//                        })
//                    }
//                }
//            })
//
//            showHtmlSpotLive.observe(this@BankSlipFragment, Observer {
//                bankSlipWebview.loadData(it, "text/html; charset=UTF-8", null);
//            })
//
//            showPdfSpotLive.observe(this@BankSlipFragment, Observer {
//                bankSlipWebview.loadUrl(it)
//            })
//
//            hasSpotError.observe(this@BankSlipFragment, Observer {
//                bankSlipWebview.gone()
//            })
//
//            showSpotLive.observe(this@BankSlipFragment, Observer {
//                when (it) {
//                    true -> bankSlipWebview.visible()
//                    false -> bankSlipWebview.gone()
//                }
//            })
//
//            priceLive.observe(this@BankSlipFragment, Observer { formattedText ->
//                formattedText?.let {
//                    paymentActivityListener.canProceed(TextUtils.formatPrice(it), createPurchaseOrder(it))
//                }
//            })
//
//            hitAnalitycSpotLive.observe(this@BankSlipFragment, Observer {
//                hitAnalytcsSpot()
//            })
//
//            start(spotName)
//        }
//    }
//
    }

    private fun initViews() {
        genreName.text = genre.name
    }
}
