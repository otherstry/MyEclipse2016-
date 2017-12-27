package com.offcn.httpClient;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class httpClient {

//	模拟B/S系统中的浏览器，将网络上的资源读取到java程序中
	@Test
//	发送get请求,设置请求信息，返回响应信息
	public void testSendRequest(){
		
//	  创建CloseableHttpClient对象
	CloseableHttpClient httpclient =HttpClients.createDefault();
//	   创建get请求
	HttpGet get = new HttpGet("http://mil.firefox.sina.com/17/1222/17/KO2HN6QRVYD3P1XS.html");
//   设置请求头
	get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; W…) Gecko/20100101 Firefox/57.0");
	get.setHeader("Accept-Encoding","gzip, deflate");
//	发送请求头的信息,获取返回对象HttpResponse
	try {
		HttpResponse httpResponse =httpclient.execute(get);
//	 从httpResponse对象中获取响应状态码
		StatusLine status = httpResponse.getStatusLine();
		int code = status.getStatusCode();
//	 状态码表示200示意正常,程序正常运行
		if(code == 200){
//	从服务器中的HttpResponse对象中获取响应的内容
		HttpEntity entity = httpResponse.getEntity();	
//	使用EntityUtils工具类中toStirng方法将响应的内容返回给客户端
		System.out.println(EntityUtils.toString(entity,"UTF-8"));
//	回收entity内容	
		EntityUtils.consume(entity);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
//	关闭连接资源
	try {
		httpclient.close();
	} catch (IOException e) {
		
		e.printStackTrace();
	}
	}
	
//	发送post请求
	@Test
	public void testPostRequest() throws UnsupportedEncodingException{
/*********************** /		
//  创建CloseableHttpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
//	创建	post对象
		HttpPost httpPost = new HttpPost("http://localhost:8080/0503_Web/send");
//	设置请求头
		httpPost.setHeader("JDDD", "34455");
//	设置请求参数
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("username","ddd"));
		list.add(new BasicNameValuePair("password","1234"));
//	把请求参数封装到httpPost对象中
		httpPost.setEntity(new UrlEncodedFormEntity(list));
//	发送请求
		try {
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
//	获取响应状态码
			StatusLine statusLine = httpResponse.getStatusLine();
		    int code =statusLine.getStatusCode();
//   状态码为200表示正常
		    if(code == 200){
//	 获取响应内容会送给客户端
		    HttpEntity entity = httpResponse.getEntity();
//	使用EntityUtils工具类中toStirng方法将响应的内容返回给客户端	 
		    String responseContent = EntityUtils.toString(entity, "UTF-8");
		    System.out.println(responseContent);
//	回收entity资源
		    EntityUtils.consume(entity);
		    }
		} catch (Exception e) {
		
			e.printStackTrace();
		} 
//	关闭连接
		try {
			httpClient.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

// 文件上传
	@Test
	public void testUpload(){
//		创建CloseableHttpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
//	    创建post对象
		HttpPost httpPost = new HttpPost("http://localhost:8080/0503_Web/uploadServlet");
//	  设置请求头参数
		httpPost.setHeader("kdkdd", "4k44444");
//	 把本地磁盘上的File文件对象转换成支持网络发送的编码对象
		File file = new File("C:\\Users\\ibm\\Desktop\\u=398952501,2656845064&fm=27&gp=0.jpg");
		FileBody fileBody = new FileBody(file);
//	 把文件封装到HttpEntity	
		HttpEntity httpEntity = MultipartEntityBuilder.create()
				.setMode(HttpMultipartMode.BROWSER_COMPATIBLE).addPart("uploadfile",fileBody).build();
//   把httpEntity封装到httpPost中
		httpPost.setEntity(httpEntity);
//	 发送请求
		try {
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
//	通过httpResponse获取状态码
			StatusLine status = httpResponse.getStatusLine();
			int code = status.getStatusCode();
			if(code == 200){
			HttpEntity entity =	httpResponse.getEntity();
			String responseContent = EntityUtils.toString(entity, "UTF-8");
		    System.out.println(responseContent);
//	回收entity资源
		    EntityUtils.consume(entity);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
//	关闭连接
		try {
			httpClient.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
//	文件下载
	@Test
	public void testDownload() throws ClientProtocolException, IOException{
//		创建CloseableHttpClient
		CloseableHttpClient httpClient = HttpClients.createDefault();
//		创建get请求
		HttpGet httpGet  = new HttpGet("http://localhost:8080/0503_Web/kl.jpg");
//		发送请求
		HttpResponse httpResponse =httpClient.execute(httpGet);
//		从httpResponse中获取要返回的内容
		HttpEntity entity =httpResponse.getEntity();
//		使用entity的getContent方法取得输入流
			InputStream in = entity.getContent();
			File file = new File("D:\\JFChart测试\\upload\\1.jpg");
			FileOutputStream os = new FileOutputStream(file);
			int len = -1;
			byte [] buffer = new byte[1024];
			while((len = in.read(buffer))!=-1){
				os.write(buffer,0,len);
			}
			os.flush();
			os.close();
			in.close();
//		关闭网络连接
		try {
			httpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
