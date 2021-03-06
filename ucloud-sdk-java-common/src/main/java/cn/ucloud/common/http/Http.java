package cn.ucloud.common.http;

import cn.ucloud.common.exception.HttpException;
import cn.ucloud.common.handler.UcloudHandler;
import cn.ucloud.common.pojo.BaseResponseResult;
import com.google.gson.Gson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @Description :
 * @Author : codezhang
 * @Date : 2019-04-17 23:13
 **/
public class Http {

    private Class<? extends BaseResponseResult> resultClass;

    public Http(Class<? extends BaseResponseResult> resultClass) {
        this.resultClass = resultClass;
    }

    private static Logger logger = LoggerFactory.getLogger(Http.class);

    private static final String SDK_VERSION = "0.8.3.0-release";

    private static final String USER_AGENT;

    static {
        String runTimeVersion = System.getProperty("java.runtime.version");
        runTimeVersion = (runTimeVersion == null || runTimeVersion.isEmpty()) ? "UnKnown RuntimeVersion" : runTimeVersion;

        USER_AGENT = String.format("JAVA_%s/JAVA-SDK/%s", runTimeVersion, SDK_VERSION);
    }

    public BaseResponseResult doHttp(HttpUriRequest request, UcloudHandler handler, Boolean async) throws Exception {
        CloseableHttpResponse response = null;
        BaseResponseResult responseResult = null;
        request.addHeader("User-Agent", USER_AGENT);

        String uriInfo = request.getURI().toString();
        String headerInfo = new Gson().toJson(request.getAllHeaders());
        if (request instanceof HttpPost) {
            String bodyInfo = EntityUtils.toString(((HttpPost) request).getEntity());
            logger.info("http POST request: \n" +
                            "\tURI:{}\n" +
                            "\tBody:{}\n" +
                            "\tHeaders:{}",
                    uriInfo,
                    bodyInfo,
                    headerInfo);
        } else if (request instanceof HttpGet) {
            logger.info("http GET request: \n" +
                            "\tURI:{}\n" +
                            "\tHeaders:{}",
                    uriInfo,
                    headerInfo);
        }

        // 创建HttpClient对象
        final CloseableHttpClient client = HttpClients.createDefault();
        try {
            response = client.execute(request);
            if (response != null) {
                // 正常响应
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                logger.info("response content:{}", content);
                if (statusOK(response)) {
                    Gson gson = new Gson();
                    responseResult = gson.fromJson(content, resultClass);
                    responseResult.setResponseContent(content);
                    if (handler != null) {
                        handlerResult(handler, responseResult);
                    }
                } else {
                    // 非200则认为是个异常
                    handlerException(handler, new HttpException(content), async);
                }
            } else {
                handlerException(handler, new NullPointerException("response is null"), async);
            }
        } catch (Exception e) {
            handlerException(handler, e, async);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                logger.error("response close error:{}", e.getMessage());
            }
            try {
                client.close();
            } catch (IOException e) {
                logger.error("httpClient close error:{}", e.getMessage());
            }
        }
        return responseResult;
    }

    private void handlerResult(UcloudHandler handler, BaseResponseResult result) {
        if (result.getRetCode() != 0) {
            handler.failed(result);
        } else {
            handler.success(result);
        }
    }

    private boolean statusOK(CloseableHttpResponse response) {
        boolean ok = false;
        if (response != null) {
            ok = response.getStatusLine().getStatusCode() == 200;
        }
        return ok;
    }

    private void handlerException(UcloudHandler handler, Exception e, Boolean async) throws Exception {
        if (handler == null) {
            if (async != null) {
                logger.error("handler is null and async is not null,but get an error:{}", e.getMessage());
            } else {
                throw e;
            }
        } else {
            handler.error(e);
        }
    }
}
