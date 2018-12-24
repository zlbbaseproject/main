package com.zbin.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.Map.Entry;


public class HttpUtil {

    private static final String SYSTEM_NAME = "td-member-manager";

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static String getBrowerInfo(HttpServletRequest req) {
        String userAgent = req.getHeader(HttpHeaders.USER_AGENT);

        return userAgent;
    }

	public static <T> T doGet(String url, TypeReference<T> type) {
		String res = httpGetWithJSON(url, null, 1000);
		if(res != null) {
			try {
				return JSON.parseObject(res, type);
			} catch (Exception e) {
				logger.error("doGet", e);
			}
		}
		return null;
	}

    public static <T> T doGet(String url, TypeReference<T> type,String warnType,String warnMsg) {
        String res = httpGetWithJSON(url, null, 1000,warnType,warnMsg);
        if(res != null) {
            return JSON.parseObject(res, type);
        }
        return null;
    }

    /**
     * post json
     *
     * @param url
     * @param params
     * @param timeout
     * @return
     */
    public static String httpPostWithJSON(String url, Map<String, Object> params, int timeout) {
        String respContent = null;
        CloseableHttpClient httpClient = createHttpClient(timeout);
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("systemid",SYSTEM_NAME );
            //httpPost.setHeader("txid", CommonUtil.createUUID());
            if(params != null) {
                StringEntity entity = new StringEntity(JSON.toJSONString(params), "utf-8"); // 解决中文乱码问题
				entity.setContentEncoding("UTF-8");
				entity.setContentType("application/json");
				httpPost.setEntity(entity);
            }
            logger.info("\n>>>>>HttpUtil 请求url: {} 参数：{}", url, JSON.toJSONString(params));
            HttpResponse resp = httpClient.execute(httpPost);
            if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity he = resp.getEntity();
                respContent = EntityUtils.toString(he, "UTF-8");
                logger.info(String.format("\n>>>>>HttpUtil 请求url: %s 返回: %s ", url, respContent));
            } else {
                logger.error("POST请求服务器异常：{}", resp.getStatusLine());
            }
        } catch (Exception e) {
            logger.error("httpPostWithJSON", e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error("httpPostWithJSON", e);
            }
        }
        return respContent;
    }

    /**
     * post json
     * @param url
     * @param params
     * @param timeout
     * @param warnType 告警类型
     * @param warnMsg 告警信息
     */
    public static String httpPostWithJSON(String url, Map<String, Object> params, int timeout,String warnType,String warnMsg) {
        String respContent = null;
        CloseableHttpClient httpClient = createHttpClient(timeout);
        try {
            HttpPost httpPost = new HttpPost(url);
            StringEntity entity = new StringEntity(JSON.toJSONString(params), "utf-8");// 解决中文乱码问题
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setHeader("systemid",SYSTEM_NAME );
            //httpPost.setHeader("txid", CommonUtil.createUUID());

            httpPost.setEntity(entity);
            logger.info("\n>>>>>HttpUtil 请求url: {} 参数：{}",url,JSON.toJSONString(params));
            HttpResponse resp = httpClient.execute(httpPost);
            if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity he = resp.getEntity();
                respContent = EntityUtils.toString(he, "UTF-8");
                logger.info(String.format("\n>>>>>HttpUtil 请求url: %s 返回: %s ",url, respContent ));
            }else{
                logger.error("POST请求服务器异常：{}", resp.getStatusLine());
            }
            //throw new Exception("测试抛异常");
        } catch (Exception e) {
            logger.error("httpPostWithJSON，{}", e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error("httpPostWithJSON", e);
            }
        }
        return respContent;
    }

    public static String httpPostWithJSONString(String url, String params, int timeout) {
        String respContent = null;
        CloseableHttpClient httpClient = createHttpClient(timeout);
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("systemid",SYSTEM_NAME );
            //httpPost.setHeader("txid", CommonUtil.createUUID());
            if(!StringUtils.isBlank(params)) {
            	StringEntity entity = new StringEntity(params, "utf-8"); // 解决中文乱码问题
				entity.setContentEncoding("UTF-8");
				entity.setContentType("application/json");
				httpPost.setEntity(entity);
			}
            logger.info("\n>>>>>HttpUtil 请求url: {} 参数：{}", url, params);
            HttpResponse resp = httpClient.execute(httpPost);
            if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity he = resp.getEntity();
                respContent = EntityUtils.toString(he, "UTF-8");
                logger.info(String.format("\n>>>>>HttpUtil 请求url: %s 返回: %s ", url, respContent));
            } else {
                logger.error("POST请求服务器异常：{}", resp.getStatusLine());
            }
        } catch (Exception e) {
            logger.error("httpPostWithJSON", e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error("httpPostWithJSON", e);
            }
        }
        return respContent;
    }

    public static String httpGetWithJSON(String url, Map<String, String> params, int timeout) {
        String respContent = null;
        CloseableHttpClient client = createHttpClient(timeout);
//        String ctype = "application/json;charset=UTF-8";
        try {
            String query = buildQuery(params, "UTF-8");
            logger.info(String.format("\n>>>>>HttpUtil 请求url: %s ", url+"?" + query));
            HttpGet get = new HttpGet(buildGetUrl(url, query));
            get.setHeader("systemid", SYSTEM_NAME);
            //get.setHeader("txid", CommonUtil.createUUID());
            HttpResponse resp = client.execute(get);
            if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity he = resp.getEntity();
                respContent = EntityUtils.toString(he, "UTF-8");
                logger.info(String.format("\n>>>>>HttpUtil 请求url: %s 返回: %s ",url, respContent ));
            } else {
                logger.error("GET请求服务器异常：{}", resp.getStatusLine());
            }
        } catch (Exception e) {
            logger.error("httpGetWithJSON", e);
        } finally {
            //关闭连接 ,释放资源
            try {
                client.close();
            } catch (IOException e) {
                logger.error("httpGetWithJSON", e);
            }
        }
        return respContent;
    }

    public static String httpGetWithJSON(String url, Map<String, String> params, int timeout,String warnType,String warnMsg) {
        String respContent = null;
        CloseableHttpClient client = createHttpClient(timeout);
//        String ctype = "application/json;charset=UTF-8";
        try {
            String query = buildQuery(params, "UTF-8");
            logger.info(String.format("\n>>>>>HttpUtil 请求url: %s ", url+"?" + query));
            HttpGet get = new HttpGet(buildGetUrl(url, query));
            get.setHeader("systemid", SYSTEM_NAME);
            //get.setHeader("txid", CommonUtil.createUUID());
            HttpResponse resp = client.execute(get);
            if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity he = resp.getEntity();
                respContent = EntityUtils.toString(he, "UTF-8");
                logger.info(String.format("\n>>>>>HttpUtil 请求url: %s 返回: %s ",url, respContent ));
            } else {
                logger.error("GET请求服务器异常：{}", resp.getStatusLine());
            }
            //throw new Exception("httpGetWithJSON 模拟抛异常");
        } catch (Exception e) {
            logger.error("httpGetWithJSON", e);
        } finally {
            //关闭连接 ,释放资源
            try {
                client.close();
            } catch (IOException e) {
                logger.error("httpGetWithJSON", e);
            }
        }
        return respContent;
    }

    private static String buildGetUrl(String strUrl, String query) throws IOException {
        URL url = new URL(strUrl);
        if (StringUtils.isEmpty(query)) {
            return strUrl;
        }

        if (StringUtils.isEmpty(url.getQuery())) {
            if (strUrl.endsWith("?")) {
                strUrl = strUrl + query;
            } else {
                strUrl = strUrl + "?" + query;
            }
        } else {
            if (strUrl.endsWith("&")) {
                strUrl = strUrl + query;
            } else {
                strUrl = strUrl + "&" + query;
            }
        }
        return strUrl;
    }

    public static String buildQuery(Map<String, String> params, String charset) throws IOException {
        if (params == null || params.isEmpty()) {
            return null;
        }
        StringBuilder query = new StringBuilder();
        Set<Entry<String, String>> entries = params.entrySet();
        boolean hasParam = false;

        for (Entry<String, String> entry : entries) {
            String name = entry.getKey();
            String value = entry.getValue() == null ? StringUtils.EMPTY : entry.getValue();
            // 忽略参数名为空的参数，参数值为空需要传递EmptyString
            if (StringUtils.isNotEmpty(name)) {
                if (hasParam) {
                    query.append("&");
                } else {
                    hasParam = true;
                }
                query.append(name).append("=").append(URLEncoder.encode(value, charset));
            }
        }
        return query.toString();
    }

    public static String formPost(String url, Map params, Map<String, String> heads, int timeout) {
        CloseableHttpClient httpClient = createHttpClient(timeout);
        try {
            HttpPost post = new HttpPost(url);

            List<NameValuePair> nvps = new ArrayList<>();
            if (params != null && params.keySet().size() > 0) {
                Iterator iterator = params.entrySet().iterator();
                while (iterator.hasNext()) {
                    Entry entry = (Entry) iterator.next();
                    nvps.add(new BasicNameValuePair((String) entry.getKey(), (String) entry.getValue()));
                }
            }
            post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            if (heads != null && heads.keySet().size() > 0) {
                Iterator iterator = heads.entrySet().iterator();
                while (iterator.hasNext()) {
                    Entry entry = (Entry) iterator.next();
                    post.addHeader((String) entry.getKey(), (String) entry.getValue());
                }
            }
            logger.info(String.format("\n<<<<<<<<HttpUtil 请求url: %s \n>>>>>HttpUtil 参数: %s \n>>>>>HttpUtil ",url, JSON.toJSON(nvps) ));
            HttpResponse response = httpClient.execute(post);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity, "UTF-8");
                logger.info(String.format("\n>>>>>HttpUtil 请求url: %s \n>>>>>HttpUtil 返回: %s \n>>>>>HttpUtil ",url, result ));
                return result;
            }
        } catch (Exception e) {
            logger.error("formPost", e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error("formPost", e);
            }
        }
        return null;
    }



    /**
     * 创建默认的httpClient实例.
     *
     * @return
     */
    private static CloseableHttpClient createHttpClient(int timeout) {
		if(timeout == 0) {
			timeout = 10000; // 默认超时时间：10秒
		}
        RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout).setConnectionRequestTimeout(timeout)
                .setSocketTimeout(timeout).build();
        return HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }


}
