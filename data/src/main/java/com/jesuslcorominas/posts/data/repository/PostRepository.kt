package com.jesuslcorominas.posts.data.repository

import com.jesuslcorominas.posts.data.source.LocalDatasource
import com.jesuslcorominas.posts.data.source.RemoteDatasource
import com.jesuslcorominas.posts.domain.DatabaseEmptyException
import com.jesuslcorominas.posts.domain.Post
import io.reactivex.Single

class PostRepository(
    private val localDatasource: LocalDatasource,
    private val remoteDatasource: RemoteDatasource
) {

    fun getPosts(): Single<List<Post>> =
        localDatasource.getPosts()
            .switchIfEmpty(
                remoteDatasource.getPosts()
                    .flatMap { items ->
                        localDatasource.savePosts(items)
                            .andThen(localDatasource.getPosts())
                            .switchIfEmpty(Single.error(DatabaseEmptyException()))
                    }
            )

    // TODO se puede devolver un postdetail sin todos los detail
    // TODO hay que guardar la info del detail en bd o se consulta cada vez
    //  es decir, se cachea el listado, pero el detalle tambien. El  detalle completo o solo
    //  el autor. Los comentarios podrian cambiar con el tiempo
    // TODO La pantalla de detalle, si no se ha entrado antes ese detalle estara vacia si no hay
    //  conexion, no?
    fun getPostDetail(postId: Int): Single<Post> {
        return localDatasource.getPostDetail(postId)
            .flatMap { post -> remoteDatasource.getPostDetail(post) }
            .flatMap { post ->
                localDatasource.savePosts(listOf(post))
                    .andThen(localDatasource.getPostDetail(post.id))
            }
    }
}
