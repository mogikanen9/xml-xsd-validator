package mogikanensoftware.xml.service.controller;

import java.io.File;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainControllerITCase {

	private static final Logger logger = LogManager.getLogger(MainControllerITCase.class);
	private static final String SERVICE_HOST = "http://localhost:8088";

	public static void main(String[] args) throws Exception {

		MainControllerITCase
				.executePostRequest(MainControllerITCase.class.getResource("/sample/simpledata.dta").getFile());
		MainControllerITCase
				.executePostRequest(MainControllerITCase.class.getResource("/sample/mr_sample_valid.xml").getFile());
		MainControllerITCase
				.executePostRequest(MainControllerITCase.class.getResource("/sample/mr_sample_invalid.xml").getFile());
	}

	protected static void executePostRequest(String filePath) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httppost = new HttpPost(SERVICE_HOST + "/validate");

			FileBody bin = new FileBody(new File(filePath));

			HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("xmlFileToValidate", bin).build();

			httppost.setEntity(reqEntity);

			logger.info("executing request " + httppost.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				logger.info("----------------------------------------");
				logger.info(response.getStatusLine());
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					logger.info("Response content length: " + resEntity.getContentLength());
					logger.info(
							String.format("Response body -> %s", IOUtils.toString(resEntity.getContent(), "UTF-8")));
				}
				EntityUtils.consume(resEntity);
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
	}
}
