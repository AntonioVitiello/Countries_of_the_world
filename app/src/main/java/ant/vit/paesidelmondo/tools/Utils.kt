package ant.vit.paesidelmondo.tools

import android.content.Context
import android.content.DialogInterface
import android.os.Handler
import android.os.Looper
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import ant.vit.paesidelmondo.R
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Vitiello Antonio
 */
class Utils {
    companion object {
        val onlyAlphaRegex by lazy(LazyThreadSafetyMode.NONE) { "[^A-Za-z\\s]".toRegex() }

        //eg: 2017-01-29 23:42:03
        val yearDateFormat by lazy(LazyThreadSafetyMode.NONE) {
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", getDefaultLocale())
        }

        fun formatDateOrNull(dateFormat: SimpleDateFormat, date: Date?): String? {
            return date?.format(dateFormat)
        }

        fun parseDateOrNull(dateFormat: SimpleDateFormat, string: String?): Date? {
            return string?.parseDateOrNull(dateFormat)
        }

        fun getDefaultLocale(): Locale {
            return Locale.getDefault()
        }

        fun isOdd(number: Int): Boolean {
            return number % 2 == 0
        }

        fun showInfoDialog(
            context: Context, @StringRes title: Int, @StringRes message: Int, timeout: Long? = null,
            listener: (() -> Unit)? = null
        ): AlertDialog {
            val alertDialog = AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton(android.R.string.ok) { dialog, _ ->
                    doDialogDismiss(dialog, listener)
                }
                .setIcon(android.R.drawable.ic_dialog_info)
                .show()
            if (timeout != null) {
                Handler(Looper.getMainLooper()).postDelayed({ doDialogDismiss(alertDialog, listener) }, timeout)
            }
            return alertDialog
        }

        fun doDialogDismiss(dialog: DialogInterface, listener: (() -> Unit)? = null) {
            dialog.dismiss()
            listener?.invoke()
        }

        fun showErrorDialog(context: Context, message: String, listener: () -> Unit) {
            AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.error_dialog_title))
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok) { dialog, _ -> doDialogDismiss(dialog, listener) }
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }

        fun showCriticalErrorDialog(
            context: Context, message: String, @StringRes leftButton: Int, @StringRes rightButton: Int,
            leftListener: () -> Unit, rightListener: () -> Unit
        ) {
            AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.error_dialog_title))
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(context.getString(leftButton)) { dialog, _ -> doDialogDismiss(dialog, leftListener) }
                .setNegativeButton(context.getString(rightButton)) { dialog, _ -> doDialogDismiss(dialog, rightListener) }
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }
    }

}