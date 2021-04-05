package ant.vit.paesidelmondo.ui

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ant.vit.paesidelmondo.R
import ant.vit.paesidelmondo.model.CountryDetailsModel
import ant.vit.paesidelmondo.tools.SingleEvent
import ant.vit.paesidelmondo.tools.Utils.Companion.showErrorDialog
import ant.vit.paesidelmondo.tools.loadImage
import ant.vit.paesidelmondo.ui.model.ToolbarType.Companion.DETAILS
import ant.vit.paesidelmondo.viewmodel.CountriesViewModel
import kotlinx.android.synthetic.main.fragment_details.*


/**
 * Created by Vitiello Antonio
 */
class DetailsFragment : Fragment(), INavigationListener {
    private val mViewModel by activityViewModels<CountriesViewModel>()

    companion object {
        const val TAG = "DetailsFragment"
        const val KEY_COUNTRY_NAME = "countryName"
        const val FLAG_IMAGE_URL = "https://www.countryflags.io/{alpha2Code}/flat/64.png"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString(KEY_COUNTRY_NAME)?.let { countryName ->
            mViewModel.loadCountryDetailsByName(countryName)
            mViewModel.setToolbarTypeRequest(DETAILS, countryName)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.countryDetailsLiveData.observe(viewLifecycleOwner, ::fillData)
        mViewModel.errorLiveData.observe(viewLifecycleOwner, ::showError)
        initComponents()
    }

    private fun initComponents() {
        flagImage.setOnClickListener {
            val model = mViewModel.countryDetailsLiveData.value
            val flagUrl = model?.peekContent()?.flagUrl
            openBrowser(flagUrl)
        }
    }

    private fun pulseFlag() {
        val pulse = AnimationUtils.loadAnimation(context, R.anim.pulse)
        flagImage.startAnimation(pulse)
    }

    private fun fillData(event: SingleEvent<CountryDetailsModel>) {
        event.getContentIfNotHandled()?.let { model ->
            countryName.text = model.nativeName ?: model.countryName
            model.alpha2Code?.let { alpha2Code ->
                if (alpha2Code.length == 2) {
                    val flagImageUrl = FLAG_IMAGE_URL.replace("{alpha2Code}", alpha2Code)
                    flagImage.loadImage(flagImageUrl)
                    pulseFlag()
                }
                row1Text.text = getString(R.string.capital_info, model.capital)
                row2Text.text = getString(R.string.alt_spellings_info, model.altSpellings)
                row3Text.text = getString(R.string.region_info, model.region)
                row4Text.text = getString(R.string.languages_info, model.languages)
                row5Text.text = getString(R.string.currencies_info, model.currencies)
                row6Text.text = getString(R.string.alpha_2_code_info, model.alpha2Code)
            }
        }
    }

    override fun canNavigateBack(): Boolean {
        return true
    }

    override fun canNavigateUp(): Boolean {
        return true
    }

    private fun openBrowser(pageUrl: String?) {
        Log.d(TAG, "Open ChromeTab with url: $pageUrl")
        if (!TextUtils.isEmpty(pageUrl)) {
            val customTabsIntent: CustomTabsIntent = CustomTabsIntent.Builder()
                .setUrlBarHidingEnabled(true)
                .setStartAnimations(requireContext(), R.anim.slide_in_right, R.anim.slide_out_left)
                .setExitAnimations(requireContext(), R.anim.slide_in_left, R.anim.slide_out_right)
                .setCloseButtonIcon(BitmapFactory.decodeResource(requireContext().resources, R.drawable.ic_flag))
                .setShareState(CustomTabsIntent.SHARE_STATE_ON)
                //.setShowTitle(true)
                //.addMenuItem("Share", getItem())
                //.setActionButton(BitmapFactory.decodeResource(getResources(), R.drawable.ic_share), "Share", getItem(), true)
                .build()
            customTabsIntent.launchUrl(requireContext(), Uri.parse(pageUrl))
        } else {
            Toast.makeText(context, getString(R.string.invalid_flag_url), Toast.LENGTH_LONG).show()
        }
    }

    private fun showError(event: SingleEvent<String>) {
        event.getContentIfNotHandled()?.let { message ->
            //Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            showErrorDialog(requireContext(), message) {
                activity?.onBackPressed()
            }
        }
    }

}

