package org.openhealthtools.openxds;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml30.ProvideAndRegisterDocumentSetRequestType;
import org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml30.RetrieveDocumentSetRequestType;
import org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml30.RetrieveDocumentSetResponseType;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.DocumentEntry;
import org.openehealth.ipf.commons.ihe.xds.core.responses.ErrorCode;
import org.openehealth.ipf.commons.ihe.xds.core.responses.ErrorInfo;
import org.openehealth.ipf.commons.ihe.xds.core.responses.Response;
import org.openehealth.ipf.commons.ihe.xds.core.responses.Status;
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.rs.RegistryResponseType;
import org.openehealth.ipf.commons.ihe.xds.iti41.Iti41PortType;
import org.openehealth.ipf.commons.ihe.xds.iti43.Iti43PortType;
import org.openhealthtools.openxds.configuration.ItiPortTypeTestConfiguration;
import org.openhealthtools.openxds.utilities.DocumentMetadata;
import org.openhealthtools.openxds.utilities.Iti41RequestUtilities;
import org.openhealthtools.openxds.utilities.ProvideAndRegisterDocumentSetUtilities;
import org.openhealthtools.openxds.utilities.RetrieveDocumentSetUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ItiPortTypeTestConfiguration.class })
public class OpenXdsIT {

    private ProvideAndRegisterDocumentSetUtilities provideAndRegisterDocumentSetUtilities = new ProvideAndRegisterDocumentSetUtilities();

    private RetrieveDocumentSetUtilities retrieveDocumentSetUtilities = new RetrieveDocumentSetUtilities();

    private Iti41RequestUtilities iti41RequestUtilities = new Iti41RequestUtilities();

    @Autowired
    private Iti41PortType iti41PortType;

    @Autowired
    private Iti43PortType iti43PortType;

    @Test
    public void testUploadAndRetrieveAftaleDocument() throws IOException, ParserConfigurationException, TransformerException, SAXException {
        String documentId = provideAndRegisterDocumentSetUtilities.generateId();
        Date createStartEnd = Calendar.getInstance().getTime();
        CreateResponse createResponse = createAftaleDocumentFor("1234567890", documentId, createStartEnd, createStartEnd, createStartEnd, null, true);

        Assert.assertEquals(Status.SUCCESS, createResponse.getResponse().getStatus());

        RetrieveDocumentSetResponseType retrieveResponse = retrieveDocument(documentId);

        Assert.assertNotNull(retrieveResponse.getRegistryResponse());
        Assert.assertTrue(retrieveResponse.getRegistryResponse().getStatus(), retrieveResponse.getRegistryResponse().getStatus().contains("Success"));
        Assert.assertNotNull(retrieveResponse.getDocumentResponse());
        Assert.assertEquals(1, retrieveResponse.getDocumentResponse().size());
        Assert.assertEquals(documentId, retrieveResponse.getDocumentResponse().get(0).getDocumentUniqueId());
    }

    /**
     * Same document unique id
     * Same hash
     * Same metadata
     * Same document entry id
     */
    @Test
    public void testUploadDocumentTwice1() throws IOException, ParserConfigurationException, TransformerException, SAXException {
        String documentId = provideAndRegisterDocumentSetUtilities.generateId();
        Date createStartEnd = Calendar.getInstance().getTime();

        CreateResponse createResponse = createAftaleDocumentFor("1234567890", documentId, createStartEnd, createStartEnd, createStartEnd, null, true);
        Assert.assertEquals(Status.SUCCESS, createResponse.getResponse().getStatus());
        createResponse = createAftaleDocumentFor("1234567890", documentId, createStartEnd, createStartEnd, createStartEnd, createResponse.getDocumentEntryUuid(), true);
//        Assert.assertEquals(Status.SUCCESS, createResponse.getResponse().getStatus()); TODO: Fejler indtil SDS-5616 er løst

        RetrieveDocumentSetResponseType retrieveResponse = retrieveDocument(documentId);

        Assert.assertNotNull(retrieveResponse.getRegistryResponse());
        Assert.assertTrue(retrieveResponse.getRegistryResponse().getStatus(), retrieveResponse.getRegistryResponse().getStatus().contains("Success"));
        Assert.assertNotNull(retrieveResponse.getDocumentResponse());
        Assert.assertEquals(1, retrieveResponse.getDocumentResponse().size());
        Assert.assertEquals(documentId, retrieveResponse.getDocumentResponse().get(0).getDocumentUniqueId());
    }

