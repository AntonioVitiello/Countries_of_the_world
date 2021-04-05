package ant.vit.paesidelmondo.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ant.vit.paesidelmondo.R
import ant.vit.paesidelmondo.model.CountryDetailsModel
import ant.vit.paesidelmondo.model.CountryNameModel
import ant.vit.paesidelmondo.model.mapAllCountryNames
import ant.vit.paesidelmondo.model.mapCountryDetails
import ant.vit.paesidelmondo.network.CountriesRepository
import ant.vit.paesidelmondo.tools.SingleEvent
import ant.vit.paesidelmondo.tools.manageLoading
import ant.vit.paesidelmondo.ui.model.ToolbarType
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Vitiello Antonio
 */
class CountriesViewModel(application: Application) : AndroidViewModel(application) {
    private val compositeDisposable = CompositeDisposable()
    var countriesLiveData = MutableLiveData<List<CountryNameModel>>()
    var countryDetailsLiveData = MutableLiveData<SingleEvent<CountryDetailsModel>>()
    var errorLiveData = MutableLiveData<SingleEvent<String>>()
    var progressLiveData = MutableLiveData<SingleEvent<Boolean>>()
    var filterLiveData = MutableLiveData<String>()
    var toolbarTypeLiveData = MutableLiveData<SingleEvent<ToolbarType>>()
    var sortedLanguagesLiveData = MutableLiveData<List<String>>()

    companion object {
        private const val TAG = "CountriesViewModel"
    }

    fun loadAllCountriesName() {
        compositeDisposable.add(
            CountriesRepository.getAllCountriesNameSingle()
                .map(::mapAllCountryNames)
                .map { models ->
                    val languageSet = mutableSetOf("")
                    models.forEach { it.languages?.let { lang -> languageSet.addAll(lang) } }
                    val sortList = languageSet.toMutableList()
                    sortList.sort()
                    sortedLanguagesLiveData.postValue(sortList)
                    models
                }
                .manageLoading(progressLiveData)
                .subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    countriesLiveData.postValue(it)
                }, {
                    val message = getApplication<Application>().getString(R.string.generic_network_error_message)
                    errorLiveData.postValue(SingleEvent(message))
                    Log.e(TAG, "Error while loading countries name.", it)
                })
        )
    }

    fun filterRequest(newText: String) {
        filterLiveData.value = newText
    }

    fun loadCountryDetailsByName(countryName: String) {
        compositeDisposable.add(
            CountriesRepository.getCountryDetailsSingle(countryName)
                .map { mapCountryDetails(it, countryName) }
                .manageLoading(progressLiveData)
                .subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    countryDetailsLiveData.postValue(SingleEvent(it))
                }, {
                    val message = getApplication<Application>().getString(R.string.generic_network_error_message)
                    errorLiveData.postValue(SingleEvent(message))
                    Log.e(TAG, "Error while loading country details.", it)
                })
        )
    }

    fun setToolbarTypeRequest(type: Int, title: String? = null) {
        toolbarTypeLiveData.value = SingleEvent(ToolbarType(type, title))
    }

    override fun onCleared() {
        compositeDisposable.dispose()
    }

}