package jp.gr.java_conf.mitchibu.test.api;

import java.util.List;

import io.reactivex.Single;
import jp.gr.java_conf.mitchibu.test.graphql.GraphQl;
import jp.gr.java_conf.mitchibu.test.graphql.QueryContainerBuilder;
import jp.gr.java_conf.mitchibu.test.graphql.Test;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Github {
	@POST("/graphql")
	@Headers({
			"Accept: application/json"
	})
	Single<ResponseBody> graphQl(@Body QueryContainerBuilder builder);

	@POST("/graphql")
	@Headers({
			"Accept: application/json"
	})
	Single<ResponseBody> graphQl2(@Body QueryContainerBuilder.QueryContainer container);

	@POST("/graphql")
	@Headers({
			"Accept: application/json"
	})
	Single<GraphQl.Data<Test>> graphQl3(@Body GraphQl.Query query);

	@GET("/login/oauth/access_token")
	@Headers({
			"Accept: application/json"
	})
	Single<AccessToken> accessToken(@Query("code") String code, @Query("client_id") String clientId, @Query("client_secret") String clientSecret);

	@GET("/user")
	@Headers({
			"Accept: application/json"
	})
	Single<User> user();

	@GET("/user/repos")
	@Headers({
			"Accept: application/json"
	})
	Single<List<Repository>> repos();

	@GET("/users/{name}/repos")
	@Headers({
			"Accept: application/json"
	})
	Single<List<Repository>> repos(@Path("name") String name);

	@GET("/repos/{owner}/{repo}/projects")
	@Headers({
			"Accept: application/vnd.github.inertia-preview+json"
	})
	Single<List<Project>> projects(@Path("owner") String owner, @Path("repo") String repo);

	@GET("/projects/{projectId}/columns")
	@Headers({
			"Accept: application/vnd.github.inertia-preview+json"
	})
	Single<List<Column>> columns(@Path("projectId") long projectId);
}
