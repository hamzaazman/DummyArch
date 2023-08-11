package com.hamzaazman.dummyarch.common

import android.content.Context
import androidx.annotation.StringRes
import com.erkutaras.statelayout.StateLayout
import com.hamzaazman.dummyarch.R


sealed class Resource<T> {

    data class Success<T>(val data: T) : Resource<T>()

    data class Error<T>(val exception: Throwable) : Resource<T>()

    data class Loading<T>(val data: T? = null) : Resource<T>()

    fun <R> mapData(transform: (T) -> R): Resource<R> = when (this) {
        is Success -> Success(
            transform(data)
        )

        is Error -> Error(
            exception
        )

        is Loading -> Loading(
            data?.let { transform(it) }
        )
    }
}


sealed class Status {
    object Content : Status()
    data class Error(val exception: Throwable) : Status()
    object Loading : Status()
    object ContentWithLoading : Status()
}

class ResourceError(
    @StringRes
    val actionText: Int,
    @StringRes
    val errorTitle: Int,
    @StringRes
    val errorDesc: Int
)

class NoContentListingException : Exception()

object ErrorResolver {

    fun resolve(throwable: Throwable): ResourceError {
        return when (throwable) {
            is NoContentListingException -> ResourceError(
                actionText = R.string.no_content_error_action,
                errorDesc = R.string.no_content_error_message,
                errorTitle = R.string.default_error_title
            )

            else ->
                ResourceError(
                    actionText = R.string.common_action_retry,
                    errorDesc = R.string.default_error_message,
                    errorTitle = R.string.default_error_title
                )
        }
    }
}

class StatusViewState(private val status: Status) {

    fun getStateInfo(context: Context): StateLayout.StateInfo {
        return when (status) {
            Status.Content -> StateLayout.provideContentStateInfo()
            is Status.Error -> provideErrorState(context, status.exception)
            Status.Loading -> StateLayout.provideLoadingStateInfo()
            Status.ContentWithLoading -> StateLayout.provideLoadingWithContentStateInfo()
        }
    }

    private fun provideErrorState(context: Context, exception: Throwable): StateLayout.StateInfo {
        val resourceError = ErrorResolver.resolve(exception)
        return StateLayout.StateInfo(
            infoImage = R.drawable.state_info,
            infoTitle = context.getString(resourceError.errorTitle),
            infoMessage = context.getString(resourceError.errorDesc),
            infoButtonText = context.getString(resourceError.actionText),
            state = StateLayout.State.ERROR
        )
    }
}