package ant.vit.paesidelmondo.ui

/**
 * Created by Vitiello Antonio
 */
interface IOnBackPressed {
    fun onBackPressed(): Boolean
    fun canNavigateUp(): Boolean
}