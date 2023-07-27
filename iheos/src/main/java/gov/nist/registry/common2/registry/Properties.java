package gov.nist.registry.common2.registry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;


public class Properties {
    final static private Properties properties = new Properties();

    final static public Integer schemaValidateRetryCount =
            properties.getProperty("schema.validate.retryCount") != null
                    ? parseInt(properties.getProperty("schema.validate.retryCount"))
                    : null;

    final static public Long schemaValidateRetryInterval =
            properties.getProperty("schema.validate.retryIntervalMs") != null
                    ? parseLong(properties.getProperty("schema.validate.retryIntervalMs"))
                    : null;

    final private java.util.Properties internalProperties = new java.util.Properties();

    private Properties() {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("openxds.properties");
        Log logger = LogFactory.getLog(Properties.class);
        if (is == null) { logger.fatal("Cannot load openxds.properties"); return; }
        try { internalProperties.load(is); } catch (Exception e) { logger.fatal(exception_details(e)); }
    }

    //public Optional<String> getProperty(String name) { return ofNullable(internalProperties.getProperty(name)); }
    public String getProperty(String name) { return internalProperties.getProperty(name); }

    public static String exception_details(Exception e) {
        if (e == null)
            return "";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        e.printStackTrace(ps);
        return "Exception thrown: " + e.getClass().getName() + "\n" + e.getMessage() + "\n" + baos;
    }
}