package org.openhealthtools.openxds.configuration;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.transport.http.HTTPConduit;
import org.openehealth.ipf.commons.ihe.xds.core.XdsClientFactory;
import org.openehealth.ipf.commons.ihe.xds.iti42.Iti42PortType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

@Configuration
public class ItiPortTypeTestConfiguration extends ItiPortTypeConfiguration {

	private TrustManager[] trustAllCerts = new TrustManager[] {
			new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType) {  }

				public void checkServerTrusted(X509Certificate[] certs, String authType) {  }
			}
	};

	@Override
	protected void initProxy(Object o) {
		
		Client proxy = ClientProxy.getClient(o);		
		proxy.getOutInterceptors().add(new LoggingOutInterceptor());
		proxy.getInInterceptors().add(new LoggingInInterceptor());
		HTTPConduit conduit = (HTTPConduit)proxy.getConduit();
		TLSClientParameters tcp = new TLSClientParameters();
		tcp.setTrustManagers(trustAllCerts);
		conduit.setTlsClientParameters(tcp);
	}
}
