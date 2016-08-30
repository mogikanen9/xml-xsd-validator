package com.mogikanensoftware.xml.utils.core.service.impl;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class CustomSAXErrorHandlerTestCase {

	private CustomSAXErrorHandler handler;
	
	@Before
	public void before(){
		handler = new CustomSAXErrorHandler();
	}
	
	@After
	public void after(){
		handler = null;
	}
	
	@Test
	public void testGetSetWarnings() {
		List<SAXParseException> warnings = handler.getWarnings();
		Assert.assertNotNull(warnings);
		Assert.assertTrue(warnings.isEmpty());
		
		List<SAXParseException> listOfWarnings = new ArrayList<>();
		listOfWarnings.add(Mockito.mock(SAXParseException.class));
		listOfWarnings.add(Mockito.mock(SAXParseException.class));
		
		handler.setWarnings(listOfWarnings);
		warnings = handler.getWarnings();
		Assert.assertNotNull(warnings);
		Assert.assertTrue(warnings.size()==2);
	}

	@Test
	public void testGetSetExceptions() {
		List<SAXParseException> errors = handler.getExceptions();
		Assert.assertNotNull(errors);
		Assert.assertTrue(errors.isEmpty());
		
		List<SAXParseException> listOfErrors = new ArrayList<>();
		listOfErrors.add(Mockito.mock(SAXParseException.class));
		listOfErrors.add(Mockito.mock(SAXParseException.class));
		
		handler.setWarnings(listOfErrors);
		errors = handler.getWarnings();
		Assert.assertNotNull(errors);
		Assert.assertTrue(errors.size()==2);
	}

	@Test
	public void testError() throws SAXException {
		List<SAXParseException> errors = handler.getExceptions();
		Assert.assertNotNull(errors);
		Assert.assertTrue(errors.isEmpty());
		
		handler.error(Mockito.mock(SAXParseException.class));
		errors = handler.getExceptions();
		Assert.assertNotNull(errors);
		Assert.assertTrue(errors.size()==1);
	}

	@Test
	public void testFatalError() throws SAXException {
		List<SAXParseException> errors = handler.getExceptions();
		Assert.assertNotNull(errors);
		Assert.assertTrue(errors.isEmpty());
		
		//log fatal error
		handler.fatalError(Mockito.mock(SAXParseException.class));
		errors = handler.getExceptions();
		Assert.assertNotNull(errors);
		Assert.assertTrue(errors.size()==1);
		
		//log one more error
		handler.error(Mockito.mock(SAXParseException.class));
		errors = handler.getExceptions();
		Assert.assertNotNull(errors);
		Assert.assertTrue(errors.size()==2);
	}

	@Test
	public void testWarning() throws SAXException {
		List<SAXParseException> warnings = handler.getWarnings();
		Assert.assertNotNull(warnings);
		Assert.assertTrue(warnings.isEmpty());	
	
		handler.warning(Mockito.mock(SAXParseException.class));
		warnings = handler.getWarnings();
		Assert.assertNotNull(warnings);
		Assert.assertTrue(warnings.size()==1);	
	}

}
