package com.mogikanensoftware.xml.utils.core.service;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mogikanensoftware.xml.utils.core.service.impl.SAXErrorsParsingServiceImpl;

import junit.framework.Assert;
import junit.framework.TestCase;

public class SAXErrorsParsingServiceImplTestCase extends TestCase {

	private static final Logger logger = LogManager.getLogger(SAXErrorsParsingServiceImplTestCase.class);
	
	protected ParsingService parsingService = new SAXErrorsParsingServiceImpl();
		

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

	public void testExtractElementName() {
		fail("Not yet implemented");
	}

	public void testSupressActualElementValue() {
		fail("Not yet implemented");
	}
}
