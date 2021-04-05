package ant.vit.paesidelmondo.ui

/**
 * Created by Vitiello Antonio
 */
interface INavigationListener {
    fun canNavigateBack(): Boolean
    fun canNavigateUp(): Boolean
}