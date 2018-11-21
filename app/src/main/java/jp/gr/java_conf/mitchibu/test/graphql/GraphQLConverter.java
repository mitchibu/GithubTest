package jp.gr.java_conf.mitchibu.test.graphql;

import android.support.annotation.Nullable;

import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class GraphQLConverter extends Converter.Factory {
	public static GraphQLConverter create() {
		return new GraphQLConverter();
	}

	@Nullable
	@Override
	public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
		return new GraphRequestConverter();
	}

	@Nullable
	@Override
	public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
		if(type instanceof ResponseBody) {
			return super.responseBodyConverter(type, annotations, retrofit);
		} else {
			return new GraphResponseConverter();
		}
	}

	private static class GraphRequestConverter implements Converter<QueryContainerBuilder, RequestBody> {
		@Override
		public RequestBody convert(QueryContainerBuilder value) throws IOException {
			QueryContainerBuilder.QueryContainer container = value.build();
			Moshi moshi = new Moshi.Builder().build();
			String json = moshi.adapter(QueryContainerBuilder.QueryContainer.class).toJson(container);
			return RequestBody.create(MediaType.parse("application/json"), json.getBytes());
		}
	}

	private static class GraphResponseConverter<T> implements Converter<ResponseBody, T> {
		@Override
		public T convert(ResponseBody value) throws IOException {
			return null;
		}
	}
}
