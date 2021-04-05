package ant.vit.paesidelmondo.network.dto.details


import com.fasterxml.jackson.annotation.JsonProperty

data class Currency(
    @JsonProperty("code")
    val code: String?,
    @JsonProperty("name")
    val name: String?,
    @JsonProperty("symbol")
    val symbol: String?
)