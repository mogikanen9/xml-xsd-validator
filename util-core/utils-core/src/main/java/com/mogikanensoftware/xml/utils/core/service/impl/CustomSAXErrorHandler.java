package com.mogikanensoftware.xml.utils.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class CustomSAXErrorHandler implements ErrorHandler {

	private List<SAXParseException> exceptions = new ArrayList<SAXParseException>();

	public List<SAXParseException> getExceptions() {
		return exceptions;
	}

	public void setExceptions(List<SAXParseException> exceptions) {
		this.exceptions = exceptions;
	}

	@Override
	public void error(SAXParseException arg0) throws SAXException {
		exceptions.add(arg0);
	}

	@Override
	public void fatalError(SAXParseException arg0) throws SAXException {
		exceptions.add(arg0);
	}

	@Override
	public void warning(SAXParseException arg0) throws SAXException {
		exceptions.add(arg0);
	}

}
