package com.mogikanensoftware.xml.service.controller;

import java.io.File;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mogikanensoftware.xml.utils.core.service.Constants;

public class MainControllerIntegrationTestCase {

	private static final Logger logger = LogManager.getLogger(MainControllerIntegrationTestCase.class);
	private static final String SERVICE_HOST = "http://localhost:8088";

	public static void main(String[] args) throws Exception{
		MainControllerIntegrationTestCase.executePostRequest("/defaultValidate",
				MainControllerIntegrationTestCase.class.getResource("/sample/simpledata.dta").getFile(), null);

		String[] xsdUrl = new String[] { Constants.REPORT_MANAGER_XSD, Constants.REPORT_MANAGER_DT_XSD };

		MainControllerIntegrationTestCase.executePostRequest("/validate",
				MainControllerIntegrationTestCase.class.getResource("/sample/mr_sample_valid.xml").getFile(), xsdUrl);
		MainControllerIntegrationTestCase.executePostRequest("/validate",
				MainControllerIntegrationTestCase.class.getResource("/sample/mr_sample_invalid.xml").getFile(), xsdUrl);
	}

	protected static void executePostRequest(String targetUrl, String filePath, String[] xsdUrls) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httppost = new HttpPost(SERVICE_HOST + targetUrl);

			FileBody bin = new FileBody(new File(filePath));

			MultipartEntityBuilder builder = MultipartEntityBuilder.create();

			builder.addPart("xmlFileToValidate", bin);
			if (xsdUrls != null && xsdUrls.length > 0) {
				for (String xsdUrlValue : xsdUrls) {
					builder.addPart("xsdUrls[]", new StringBody(xsdUrlValue, ContentType.TEXT_PLAIN));
				}
			}

			HttpEntity reqEntity = builder.build();
			httppost.setEntity(reqEntity);
			httppost.addHeader("testValue", "kokojambo");

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
