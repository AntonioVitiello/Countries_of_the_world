package ant.vit.paesidelmondo.ui

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import ant.vit.paesidelmondo.R
import ant.vit.paesidelmondo.tools.SingleEvent
import ant.vit.paesidelmondo.tools.Utils
import ant.vit.paesidelmondo.ui.model.ToolbarType
import ant.vit.paesidelmondo.ui.model.ToolbarType.Companion.DETAILS
import ant.vit.paesidelmondo.ui.model.ToolbarType.Companion.LIST
import ant.vit.paesidelmondo.viewmodel.CountriesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_countries.*
import javax.inject.Inject


/**
 * Created by Vitiello Antonio
 */
@AndroidEntryPoint
class CountriesActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    @Inject
    lateinit var mViewModel: CountriesViewModel
    private var mMenuRes = R.menu.search_countries_menu
    private lateinit var mNavHostFragment: NavHostFragment
    private lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countries)
        mViewModel.progressLiveData.observe(this, ::showProgress)
        mViewModel.toolbarTypeLiveData.observe(this, ::setToolbarType)
        mViewModel.loadAllCountriesName()
        initComponents()
    }

    override fun onResume() {
        super.onResume()
        mNavHostFragment = supportFragmentManager.fragments[0] as NavHostFragment
        mNavController = findNavController(this, R.id.navContainer)
    }

    private fun initComponents() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_flight)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(mMenuRes, menu)
        if (mMenuRes == R.menu.search_countries_menu) {
            val searchItem = menu.findItem(R.id.action_search)
            val searchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(this)
        }
        return true
    }

    private fun showProgress(event: SingleEvent<Boolean>) {
        progress.show = event.getContentIfNotHandled() == true
    }

    private fun showWelcomeMessage() {
        Utils.showInfoDialog(this, R.string.app_name, R.string.welcome_message, 4000L)
    }

    override fun onSupportNavigateUp(): Boolean {
        if (mNavController.previousBackStackEntry != null) {
            onBackPressed()
        } else {
            showWelcomeMessage()
        }
        return false
    }

    override fun onBackPressed() {
        val currentFragment = mNavHostFragment.childFragmentManager.fragments[0]
        if (currentFragment is INavigationListener) {
            if (currentFragment.canNavigateBack()) {
                popBackStack()
            }
        } else {
            popBackStack()
        }
    }

    private fun popBackStack() {
        if (!mNavController.popBackStack()) {
            finish()
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            mViewModel.filterRequest(newText)
        }
        return true
    }

    private fun setToolbarType(event: SingleEvent<ToolbarType>) {
        event.getContentIfNotHandled()?.let { toolbarType ->
            when (toolbarType.type) {
                LIST -> {
                    supportActionBar?.apply {
                        title = getString(R.string.app_name)
                        setHomeAsUpIndicator(R.drawable.ic_flight)
                    }
                    mMenuRes = R.menu.search_countries_menu
                }
                DETAILS -> {
                    supportActionBar?.apply {
                        title = toolbarType.title
                        setHomeAsUpIndicator(R.drawable.ic_flag)
                    }
                    mMenuRes = R.menu.empty_menu
                }
            }
            invalidateOptionsMenu()
        }
    }

}

