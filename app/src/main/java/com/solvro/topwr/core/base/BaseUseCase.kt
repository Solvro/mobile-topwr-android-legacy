package com.solvro.topwr.core.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseUseCase<Params, ResultType> {

    abstract suspend fun action(params: Params): ResultType

    operator fun invoke(
        params: Params,
        scope: CoroutineScope,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        onResult: (ResultType) -> Unit
    ) {
        scope.launch(dispatcher) {
            val result = action(params)
            onResult(result)
        }
    }
}