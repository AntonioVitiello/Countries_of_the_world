package ant.vit.paesidelmondo.network.dto.details


import com.fasterxml.jackson.annotation.JsonProperty

data class Translations(
    @JsonProperty("br")
    val br: String?,
    @JsonProperty("de")
    val de: String?,
    @JsonProperty("es")
    val es: String?,
    @JsonProperty("fa")
    val fa: String?,
    @JsonProperty("fr")
    val fr: String?,
    @JsonProperty("hr")
    val hr: String?,
    @JsonProperty("it")
    val it: String?,
    @JsonProperty("ja")
    val ja: String?,
    @JsonProperty("nl")
    val nl: String?,
    @JsonProperty("pt")
    val pt: String?
)