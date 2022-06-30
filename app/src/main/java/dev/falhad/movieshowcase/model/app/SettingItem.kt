package dev.falhad.movieshowcase.model.app

sealed class SettingItem {

    class InfoItem(val key: String, val value: String, val tag: String) : SettingItem()

    class DefaultItem(val title: String, val slug: SettingSlug, val icon: Int) : SettingItem()

    class StringItem(val title: String, val slug: SettingSlug, val icon: Int, val value: String) :
        SettingItem()

    class ToggleItem(val title: String, val slug: SettingSlug, val icon: Int, val value: Boolean) :
        SettingItem()

    class IconItem(
        val title: String,
        val slug: SettingSlug,
        val icon: Int,
        val icon2: Int,
        val icon2Color: Int
    ) :
        SettingItem()
}