package ant.vit.paesidelmondo.network.dto.all


import com.fasterxml.jackson.annotation.JsonProperty

data class RegionalBloc(
    @JsonProperty("acronym")
    val acronym: String?,
    @JsonProperty("name")
    val name: String?,
    @JsonProperty("otherAcronyms")
    val otherAcronyms: List<Any>?,
    @JsonProperty("otherNames")
    val otherNames: List<String>?
)