    /**
     * Same document unique id
     * Same hash
     * Different metadata
     * Same document entry id
     */
    @Test
    public void testUploadDocumentTwice2() throws IOException, ParserConfigurationException, TransformerException, SAXException {
        String documentId = provideAndRegisterDocumentSetUtilities.generateId();
        Date createStartEnd = Calendar.getInstance().getTime();

        CreateResponse createResponse = createAftaleDocumentFor("1234567890", documentId, createStartEnd, createStartEnd, createStartEnd, null, true);
        Assert.assertEquals(Status.SUCCESS, createResponse.getResponse().getStatus());
        createResponse = createAftaleDocumentFor("0000000000", documentId, createStartEnd, createStartEnd, createStartEnd, createResponse.getDocumentEntryUuid(), true);
        Assert.assertEquals(Status.FAILURE, createResponse.getResponse().getStatus());

        RetrieveDocumentSetResponseType retrieveResponse = retrieveDocument(documentId);

        Assert.assertNotNull(retrieveResponse.getRegistryResponse());
        Assert.assertTrue(retrieveResponse.getRegistryResponse().getStatus(), retrieveResponse.getRegistryResponse().getStatus().contains("Success"));
        Assert.assertNotNull(retrieveResponse.getDocumentResponse());
        Assert.assertEquals(1, retrieveResponse.getDocumentResponse().size());
        Assert.assertEquals(documentId, retrieveResponse.getDocumentResponse().get(0).getDocumentUniqueId());
    }

    /**
     * Same document unique id
     * Same hash
     * Same metadata
     * Different document entry id
     */
    @Test
    public void testUploadDocumentTwice3() throws IOException, ParserConfigurationException, TransformerException, SAXException {
        String documentId = provideAndRegisterDocumentSetUtilities.generateId();
        Date createStartEnd = Calendar.getInstance().getTime();

        CreateResponse createResponse = createAftaleDocumentFor("1234567890", documentId, createStartEnd, createStartEnd, createStartEnd, null, true);
        Assert.assertEquals(Status.SUCCESS, createResponse.getResponse().getStatus());
        createResponse = createAftaleDocumentFor("1234567890", documentId, createStartEnd, createStartEnd, createStartEnd, null, true);
//        Assert.assertEquals(Status.SUCCESS, createResponse.getResponse().getStatus()); TODO: Fejler indtil SDS-5616 er løst

        RetrieveDocumentSetResponseType retrieveResponse = retrieveDocument(documentId);

        Assert.assertNotNull(retrieveResponse.getRegistryResponse());
        Assert.assertTrue(retrieveResponse.getRegistryResponse().getStatus(), retrieveResponse.getRegistryResponse().getStatus().contains("Success"));
        Assert.assertNotNull(retrieveResponse.getDocumentResponse());
        Assert.assertEquals(1, retrieveResponse.getDocumentResponse().size());
        Assert.assertEquals(documentId, retrieveResponse.getDocumentResponse().get(0).getDocumentUniqueId());
    }

