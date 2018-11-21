package jp.gr.java_conf.mitchibu.test.graphql;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class GraphQl {
	public static class Query {
		private String query = null;
		private Map<String, Object> variables = null;

		public Query query(String query) {
			this.query = query;
			return this;
		}

		public Query loadQueryFromRawResource(Context context, int id) throws IOException {
			InputStream in = null;
			try {
				in = context.getResources().openRawResource(id);
				StringBuilder sb = new StringBuilder();
				byte[] b = new byte[1024];
				int n;
				while((n = in.read(b)) > 0) sb.append(new String(b, 0, n));
				return query(sb.toString());
			} finally {
				if(in != null) {
					try {in.close();} catch(Exception e) {e.printStackTrace();}
				}
			}
		}

		public Query put(String key, Object value) {
			if(variables == null) {
				variables = new HashMap<>();
			}
			variables.put(key, value);
			return this;
		}
	}

	public static class Data<T> {
		public T data;
	}
}
