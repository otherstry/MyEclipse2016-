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

//	ģ��B/Sϵͳ�е���������������ϵ���Դ��ȡ��java������
	@Test
//	����get����,����������Ϣ��������Ӧ��Ϣ
	public void testSendRequest(){
		
//	  ����CloseableHttpClient����
	CloseableHttpClient httpclient =HttpClients.createDefault();
//	   ����get����
	HttpGet get = new HttpGet("http://mil.firefox.sina.com/17/1222/17/KO2HN6QRVYD3P1XS.html");
//   ��������ͷ
	get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; W��) Gecko/20100101 Firefox/57.0");
	get.setHeader("Accept-Encoding","gzip, deflate");
//	��������ͷ����Ϣ,��ȡ���ض���HttpResponse
	try {
		HttpResponse httpResponse =httpclient.execute(get);
//	 ��httpResponse�����л�ȡ��Ӧ״̬��
		StatusLine status = httpResponse.getStatusLine();
		int code = status.getStatusCode();
//	 ״̬���ʾ200ʾ������,������������
		if(code == 200){
//	�ӷ������е�HttpResponse�����л�ȡ��Ӧ������
		HttpEntity entity = httpResponse.getEntity();	
//	ʹ��EntityUtils��������toStirng��������Ӧ�����ݷ��ظ��ͻ���
		System.out.println(EntityUtils.toString(entity,"UTF-8"));
//	����entity����	
		EntityUtils.consume(entity);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
//	�ر�������Դ
	try {
		httpclient.close();
	} catch (IOException e) {
		
		e.printStackTrace();
	}
	}
	
//	����post����
	@Test
	public void testPostRequest() throws UnsupportedEncodingException{
		
//  ����CloseableHttpClient����
		CloseableHttpClient httpClient = HttpClients.createDefault();
//	����	post����
		HttpPost httpPost = new HttpPost("http://localhost:8080/0503_Web/send");
//	��������ͷ
		httpPost.setHeader("JDDD", "34455");
//	�����������
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("username","ddd"));
		list.add(new BasicNameValuePair("password","1234"));
//	�����������װ��httpPost������
		httpPost.setEntity(new UrlEncodedFormEntity(list));
//	��������
		try {
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
//	��ȡ��Ӧ״̬��
			StatusLine statusLine = httpResponse.getStatusLine();
		    int code =statusLine.getStatusCode();
//   ״̬��Ϊ200��ʾ����
		    if(code == 200){
//	 ��ȡ��Ӧ���ݻ��͸��ͻ���
		    HttpEntity entity = httpResponse.getEntity();
//	ʹ��EntityUtils��������toStirng��������Ӧ�����ݷ��ظ��ͻ���	 
		    String responseContent = EntityUtils.toString(entity, "UTF-8");
		    System.out.println(responseContent);
//	����entity��Դ
		    EntityUtils.consume(entity);
		    }
		} catch (Exception e) {
		
			e.printStackTrace();
		} 
//	�ر�����
		try {
			httpClient.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

// �ļ��ϴ�
	@Test
	public void testUpload(){
//		����CloseableHttpClient����
		CloseableHttpClient httpClient = HttpClients.createDefault();
//	    ����post����
		HttpPost httpPost = new HttpPost("http://localhost:8080/0503_Web/uploadServlet");
//	  ��������ͷ����
		httpPost.setHeader("kdkdd", "4k44444");
//	 �ѱ��ش����ϵ�File�ļ�����ת����֧�����緢�͵ı������
		File file = new File("C:\\Users\\ibm\\Desktop\\u=398952501,2656845064&fm=27&gp=0.jpg");
		FileBody fileBody = new FileBody(file);
//	 ���ļ���װ��HttpEntity	
		HttpEntity httpEntity = MultipartEntityBuilder.create()
				.setMode(HttpMultipartMode.BROWSER_COMPATIBLE).addPart("uploadfile",fileBody).build();
//   ��httpEntity��װ��httpPost��
		httpPost.setEntity(httpEntity);
//	 ��������
		try {
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
//	ͨ��httpResponse��ȡ״̬��
			StatusLine status = httpResponse.getStatusLine();
			int code = status.getStatusCode();
			if(code == 200){
			HttpEntity entity =	httpResponse.getEntity();
			String responseContent = EntityUtils.toString(entity, "UTF-8");
		    System.out.println(responseContent);
//	����entity��Դ
		    EntityUtils.consume(entity);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
//	�ر�����
		try {
			httpClient.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
//	�ļ�����
	@Test
	public void testDownload() throws ClientProtocolException, IOException{
//		����CloseableHttpClient
		CloseableHttpClient httpClient = HttpClients.createDefault();
//		����get����
		HttpGet httpGet  = new HttpGet("http://localhost:8080/0503_Web/kl.jpg");
//		��������
		HttpResponse httpResponse =httpClient.execute(httpGet);
//		��httpResponse�л�ȡҪ���ص�����
		HttpEntity entity =httpResponse.getEntity();
//		ʹ��entity��getContent����ȡ��������
			InputStream in = entity.getContent();
			File file = new File("D:\\JFChart����\\upload\\1.jpg");
			FileOutputStream os = new FileOutputStream(file);
			int len = -1;
			byte [] buffer = new byte[1024];
			while((len = in.read(buffer))!=-1){
				os.write(buffer,0,len);
			}
			os.flush();
			os.close();
			in.close();
//		�ر���������
		try {
			httpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
