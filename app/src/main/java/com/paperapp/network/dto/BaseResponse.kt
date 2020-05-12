package com.paperapp.network.dto

import com.paperapp.logger.PaperLoggerFactory

enum class BaseResponseCode(val code: Int) {
    UNMODIFIED(304),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403)
}

class BaseResponseEmptyBody

class BaseResponse<T> private constructor(
    internal val success: (BaseResponseSuccess<T>)? = null,
    internal val failure: (BaseResponseFailure)? = null,
    internal val unmodified: (BaseResponseUnmodified)? = null
) {
    companion object {
        fun <T> ofSuccess(success: BaseResponseSuccess<T>): BaseResponse<T> =
            BaseResponse(success = success)

        fun <T> ofFailure(failure: BaseResponseFailure): BaseResponse<T> =
            BaseResponse(failure = failure)

        fun <T> ofUnmodified(unmodified: BaseResponseUnmodified): BaseResponse<T> =
            BaseResponse(unmodified = unmodified)
    }

    fun handle(name: String) = BaseResponseHandler(name = name, baseResponse = this)
}

data class BaseResponseSuccess<T>(
    val data: T?
)

data class BaseResponseFailure(
    val responseCode: Int,
    val responseMessage: String
)

data class BaseResponseUnmodified(
    val responseMessage: String
)

class BaseResponseHandler<T>(
    private val name: String,
    private val baseResponse: BaseResponse<T>
) {
    private val logger = PaperLoggerFactory.getLogger("ResponseHandler")

    /**
     * Success
     */

    fun onSuccess(action: (T?) -> Unit): BaseResponseHandler<T> {
        if (baseResponse.success != null) {
            logger.info("'${name}' request success has been handled")

            action(baseResponse.success.data)
        }

        return this
    }

    /**
     * Any
     */

    fun onAnyResult(action: () -> Unit) = handle(
        handlerName = "any result",
        condition = true,
        action = action
    )

    /**
     * Unmodified
     */

    fun onUnmodified(action: () -> Unit) = handle(
        handlerName = "unmodified result",
        condition = baseResponse.unmodified != null,
        action = action
    )

    /**
     * Failure
     */

    fun onAnyFailure(action: () -> Unit) = handle(
        handlerName = "failure result",
        condition = baseResponse.failure != null,
        action = action
    )

    fun onBadRequest(action: () -> Unit) = handle(
        handlerName = "bad request result",
        code = BaseResponseCode.BAD_REQUEST,
        action = action
    )

    fun onUnauthorized(action: () -> Unit) = handle(
        handlerName = "unauthorized result",
        code = BaseResponseCode.UNAUTHORIZED,
        action = action
    )

    fun onForbidden(action: () -> Unit) = handle(
        handlerName = "forbidden result",
        code = BaseResponseCode.FORBIDDEN,
        action = action
    )

    fun onUnknownResponseCode(action: () -> Unit) = handle(
        handlerName = "unknown response code",
        condition = baseResponse.failure?.responseCode?.let { code ->
            BaseResponseCode.values().map { it.code }.contains(code)
        } ?: false,
        action = action
    )

    /**
     * Inner handlers
     */

    private fun handle(
        handlerName: String,
        code: BaseResponseCode,
        action: () -> Unit
    ): BaseResponseHandler<T> {
        return handle(
            handlerName = handlerName,
            condition = baseResponse.failure?.responseCode == code.code,
            action = action
        )
    }

    private fun handle(
        handlerName: String,
        condition: Boolean,
        action: () -> Unit
    ): BaseResponseHandler<T> {
        if (condition) {
            logger.info("'${name}' request $handlerName has been handled")

            action()
        }

        return this
    }
}