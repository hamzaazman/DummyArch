package com.hamza.common.common


import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transform

fun <T> Flow<Resource<T>>.doOnSuccess(action: suspend (T) -> Unit): Flow<Resource<T>> =
    transform { value ->
        if (value is Resource.Success) {
            action(value.data)
        }
        return@transform emit(value)
    }

fun <T> Flow<Resource<T>>.doOnError(action: suspend (Throwable) -> Unit): Flow<Resource<T>> =
    transform { value ->
        if (value is Resource.Error) {
            action(value.exception)
        }
        return@transform emit(value)
    }

fun <T> Flow<Resource<T>>.doOnStatusChanged(action: suspend (Status) -> Unit): Flow<Resource<T>> =
    transform { value ->
        when (value) {
            is Resource.Success -> action(Status.Content)
            is Resource.Error -> action(Status.Error(value.exception))
            is Resource.Loading -> action(Status.Loading)
        }
        return@transform emit(value)
    }

fun <T> Flow<Resource<T>>.doOnLoading(action: suspend () -> Unit): Flow<Resource<T>> =
    transform { value ->
        if (value is Resource.Loading) {
            action()
        }
        return@transform emit(value)
    }

fun String.addSearchPrefix(prefix: String): String {
    return prefix.plus(this)
}

fun Int.addSearchPrefix(prefix: Int): Int {
    return prefix.plus(this)
}

infix fun String.okWith(bound: Int) = length > bound

fun EditText.observeTextChanges(): Flow<String> {
    return callbackFlow {
        val textWatcher = object : AbstractTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                super.onTextChanged(s, start, before, count)
                trySend(s.toString())
            }
        }
        addTextChangedListener(textWatcher)

        awaitClose {
            removeTextChangedListener(textWatcher)
        }
    }.onStart {
        text?.let {
            emit(it.toString())
        }
    }
}

abstract class AbstractTextWatcher : TextWatcher {
    override fun afterTextChanged(s: Editable?) {}
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}
