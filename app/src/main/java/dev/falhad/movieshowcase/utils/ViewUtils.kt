package dev.falhad.movieshowcase.utils

import android.app.Activity
import android.content.Context
import android.content.res.TypedArray
import android.graphics.ColorFilter
import android.text.Spanned
import android.util.TypedValue
import android.view.View
import android.widget.*
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import coil.imageLoader
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.SimpleColorFilter
import com.airbnb.lottie.model.KeyPath
import com.airbnb.lottie.value.LottieValueCallback
import com.google.android.material.textfield.TextInputLayout
import dev.falhad.movieshowcase.R
import dev.falhad.movieshowcase.network.Presenter





fun Activity.showSnackBar(msg: String?) {
    if (msg.isNullOrEmpty()) return
    Toast.makeText(this, "$msg", Toast.LENGTH_SHORT).show()
}

fun Fragment.showSnackBar(msg: String?) = requireActivity().showSnackBar(msg)


fun Activity.showPresenterErrorSnackBar(presenter: Presenter.Error) =
    showSnackBar(presenter.msg ?: getString(presenter.res ?: R.string.try_again))

fun Fragment.showPresenterErrorSnackBar(presenter: Presenter.Error) =
    requireActivity().showPresenterErrorSnackBar(presenter)


fun View?.visible() {
    this?.visibility = View.VISIBLE
}

fun View?.invisible() {
    this?.visibility = View.INVISIBLE
}

fun View?.gone() {
    this?.visibility = View.GONE
}

fun LottieAnimationView.changeLayersColor(
    @ColorRes colorRes: Int
) {
    val color = ContextCompat.getColor(context, colorRes)
    val filter = SimpleColorFilter(color)
    val keyPath = KeyPath("**")
    val callback: LottieValueCallback<ColorFilter> = LottieValueCallback(filter)

    addValueCallback(keyPath, LottieProperty.COLOR_FILTER, callback)
}

fun TextInputLayout?.hideErrors() {
    this?.isErrorEnabled = false
    this?.error = null
}

fun TextInputLayout?.showErrorIfEmpty(text: String?, error: String) {
    if (text.isNullOrEmpty()) {
        this?.error = error
        this?.requestFocus()
    }
}

fun EditText?.disable() {
    this?.isFocusable = false
    this?.isClickable = true
}

fun EditText?.enable() {
    this?.isFocusable = true
    this?.isFocusableInTouchMode = true
    this?.isClickable = true
}


fun TextInputLayout?.showError(error: String) {
    this?.error = error
    this?.requestFocus()
}


fun EditText?.textOrNull(): String? = this?.text?.toString()

fun ImageView?.loadSvg(url: String) {
    this?.context?.let { ctx ->
        val request = ImageRequest.Builder(ctx)
            .data(url)
            .transformations(CircleCropTransformation())
            .target(this)
            .build()

        ctx.imageLoader.enqueue(request)
    }
}

fun ImageView?.loadImage(url: String?) {

    this?.context?.let { ctx ->

        val request = ImageRequest.Builder(ctx)
            .data(url)
            .target(this)
            .build()

        ctx.imageLoader.enqueue(request)
    }
}


fun Button.changeColor(context: Context, @ColorRes color: Int) {
    this.backgroundTintList = ContextCompat.getColorStateList(context, color)
}


@ColorInt
fun Context.getColorFromAttr(
    @AttrRes attrColor: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int {
    theme.resolveAttribute(attrColor, typedValue, resolveRefs)
    return typedValue.data
}


fun Context.colorByAttr(attr: Int): Int {
    val typedValue = TypedValue()
    val a: TypedArray =
        this.obtainStyledAttributes(typedValue.data, intArrayOf(attr))
    val color = a.getColor(0, 0)
    a.recycle()
    return color
}

fun String?.toHtml(): Spanned? {
    if (this.isNullOrEmpty()) return null
    return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_COMPACT)
}
