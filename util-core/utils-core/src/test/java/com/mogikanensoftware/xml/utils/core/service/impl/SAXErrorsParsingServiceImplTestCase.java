package com.mogikanensoftware.xml.utils.core.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mogikanensoftware.xml.utils.core.service.ParsingService;
import com.mogikanensoftware.xml.utils.core.service.ParsingServiceException;
import com.mogikanensoftware.xml.utils.core.service.impl.SAXErrorsParsingServiceImpl;

import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;

public class SAXErrorsParsingServiceImplTestCase extends TestCase {

	private static final Logger logger = LogManager.getLogger(SAXErrorsParsingServiceImplTestCase.class);
	
	protected ParsingService parsingService = new SAXErrorsParsingServiceImpl();
		

	@Test
	public void testExtractErrorType() throws ParsingServiceException {
		String rs = parsingService.extractErrorType("cvc-type.3.1.3: The value 'NJA111555000000000000000' of element 'DeliverToUserID' is not valid");
		Assert.assertEquals("cvc-type.3.1.3", rs);
		logger.info("rs->"+rs);
		
		rs = parsingService.extractErrorType("cvc-maxLength-valid: Value '(333)333-3333000000000000000000000000000000000' with length = '46' is not facet-valid with respect to maxLength '25' for type '#AnonType_phoneNumberphoneNumber'");
		Assert.assertEquals("cvc-maxLength-valid", rs);
		logger.info("rs->"+rs);
		
		rs = parsingService.extractErrorType("cvc-attribute.3: The value 'DF' of attribute 'phoneNumberType' on element 'PhoneNumber' is not valid with respect to its type, 'phoneNumberType'");
		Assert.assertEquals("cvc-attribute.3", rs);
		logger.info("rs->"+rs);		
		
	}

	@Test
	public void testExtractElementName() throws ParsingServiceException {
		String rs = parsingService.extractElementName("cvc-type.3.1.3: The value 'NJA111555000000000000000' of element 'DeliverToUserID' is not valid");
		Assert.assertEquals("DeliverToUserID", rs);
		logger.info("rs->"+rs);
		
		rs = parsingService.extractElementName("cvc-maxLength-valid: Value '(333)333-3333000000000000000000000000000000000' with length = '46' is not facet-valid with respect to maxLength '25' for type '#AnonType_phoneNumberphoneNumber'");
		Assert.assertEquals("#AnonType_phoneNumberphoneNumber", rs);
		logger.info("rs->"+rs);
		
		rs = parsingService.extractElementName("cvc-attribute.3: The value 'DF' of attribute 'phoneNumberType' on element 'PhoneNumber' is not valid with respect to its type, 'phoneNumberType'");
		Assert.assertEquals("PhoneNumber", rs);
		logger.info("rs->"+rs);
	}

	@Test
	public void testSupressActualElementValue() throws ParsingServiceException {
		String rs = parsingService.supressActualElementValue("cvc-type.3.1.3: The value 'NJA111555000000000000000' of element 'DeliverToUserID' is not valid");
		Assert.assertEquals("cvc-type.3.1.3: The value  of element 'DeliverToUserID' is not valid", rs);
		logger.info("rs->"+rs);
		
		rs = parsingService.supressActualElementValue("cvc-maxLength-valid: Value '(333)333-3333000000000000000000000000000000000' with length = '46' is not facet-valid with respect to maxLength '25' for type '#AnonType_phoneNumberphoneNumber'");
		Assert.assertEquals("cvc-maxLength-valid: Value  with length = '46' is not facet-valid with respect to maxLength '25' for type '#AnonType_phoneNumberphoneNumber'", rs);
		logger.info("rs->"+rs);
	}
}
