package ant.vit.paesidelmondo.ui.model

/**
 * Created by Vitiello Antonio
 */
class ToolbarType(val type: Int, val title: String? = null) {
    companion object {
        const val LIST = 0
        const val DETAILS = 1
    }
}