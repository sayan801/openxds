package org.openhealthtools.openxds.configuration;

import org.openehealth.ipf.commons.ihe.ws.WsTransactionConfiguration;
import org.openehealth.ipf.commons.ihe.xds.core.XdsClientFactory;
import org.openehealth.ipf.commons.ihe.xds.iti41.Iti41PortType;
import org.openehealth.ipf.commons.ihe.xds.iti43.Iti43PortType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.namespace.QName;

@Configuration
public class ItiPortTypeConfiguration {

	public static final String XDS_B_2007_TARGET_NAMESPACE = "urn:ihe:iti:xds-b:2007";
	public static final String XDS_B_2010_TARGET_NAMESPACE = "urn:ihe:iti:xds-b:2010";

	private final String serviceEndpoint = System.getProperty("service.endpoint", "http://localhost:8020/axis2/services/xdsrepositoryb");

	@Bean
	public Iti43PortType getDocumentRepositoryServiceIti43() {
		XdsClientFactory xdsClientFactory = generateXdsRepositoryClientFactory("wsdl/iti43.wsdl", serviceEndpoint, Iti43PortType.class);
		Iti43PortType client = (Iti43PortType) xdsClientFactory.getClient();

		initProxy(client);

		return client;
	}

	@Bean
	public Iti41PortType getDocumentRepositoryServiceIti41() {
		return new Iti41PortTypeThreadLocalWrapper(() -> {
			XdsClientFactory xdsClientFactory = generateXdsRepositoryClientFactory("wsdl/iti41.wsdl", serviceEndpoint, Iti41PortType.class);
			Iti41PortType client = (Iti41PortType) xdsClientFactory.getClient();

			initProxy(client);

			return client;
		});
	}

	protected XdsClientFactory generateXdsRepositoryClientFactory(String wsdl, String url, Class<?> clazz){
		final WsTransactionConfiguration WS_CONFIG = new WsTransactionConfiguration(
				new QName("urn:ihe:iti:xds-b:2007", "DocumentRepository_Service",
						"ihe"), clazz, new QName(
								XDS_B_2007_TARGET_NAMESPACE,
								"DocumentRepository_Binding_Soap12", "ihe"), true,
				wsdl, true, false, false, false);


		XdsClientFactory xcf = new XdsClientFactory(WS_CONFIG, url, null, null,null);
		return xcf;
	}
	protected XdsClientFactory generateXdsRegistryClientFactory(String wsdl, String url, String targetNameSpace, Class<?> clazz){
		final WsTransactionConfiguration WS_CONFIG = new WsTransactionConfiguration(
				new QName(targetNameSpace, "DocumentRegistry_Service",
						"ihe"), clazz, new QName(
								targetNameSpace,
								"DocumentRegistry_Binding_Soap12", "ihe"), false,
				wsdl, true, false, false, false);


		XdsClientFactory xcf = new XdsClientFactory(WS_CONFIG, url, null, null,null);
		return xcf;
	}


	protected void initProxy(Object o) {
		// Can be overridden in subclasses
	}

}
