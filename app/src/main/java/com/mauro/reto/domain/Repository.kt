package com.mauro.reto.domain

import com.mauro.reto.api.Service
import com.mauro.reto.api.request.*
import com.mauro.reto.api.response.*
import com.mauro.reto.core.ui.Result
import com.mauro.reto.core.utils.httpError
import com.mauro.reto.storage.MovieDao
import com.mauro.reto.storage.entity.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import org.json.JSONArray
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


interface Repository {

    fun getMovies(request: MovieRequest): Flow<Result<List<MovieDb>>>
    fun getMovieDetail(localId:Int): Flow<Result<MovieDb>>

    suspend fun getMovieWS(page: Int): Response<BaseResponse<List<MoviesResponse>>>
}

@Singleton
class DefaultRepository @Inject constructor(
    private val movieDao: MovieDao,
    private val service: Service,

) : Repository {
    companion object{
        val ERROR_MESSAGE = "No se pudo conectar con el servidor"
        val ERROR_MESSAGE_SAVE = "Se grabaron los cambios localmente"
    }

    override fun getMovies(
        request: MovieRequest
    ): Flow<Result<List<MovieDb>>> = flow {
        emit(Result.Loading)
        if (request.isNetworkAvailable) {
            val response = getMovieWS(
                request.page
            )
            if (!response.isSuccessful) {
                try {
                    val jError = JSONArray(response.errorBody()!!.string())
                    emit(Result.Error(List(jError.length()) { i ->
                        Exception(
                            jError.getJSONObject(
                                i
                            ).toString()
                        )
                    }))
                } catch (e: Exception) {
                    Timber.e(e)
                    emit(Result.Error(List(1) { Exception(ERROR_MESSAGE) }))
                }
            } else if (response.body()!!.errors != null && response.body()!!.errors!!.isNotEmpty()) {
                emit(Result.Error(response.body()!!.errors!!.map { Exception(it) }))
            } else {
                val data = response.body()!!.data//.map { it.total_pages = response.body()!!.total_pages  }
                //total_pages = response.body()!!.total_pages

                movieDao.clearAndInsertMovie(
                    data.map {
                        it.page = request.page
                        it.total_pages = response.body()!!.total_pages
                        it.toMovieDb()
                    },
                    request.page==1
                )
            }
        }
        /*else
        {
            emit(Result.Error(List(1) { Exception(ERROR_MESSAGE) }))
        }*/
        val cached = movieDao.getMovies(request.page)
        emitAll(cached.map { Result.Success(it) })

    }.flowOn(Dispatchers.IO)

    override suspend fun getMovieWS(
        page:Int
    ): Response<BaseResponse<List<MoviesResponse>>> {
        return try {
            service.initial(page,"f46b58478f489737ad5a4651a4b25079")
        } catch (e: Exception) {
            Timber.e(e)
            httpError(404)
        }
    }

    override fun getMovieDetail(localId:Int): Flow<Result<MovieDb>> = flow {
        emit(Result.Loading)
        val cached = movieDao.getMovieById(localId)
        emitAll(cached.map {
            if (it != null)
                Result.Success(it)
            else
                Result.Error(List(1) {Exception("No se encontró registro")})
        })
    }.flowOn(Dispatchers.IO)

}

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun it(it: DefaultRepository): Repository
}