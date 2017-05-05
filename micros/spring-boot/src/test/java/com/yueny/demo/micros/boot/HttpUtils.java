package com.yueny.demo.micros.boot;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.yueny.rapid.lang.util.StringUtil;

public class HttpUtils {
	private static PoolingHttpClientConnectionManager connectionManager;
	private static final int MAX_TIMEOUT = 7000;
	private static RequestConfig requestConfig;

	static {
		// 设置连接池
		connectionManager = new PoolingHttpClientConnectionManager();
		// 设置连接池大小
		connectionManager.setMaxTotal(1000);
		// // 在提交请求之前 测试连接是否可用
		// connectionManager.setValidateAfterInactivity(500);
		connectionManager.setDefaultMaxPerRoute(connectionManager.getMaxTotal());

		final RequestConfig configBuilder = RequestConfig.custom()
				// 设置读取超时
				.setSocketTimeout(MAX_TIMEOUT)
				// 设置连接超时
				.setConnectTimeout(MAX_TIMEOUT)
				// 设置从连接池获取连接实例的超时
				.setConnectionRequestTimeout(MAX_TIMEOUT).build();

		requestConfig = configBuilder;
	}

	/**
	 * 发送 GET 请求（HTTP）,不带参数
	 *
	 * @param url
	 *            请求地址
	 * @return String 返回值(字符串)
	 */
	@Deprecated
	public static String doGet(final String url) {
		return doGet(url, new HashMap<String, Object>());
	}

