package ru.textmaps.product.testmaps.interactors

import air.ru.obi.mobile.core.network.InteractorResult

interface Interactor<T, P> {
    suspend fun run(param: P): InteractorResult<T>
}
