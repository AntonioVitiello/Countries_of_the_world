package ant.vit.paesidelmondo.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.isVisible
import ant.vit.paesidelmondo.R
import kotlinx.android.synthetic.main.widget_progress.view.*

/**
 * Created by Vitiello Antonio
 */
class ProgressWidget : FrameLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        View.inflate(context, R.layout.widget_progress, this)
    }

    var show: Boolean
        get() = isVisible
        set(value) {
            isVisible = value
            progressBar.isVisible = value
        }

}