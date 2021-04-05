package ant.vit.paesidelmondo.ui

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import ant.vit.paesidelmondo.R
import ant.vit.paesidelmondo.tools.SingleEvent
import ant.vit.paesidelmondo.ui.model.ToolbarType
import ant.vit.paesidelmondo.ui.model.ToolbarType.Companion.DETAILS
import ant.vit.paesidelmondo.ui.model.ToolbarType.Companion.LIST
import ant.vit.paesidelmondo.viewmodel.CountriesViewModel
import kotlinx.android.synthetic.main.activity_countries.*

/**
 * Created by Vitiello Antonio
 */
class CountriesActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private val mViewModel by viewModels<CountriesViewModel>()
    private var mMenuRes = R.menu.search_countries_menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countries)
        mViewModel.progressLiveData.observe(this, ::showProgress)
        mViewModel.toolbarTypeLiveData.observe(this, ::setToolbarType)
        mViewModel.loadAllCountriesName()
        initComponents()
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

    override fun onSupportNavigateUp(): Boolean {
        val iOnBackPressed = supportFragmentManager.fragments[0] as? IOnBackPressed
        if (iOnBackPressed?.canNavigateUp() == true) {
            onBackPressed()
        }
        return false
    }

    override fun onBackPressed() {
        //val iOnBackPressed = (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as? IOnBackPressed)
        val iOnBackPressed = supportFragmentManager.fragments[0] as? IOnBackPressed
        if (iOnBackPressed?.onBackPressed() == true) {
            return
        }
        //if backpressed has not already been managed
        if (mMenuRes != R.menu.search_countries_menu) {
            mMenuRes = R.menu.search_countries_menu
            invalidateOptionsMenu()
        }
        super.onBackPressed()
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

