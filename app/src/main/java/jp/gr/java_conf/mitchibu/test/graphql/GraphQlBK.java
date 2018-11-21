package jp.gr.java_conf.mitchibu.test.graphql;

import android.content.Context;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class GraphQlBK<T> {
	private final Map<String, Object> variables = new HashMap<>();

	private String query;
	public T data;

	public static class Builder<T> {
		private final Context context;
		private final GraphQlBK<T> graphQl = new GraphQlBK<>();

		public Builder(Context context) {
			this.context = context;
		}

		public Builder query(int query) {
			return query(getRawString(query));
		}

		public Builder query(String query) {
			graphQl.query = query;
			return this;
		}

		public Builder put(String key, Object value) {
			graphQl.variables.put(key, value);
			return this;
		}

		public GraphQlBK<T> build() {
			return graphQl;
		}

		private String getRawString(int id) {
			InputStream in = null;
			try {
				in = context.getResources().openRawResource(id);
				StringBuilder sb = new StringBuilder();
				byte[] b = new byte[1024];
				int n;
				while((n = in.read(b)) > 0) sb.append(new String(b, 0, n));
				return sb.toString();
			} catch(Exception e) {
				e.printStackTrace();
				return null;
			} finally {
				if(in != null) {
					try {in.close();} catch(Exception e) {e.printStackTrace();}
				}
			}
		}
	}
}
