package dev.falhad.movieshowcase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import dev.falhad.movieshowcase.R
import dev.falhad.movieshowcase.model.db.entity.MovieEntity
import dev.falhad.movieshowcase.model.db.entity.summaryString
import dev.falhad.movieshowcase.utils.loadImage

enum class MovieListMode {
    CARD,
    ROW
}

class MovieListAdapter(
    private var onItemClicked: (MovieEntity) -> Unit,
    private var onFavClicked: (MovieEntity) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var _mode: MovieListMode = MovieListMode.CARD
    private val _items: ArrayList<MovieEntity> = arrayListOf()



    fun update(items: ArrayList<MovieEntity>) {
        _items.clear()
        _items.addAll(items)
        notifyDataSetChanged()
    }

    fun clear(){
        _items.clear()
        notifyDataSetChanged()
    }

    fun changeMode(mode: MovieListMode) {
        _mode = mode
        notifyDataSetChanged()
    }


    override fun getItemViewType(position: Int) = when (_mode) {
        MovieListMode.CARD -> R.layout.item_movie_card
        MovieListMode.ROW -> R.layout.item_movie_row
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val v = layoutInflater.inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.item_movie_card -> MovieCardViewHolder(v)
            R.layout.item_movie_row -> MovieRowViewHolder(v)
            else -> MovieCardViewHolder(v)
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = _items[position]
        when (_mode) {
            MovieListMode.CARD -> (holder as MovieCardViewHolder).onBindView(
                item,
                onItemClicked,
                onFavClicked
            )
            MovieListMode.ROW -> (holder as MovieRowViewHolder).onBindView(
                item,
                onItemClicked,
                onFavClicked
            )
        }
    }

    override fun getItemCount() = _items.size

}


class MovieCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var movieCv: CardView = itemView.findViewById(R.id.movie_cv)
    private var rate: TextView = itemView.findViewById(R.id.rate)
    private var cover: ImageView = itemView.findViewById(R.id.cover)
    private var genres: TextView = itemView.findViewById(R.id.genres)
    private var title: TextView = itemView.findViewById(R.id.title)

    fun onBindView(
        movie : MovieEntity,
        onItemClicked: (MovieEntity) -> Unit,
        onFavClicked: (MovieEntity) -> Unit
    ) {

        title.text = movie.title
        title.isSelected = true
        rate.text = movie.imdbRating
        genres.text = movie.summaryString()
        genres.isSelected = true
        cover.loadImage(movie.poster)

        movieCv.setOnClickListener { onItemClicked(movie) }

    }

}

class MovieRowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var movieCv: CardView = itemView.findViewById(R.id.movie_cv)
    private var description: TextView = itemView.findViewById(R.id.description)
    private var rate: TextView = itemView.findViewById(R.id.rate)
    private var cover: ImageView = itemView.findViewById(R.id.cover)
    private var genres: TextView = itemView.findViewById(R.id.genres)
    private var title: TextView = itemView.findViewById(R.id.title)


    fun onBindView(
        movie: MovieEntity,
        onItemClicked: (MovieEntity) -> Unit,
        onFavClicked: (MovieEntity) -> Unit
    ) {

        title.text = movie.title
        title.isSelected = true

        rate.text = movie.imdbRating
        genres.text = movie.summaryString()
        genres.isSelected = true
        cover.loadImage(movie.poster)

        description.text = movie.plot

        movieCv.setOnClickListener { onItemClicked(movie) }



    }
}