package com.mogikanensoftware.xml.utils.core.service.impl;

import com.mogikanensoftware.xml.utils.core.service.ParsingService;
import com.mogikanensoftware.xml.utils.core.service.ParsingServiceException;

public class SAXErrorsParsingServiceImpl implements ParsingService {
	

	@Override
	public String extractErrorType(String errorMessage) throws ParsingServiceException {
		int firstColonSymbolIndex =  errorMessage.indexOf(':');
		
		String errorType=  null;
		
		if(firstColonSymbolIndex!=-1){
			errorType = errorMessage.substring(0, firstColonSymbolIndex);
		}
				
		return errorType;
	}

	@Override
	public String extractElementName(String errorMessage) throws ParsingServiceException {
		
		String rs = "unknown";
		
		//for type
		int index = errorMessage.indexOf("for type '");
		if(index!=-1){
			rs = errorMessage.substring(index+10);			
		}else{

			//of element
			index = errorMessage.indexOf("of element '");
			if(index!=-1){
				rs = errorMessage.substring(index+12);
			}else{
			
				//on element
				index = errorMessage.indexOf("on element '");
				if(index!=-1){
					rs = errorMessage.substring(index+12);
				}
			}
			
		}

		rs = rs.trim();
		
		index = rs.indexOf('\'');
		if(index!=-1 && ! rs.startsWith("'")){
			rs = rs.substring(0, index);
		}
		
		return rs;
	}

	@Override
	public String supressActualElementValue(String errorMessage) throws ParsingServiceException {
		//Value '
		
		String rs = errorMessage.replaceAll("", "");
		return rs;
	}

}
