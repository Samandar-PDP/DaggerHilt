package uz.digital.daggerhilt.util

import uz.digital.daggerhilt.database.PostEntity
import uz.digital.daggerhilt.model.Post

fun Post.toPostEntity(): PostEntity {
    return PostEntity(
        title = title,
        body = body
    )
}
fun PostEntity.toPost(): Post {
    return Post(
        title = title,
        body = body
    )
}