package jp.gr.java_conf.mitchibu.test.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class AuthorizationInterceptor implements Interceptor {
	public static String accessToken = null;

	@Override
	public Response intercept(Chain chain) throws IOException {
		if(accessToken == null) {
			return chain.proceed(chain.request());
		} else {
			return chain.proceed(chain.request().newBuilder()
					.header("Authorization", "token " + accessToken)
					.build());
		}
	}
}
