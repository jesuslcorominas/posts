package com.jesuslcorominas.posts.data.source

import com.jesuslcorominas.posts.domain.Post
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface LocalDatasource {

    /**
     * Obtiene un listado de post (sin detalles) de la base de datos
     */
    fun getPosts(): Maybe<List<Post>>

    /**
     * Guarda una lista de post, y sus detalles asociados, en base de datos
     */
    fun savePosts(posts: List<Post>): Completable

    /**
     * Obtiene un post, sin detalle de base de datos.
     */
    fun findPostById(postId: Int): Single<Post>

    /**
     * Obtiene un post, con sus detalles asociados, de base de datos o nada si el autor
     * de este post es nulo
     */
    fun getPostDetail(postId: Int): Maybe<Post>
}
