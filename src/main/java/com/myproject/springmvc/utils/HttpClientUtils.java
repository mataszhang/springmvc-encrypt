package com.myproject.springmvc.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.BestMatchSpecFactory;
import org.apache.http.impl.cookie.BrowserCompatSpecFactory;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtils {

	private final static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

	public static final String UTF_8 = "UTF-8";
	
	 // 创建CookieStore实例
	private  static CookieStore cookieStore = null;
	
	private  static HttpClientContext context = null;
	  
	/**
	 * post 返回实体
	 * 
	 * @param uri
	 * @param rtnType
	 * @return
	 */
	public static String getRequest(String uri) {
		String output;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpGet getRequest = new HttpGet(uri);
			getRequest.setHeader("accept", "application/json");
			HttpResponse response = httpClient.execute(getRequest);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("GET调用异常 : " + response.getStatusLine().getStatusCode());
			}
			output = EntityUtils.toString(response.getEntity(), UTF_8);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(String.format("GET调用异常,异常信息", e.getMessage()), e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return output;
	}
	
	/**
	 * post 返回实体
	 * 
	 * @param uri
	 * @param rtnType
	 * @return
	 */
	public static <T> T getRequest(String uri, Class<T> rtnType) {
		T rtnRst = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpGet getRequest = new HttpGet(uri);
			getRequest.setHeader("accept", "application/json");
			HttpResponse response = httpClient.execute(getRequest);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("GET调用异常 : " + response.getStatusLine().getStatusCode());
			}
			String output;
			output = EntityUtils.toString(response.getEntity(), UTF_8);
			rtnRst = JsonUtil.fromJson(output, rtnType);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("GET调用异常", e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return rtnRst;
	}

	/**
	 * post 参数是json字符串，返回实体
	 * 
	 * @param uri
	 * @param jsonParams
	 * @param rtnType
	 * @return
	 */
	public static <T> T postRequest(String uri, String jsonParams, Class<T> rtnType) {
		T rtnRst = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost postRequest = new HttpPost(uri);
			postRequest.setHeader("Content-Type", "application/json");
			postRequest.addHeader("charset", UTF_8);
			StringEntity input = new StringEntity(jsonParams,UTF_8);
			postRequest.setEntity(input);

			HttpResponse response = httpClient.execute(postRequest);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("POST调用异常 : " + response.getStatusLine().getStatusCode());
			}
			String output;
			output = EntityUtils.toString(response.getEntity(), UTF_8);
			rtnRst = JsonUtil.fromJson(output, rtnType);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("POST调用异常", e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return rtnRst;
	}

	public static void setContext() {
	    System.out.println("----setContext");
	    context = HttpClientContext.create();
	    Registry<CookieSpecProvider> registry = RegistryBuilder
													        .<CookieSpecProvider> create()
													        .register(CookieSpecs.BEST_MATCH, new BestMatchSpecFactory())
													        .register(CookieSpecs.BROWSER_COMPATIBILITY,
													            new BrowserCompatSpecFactory()).build();
	    context.setCookieSpecRegistry(registry);
	    context.setCookieStore(cookieStore);
	  }

	  public static void setCookieStore(HttpResponse httpResponse) {
	    System.out.println("----setCookieStore");
	    cookieStore = new BasicCookieStore();
	    // JSESSIONID
	    String setCookie = httpResponse.getFirstHeader("Set-Cookie").getValue();
	    String JSESSIONID = setCookie.substring("JSESSIONID=".length(), setCookie.indexOf(";"));
	    System.out.println("JSESSIONID:" + JSESSIONID);
	    // 新建一个Cookie
	    BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", JSESSIONID);
	    cookie.setVersion(0);
//	    cookie.setDomain("127.0.0.1");
//	    cookie.setPath("/CwlProClient");
	    // cookie.setAttribute(ClientCookie.VERSION_ATTR, "0");
	    // cookie.setAttribute(ClientCookie.DOMAIN_ATTR, "127.0.0.1");
	    // cookie.setAttribute(ClientCookie.PORT_ATTR, "8080");
	    // cookie.setAttribute(ClientCookie.PATH_ATTR, "/CwlProWeb");
	    cookieStore.addCookie(cookie);
	  }
	  
	public static Map<String, String> postRequestWithCookie(String uri, String jsonParams) {
		Map<String, String> resultMap = new HashMap<String, String>();
		String output;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost postRequest = new HttpPost(uri);
			postRequest.setHeader("Content-Type", "application/json;charset=utf-8");
			StringEntity input = new StringEntity(jsonParams, UTF_8);
			postRequest.setEntity(input);

			HttpResponse response = httpClient.execute(postRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				logger.warn("请求异常：{}", response);
				throw new RuntimeException("POST调用异常 : " + response.getStatusLine().getStatusCode());
			}
			output = EntityUtils.toString(response.getEntity(), UTF_8);
			resultMap.put("output", output);

			// 设置session
			// postRequest.setHeader("Cookie",
			// "JSESSIONID=817CF84802016AF09E3DFFE59532FD02");

			// 得到Cookie值
			String setCookieValue = response.getFirstHeader("Set-Cookie").getValue();
			if (setCookieValue != null) {
				if (!"".equals(setCookieValue)) {
					resultMap.put("cookie", setCookieValue);
				}
			}

			// 打印Cookie值
			// System.out.println(setCookieValue.substring(0,setCookieValue.indexOf(";")));

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("POST调用异常", e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultMap;
	}
		
	public static String postRequestWithCookie(String uri, String jsonParams, String cookie) {
		String output;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost postRequest = new HttpPost(uri);
			
			postRequest.setHeader("Cookie", cookie);
			postRequest.setHeader("Content-Type", "application/json;charset=utf-8");
			StringEntity input = new StringEntity(jsonParams, UTF_8);
			postRequest.setEntity(input);

			HttpResponse response = httpClient.execute(postRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				logger.warn("请求异常：{}", response);
				throw new RuntimeException("POST调用异常 : " + response.getStatusLine().getStatusCode());
			}
			output = EntityUtils.toString(response.getEntity(), UTF_8);

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("POST调用异常", e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return output;
	}
	
	/**
	 * post 参数json字符串，返回String
	 * 
	 * @param uri
	 * @param jsonParams
	 * @return
	 */
	public static String postRequest(String uri, String jsonParams) {
		String output;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost postRequest = new HttpPost(uri);
			postRequest.setHeader("Content-Type", "application/json;charset=utf-8");
			StringEntity input = new StringEntity(jsonParams,UTF_8);
			postRequest.setEntity(input);

			HttpResponse response = httpClient.execute(postRequest);
	
			if (response.getStatusLine().getStatusCode() != 200) {
				logger.warn("请求异常：{}", response);
				throw new RuntimeException("POST调用异常 : " + response.getStatusLine().getStatusCode());
			}
			output = EntityUtils.toString(response.getEntity(), UTF_8);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("POST调用异常", e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return output;
	}
	
	/**
	 * 要求服务端对response进行GZip压缩
	 * 
	 * @param uri
	 * @param jsonParams
	 * @return
	 */
	public static String postRequestWithGZipResponse(String uri, String jsonParams) {
		String output;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost postRequest = new HttpPost(uri);
			postRequest.setHeader("Content-Type", "application/json;charset=utf-8");
			postRequest.setHeader("Accept-Encoding", "gzip, deflate");
			StringEntity input = new StringEntity(jsonParams,UTF_8);
			postRequest.setEntity(input);

			HttpResponse response = httpClient.execute(postRequest);
	
			if (response.getStatusLine().getStatusCode() != 200) {
				logger.warn("请求异常：{}", response);
				throw new RuntimeException("POST调用异常 : " + response.getStatusLine().getStatusCode());
			}
			
			HttpEntity httpEntity = response.getEntity(); 
			Header header = httpEntity.getContentEncoding();
			System.out.println(header);
			GZIPInputStream gzin; 
			InputStream in = httpEntity.getContent();
			gzin = new GZIPInputStream(in);
			InputStreamReader isr = new InputStreamReader(gzin, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			StringBuffer buffer = new StringBuffer();
			String temp;
			while ((temp = br.readLine()) != null) {
				buffer.append(temp);
				buffer.append("\r\n");
			}
			br.close();
			isr.close();
			output = buffer.toString();
			// output = EntityUtils.toString(response.getEntity(), UTF_8);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("POST调用异常", e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return output;
	}
	
	/**
	 * post 参数json字符串，返回String
	 * 
	 * @param uri
	 * @param jsonParams
	 * @return
	 */
	public static String postRequestH5(String uri, String jsonParams) {
		return postRequest(uri, jsonParams);
//		String output;
//		try {
//			 //创建HttpClientBuilder  
//	        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
//	        //HttpClient  
//	        CloseableHttpClient httpClient = httpClientBuilder.build();  
//	        
//			HttpPost postRequest = new HttpPost(uri);
//			postRequest.setHeader("Content-Type", "application/json;charset=utf-8");
//			StringEntity input = new StringEntity(jsonParams,UTF_8);
//			postRequest.setEntity(input);
//
//			HttpResponse response = httpClient.execute(postRequest);
//			if (response.getStatusLine().getStatusCode() != 200) {
//				logger.warn("请求异常：{}", response);
//				throw new RuntimeException("POST调用异常 : " + response.getStatusLine().getStatusCode());
//			}
//			output = EntityUtils.toString(response.getEntity(), UTF_8);
//			httpClient.close();
//		} catch (IOException e) {
//			logger.error(e.getMessage(), e);
//			throw new RuntimeException("POST调用异常", e);
//		}
//		return output;
	}

	public static <T> T postRequestForPayment(String uri, String jsonParams, Class<T> rtnType) {
		T rtnRst = null;
		String token = PropertiesConfigUtils.getProperty("bank", "bank.wangyin.payment.token");
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost postRequest = new HttpPost(uri);
			postRequest.setHeader("Content-Type", "application/json");
			postRequest.setHeader("token", token);
			StringEntity input = new StringEntity(jsonParams);
			postRequest.setEntity(input);
			HttpResponse response = httpClient.execute(postRequest);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("POST调用异常 : " + response.getStatusLine().getStatusCode());
			}
			String output;
			output = EntityUtils.toString(response.getEntity(), UTF_8);
			rtnRst = JsonUtil.fromJson(output, rtnType);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("POST调用异常", e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return rtnRst;
	}

	public static <T> T postRequestForSettlement(String uri, String jsonParams, Class<T> rtnType) {
		T rtnRst = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
    	// 调用外单银行资金帐token authUser
    	String authUser = PropertiesConfigUtils.getProperty("bank", "out.order.bank.account.auth.user");
    	// 调用外单银行资金帐token authPass
    	String authPass = PropertiesConfigUtils.getProperty("bank", "out.order.bank.account.auth.pass");
    	Map<String, String> token = new HashMap<String, String>();
    	token.put("authUser", authUser);
    	token.put("authPass", authPass);
		try {
			String tokenStr = JsonUtil.toJson(token);
			logger.info("tokenStr={}", tokenStr);
			HttpPost postRequest = new HttpPost(uri);
			postRequest.setHeader("Content-Type", "application/json");
			postRequest.setHeader("token", tokenStr);
			StringEntity input = new StringEntity(jsonParams);
			postRequest.setEntity(input);

			HttpResponse response = httpClient.execute(postRequest);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("POST调用异常 : " + response.getStatusLine().getStatusCode());
			}
			String output;
			output = EntityUtils.toString(response.getEntity(), UTF_8);
			rtnRst = JsonUtil.fromJson(output, rtnType);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("POST调用异常", e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return rtnRst;
	}
	
	public static <T> T postRequest(String uri, Map<String, Object> params, Class<T> rtnType) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		T rtnRst = null;
		try {
			HttpPost httpPost = new HttpPost(uri);

			// httpClient.getParams().setParameter("http.protocol.content-charset",
			// UTF_8);
			// httpClient.getParams().setParameter(HTTP.CONTENT_ENCODING,
			// UTF_8);
			// httpClient.getParams().setParameter(HTTP.CHARSET_PARAM,
			// UTF_8);
			// httpClient.getParams().setParameter(HTTP.DEFAULT_PROTOCOL_CHARSET,
			// UTF_8);
			//
			// httpPost.getParams().setParameter("http.protocol.content-charset",UTF_8);
			// httpPost.getParams().setParameter(HTTP.CONTENT_ENCODING,
			// UTF_8);
			// httpPost.getParams().setParameter(HTTP.CHARSET_PARAM,
			// UTF_8);
			// httpPost.getParams().setParameter(HTTP.DEFAULT_PROTOCOL_CHARSET,
			// UTF_8);

			List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				nameValuePairList.add(new BasicNameValuePair(entry.getKey(), StringUtils.getString(entry.getValue())));
			}
			// 设置报文头
			httpPost.setHeader("Content-Type", "application/json");
			UrlEncodedFormEntity en = new UrlEncodedFormEntity(nameValuePairList, UTF_8);
			// en.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
			// "application/json"));
			httpPost.setEntity(en);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			Integer statusCode = httpResponse.getStatusLine().getStatusCode();
			logger.info("POST请求返回的状态码:{}", statusCode);
			if (statusCode != 200) {
				throw new RuntimeException("POST调用异常 : " + statusCode);
			}
			HttpEntity entity = httpResponse.getEntity();
			String result = EntityUtils.toString(entity, UTF_8);
			System.out.println(result);
			logger.info("POST请求返回结果:{}", result);
			rtnRst = JsonUtil.fromJson(result, rtnType);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("POST调用异常", e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return rtnRst;
	}


	
    public static String postRequest(String uri, Map<String, Object> params) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result;
        try {
            HttpPost httpPost = new HttpPost(uri);

            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                nameValuePairList.add(new BasicNameValuePair(entry.getKey(), StringUtils.getString(entry.getValue())));
            }
            // 设置报文头
            httpPost.setHeader("Content-Type", "application/json");
            UrlEncodedFormEntity en = new UrlEncodedFormEntity(nameValuePairList, UTF_8);
            // en.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
            // "application/json"));
            httpPost.setEntity(en);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            Integer statusCode = httpResponse.getStatusLine().getStatusCode();
            logger.info("POST请求返回的状态码:{}", statusCode);
            if (statusCode != 200) {
                throw new RuntimeException("POST调用异常 : " + statusCode);
            }
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity, UTF_8);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("POST调用异常", e);
        } finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        return result;
	}
	
	public static InputStream postRemoteFile(String uri, String remoteFileName, Map<String, String> params) {
		InputStream in = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(uri);
			// 建立一个NameValuePair数组，用于存储欲传送的参
			List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> entry : params.entrySet()) {
				nameValuePairList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}

			// StringEntity input = new StringEntity(jsonParams);
			// httpPost.setEntity(input);

			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList, UTF_8));

			HttpResponse httpResponse = httpClient.execute(httpPost);

			HttpEntity entity = httpResponse.getEntity();
			in = entity.getContent();

			long length = entity.getContentLength();
			if (length < 0) {
				logger.warn("下载的文件{}不存在", remoteFileName);
				return null;
			}
			Integer statusCode = httpResponse.getStatusLine().getStatusCode();
			logger.info("POST下载文件，结果码:{}", statusCode);
			// logger.info("The response value of token:" +
			// httpResponse.getFirstHeader("token"));
			if (statusCode == 200) {
				logger.info("请求成功");
				Header header = httpResponse.getFirstHeader("Return-Code");
				if (header == null) {
					logger.info("文件下载成功");
				} else {
					String bizResultCode = header.getValue();
					logger.info("文件下载失败，状态码：{}", bizResultCode);
					return null;
				}
			} else {
				logger.warn("请求下载失败");
				return null;
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("httpClient下载zip文件异常", e);
		} 
		// finally {
			// 此httpClient暂时不能关闭，因为调用放需要用到该inputstream
//			try {
//				httpClient.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		// }
		return in;
	}

	/**
	 * 用于http 302 重定向
	 * 
	 * @param uri
	 * @param jsonParams
	 * @return
	 */
	public static String postRequestFor302Location(String uri, String jsonParams) {
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost postRequest = new HttpPost(uri);
			StringEntity input = new StringEntity(jsonParams);
			postRequest.setEntity(input);

			HttpResponse response = httpClient.execute(postRequest);
			if (response.getStatusLine().getStatusCode() != 302) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}
			Header[] locationHeader = response.getHeaders("location");
			if (locationHeader != null && locationHeader.length > 0) {
				result = locationHeader[0].getValue();
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static <T> T putRequestWithHeaders(String uri, String jsonParams, Class<T> rtnType) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		T rtnRst = null;
		try {
			HttpPut httpPost = new HttpPut(uri);
			// 设置报文头
			String secretValue = PropertiesConfigUtils.getProperty("openfire", "openfire.restapi.plugin.secret");
			httpPost.setHeader("Content-Type", "application/json");
			httpPost.setHeader("Authorization", secretValue);
			
			if (jsonParams != null) {
				if ( !"".equals(jsonParams) ) {
					StringEntity input = new StringEntity(jsonParams);
					httpPost.setEntity(input);					
				}
			 }
			
			HttpResponse httpResponse = httpClient.execute(httpPost);
			Integer statusCode = httpResponse.getStatusLine().getStatusCode();
			logger.info("POST请求返回的状态码:{}", statusCode);
			if (statusCode != 200) {
				throw new RuntimeException("POST调用异常 : " + statusCode);
			}
			HttpEntity entity = httpResponse.getEntity();
			String result = EntityUtils.toString(entity, UTF_8);
			System.out.println(result);
			logger.info("POST请求返回结果:{}", result);
			rtnRst = JsonUtil.fromJson(result, rtnType);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("POST调用异常", e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return rtnRst;
	}
	
	public static <T> T deleteRequestWithHeaders(String uri, Class<T> rtnType) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		T rtnRst = null;
		try {
			HttpDelete httpDelete = new HttpDelete(uri);
			String secretValue = PropertiesConfigUtils.getProperty("openfire", "openfire.restapi.plugin.secret");
			// 设置报文头
			httpDelete.setHeader("Content-Type", "application/json");
			httpDelete.setHeader("Authorization", secretValue);
			
			HttpResponse httpResponse = httpClient.execute(httpDelete);
			Integer statusCode = httpResponse.getStatusLine().getStatusCode();
			logger.info("Delete请求返回的状态码:{}", statusCode);
			if (statusCode != 200) {
				throw new RuntimeException("POST调用异常 : " + statusCode);
			}
			HttpEntity entity = httpResponse.getEntity();
			String result = EntityUtils.toString(entity, UTF_8);
			System.out.println(result);
			logger.info("Delete请求返回结果:{}", result);
			rtnRst = JsonUtil.fromJson(result, rtnType);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("POST调用异常", e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return rtnRst;
	}
	public static <T> T deleteRequestWithHeadersV2(String uri, Class<T> rtnType) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		T rtnRst = null;
		try {
			HttpDelete httpDelete = new HttpDelete(uri);
			String secretValue = PropertiesConfigUtils.getProperty("openfire", "openfire.restapi.plugin.secret");
			// 设置报文头
			httpDelete.setHeader("Content-Type", "application/json");
			httpDelete.setHeader("Authorization", secretValue);
			
			HttpResponse httpResponse = httpClient.execute(httpDelete);
			Integer statusCode = httpResponse.getStatusLine().getStatusCode();
			HttpEntity entity = httpResponse.getEntity();
			String result = EntityUtils.toString(entity, UTF_8);
			rtnRst = JsonUtil.fromJson(result, rtnType);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("POST调用异常", e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return rtnRst;
	}
	
	public static <T> T postRequestWithHeaders(String uri, String jsonParams, Class<T> rtnType) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		T rtnRst = null;
		try {
			HttpPost httpPost = new HttpPost(uri);
			// 设置报文头
			String secretValue = PropertiesConfigUtils.getProperty("openfire", "openfire.restapi.plugin.secret");
			httpPost.setHeader("Content-Type", "application/json");
			httpPost.setHeader("Authorization", secretValue);
			
			StringEntity input = new StringEntity(jsonParams);
			httpPost.setEntity(input);
			
			HttpResponse httpResponse = httpClient.execute(httpPost);
			Integer statusCode = httpResponse.getStatusLine().getStatusCode();
			logger.info("POST请求返回的状态码:{}", statusCode);
			if (statusCode != 201) {
				throw new RuntimeException("POST调用异常 : " + statusCode);
			}
			HttpEntity entity = httpResponse.getEntity();
			String result = EntityUtils.toString(entity, UTF_8);
			System.out.println(result);
			logger.info("POST请求返回结果:{}", result);
			rtnRst = JsonUtil.fromJson(result, rtnType);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("POST调用异常", e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return rtnRst;
	}
	
	public static <T> T postRequestWithHeadersV2(String uri, String jsonParams, Class<T> rtnType) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		T rtnRst = null;
		try {
			HttpPost httpPost = new HttpPost(uri);
			// 设置报文头
			String secretValue = PropertiesConfigUtils.getProperty("openfire", "openfire.restapi.plugin.secret");
			httpPost.setHeader("Content-Type", "application/json");
			httpPost.setHeader("Authorization", secretValue);
			
			StringEntity input = new StringEntity(jsonParams);
			httpPost.setEntity(input);
			
			HttpResponse httpResponse = httpClient.execute(httpPost);
			Integer statusCode = httpResponse.getStatusLine().getStatusCode();
			HttpEntity entity = httpResponse.getEntity();
			String result = EntityUtils.toString(entity, UTF_8);
			rtnRst = JsonUtil.fromJson(result, rtnType);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("POST调用异常", e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return rtnRst;
	}
	
	public static Integer getRequestWithHeaders(String uri) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet(uri);
			// 设置报文头
			String secretValue = PropertiesConfigUtils.getProperty("openfire", "openfire.restapi.plugin.secret");
			httpGet.setHeader("Content-Type", "application/json");
			httpGet.setHeader("Authorization", secretValue);
			
			HttpResponse httpResponse = httpClient.execute(httpGet);
			Integer statusCode = httpResponse.getStatusLine().getStatusCode();
			logger.info("Get请求返回的状态码:{}", statusCode);
			HttpEntity entity = httpResponse.getEntity();
			String result = EntityUtils.toString(entity, UTF_8);
			logger.info("Get请求返回结果:{}", result);
			return statusCode;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("GET调用异常", e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String getRequestResultWithHeaders(String uri) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet(uri);
			// 设置报文头
			String secretValue = PropertiesConfigUtils.getProperty("openfire", "openfire.restapi.plugin.secret");
			httpGet.setHeader("Content-Type", "application/json");
			httpGet.setHeader("Authorization", secretValue);
			
			HttpResponse httpResponse = httpClient.execute(httpGet);
			Integer statusCode = httpResponse.getStatusLine().getStatusCode();
			logger.info("Get请求返回的状态码:{}", statusCode);
			HttpEntity entity = httpResponse.getEntity();
			String result = EntityUtils.toString(entity, UTF_8);
			logger.info("Get请求返回结果:{}", result);
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("GET调用异常", e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String getRequestWithHeadersV2(String uri) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet(uri);
			// 设置报文头
			String secretValue = PropertiesConfigUtils.getProperty("openfire", "openfire.restapi.plugin.secret");
			httpGet.setHeader("Content-Type", "application/json");
			httpGet.setHeader("Authorization", secretValue);
			httpGet.setHeader("Accept", "application/json");
			
			HttpResponse httpResponse = httpClient.execute(httpGet);
			Integer statusCode = httpResponse.getStatusLine().getStatusCode();
			logger.info("Get请求返回的状态码:{}", statusCode);
			HttpEntity entity = httpResponse.getEntity();
			String result = EntityUtils.toString(entity, UTF_8);
			logger.info("Get请求返回结果:{}", result);
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("POST调用异常", e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		// Get请求
		// String uri =
		// "http://192.168.194.118:8003/app/cfgInfo?apiVersion=1.0&clientOs=ANDROID&channel=Xiaomi&clientVersion=1.0&signKey=363acd1d424bf6327c3e618b5d86cfc5";
		// String resultJson = HttpClientUtils.getRequest(uri, String.class);
		// System.out.println(resultJson);

		// Post请求
		// String uri =
		// "http://192.168.194.118:8003/taskOrder/getTaskOrderList?apiVersion=1.0&clientOs=ANDROID&clientVersion=1.0&channel=Xiaomi&signKey=363acd1d424bf6327c3e618b5d86cfc5";
		// String jsonParams =
		// "{\"deliveryManId\":4,\"longitude\":121.3612473119,\"latitude\":31.2203896180,\"axisType\":1, \"requestPageNum\":1, \"pageSize\":10}";
		// String resultJson = HttpClientUtils.postRequest(uri, jsonParams,
		// String.class);
		// System.out.println(resultJson);

		// String url = "http://172.24.5.171:80/api/down.do";
		// Map<String, Object> params = new HashMap<String, Object>();
		// String owner = "";
		// String data = "";
		// String md5 = "";
		// params.put("md5", md5);
		// params.put("data", data);
		// params.put("owner", owner);
		//
		// InputStream input = HttpClientUtils.postRemoteFile(url, "test.excel",
		// JsonUtil.toJson(params));
		// System.out.println("input=" + input);

		// 查询用户是否存在
//		String domain = "test.weilive.cc";
//		String userId = "501";
//		String retrieveUserURI = String.format("http://%s:9090/plugins/restapi/v1/users/%s", domain, userId);
//		Integer resultCode = HttpClientUtils.getRequestWithHeaders(retrieveUserURI);
//		System.out.println(resultCode);
		
		// 查询mucRoom是否存在
//		String domain = "test.weilive.cc";
//		String roomName = "502";
//		String retrieveUserURI = String.format("http://%s:9090/plugins/restapi/v1/chatrooms/%s", domain, roomName);
//		Integer resultCode = HttpClientUtils.getRequestWithHeaders(retrieveUserURI);
//		System.out.println(resultCode);
		
	}
	
	
	
}
