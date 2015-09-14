package com.mogikanensoftware.xml.utils.core.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import com.mogikanensoftware.xml.utils.core.service.ValidationService;
import com.mogikanensoftware.xml.utils.core.service.ValidationServiceException;

public class BasicValidationServiceImpl implements ValidationService {

	@Override
	public void validate(File xmlFileToValidate, File xsdFile) throws ValidationServiceException {

		InputStream inXsd = null;
		InputStream inXml = null;
		try {
			inXsd = new FileInputStream(xsdFile);
			inXml = new FileInputStream(xmlFileToValidate);
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(new StreamSource(inXsd));
			Validator validator = schema.newValidator();
			StreamSource xmlFile = new StreamSource(inXml);
			validator.validate(xmlFile);
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			throw new ValidationServiceException(e);

		} finally {
			if (inXsd != null) {
				try {
					inXsd.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (inXml != null) {
				try {
					inXml.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}
