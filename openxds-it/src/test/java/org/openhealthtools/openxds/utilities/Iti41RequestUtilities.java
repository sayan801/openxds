package org.openhealthtools.openxds.utilities;

import org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml30.ProvideAndRegisterDocumentSetRequestType;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Date;

public class Iti41RequestUtilities extends AbstractXdsUtilities {

	ProvideAndRegisterDocumentSetUtilities provideAndRegisterDocumentSetUtilities = new ProvideAndRegisterDocumentSetUtilities();
	
	public DocumentMetadata createDocumentMetadataForAftale(String patientCpr, Date creationDate, Date serviceStartTime, Date serviceStopTime) {
		
		// Metadata skal overholde Medcoms meta-data profil, som i skrivende stund (feb 2022) findes her: https://svn.medcom.dk/svn/drafts/Standarder/IHE/DK_profil_metadata/Metadata-v096.pdf 
		// Attributter, som sættes i metadata skal ligeledes være givet af Medcom i deres lister over værdier: http://svn.medcom.dk/svn/drafts/Standarder/IHE/OID/DK-IHE_Metadata-Common_Code_systems-Value_sets.xlsx
		
		// Eksempel på udfyldelse af metadata for en aftale
		DocumentMetadata documentMetadata = new DocumentMetadata();
		documentMetadata.setTitle("Dato og tidspunkt for møde mellem patient og sundhedsperson");
		documentMetadata.setPatientId(new Code(patientCpr, new LocalizedString(Codes.CPR_AUTHORITYNAME), Codes.CPR_OID));
		
		PatientInfo sourcePatientInfo = new PatientInfo();
		sourcePatientInfo.setName(new XpnName("Berggren", "Nancy", "Ann", null, null, null));
		sourcePatientInfo.setGender("F");
		sourcePatientInfo.setDateOfBirth("19481225");
		documentMetadata.setSourcePatientInfo(sourcePatientInfo);
		
		documentMetadata.setReportTime(creationDate);
		documentMetadata.setOrganisation(new Code("12345679999", new LocalizedString("DROS Testafdeling"), Codes.SOR_OID));
		documentMetadata.setClassCode(new Code("001", new LocalizedString("Klinisk rapport"), "1.2.208.184.100.9"));
		documentMetadata.setFormatCode(Codes.FORMAT_CODE_AFTALEDOKUMENT);
		documentMetadata.setHealthcareFacilityTypeCode(new Code("22232009", new LocalizedString("hospital"), "2.16.840.1.113883.6.96"));
		documentMetadata.setPracticeSettingCode(new Code("408443003", new LocalizedString("almen medicin"), "2.16.840.1.113883.6.96"));
		documentMetadata.setSubmissionTime(creationDate);
		documentMetadata.setContentTypeCode(Codes.TYPE_CODE_AFTALEDOKUMENT);
		documentMetadata.setTypeCode(Codes.TYPE_CODE_AFTALEDOKUMENT);
		documentMetadata.setServiceStartTime(serviceStartTime);
		documentMetadata.setServiceStopTime(serviceStopTime);
		documentMetadata.setMimeType("text/xml");
		documentMetadata.setLanguageCode("da-DK");
		documentMetadata.setConfidentialityCode(new Code("N", new LocalizedString("N"), "2.16.840.1.113883.5.25"));
		return documentMetadata;
	}

	public DocumentEntry createDocumentEntry(String documentId, DocumentMetadata documentMetadata, String documentEntryUuid) {
		documentEntryUuid = documentEntryUuid == null ? generateUUIDwithPrefix() : documentEntryUuid;
		return provideAndRegisterDocumentSetUtilities.createDocumentEntry(documentMetadata, documentEntryUuid, documentId, documentId);
	}
	
	public ProvideAndRegisterDocumentSetRequestType buildProvideAndRegisterDocumentSetRequestAftale(DocumentEntry documentEntry, DocumentMetadata documentMetadata, String documentPayload, String documentEntryUuidToReplace) throws SAXException, IOException, ParserConfigurationException, TransformerException {
		
		return provideAndRegisterDocumentSetUtilities.buildProvideAndRegisterDocumentSetRequest(documentEntry, documentPayload, documentMetadata, documentEntryUuidToReplace);
	}
	
	public String getAppointmentXmlDocument(boolean useDefaultDocument) throws SAXException, IOException, ParserConfigurationException, TransformerException {
		String documentPath = useDefaultDocument ? "/cda/DK-APD_Prototype.xml" : "/cda/DK-APD_Prototype_modified.xml";
		InputStream is = Iti41RequestUtilities.class.getResourceAsStream(documentPath);
		return getXmlFileContent(is);
	}


	public String getXmlFileContent(InputStream is) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = db.parse(is);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(doc), new StreamResult(writer));
		String output = writer.getBuffer().toString();
		String result = output;
		return result;
	}

}
