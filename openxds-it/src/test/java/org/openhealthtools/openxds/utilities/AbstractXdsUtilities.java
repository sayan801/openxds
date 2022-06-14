package org.openhealthtools.openxds.utilities;

import org.openehealth.ipf.commons.ihe.xds.core.ebxml.EbXMLFactory;
import org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml30.EbXMLFactory30;
import org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml30.EbXMLRegistryResponse30;
import org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml30.EbXMLSubmitObjectsRequest30;
import org.openehealth.ipf.commons.ihe.xds.core.requests.RegisterDocumentSet;
import org.openehealth.ipf.commons.ihe.xds.core.responses.Response;
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.lcm.SubmitObjectsRequest;
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.rs.RegistryResponseType;
import org.openehealth.ipf.commons.ihe.xds.core.transform.requests.RegisterDocumentSetTransformer;
import org.openehealth.ipf.commons.ihe.xds.core.transform.responses.ResponseTransformer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AbstractXdsUtilities {

	protected static final EbXMLFactory ebXMLFactory = new EbXMLFactory30();

	protected DateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");

	public Response convertReponse(RegistryResponseType rrt) {
		
		ResponseTransformer responseTransformer = new ResponseTransformer(ebXMLFactory);
		Response response = responseTransformer.fromEbXML(new EbXMLRegistryResponse30(rrt));
		return response;
	}

	public SubmitObjectsRequest createSubmitObjectsRequest(RegisterDocumentSet request) {
		RegisterDocumentSetTransformer requestTransformer = new RegisterDocumentSetTransformer(ebXMLFactory);
		EbXMLSubmitObjectsRequest30 ebxmlRequest = (EbXMLSubmitObjectsRequest30) requestTransformer.toEbXML(request);
		SubmitObjectsRequest submitObjectsRequest = ebxmlRequest.getInternal();
		
		return submitObjectsRequest;
	}

	
	public String generateId() {
		java.util.UUID uuid = java.util.UUID.randomUUID();
		return Math.abs(uuid.getLeastSignificantBits()) + "." + Math.abs(uuid.getMostSignificantBits())+"."+Calendar.getInstance().getTimeInMillis();
	}

	public String generateUUID() {
		java.util.UUID uuid = java.util.UUID.randomUUID();
		return uuid.toString();
	}
	
	public String generateUUIDwithPrefix() {
		java.util.UUID uuid = java.util.UUID.randomUUID();
		return "urn:uuid:" + uuid.toString();
	}

}

