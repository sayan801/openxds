package org.openhealthtools.openxds.configuration;

import org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml30.ProvideAndRegisterDocumentSetRequestType;
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.rs.RegistryResponseType;
import org.openehealth.ipf.commons.ihe.xds.iti41.Iti41PortType;

import java.util.function.Supplier;

public class Iti41PortTypeThreadLocalWrapper extends ItiPortTypeThreadLocalWrapper<Iti41PortType> implements Iti41PortType {
    public Iti41PortTypeThreadLocalWrapper(Supplier<Iti41PortType> supplier) {
        super(supplier);
    }

    @Override
    public RegistryResponseType documentRepositoryProvideAndRegisterDocumentSetB(ProvideAndRegisterDocumentSetRequestType provideAndRegisterDocumentSetRequestType) {
        return getPort().documentRepositoryProvideAndRegisterDocumentSetB(provideAndRegisterDocumentSetRequestType);
    }
}
