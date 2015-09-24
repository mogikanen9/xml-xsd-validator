package com.mogikanensoftware.xml.utils.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class CustomSAXErrorHandler implements ErrorHandler {	

	private static final Logger logger = LogManager.getFormatterLogger(CustomSAXErrorHandler.class);

	private List<SAXParseException> exceptions = new ArrayList<SAXParseException>();
	private List<SAXParseException> warnings = new ArrayList<SAXParseException>();

	public List<SAXParseException> getWarnings() {
		return warnings;
	}

	public void setWarnings(List<SAXParseException> warnings) {
		this.warnings = warnings;
	}

	public List<SAXParseException> getExceptions() {
		return exceptions;
	}

	public void setExceptions(List<SAXParseException> exceptions) {
		this.exceptions = exceptions;
	}

	@Override
	public void error(SAXParseException spe) throws SAXException {

		if (logger.isDebugEnabled()) {
			logger.debug("error-> " + spe.getMessage());
		}

		exceptions.add(spe);
	}

	@Override
	public void fatalError(SAXParseException spe) throws SAXException {

		if (logger.isDebugEnabled()) {
			logger.debug("fatalError-> " + spe.getMessage());
		}

		exceptions.add(spe);
	}

	@Override
	public void warning(SAXParseException spe) throws SAXException {

		if (logger.isDebugEnabled()) {
			logger.debug("warning-> " + spe.getMessage());
		}

		warnings.add(spe);
	}

}
