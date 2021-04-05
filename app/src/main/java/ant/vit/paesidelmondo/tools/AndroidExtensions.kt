package ant.vit.paesidelmondo.tools

import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import ant.vit.paesidelmondo.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import io.reactivex.Single
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Vitiello Antonio
 */
fun Fragment.addToStackFragment(@IdRes fragmentContainer: Int, fragment: Fragment, backStackName: String) {
    parentFragmentManager.beginTransaction().apply {
        setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
        replace(fragmentContainer, fragment).addToBackStack(backStackName)
    }.commit()
}

fun FragmentActivity.addToStackFragment(@IdRes fragmentContainer: Int, fragment: Fragment, backStackName: String) {
    supportFragmentManager.beginTransaction().apply {
        setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
        replace(fragmentContainer, fragment).addToBackStack(backStackName)
    }.commit()
}

fun FragmentActivity.swapFragment(@IdRes fragmentContainer: Int, fragment: Fragment, backStackName: String) {
    supportFragmentManager.apply {
        popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        beginTransaction().apply {
            setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
            replace(fragmentContainer, fragment, backStackName)
        }.commit()
    }
}

fun <T> Single<T>.manageLoading(showProgressLiveData: MutableLiveData<SingleEvent<Boolean>>): Single<T> {
    return compose { upstream ->
        upstream
            .doOnSubscribe {
                showProgressLiveData.postValue(SingleEvent(true))
            }
            .doOnDispose {
                showProgressLiveData.postValue(SingleEvent(false))
            }
            .doOnError {
                showProgressLiveData.postValue(SingleEvent(false))
            }
            .doOnSuccess {
                showProgressLiveData.postValue(SingleEvent(false))
            }
    }
}

fun Date.format(dateFormat: SimpleDateFormat): String {
    return dateFormat.format(this)
}

fun String.parseDateOrNull(dateFormat: SimpleDateFormat): Date? {
    return try {
        dateFormat.parse(this)
    } catch (exc: ParseException) {
        Log.e("AndroidExtensions", "Error while parsing date:$this.")
        null
    }
}

fun ImageView.loadImage(imageUrl: String?) {
    Picasso.get()
        .load(imageUrl)
        .fit()
        .placeholder(R.drawable.ic_placeholder)
        .error(R.drawable.ic_broken_image)
        .into(this, object : Callback {
            override fun onSuccess() {
                Log.d("AndroidExtensions", "Image loaded: $imageUrl")
            }

            override fun onError(exc: Exception) {
                Log.e("AndroidExtensions", "Error while loading image: $imageUrl", exc)
            }
        })
}

fun FragmentActivity.closeKeyboard() {
    val viewFocus = currentFocus ?: return
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(viewFocus.applicationWindowToken, 0)
}

/**
 * Find implemented inteface in parentFragment, targetFragment, activity or NULL
 */
@Suppress("DEPRECATION")
inline fun <reified T> Fragment.implementedInterface(): T? {
    return when {
        parentFragment is T -> parentFragment as T
        targetFragment is T -> targetFragment as T
        activity is T -> activity as T
        else -> {
            Log.e("AndroidExtension", "Cannot trigger interface methods cause the caller doesn't implement the interface ${T::class.java}")
            null
        }
    }
}
