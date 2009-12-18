package gov.nist.registry.ws.serviceclasses;

import gov.nist.registry.common2.exception.XdsValidationException;
import gov.nist.registry.ws.AdhocQueryRequest;

import org.apache.axiom.om.OMElement;

public class XcaRegistry extends RegistryB {
	String home;

	protected void validateQueryInputDecoration(OMElement sor, AdhocQueryRequest a)
	throws XdsValidationException {

		home = getHomeParameter(sor, a);
		boolean hasHome = home != null && !home.equals("");
		boolean homeRequired = a.requiresHomeInXGQ(sor);

		if (homeRequired && !hasHome) 
			throw new XdsValidationException("This endpoint simulates Stored Query in the presence of XCA, homeCommunityId is required on this Stored Query");
	}

	protected void decorateQueryOutput(OMElement sor, AdhocQueryRequest a, OMElement result) throws XdsValidationException {
		home = getHomeParameter(sor, a);
		if (home == null || home.equals(""))
			home = properties.getString("home.community.id");
			setHomeOnSQResponse(result, home);
	}


}
