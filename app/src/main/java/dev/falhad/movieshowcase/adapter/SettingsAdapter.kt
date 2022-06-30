package dev.falhad.movieshowcase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import dev.falhad.movieshowcase.R
import dev.falhad.movieshowcase.model.app.SettingItem


class SettingsAdapter(
    private var onItemClicked: (SettingItem) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val _items: ArrayList<SettingItem> = arrayListOf();

    fun update(items: ArrayList<SettingItem>){
        _items.clear()
        _items.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int) = R.layout.item_setting_default

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val v = layoutInflater.inflate(viewType, parent, false)
        return SettingDefaultItemViewHolder(v)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = _items[position]
        when (holder) {
            is SettingDefaultItemViewHolder -> holder.onBindView(
                item as SettingItem.DefaultItem,
                onItemClicked
            )
        }
    }

    override fun getItemCount() = _items.size

}


class SettingDefaultItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var settingItemCv: CardView = itemView.findViewById(R.id.setting_item_cv)
    private var itemIconIv: ImageView = itemView.findViewById(R.id.item_icon_iv)
    private var itemTitleTv: TextView = itemView.findViewById(R.id.item_title_tv)

    fun onBindView(defaultItem: SettingItem.DefaultItem, onItemClicked: (SettingItem) -> Unit) {
        settingItemCv.setOnClickListener { onItemClicked.invoke(defaultItem) }
        itemIconIv.setImageResource(defaultItem.icon)
        itemTitleTv.text = defaultItem.title
    }
}
