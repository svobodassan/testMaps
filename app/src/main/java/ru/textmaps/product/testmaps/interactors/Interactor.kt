package ru.textmaps.product.testmaps.interactors

interface Interactor<T, P> {
    suspend fun run(param: P): InteractorResult<T>
}
