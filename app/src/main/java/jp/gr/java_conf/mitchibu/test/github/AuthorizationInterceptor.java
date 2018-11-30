package jp.gr.java_conf.mitchibu.test.github;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class AuthorizationInterceptor implements Interceptor {
	private final SharedPreferences sp;

	AuthorizationInterceptor(SharedPreferences sp) {
		this.sp = sp;
	}

	@Override
	public Response intercept(@NonNull Chain chain) throws IOException {
		String token = sp.getString(GithubClient.TOKEN, null);
		if(token == null) {
			return chain.proceed(chain.request());
		} else {
			return chain.proceed(chain.request().newBuilder()
					.header("Authorization", "token " + token)
					.build());
		}
	}
}
