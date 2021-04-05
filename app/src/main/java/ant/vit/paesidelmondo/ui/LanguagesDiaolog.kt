package ant.vit.paesidelmondo.ui

import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import ant.vit.paesidelmondo.R
import ant.vit.paesidelmondo.tools.implementedInterface
import ant.vit.paesidelmondo.viewmodel.CountriesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_languages.*
import javax.inject.Inject


/**
 * Created by Vitiello Antonio
 */
@AndroidEntryPoint
class LanguagesDiaolog : DialogFragment() {

    @Inject
    lateinit var mViewModel: CountriesViewModel
    private var languageSelected: String? = null

    companion object {
        const val TAG = "LanguagesDiaolog"

        fun newInstance() = LanguagesDiaolog()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        //dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.dialog_languages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.sortedLanguagesLiveData.observe(viewLifecycleOwner, ::fillData)

        initComponents()
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.92).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun initComponents() {
        langSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selected = parent?.selectedItem as? String
                if (!TextUtils.isEmpty(selected)) {
                    languageSelected = selected!!
                    dismiss()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //do nothing
            }
        }
    }

    private fun fillData(languages: List<String>) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, languages)
        langSpinner.adapter = adapter
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        languageSelected?.let {
            implementedInterface<ILanguagesDialog>()?.onLanguageSelected(it)
        }
    }

}