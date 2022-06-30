package dev.falhad.movieshowcase.ui.main

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.mikepenz.iconics.typeface.library.googlematerial.GoogleMaterial
import com.mikepenz.materialdrawer.iconics.iconicsIcon
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.nameRes
import com.mikepenz.materialdrawer.widget.AccountHeaderView
import dev.falhad.movieshowcase.R
import dev.falhad.movieshowcase.databinding.ActivityMainBinding
import dev.falhad.movieshowcase.ui.favorite.FavoriteActivity
import dev.falhad.movieshowcase.ui.hidden.HiddenActivity
import dev.falhad.movieshowcase.ui.search.SearchActivity
import dev.falhad.movieshowcase.utils.gone
import dev.falhad.movieshowcase.utils.showSnackBar
import dev.falhad.movieshowcase.utils.visible

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)

        setupDrawer(savedInstanceState)

        binding.navToSettings.setOnClickListener {
            startActivity(Intent(WifiManager.ACTION_PICK_WIFI_NETWORK))
        }

        monitorNetworkChanges()


    }

    private fun monitorNetworkChanges() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                onNetworkAvailable()
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                onNetworkLost()
            }
        }


        val connectivityManager = getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)

    }


    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }

    private fun onNetworkAvailable() {
        runOnUiThread {
            binding.internetLostCv.gone()
        }
    }

    private fun onNetworkLost() {
        runOnUiThread {
            binding.internetLostCv.visible()
        }
    }

    private fun setupDrawer(savedInstanceState: Bundle?) {
        AccountHeaderView(this).apply {
            attachToSliderView(binding.slider)
            selectionListEnabledForSingleProfile = false
            onAccountHeaderListener = { _, _, _ ->
                false
            }
            withSavedInstance(savedInstanceState)
        }


        val home = PrimaryDrawerItem().apply {
            nameRes = R.string.drawer_item_home
            iconicsIcon = GoogleMaterial.Icon.gmd_movie
            identifier = 1
        }

        val favorites = PrimaryDrawerItem().apply {
            nameRes = R.string.drawer_item_favorites
            iconicsIcon = GoogleMaterial.Icon.gmd_favorite
            identifier = 2
        }

        val hiddens = PrimaryDrawerItem().apply {
            nameRes = R.string.drawer_item_hiddens
            iconicsIcon = GoogleMaterial.Icon.gmd_remove_red_eye
            identifier = 3
        }

        val search = PrimaryDrawerItem().apply {
            nameRes = R.string.drawer_item_search
            iconicsIcon = GoogleMaterial.Icon.gmd_search
            identifier = 6
        }


        val settings = PrimaryDrawerItem().apply {
            nameRes = R.string.drawer_item_settings
            iconicsIcon = GoogleMaterial.Icon.gmd_settings
            identifier = 4
        }

        val logout = PrimaryDrawerItem().apply {
            nameRes = R.string.drawer_item_logout
            iconicsIcon = GoogleMaterial.Icon.gmd_logout
            identifier = 5
        }

        binding.slider.itemAdapter.add(
            home,
            search,
            favorites,
            hiddens,
            DividerDrawerItem(),
            settings,
            logout
        )

        binding.slider.onDrawerItemClickListener = { v, drawerItem, position ->
            when (drawerItem.identifier) {
                1L -> {}
                2L -> startActivity(Intent(this, FavoriteActivity::class.java))
                3L -> startActivity(Intent(this, HiddenActivity::class.java))
                4L -> notImplemented()
                5L -> logout()
                6L -> startActivity(Intent(this, SearchActivity::class.java))
            }

            closeDrawer()
            false
        }
    }

    private fun notImplemented() {
        showSnackBar(getString(R.string.feature_not_implemented))
    }

    fun logout() {
        notImplemented()
    }

    fun openDrawer() {
        binding.slider.drawerLayout?.open()
    }

    fun closeDrawer() {
        binding.slider.drawerLayout?.close()
    }


}