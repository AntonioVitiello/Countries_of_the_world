package ant.vit.paesidelmondo.ui.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ant.vit.paesidelmondo.R
import ant.vit.paesidelmondo.model.CountryNameModel
import ant.vit.paesidelmondo.tools.Utils
import kotlinx.android.synthetic.main.item_country.view.*

/**
 * Created by Vitiello Antonio
 */
class CountriesAdapter(val listener: (CountryNameModel) -> Unit) : RecyclerView.Adapter<CountriesAdapter.ViewHolder>() {
    private val mCountriesModels = mutableListOf<CountryNameModel>()
    private val mCountriesModelsBck = mutableListOf<CountryNameModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = mCountriesModels.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindItem(mCountriesModels[position], position)

    fun switchData(data: List<CountryNameModel>?) {
        mCountriesModels.clear()
        mCountriesModelsBck.clear()
        if (data != null) {
            mCountriesModels.addAll(data)
            mCountriesModelsBck.addAll(data)
        }
        notifyDataSetChanged()
    }

    fun filterByCodeOrName(newText: String?) {
        if (TextUtils.isEmpty(newText)) {
            resetFilters()
        } else {
            mCountriesModels.apply {
                clear()
                val results = if (newText!!.length < 3) {
                    mCountriesModelsBck.filter { it.alpha2Code.equals(newText, true) }
                } else {
                    mCountriesModelsBck.filter { it.countryName.contains(newText, true) }
                }
                addAll(results)
            }
        }
        notifyDataSetChanged()
    }

    fun filterByLanguage(language: String?) {
        if (TextUtils.isEmpty(language)) {
            resetFilters()
        } else {
            mCountriesModels.apply {
                clear()
                val results = mCountriesModelsBck.filter { it.languages?.contains(language) == true }
                addAll(results)
            }
        }
        notifyDataSetChanged()
    }

    fun resetFilters() {
        if(mCountriesModels.size != mCountriesModelsBck.size){
            mCountriesModels.apply {
                clear()
                addAll(mCountriesModelsBck)
            }
        }
        notifyDataSetChanged()
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItem(model: CountryNameModel, position: Int) {
            with(itemView) {
                countryName.text = context.getString(R.string.list_country_label, model.countryName, model.alpha2Code)
                setBackgroundColor(
                    if (Utils.isOdd(position)) {
                        ContextCompat.getColor(context, R.color.evenBackround)
                    } else {
                        ContextCompat.getColor(context, R.color.oddBackround)
                    }
                )
                setOnClickListener { listener.invoke(model) }
            }
        }

    }

}