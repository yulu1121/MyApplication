package com.example.mvpmodetraning.http;

import android.os.AsyncTask;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 网络请求封装类
 * @author yangjw
 *
 */
public class AsyncTaskTool {
	
	/**
	 * 请求网络
	 * @param path 网络地址
	 */
	public static MyGiftTask load(String path) {
		return new MyGiftTask(path);
	}
	
	
	public static class MyGiftTask extends AsyncTask<String, Integer, String> {

		private IMyCallback callback;
		private boolean isPost;
		private String params;
		private String path;
		/**
		 * POST请求
		 * 如果调用了此方法，则表示要使用POST请求加载数据，否则使用Get（默认是Get）
		 * @param param
		 * @return
		 */
		public MyGiftTask post(String param) {
			isPost = true;
			this.params = param;
			return this;
		}
		/**
		 * 执行异步任务
		 * @param callback
		 */
		public void execute(IMyCallback callback) {
			this.callback = callback;
			this.execute(path,params);
		}
		
		public MyGiftTask(String path) {
			super();
			this.path = path;
			this.isPost = false;
		}

		@Override
		protected String doInBackground(String... params) {
			String path = params[0];
			InputStream is = null;
			StringBuilder builder = new StringBuilder();
			try {
				URL url = new URL(path);
				HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
				if (isPost) {
					httpURLConnection.setDoOutput(true);
					httpURLConnection.setRequestMethod("POST");
					httpURLConnection.setUseCaches(false);
					httpURLConnection.setRequestProperty("accept", "*/*");
					httpURLConnection.setRequestProperty("connection", "Keep-Alive");
					httpURLConnection.setRequestProperty(
							"user-agent",
							"Mozilla/5.0 (Mozilla/5.0 (Windows NT 6.3; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
					httpURLConnection.setRequestProperty("Content-Type",
							"application/x-www-form-urlencoded");
					httpURLConnection.connect();
					httpURLConnection.getOutputStream().write(params[1].getBytes());
					httpURLConnection.getOutputStream().flush();
				} else {
					httpURLConnection.connect();
				}

				if (httpURLConnection.getResponseCode() == 200) {
					is = httpURLConnection.getInputStream();
					int len = 0;
					byte[] buffer = new byte[1024];
					
					while((len = is.read(buffer)) != -1) {
						builder.append(new String(buffer, 0, len));
					}
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				close(is);
			}
			
			return builder.toString();
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			callback.success(result);
			
		}
		
		private void close(Closeable close) {
			if (close != null) {
				try {
					close.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public interface IMyCallback {
		void success(String result);
	}

}
