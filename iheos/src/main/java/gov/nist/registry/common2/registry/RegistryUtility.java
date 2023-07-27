package gov.nist.registry.common2.registry;

import gov.nist.registry.common2.exception.ExceptionUtil;
import gov.nist.registry.common2.exception.SchemaValidationException;
import gov.nist.registry.common2.exception.XdsException;
import gov.nist.registry.common2.exception.XdsInternalException;
import gov.nist.registry.common2.registry.validation.Validator;
import gov.nist.registry.common2.xml.SchemaValidation;
import org.apache.axiom.om.OMElement;
import org.openhealthtools.openexchange.actorconfig.net.IConnectionDescription;
import org.openhealthtools.openxds.log.LogMessage;
import org.openhealthtools.openxds.log.LoggerException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class RegistryUtility {

	static public void schema_validate_local(OMElement ahqr, int metadata_type)
			throws XdsInternalException, SchemaValidationException {
		String schema_messages = null;
		final int retries = Properties.schemaValidateRetryCount != null
                ? Properties.schemaValidateRetryCount
                : 3;
		int numRetries = 0;
		while (numRetries < retries) {
			try {
				schema_messages = SchemaValidation.validate(ahqr, metadata_type);
				if (schema_messages == null || schema_messages.length() == 0) { break; }
			} catch (Exception e) {
				if (numRetries == retries - 1) {
					throw new XdsInternalException("Schema Validation threw internal error: " + e.getMessage(), e);
				} else {
					try {
						Thread.sleep(Properties.schemaValidateRetryInterval != null
								? Properties.schemaValidateRetryInterval
								: 100);
					} catch (InterruptedException ex) { }
				}
			}
			numRetries++;
		}

		if (schema_messages != null && schema_messages.length() > 0) {
			throw new SchemaValidationException("Input did not validate against schema:" + schema_messages);
		}
	}

 	static public RegistryErrorList metadata_validator(Metadata m, boolean is_submit, boolean isPnR, IConnectionDescription connection) throws XdsException {
		RegistryErrorList rel = new RegistryErrorList((m.isVersion2() ? RegistryErrorList.version_2 : RegistryErrorList.version_3));
		try {
			Validator v = new Validator(m, rel, is_submit, !m.isVersion2(), (LogMessage)null, isPnR, connection);
			v.run();
			return rel;
		} catch (LoggerException e) {
			throw new XdsInternalException(ExceptionUtil.exception_details(e));
		}
	}

	public static String exception_details(Exception e) {
		if (e == null)
			return "";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		e.printStackTrace(ps);

		return "Exception thrown: " + e.getClass().getName() + "\n" + e.getMessage() + "\n" + new String(baos.toByteArray());
	}

	public static String exception_trace(Exception e) {
		if (e == null)
			return "";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		StackTraceElement ste[] = e.getStackTrace();
		ps.print("\n");
		for (int i=0; i<ste.length && i<15; i++)
			ps.print("\t" + ste[i].toString() + "\n");
		//e.printStackTrace(ps);

		return new String(baos.toByteArray());
	}

}
