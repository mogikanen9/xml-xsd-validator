package mogikanensoftware.xml.service.data.sm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class ServiceManagerExceptionTests {

	@Test
	public void testServiceManagerExceptionStringThrowable() {
		final String msgNew  = "My new error message";
		final String msgNull  = "Cannot be null";
		Exception exNull = new NullPointerException(msgNull);
		ServiceManagerException smEx = new ServiceManagerException(msgNew,exNull);
		assertEquals(smEx.getMessage(), msgNew);
		assertTrue(smEx.getCause() instanceof NullPointerException);
		assertEquals(smEx.getCause().getMessage(), msgNull);
	}

	@Test
	public void testServiceManagerExceptionString() {
		final String msg  = "My error message";
		ServiceManagerException smEx = new ServiceManagerException(msg);
		assertEquals(smEx.getMessage(), msg);
	}

}
