package com.mogikanensoftware.xml.utils.core.service;

public class ParsingServiceException extends Exception {

		private static final long serialVersionUID = 1L;

		public ParsingServiceException(String message, Throwable cause) {
			super(message, cause);			
		}

		public ParsingServiceException(String message) {
			super(message);
		}

		public ParsingServiceException(Throwable cause) {
			super(cause);
		}

}
