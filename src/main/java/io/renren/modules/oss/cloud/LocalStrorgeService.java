package io.renren.modules.oss.cloud;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Created with IntelliJ IDEA
 * Created By YangF
 * Date: 2017/7/20
 * Time: 11:28
 */
public class LocalStrorgeService  extends CloudStorageService  {
    private String remote_url;
    private String prefix;

    public LocalStrorgeService(CloudStorageConfig config){
        this.config = config;
        //初始化
        init();
    }

    private void init(){
        remote_url = config.getLocalDomain();
        prefix = config.getLocalPrefix();
    }

    @Override
    public String upload(byte[] data, String path) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        return    upload(inputStream,path);
    }

    @Override
    public String upload(InputStream inputStream, String path) {

        String remote_url =path;// 第三方服务器请求地址
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = "";
        try {
//            String fileName = file.getOriginalFilename();
            String fileName = "oas.apk";
            //TODO URL
            HttpPost httpPost = new HttpPost(remote_url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("file",inputStream, ContentType.MULTIPART_FORM_DATA,fileName);// 文件流
            builder.addTextBody("filename", fileName);// 类似浏览器表单提交，对应input的name和value
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);// 执行提交
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                // 将响应内容转换为字符串
                result = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public String upload(byte[] data) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        return   upload(inputStream,remote_url);
    }

    @Override
    public String upload(InputStream inputStream) {
        return upload(inputStream,remote_url);
    }
}
