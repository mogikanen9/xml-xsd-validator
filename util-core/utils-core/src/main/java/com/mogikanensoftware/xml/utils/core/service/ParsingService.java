package com.mogikanensoftware.xml.utils.core.service;

public interface ParsingService {

	
	String extractErrorType (String errorMessage) throws ParsingServiceException;
	
	String extractElementName (String errorMessage) throws ParsingServiceException;
	
	String supressActualElementValue (String errorMessage) throws ParsingServiceException;
}
