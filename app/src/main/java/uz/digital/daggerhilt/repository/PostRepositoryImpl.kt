package uz.digital.daggerhilt.repository

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.digital.daggerhilt.model.Post
import uz.digital.daggerhilt.network.ApiService
import uz.digital.daggerhilt.util.Response
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : PostRepository {
    override suspend fun getAllRemotePosts(): Flow<Response<List<Post>>> = flow {
        try {
            emit(Response.Loading)
            delay(500L)
            val response = apiService.getAllPosts()
            if (response.isSuccessful) {
                emit(Response.Success(response.body()!!))
            }
        } catch (e: Exception) {
            emit(Response.Error(e.message.toString()))
        }
    }

    override suspend fun getPostById(id: Int): Flow<Response<Post>> = flow {
        try {
            emit(Response.Loading)
            delay(500L)
            val response = apiService.getPostById(id)
            if (response.isSuccessful) {
                emit(Response.Success(response.body()!!))
            }
        } catch (e: Exception) {
            emit(Response.Error(e.message.toString()))
        }
    }

    override suspend fun createPost(post: Post): Flow<Boolean> = flow {
        try {
            val response = apiService.createPost(post)
            emit(response.code() == 201)
        } catch (e: Exception) {
            emit(false)
        }
    }

    override suspend fun updatePost(id: Int): Flow<Boolean> = flow {
        emit(apiService.updatePost(id).isSuccessful)
    }

    override suspend fun deletePost(id: Int): Flow<Boolean> = flow {
        emit(apiService.deletePost(id).isSuccessful)
    }
}