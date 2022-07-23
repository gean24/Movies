package com.mauro.reto.core.interceptor

import com.mauro.reto.core.event.UnauthorizedEvent
import okhttp3.Interceptor
import okhttp3.Response
import org.greenrobot.eventbus.EventBus
import java.io.IOException

internal class UnauthorizedInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response: Response = chain.proceed(chain.request())
        if (response.code == 401) {
            EventBus.getDefault().post(UnauthorizedEvent.instance())
        }
        return response
    }
}