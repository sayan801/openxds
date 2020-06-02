package gov.nist.registry.common2.util;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;   

public class PidHelper {
	
	private final static Pattern mxBeanJvmNamePattern = Pattern.compile("^([0-9]+)@.*");

    private final static Log logger = LogFactory.getLog(PidHelper.class);
	
	private static String cached_pid = null;
	/**
	 * Sadly, there does not seem to be any elegant way to find the Process ID of
	 * the JVM. We fall back to looking at the JVM name - this returns a name of the
	 * form 'pid@hostname' at least for the SUN JVM's on Windows, Linux and Mac.
	 * 
	 * See also: http://blog.igorminar.com/2007/03/how-java-application-can-discover-its.html
	 * and http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4244896
	 * 
	 * @return the Process ID of the currently running JVM, if it can be determined.
	 */
	public static synchronized String getPid() {
		
		if (cached_pid != null) {
			return cached_pid;
		}
		
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
		String jvmName = runtimeMXBean.getName();
		
		Matcher matcher = mxBeanJvmNamePattern.matcher(jvmName);
		if (matcher.matches()) {
			cached_pid = matcher.group(1);
			return cached_pid;
		}
		else
		{
			cached_pid = " ";
			return cached_pid;
		}
	}
}
