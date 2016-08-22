package com.mogikanensoftware.xml.utils.core.service.impl;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mogikanensoftware.xml.utils.core.service.ParsingService;
import com.mogikanensoftware.xml.utils.core.service.ParsingServiceException;

public class SAXErrorsParsingServiceImpl implements ParsingService {
	
	private static final Logger logger = LogManager.getLogger(SAXErrorsParsingServiceImpl.class);

	@Override
	public String extractErrorType(String errorMessage) throws ParsingServiceException {
		int firstColonSymbolIndex =  errorMessage.indexOf(':');
		
		String errorType=  null;
		
		if(firstColonSymbolIndex!=-1){
			errorType = errorMessage.substring(0, firstColonSymbolIndex);
		}else{
			logger.warn("Cannot extract errorType in errorMessage->"+errorMessage);
		}
				
		return errorType;
	}

	@Override
	public String extractElementName(String errorMessage) throws ParsingServiceException {
		
		String rs = null;
		
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
				}else{
					logger.warn("Cannot extract Element name from errorMessage->"+errorMessage);
				}
			}												
		}
			
		if(rs!=null){
			rs = rs.trim();
			
			index = rs.indexOf('\'');
			if(index!=-1 && ! rs.startsWith("'")){
				rs = rs.substring(0, index);
			}
		}else{
			rs = "unknown";
			logger.warn("Element name from errorMessage->"+errorMessage+" was set to 'unknown'.");
		}
		
		
		return rs;
	}

	@Override
	public String supressActualElementValue(String errorMessage) throws ParsingServiceException {
		
		String rs = errorMessage;
		
		//Value '
		int indexFrom = errorMessage.toLowerCase().indexOf("value '");
		
		if(indexFrom!=-1){
			int indexTo = errorMessage.toLowerCase().indexOf("'", indexFrom+7);
			if(indexFrom!=-1){
				rs = errorMessage.substring(0,indexFrom+6)+ errorMessage.substring(indexTo+1);	
			}else{
				logger.warn("Cannot find the END of the actual element value to suppress it. errorMessage->"+errorMessage);
			}			
		}else{
			logger.warn("Cannot find actual element value to suppress it. errorMessage->"+errorMessage);
		}
				
		return rs;
	}

}
