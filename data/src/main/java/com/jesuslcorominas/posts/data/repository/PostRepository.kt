package com.jesuslcorominas.posts.data.repository

import com.jesuslcorominas.posts.data.source.LocalDatasource
import com.jesuslcorominas.posts.data.source.RemoteDatasource
import com.jesuslcorominas.posts.domain.DatabaseEmptyException
import com.jesuslcorominas.posts.domain.DatabaseException
import com.jesuslcorominas.posts.domain.Post
import io.reactivex.Single

class PostRepository(
    private val localDatasource: LocalDatasource,
    private val remoteDatasource: RemoteDatasource
) {

    /**
     * Obtiene el listado de Post de la aplicacion. Primero trata de obtener los Post remotos
     * y guardarlos en la base de datos. Si falla al obtenerlos del servidor trata de obtenerlos
     * de la base de datos y si eso tambien falla lanza el error
     */
    fun getPosts(): Single<List<Post>> =
    remoteDatasource
        .getPosts()
        .flatMap { posts ->
            localDatasource
                .savePosts(posts)
                .andThen(getLocalPostsOrThrowException(DatabaseException()))
        }.onErrorResumeNext {
            getLocalPostsOrThrowException(it)
        }

    private fun getLocalPostsOrThrowException(e: Throwable) = localDatasource
        .getPosts()
        .switchIfEmpty(Single.error(e))

    /**
     * Obtiene el detalle de un Post. Primero obtiene el Post (sin detalles) de la base de datos
     * a partir de su id. Con este post obtiene los detalles del servidor (autor y comentarios).
     * Si falla ira a buscar esos datos a la base de datos, siendo imprescindible que obtenga el
     * autor lanzando un error si este es nulo.
     */
    fun getPostDetail(postId: Int): Single<Post> = localDatasource
        .findPostById(postId)
        .onErrorResumeNext(Single.error(DatabaseException()))
        .flatMap { post -> remoteDatasource.getPostDetail(post) }
        .onErrorResumeNext {
            getLocalPostDetailOrThrowException(postId, it)
        }
        .flatMap { post ->
            localDatasource
                .savePosts(listOf(post))
                .andThen(getLocalPostDetailOrThrowException(postId, DatabaseEmptyException()))
        }


    private fun getLocalPostDetailOrThrowException(postId: Int, e: Throwable) = localDatasource
        .getPostDetail(postId)
        .switchIfEmpty(Single.error(e))

}
