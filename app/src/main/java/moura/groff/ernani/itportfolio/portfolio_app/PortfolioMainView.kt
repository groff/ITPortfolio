package moura.groff.ernani.itportfolio.portfolio_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_portfolio_main.*
import moura.groff.ernani.itportfolio.R
import moura.groff.ernani.itportfolio.portfolio_app.adapter.PortfolioAppPagerAdapter

class PortfolioMainView : Fragment() {

    private lateinit var viewModel: PortfolioMainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_portfolio_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "App Portfolio"

        viewModel = ViewModelProviders.of(this).get(PortfolioMainViewModel::class.java)
        viewModel.initModel()

        vpContent.adapter = fragmentManager?.let { PortfolioAppPagerAdapter(it) }
        tlDots.setupWithViewPager(vpContent, true)

        configObservers()
    }

    fun configObservers() {
        viewModel.listAppPortfolio.observe(this, Observer {
            if (it != null) {
                (vpContent.adapter as PortfolioAppPagerAdapter).setList(it)
                (vpContent.adapter as PortfolioAppPagerAdapter).notifyDataSetChanged()
            }
        })

        viewModel.showLoader.observe(this, Observer {
            if (it ?: false) {
                pbLoader.visibility = View.VISIBLE
            } else {
                pbLoader.visibility = View.GONE
            }
        })
    }
}