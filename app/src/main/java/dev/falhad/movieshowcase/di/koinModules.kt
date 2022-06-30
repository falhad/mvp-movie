package dev.falhad.movieshowcase.di

import android.content.Context.MODE_PRIVATE
import androidx.room.Room
import com.afollestad.rxkprefs.rxkPrefs
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import dev.falhad.movieshowcase.RootViewModel
import dev.falhad.movieshowcase.model.db.AppDatabase
import dev.falhad.movieshowcase.network.controller.MovieController
import dev.falhad.movieshowcase.repository.AuthRepository
import dev.falhad.movieshowcase.repository.MovieRepository
import dev.falhad.movieshowcase.ui.favorite.FavoriteViewModel
import dev.falhad.movieshowcase.ui.hidden.HiddenViewModel
import dev.falhad.movieshowcase.ui.home.HomeViewModel
import dev.falhad.movieshowcase.ui.movie.MovieViewModel
import dev.falhad.movieshowcase.ui.search.SearchViewModel
import dev.falhad.movieshowcase.utils.API_URL
import dev.falhad.movieshowcase.utils.debugMode
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    single {
        rxkPrefs(androidContext(), "main_pref", MODE_PRIVATE)
    }

    single { androidContext().getSharedPreferences("default", MODE_PRIVATE) }
    single { Gson() }

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, "moviedb"
        ).fallbackToDestructiveMigration().build()
    }


    single { get<AppDatabase>().settingDao() }
    single { get<AppDatabase>().movieDao() }

    single { Moshi.Builder().build() }


    single { AuthRepository(get(), get()) }
    single { MovieRepository(get(),get()) }


    single {

        val builder = OkHttpClient.Builder().apply {
//            connectTimeout(10, TimeUnit.SECONDS)
//            writeTimeout(10, TimeUnit.SECONDS)
//            readTimeout(10, TimeUnit.SECONDS)
//            retryOnConnectionFailure(true)

            if (debugMode) {
                addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
            }

        }


        val client = builder.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(client)
            .build()

        retrofit
    }


    single { get<Retrofit>().create(MovieController::class.java) }



    viewModel { RootViewModel(get(), get(), get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { MovieViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { HiddenViewModel(get()) }

}