package ant.vit.paesidelmondo.network.dto.details


import com.fasterxml.jackson.annotation.JsonProperty

data class Language(
    @JsonProperty("iso639_1")
    val iso6391: String?,
    @JsonProperty("iso639_2")
    val iso6392: String?,
    @JsonProperty("name")
    val name: String?,
    @JsonProperty("nativeName")
    val nativeName: String?
)