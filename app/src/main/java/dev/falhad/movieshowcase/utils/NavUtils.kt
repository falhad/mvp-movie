package dev.falhad.movieshowcase.utils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import dev.falhad.movieshowcase.R
import dev.falhad.movieshowcase.model.api.GeneralException
import dev.falhad.movieshowcase.model.db.entity.MovieEntity
import dev.falhad.movieshowcase.ui.movie.MovieActivity


fun String.openAsWebPage(context: Context): Result<String> {
    return try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(this))
        context.startActivity(intent)
        Result.success("")
    } catch (e: Exception) {
        Result.failure(GeneralException(e.message ?: ""))
    }
}

fun Activity.copyText(text: String?) {
    if (text.isNullOrEmpty()) return
    val clipboard = getSystemService(AppCompatActivity.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("copy", text)
    clipboard.setPrimaryClip(clip)
    showSnackBar(getString(R.string.text_s_copied).format(text))
}

fun Fragment.copyText(text: String?) = this.requireActivity().copyText(text)

fun Fragment.finishActivity() = this.requireActivity().finish()

fun Activity.handleInAppUrl(url: String? = null) {
    try {
        val browserIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(url)
        )
        browserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(browserIntent)
    } catch (e: Exception) {
    }
}

fun Fragment.handleInAppUrl(url: String? = null) = requireActivity().handleInAppUrl(url)


fun Activity.openMovie(movie : MovieEntity){
    val intent = Intent(this, MovieActivity::class.java)
    intent.putExtra("id",movie.id)
    startActivity(intent)
}

fun Fragment.openMovie(movie: MovieEntity) = requireActivity().openMovie(movie)

//fun Activity.openWebViewActivity(url: String, title: String) {
//    val intent = Intent(this, WebViewActivity::class.java)
//    intent.putExtra("url", url)
//    intent.putExtra("title", title)
//    println("url is $url")
//    startActivity(intent)
//}

//fun Fragment.openWebViewActivity(url: String, title: String) =
//    this.requireActivity().openWebViewActivity(url, title)