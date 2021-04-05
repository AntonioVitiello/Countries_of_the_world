package ant.vit.paesidelmondo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import ant.vit.paesidelmondo.R
import ant.vit.paesidelmondo.model.CountryNameModel
import ant.vit.paesidelmondo.tools.SingleEvent
import ant.vit.paesidelmondo.tools.Utils
import ant.vit.paesidelmondo.tools.closeKeyboard
import ant.vit.paesidelmondo.ui.DetailsFragment.Companion.KEY_COUNTRY_NAME
import ant.vit.paesidelmondo.ui.adapter.CountriesAdapter
import ant.vit.paesidelmondo.ui.model.ToolbarType.Companion.LIST
import ant.vit.paesidelmondo.viewmodel.CountriesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_countries.*
import javax.inject.Inject

/**
 * Created by Vitiello Antonio
 */
@AndroidEntryPoint
class CountryListFragment : Fragment(), INavigationListener, ILanguagesDialog {

    @Inject
    lateinit var mViewModel: CountriesViewModel
    private lateinit var mAdapter: CountriesAdapter

    companion object {
        const val TAG = "CountryListFragment"
        fun newInstance() = CountryListFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_countries, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.countriesLiveData.observe(viewLifecycleOwner, ::fillData)
        mViewModel.filterLiveData.observe(viewLifecycleOwner, ::filter)
        mViewModel.errorLiveData.observe(viewLifecycleOwner, ::showError)
        initComponents()
    }

    override fun onResume() {
        super.onResume()
        mViewModel.setToolbarTypeRequest(LIST)
        mAdapter.resetFilters()
    }

    private fun initComponents() {
        mAdapter = CountriesAdapter { model ->
            val navController = Navigation.findNavController(requireView())
            navController.navigate(R.id.action_details,
                Bundle().apply { putString(KEY_COUNTRY_NAME, model.countryName) })
        }
        recyclerView.apply {
            setHasFixedSize(false)
            adapter = mAdapter
        }
        languageFab.setOnClickListener {
            val colorSelected = ContextCompat.getColorStateList(requireContext(), R.color.colorAccent)
            if (languageFab.backgroundTintList == colorSelected) {
                resetFilters()
            } else {
                showLanguagesDialog()
            }
        }
        swipeToRefreshLayout.setOnRefreshListener {
            mViewModel.loadAllCountriesName()
            activity?.closeKeyboard()
            mViewModel.setToolbarTypeRequest(LIST)
        }
    }

    private fun resetFilters() {
        mAdapter.resetFilters()
        val colorSelected = ContextCompat.getColorStateList(requireContext(), R.color.teal_200)
        languageFab.backgroundTintList = colorSelected
    }

    private fun showLanguagesDialog() {
        LanguagesDiaolog.newInstance().also {
            @Suppress("DEPRECATION")
            it.setTargetFragment(this, 0)
            it.show(parentFragmentManager, LanguagesDiaolog.TAG)
        }
    }

    private fun fillData(models: List<CountryNameModel>) {
        swipeToRefreshLayout.isRefreshing = false
        mAdapter.switchData(models)
        val colorSelected = ContextCompat.getColorStateList(requireContext(), R.color.teal_200)
        languageFab.backgroundTintList = colorSelected
    }

    private fun filter(newText: String?) {
        mAdapter.filterByCodeOrName(newText)
    }

    override fun canNavigateBack(): Boolean {
        return true
    }

    override fun canNavigateUp(): Boolean {
        return false
    }

    override fun onLanguageSelected(language: String) {
        val colorSelected = ContextCompat.getColorStateList(requireContext(), R.color.colorAccent)
        languageFab.backgroundTintList = colorSelected
        mAdapter.filterByLanguage(language)
    }

    private fun showError(event: SingleEvent<String>) {
        event.getContentIfNotHandled()?.let { message ->
            swipeToRefreshLayout.isRefreshing = false
            Utils.showCriticalErrorDialog(
                requireContext(), message,
                R.string.error_dialog_try_again, R.string.error_dialog_exit,
                {
                    mViewModel.loadAllCountriesName()
                }, {
                    activity?.finish()
                })
        }
    }

}

