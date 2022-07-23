package com.mauro.reto.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mauro.reto.storage.entity.MovieDb.Movies.tableName
import com.mauro.reto.storage.entity.MovieDb.Movies.Column

@Entity(tableName = tableName)
class MovieDb (
    @PrimaryKey(autoGenerate = true)
    var localId: Int = 0,

    @ColumnInfo(name = Column.adult)
    val adult: Boolean,

    @ColumnInfo(name = Column.backdrop_path)
    val backdrop_path: String,

    @ColumnInfo(name = Column.id)
    val id: Int,

    @ColumnInfo(name = Column.original_language)
    val original_language: String,

    /*@ColumnInfo(name = Column.original_title)
    val original_title: String,*/

    @ColumnInfo(name = Column.overview)
    val overview: String,

    @ColumnInfo(name = Column.popularity)
    val popularity: Double,

    @ColumnInfo(name = Column.poster_path)
    val poster_path: String,

    @ColumnInfo(name = Column.release_date)
    val release_date: String,

    @ColumnInfo(name = Column.title)
    val title: String,

    @ColumnInfo(name = Column.video)
    val video: Boolean,

    @ColumnInfo(name = Column.vote_average)
    val vote_average: Double,

    @ColumnInfo(name = Column.page)
    val page: Int = 0,

    @ColumnInfo(name = Column.total_page)
    val total_page: Int = 0,
)
{
    object Movies {
        const val tableName = "movies"

        object Column {
            //const val localId = "localId"
            const val adult = "adult"
            const val backdrop_path = "backdrop_path"
            const val id = "id"
            const val original_language = "original_language"
            const val original_title = "original_title"
            const val overview = "overview"
            const val popularity = "popularity"
            const val poster_path = "poster_path"
            const val release_date = "release_date"
            const val title = "title"
            const val video = "video"
            const val vote_average = "vote_average"
            const val page = "page"
            const val total_page = "total_page"
        }
    }
}