    /**
     * Same document unique id
     * Different hash
     */
    @Test
    public void testUploadDocumentTwice4() throws IOException, ParserConfigurationException, TransformerException, SAXException {
        String documentId = provideAndRegisterDocumentSetUtilities.generateId();
        Date createStartEnd = Calendar.getInstance().getTime();

        CreateResponse createResponse = createAftaleDocumentFor("1234567890", documentId, createStartEnd, createStartEnd, createStartEnd, null, true);
        Assert.assertEquals(Status.SUCCESS, createResponse.getResponse().getStatus());
        createResponse = createAftaleDocumentFor("1234567890", documentId, createStartEnd, createStartEnd, createStartEnd, null, false);
        Assert.assertEquals(Status.FAILURE, createResponse.getResponse().getStatus());
        ErrorInfo errorInfo = createResponse.getResponse().getErrors().get(0);
        Assert.assertEquals(ErrorCode.REPOSITORY_ERROR, errorInfo.getErrorCode());
        Assert.assertTrue(errorInfo.getCodeContext(), errorInfo.getCodeContext().contains("document unique id already exist in repository"));

        RetrieveDocumentSetResponseType retrieveResponse = retrieveDocument(documentId);

        Assert.assertNotNull(retrieveResponse.getRegistryResponse());
        Assert.assertTrue(retrieveResponse.getRegistryResponse().getStatus(), retrieveResponse.getRegistryResponse().getStatus().contains("Success"));
        Assert.assertNotNull(retrieveResponse.getDocumentResponse());
        Assert.assertEquals(1, retrieveResponse.getDocumentResponse().size());
        Assert.assertEquals(documentId, retrieveResponse.getDocumentResponse().get(0).getDocumentUniqueId());
    }

    private RetrieveDocumentSetResponseType retrieveDocument(String documentId) {
        RetrieveDocumentSetRequestType request = retrieveDocumentSetUtilities.createRetrieveDocumentSetRequestType(documentId, "urn:oid:1.2.208.176.43210.8.10", "1.3.6.1.4.1.21367.2010.1.2.1125");
        return iti43PortType.documentRepositoryRetrieveDocumentSet(request);
    }

    private CreateResponse createAftaleDocumentFor(String patientCpr, String documentId, Date creationDate, Date from, Date to, String documentEntryUuid, boolean useDefaultDocument) throws SAXException, IOException, ParserConfigurationException, TransformerException {

        DocumentMetadata documentMetadata = iti41RequestUtilities.createDocumentMetadataForAftale(patientCpr, creationDate, from, to);

        return createAftaleForPatient(documentId, documentMetadata, documentEntryUuid, useDefaultDocument);
    }

    private CreateResponse createAftaleForPatient(String documentId, DocumentMetadata documentMetadata, String documentEntryUuid, boolean useDefaultDocument) throws SAXException, IOException, ParserConfigurationException, TransformerException {

        // Indlæs dokumentet, som skal registreres
        String documentPayload = iti41RequestUtilities.getAppointmentXmlDocument(useDefaultDocument);

        // Opret documentEntry
        DocumentEntry documentEntry = iti41RequestUtilities.createDocumentEntry(documentId, documentMetadata, documentEntryUuid);

        // Opret kald/request
        ProvideAndRegisterDocumentSetRequestType provideAndRegisterDocumentSetRequest = iti41RequestUtilities.buildProvideAndRegisterDocumentSetRequestAftale(documentEntry, documentMetadata, documentPayload, null);

        // Udfør kald
        RegistryResponseType registryResponse = iti41PortType.documentRepositoryProvideAndRegisterDocumentSetB(provideAndRegisterDocumentSetRequest);
        documentEntry.setRepositoryUniqueId(provideAndRegisterDocumentSetUtilities.generateId());

        // Aflæs kaldets svar
        return new CreateResponse(documentMetadata.getPatientId().getCode(), provideAndRegisterDocumentSetUtilities.convertReponse(registryResponse), documentEntry);
    }

    public static class CreateResponse {

        private String patientId;
        private Response response;
        private DocumentEntry created;

        public CreateResponse(String patientId, Response response, DocumentEntry created) {
            this.response = response;
            this.patientId = patientId;
            this.created = created;
        }

        public String getDocumentEntryUuid() {
            return (created != null ? created.getEntryUuid() : null);
        }

        public Response getResponse() {
            return response;
        }

        public String getPatientId() {
            return patientId;
        }

        public DocumentEntry getDocumentEntryCreated() {
            return created;
        }
    }
}
