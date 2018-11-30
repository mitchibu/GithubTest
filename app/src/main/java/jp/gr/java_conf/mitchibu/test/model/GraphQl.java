package jp.gr.java_conf.mitchibu.test.model;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class GraphQl {
	public static GraphQl queryFromRawResource(Context context, int id) throws IOException {
		InputStream in = null;
		try {
			in = context.getResources().openRawResource(id);
			StringBuilder sb = new StringBuilder();
			byte[] b = new byte[1024];
			int n;
			while((n = in.read(b)) > 0) sb.append(new String(b, 0, n));
			return new GraphQl(sb.toString());
		} finally {
			if(in != null) {
				try {in.close();} catch(Exception e) {e.printStackTrace();}
			}
		}
	}

	private String query;
	private Map<String, Object> variables = null;

	public Query data;

	public GraphQl() {
	}

	private GraphQl(String query) {
		this.query = query;
	}
}
