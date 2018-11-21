package jp.gr.java_conf.mitchibu.test.api;

import android.content.Context;

import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import jp.gr.java_conf.mitchibu.test.graphql.GraphQLConverter;
import jp.gr.java_conf.mitchibu.test.graphql.GraphQl;
import jp.gr.java_conf.mitchibu.test.graphql.QueryContainerBuilder;
import jp.gr.java_conf.mitchibu.test.graphql.Test;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Client {
	private static <T> Single<T> api(Single<T> observable) {
		return observable.compose(new SingleTransformer<T, T>() {
			@Override
			public SingleSource<T> apply(Single<T> upstream) {
				return upstream
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread());
						//.retry(3)
//						.retry(new BiPredicate<Integer, Throwable>() {
//							@Override
//							public boolean test(Integer integer, Throwable throwable) throws Exception {
//								return integer.compareTo(3) <= 0 && !(throwable instanceof HttpException);
//							}
//						});
			}
		});
	}

	private final Github auth;
	private final Github api;
	private final Github graphQl;

	public Client(Context context) {
		OkHttpClient client = new OkHttpClient.Builder()
				.connectTimeout(10, TimeUnit.SECONDS)
				.readTimeout(10, TimeUnit.SECONDS)
				.writeTimeout(10, TimeUnit.SECONDS)
				.addInterceptor(new AuthorizationInterceptor())
				.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
				.build();
		auth = new Retrofit.Builder()
				.baseUrl("https://github.com")
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.addConverterFactory(ScalarsConverterFactory.create())
				.addConverterFactory(MoshiConverterFactory.create(new Moshi.Builder().build()))
				.client(client)
				.build()
				.create(Github.class);
		api = new Retrofit.Builder()
				.baseUrl("https://api.github.com")
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.addConverterFactory(ScalarsConverterFactory.create())
				.addConverterFactory(MoshiConverterFactory.create(new Moshi.Builder().build()))
				.client(client)
				.build()
				.create(Github.class);
		graphQl = new Retrofit.Builder()
				.baseUrl("https://api.github.com")
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.addConverterFactory(GraphQLConverter.create())
				.client(client)
				.build()
				.create(Github.class);
	}

	public Single<AccessToken> accessToken(String code, String clientId, String clientSecret) {
		return auth.accessToken(code, clientId, clientSecret);
	}

	public Single<User> user() {
		return api.user();
	}

	public Single<List<Repository>> repos(String name) {
		return api.repos(name);
	}

	public Single<List<Repository>> repos() {
		return api.repos();
	}

	public Single<List<Project>> projects(String owner, String repo) {
		return api.projects(owner, repo);
	}

	public Single<List<Column>> columns(long projectId) {
		return api.columns(projectId);
	}

	public Single<ResponseBody> graphQl() {
//		return graphQl.graphQl(new QueryContainerBuilder().query("query {repository(owner: \"mitchibu\", name: \"Misc\") {name,description}}"));
		return graphQl.graphQl(new QueryContainerBuilder().query("query test($owner: String!, $name: String!){repository(owner: $owner, name: $name) {name,description}}").put("owner", "mitchibu").put("name", "Misc"));
	}

	public Single<ResponseBody> graphQl2(Context context, int id) {
		QueryContainerBuilder.QueryContainer container = new QueryContainerBuilder().query(context.getString(id)).put("owner", "mitchibu").put("name", "Misc").build();
		return api.graphQl2(container);
	}

	public Single<GraphQl.Data<Test>> graphQl3(Context context, int id) {
		GraphQl.Query query = new GraphQl.Query();
		try {
			query.loadQueryFromRawResource(context, id);
		} catch(IOException e) {
			e.printStackTrace();
		}
		query.put("owner", "mitchibu");
		query.put("name", "Misc");
		return api.graphQl3(query);
	}
}
