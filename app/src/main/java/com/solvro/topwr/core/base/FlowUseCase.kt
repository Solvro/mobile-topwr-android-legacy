package com.solvro.topwr.core.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class FlowUseCase<Params, ResultType> {
    abstract suspend fun action(params: Params, scope: CoroutineScope): Flow<ResultType>

    operator fun invoke(
        params: Params,
        scope: CoroutineScope,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        onResult: (ResultType) -> Unit
    ) {
        scope.launch(dispatcher) {
            action(params, scope).collectLatest {
                onResult(it)
            }
        }
    }
}