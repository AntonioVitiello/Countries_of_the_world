package ant.vit.paesidelmondo.network.dto.all


import com.fasterxml.jackson.annotation.JsonProperty

data class AllResponseItem(
    @JsonProperty("alpha2Code")
    val alpha2Code: String?,
    @JsonProperty("alpha3Code")
    val alpha3Code: String?,
    @JsonProperty("altSpellings")
    val altSpellings: List<String>?,
    @JsonProperty("area")
    val area: Double?,
    @JsonProperty("borders")
    val borders: List<String>?,
    @JsonProperty("callingCodes")
    val callingCodes: List<String>?,
    @JsonProperty("capital")
    val capital: String?,
    @JsonProperty("cioc")
    val cioc: String?,
    @JsonProperty("currencies")
    val currencies: List<Currency>?,
    @JsonProperty("demonym")
    val demonym: String?,
    @JsonProperty("flag")
    val flag: String?,
    @JsonProperty("gini")
    val gini: Double?,
    @JsonProperty("languages")
    val languages: List<Language>?,
    @JsonProperty("latlng")
    val latlng: List<Double>?,
    @JsonProperty("name")
    val name: String?,
    @JsonProperty("nativeName")
    val nativeName: String?,
    @JsonProperty("numericCode")
    val numericCode: String?,
    @JsonProperty("population")
    val population: Int?,
    @JsonProperty("region")
    val region: String?,
    @JsonProperty("regionalBlocs")
    val regionalBlocs: List<RegionalBloc>?,
    @JsonProperty("subregion")
    val subregion: String?,
    @JsonProperty("timezones")
    val timezones: List<String>?,
    @JsonProperty("topLevelDomain")
    val topLevelDomain: List<String>?,
    @JsonProperty("translations")
    val translations: Translations?
)