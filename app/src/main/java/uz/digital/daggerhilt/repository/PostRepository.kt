package uz.digital.daggerhilt.repository

import kotlinx.coroutines.flow.Flow
import uz.digital.daggerhilt.model.Post
import uz.digital.daggerhilt.util.Response

interface PostRepository {
    // remote
    suspend fun getAllRemotePosts(): Flow<Response<List<Post>>>
    suspend fun createPost(post: Post): Flow<Boolean>
    suspend fun updatePost(id: Int): Flow<Boolean>
    suspend fun deletePost(id: Int): Flow<Boolean>
    suspend fun getPostById(id: Int): Flow<Response<Post>>

    // local
    suspend fun savePostList(list: List<Post>)
    suspend fun clearData()
    fun getAllLocalPosts(): Flow<List<Post>>
}