	/**
	 * 发送 GET 请求（HTTP），K-V形式
	 *
	 * @param url
	 *            请求地址
	 * @param params
	 *            参数
	 * @return String 返回值(字符串)
	 */
	@Deprecated
	public static String doGet(final String url, final Map<String, Object> params) {
		String apiUrl = url;
		final StringBuffer param = new StringBuffer();
		int i = 0;
		for (final String key : params.keySet()) {
			if (i == 0) {
				param.append("?");
			} else {
				param.append("&");
			}
			param.append(key).append("=").append(params.get(key));
			i++;
		}
		apiUrl += param;
		String result = null;
		final CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(connectionManager)
				.setDefaultRequestConfig(requestConfig).build();
		try {
			final HttpGet httpPost = new HttpGet(apiUrl);
			CloseableHttpResponse response = null;
			try {
				response = httpclient.execute(httpPost);
				final int statusCode = response.getStatusLine().getStatusCode();
				System.out.println("执行状态码 : " + statusCode);

				final HttpEntity entity = response.getEntity();
				if (entity != null) {
					final InputStream instream = entity.getContent();
					result = IOUtils.toString(instream, "UTF-8");
				}
			} catch (final IOException e) {
				e.printStackTrace();
			} finally {
				if (response != null) {
					response.close();
				}
			}
		} catch (final Exception ex) {
			ex.printStackTrace();
		} finally {
			if (httpclient != null) {
				try {
					httpclient.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * 发送 POST 请求（HTTP）,不带输入数据
	 *
	 * @param url
	 *            请求地址
	 * @return String 返回值(字符串)
	 */
	public static String doPost(final String url) {
		return doPost(url, "");
	}

	/**
	 * 发送 POST 请求（HTTP）,K-V形式
	 *
	 * @param url
	 *            请求地址
	 * @param params
	 *            参数(Map)
	 * @return String 返回值(字符串)
	 */
	@Deprecated
	public static String doPost(final String url, final Map<String, Object> params) {
		String result = null;
		final HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;

		try {
			httpPost.setConfig(requestConfig);
			final List<NameValuePair> pairList = new ArrayList<>(params.size());
			for (final Map.Entry<String, Object> entry : params.entrySet()) {
				final NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
				pairList.add(pair);
			}
			httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));

			final CloseableHttpClient httpClient = getHttpClient();
			response = httpClient.execute(httpPost);
			System.out.println(response.toString());

			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * 发送 POST 请求（HTTP）,JSON形式
	 *
	 * @param url
	 *            请求地址
	 * @param req
	 *            req对象
	 * @return String 返回值(字符串)
	 */
	public static String doPost(final String url, final Object req) {
		return doPost(url, JSONObject.toJSONString(req));
	}

	/**
	 * 发送 POST 请求（HTTP）,K-V形式
	 *
	 * @param url
	 *            请求地址
	 * @param json
	 *            参数(json)
	 * @return String 返回值(字符串)
	 */
	public static String doPost(final String url, final String json) {
		String result = null;
		final HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;

		try {
			httpPost.setConfig(requestConfig);

			if (StringUtil.isNotEmpty(json)) {
				final StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
				httpPost.setEntity(entity);
			}
			// final List<NameValuePair> pairList = new
			// ArrayList<>(params.size());
			// for (final Map.Entry<String, Object> entry : params.entrySet()) {
			// final NameValuePair pair = new BasicNameValuePair(entry.getKey(),
			// entry.getValue().toString());
			// pairList.add(pair);
			// }
			// httpPost.setEntity(new UrlEncodedFormEntity(pairList,
			// Charset.forName("UTF-8")));

			final CloseableHttpClient httpClient = getHttpClient();
			response = httpClient.execute(httpPost);
			System.out.println(response.toString());

			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
					// response.getEntity().getContent().close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * 发送 SSL POST 请求（HTTPS），K-V形式
	 *
	 * @param url
	 *            请求地址
	 * @param params
	 *            参数map
	 * @return String 返回值(字符串)
	 */
	@Deprecated
	public static String doPostSSL(final String url, final Map<String, Object> params) {
		final CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
				.setConnectionManager(connectionManager).setDefaultRequestConfig(requestConfig).build();
		final HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		String httpStr = null;

		try {
			httpPost.setConfig(requestConfig);
			final List<NameValuePair> pairList = new ArrayList<>(params.size());
			for (final Map.Entry<String, Object> entry : params.entrySet()) {
				final NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
				pairList.add(pair);
			}
			httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("utf-8")));
			response = httpClient.execute(httpPost);
			final int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}
			final HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			httpStr = EntityUtils.toString(entity, "utf-8");
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}

	/**
	 * 发送 SSL POST 请求（HTTPS），JSON形式
	 *
	 * @param apiUrl
	 *            API接口URL
	 * @param json
	 *            JSON对象
	 * @return
	 */
	@Deprecated
	public static String doPostSSL(final String apiUrl, final Object json) {
		final CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
				.setConnectionManager(connectionManager).setDefaultRequestConfig(requestConfig).build();
		final HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;
		String httpStr = null;

		try {
			httpPost.setConfig(requestConfig);
			final StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			final int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}
			final HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			httpStr = EntityUtils.toString(entity, "utf-8");
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}

	/**
	 * 发送 GET 请求（HTTP）,不带参数
	 *
	 * @param url
	 *            地址
	 * @return T 返回值
	 */
	public static <T> T get(final String url, final Class<T> classType) {
		final String result = doGet(url);
		return convertResp(result, classType);
	}

	/**
	 * 发送 GET 请求（HTTP），带参数
	 *
	 * @param url
	 *            请求地址
	 * @param data
	 *            参数
	 * @return T 返回值
	 */
	public static <T> T get(final String url, final Object data, final Class<T> classType) {
		final Map<String, Object> params = convertRequest(data);
		final String result = doGet(url, params);
		return convertResp(result, classType);
	}

	/**
	 * 发送 post 请求（HTTP）,不带参数
	 *
	 * @param url
	 *            请求地址
	 * @return T 返回值
	 */
	public static <T> T post(final String url, final Class<T> classType) {
		final String result = doPost(url);
		return convertResp(result, classType);
	}

	/**
	 * 发送 POST 请求（HTTP），带参数
	 *
	 * @param url
	 *            请求地址
	 * @param data
	 *            请求参数
	 * @return T 返回值
	 */
	public static <T> T post(final String url, final Object data, final Class<T> classType) {
		// final Map<String, Object> params = convertRequest(data);
		final String result = doPost(url, JSONObject.toJSONString(data));
		return convertResp(result, classType);
	}

	/**
	 * 请求参数转换
	 *
	 * @param data
	 *            请求参数
	 * @return Map<String, Object>
	 */
	private static Map<String, Object> convertRequest(final Object data) {
		final Map<String, Object> param = new HashMap();
		try {
			final Field[] fields = data.getClass().getDeclaredFields();
			for (final Field f : fields) {
				f.setAccessible(true);
				final String name = f.getName();
				param.put(name, f.get(data));
			}
		} catch (final Exception e) {
			e.printStackTrace();
			return new HashMap();
		}

		return param;
	}

	/**
	 * 返回值转换
	 *
	 * @param result
	 *            返回值
	 * @param classType
	 *            class 对象
	 * @return T
	 */
	private static <T> T convertResp(final String result, final Class<T> classType) {
		if (result == null || "".equals(result.trim())) {
			return null;
		}

		return JSONObject.parseObject(result, classType);
		// return JSON.parseObject(result, classType);
	}

	/**
	 * 返回值转换
	 *
	 * @param result
	 *            返回值
	 * @param classType
	 *            class 对象
	 * @return T
	 */
	private static <T> T convertResp(final String result, final TypeToken<T> classType) {
		if (result == null || "".equals(result.trim())) {
			return null;
		}

		if (StringUtils.startsWith(result, "{")) {
			return (T) result;
		}

		final Gson gson = new GsonBuilder().enableComplexMapKeySerialization().serializeNulls()
				.setDateFormat("yyyy-MM-dd HH:mm:ss:SSS").setPrettyPrinting().create();

		final T clazz = gson.fromJson(result, classType.getType());
		return clazz;
	}

	/**
	 * 创建SSL安全连接
	 *
	 * @return
	 */
	private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
		SSLConnectionSocketFactory sslsf = null;
		try {
			final SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {

				@Override
				public boolean isTrusted(final X509Certificate[] chain, final String authType)
						throws CertificateException {
					return true;
				}
			}).build();
			sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {

				@Override
				public boolean verify(final String arg0, final SSLSession arg1) {
					return true;
				}

				@Override
				public void verify(final String host, final SSLSocket ssl) throws IOException {
				}

				@Override
				public void verify(final String host, final String[] cns, final String[] subjectAlts)
						throws SSLException {
				}

				@Override
				public void verify(final String host, final X509Certificate cert) throws SSLException {
				}
			});
		} catch (final GeneralSecurityException e) {
			e.printStackTrace();
		}
		return sslsf;
	}

	private static CloseableHttpClient getHttpClient() {
		// final CloseableHttpClient httpclient=
		// HttpClients.createMinimal(connectionManager);
		final CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(connectionManager)
				// .setDefaultRequestConfig(requestConfig)
				.build();

		return httpclient;
	}

}