package com.haife.app.nobles.spirits.kotlin.app.impl;

import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.haife.app.nobles.spirits.kotlin.app.constant.SPConstant;
import com.jess.arms.http.GlobalHttpHandler;
import com.jess.arms.http.log.RequestInterceptor;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class GlobalHttpHandleImpl implements GlobalHttpHandler {
    private Context context;

    public GlobalHttpHandleImpl(Context context) {
        this.context = context;
    }

    /**
     * 这里可以先客户端一步拿到每一次 Http 请求的结果, 可以先解析成 Json, 再做一些操作, 如检测到 token 过期后
     * 重新请求 token, 并重新执行请求
     *
     * @param httpResult 服务器返回的结果 (已被框架自动转换为字符串)
     * @param chain      {@link Interceptor.Chain}
     * @param response   {@link Response}
     * @return
     */
    @Override
    public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
        if (!TextUtils.isEmpty(httpResult) && RequestInterceptor.isJson(response.body().contentType())) {
            //ArmsUtils.obtainAppComponentFromContext(context).gson().fromJson(httpResult, new TypeToken<>())
        }
         /* 这里如果发现 token 过期, 可以先请求最新的 token, 然后在拿新的 token 放入 Request 里去重新请求
        注意在这个回调之前已经调用过 proceed(), 所以这里必须自己去建立网络请求, 如使用 Okhttp 使用新的 Request 去请求
        create a new request and modify it accordingly using the new token
        Request newRequest = chain.request().newBuilder().header("token", newToken)
                             .build();
        retry the request
        response.body().close();
        如果使用 Okhttp 将新的请求, 请求成功后, 再将 Okhttp 返回的 Response return 出去即可
        如果不需要返回新的结果, 则直接把参数 response 返回出去即可*/
        return response;
    }

    /**
     * 这里可以在请求服务器之前拿到 {@link Request}, 做一些操作比如给 {@link Request} 统一添加 token 或者 header 以及参数加密等操作
     *
     * @param chain   {@link Interceptor.Chain}
     * @param request {@link Request}
     * @return
     */
    @Override
    public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
        return chain.request().newBuilder().addHeader("Content-Type", "application/json; charset=utf-8").addHeader("Cookie","UID=" +SPUtils.getInstance().getLong(SPConstant.UID, 0)+"; SID="+SPUtils.getInstance().getString(SPConstant.SID, "")).build();
    }
}
