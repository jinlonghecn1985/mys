package com.jing.utils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.util.EntityUtils;

import com.jing.platform.controller.vo.HttpEntityVo;

public class HttpTool {

	/**
	 * @Title: simpleURIPost
	 * @Description: post请求
	 * @param uri
	 * @param body
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 *             String
	 * @author: li chao
	 */
	public static HttpEntityVo simpleURIPost(String uri, String body, final int timeout)
			throws ClientProtocolException, IOException {
		URI url = URI.create(uri);
		HttpEntityVo httpEntityVo = null;
		httpEntityVo = Request.Post(url).useExpectContinue().version(HttpVersion.HTTP_1_1)
				.bodyString(body, ContentType.APPLICATION_JSON).connectTimeout(timeout).socketTimeout(timeout).execute()
				.handleResponse(new ResponseHandler<HttpEntityVo>() {
					@Override
					public HttpEntityVo handleResponse(HttpResponse response) throws IOException {
						HttpEntity httpEntity = response.getEntity();
						HttpEntityVo httpEntityVo = new HttpEntityVo();
						if (null == httpEntity) {
							httpEntityVo.setCode(500);
							httpEntityVo.setEntity(null);
						} else {
							String back = EntityUtils.toString(response.getEntity(), Charset.defaultCharset());
							Integer status = response.getStatusLine().getStatusCode();
							httpEntityVo.setCode(status);
							httpEntityVo.setEntity(back);
						}
						return httpEntityVo;
					}
				});
		return httpEntityVo;
	}

	/**
	 * 
	 * @Title: simpleURIPostFile
	 * @Description: 上传文件
	 * @param uri
	 * @param fileName
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 *             String
	 * @author: li chao
	 */
	public static String simpleURIPostFile(String uri, String fileName) throws ClientProtocolException, IOException {
		File file = new File(fileName);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.addBinaryBody(file.getName(), file, ContentType.APPLICATION_OCTET_STREAM, file.getName());
		HttpEntity entity = builder.build();
		URI url = URI.create(uri);
		return Request.Post(url).useExpectContinue().version(HttpVersion.HTTP_1_1).body(entity).execute()
				.returnContent().asString(Charset.defaultCharset());
	}

	/**
	 * 
	 * @Title: simpleURIGet
	 * @Description: get请求
	 * @param uri
	 * @param body
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 *             String
	 * @author: li chao
	 */
	public static HttpEntityVo simpleURIGet(String uri, final int timeout) throws ClientProtocolException, IOException {
		URI url = URI.create(uri);
		HttpEntityVo httpEntityVo = null;
		httpEntityVo = Request.Get(url).addHeader("Content-Type", "application/json")
				.addHeader("Accept", "application/json").connectTimeout(timeout).socketTimeout(timeout).execute()
				.handleResponse(new ResponseHandler<HttpEntityVo>() {
					@Override
					public HttpEntityVo handleResponse(HttpResponse response) throws IOException {
						HttpEntity httpEntity = response.getEntity();
						HttpEntityVo httpEntityVo = new HttpEntityVo();
						if (null == httpEntity) {
							httpEntityVo.setCode(500);
							httpEntityVo.setEntity(null);
						} else {
							String back = EntityUtils.toString(response.getEntity(), Charset.defaultCharset());
							Integer status = response.getStatusLine().getStatusCode();
							httpEntityVo.setCode(status);
							httpEntityVo.setEntity(back);
						}
						return httpEntityVo;
					}
				});
		return httpEntityVo;
	}

	/**
	 * 
	 * @Title: simpleURIPut
	 * @Description: put请求
	 * @param uri
	 * @param body
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 *             String
	 * @author: li chao
	 */
	public static HttpEntityVo simpleURIPut(String uri, String body, final int timeout)
			throws ClientProtocolException, IOException {
		URI url = URI.create(uri);
		HttpEntityVo httpEntityVo = null;
		httpEntityVo = Request.Put(url).useExpectContinue().version(HttpVersion.HTTP_1_1)
				.bodyString(body, ContentType.APPLICATION_JSON).connectTimeout(timeout).socketTimeout(timeout).execute()
				.handleResponse(new ResponseHandler<HttpEntityVo>() {
					@Override
					public HttpEntityVo handleResponse(HttpResponse response) throws IOException {
						HttpEntity httpEntity = response.getEntity();
						HttpEntityVo httpEntityVo = new HttpEntityVo();
						if (null == httpEntity) {
							httpEntityVo.setCode(500);
							httpEntityVo.setEntity(null);
						} else {
							String back = EntityUtils.toString(response.getEntity(), Charset.defaultCharset());
							Integer status = response.getStatusLine().getStatusCode();
							httpEntityVo.setCode(status);
							httpEntityVo.setEntity(back);
						}
						return httpEntityVo;
					}
				});
		return httpEntityVo;
	}

	/**
	 * 
	 * @Title: getfile
	 * @Description:读取文件
	 * @param fileName
	 * @return
	 * @throws IOException
	 *             String
	 * @author: li chao
	 */
	public static String getfile(String fileName) throws IOException {
		File file = new File(fileName);
		return FileUtils.readFileToString(file, Charset.defaultCharset());
	}

}
