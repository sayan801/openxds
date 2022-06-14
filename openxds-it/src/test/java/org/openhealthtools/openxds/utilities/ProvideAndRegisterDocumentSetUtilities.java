package org.openhealthtools.openxds.utilities;

import org.joda.time.DateTime;
import org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml30.EbXMLProvideAndRegisterDocumentSetRequest30;
import org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml30.ProvideAndRegisterDocumentSetRequestType;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.*;
import org.openehealth.ipf.commons.ihe.xds.core.requests.ProvideAndRegisterDocumentSet;
import org.openehealth.ipf.commons.ihe.xds.core.transform.requests.ProvideAndRegisterDocumentSetTransformer;

import javax.activation.DataHandler;
import javax.mail.util.ByteArrayDataSource;
import java.util.Date;
import java.util.List;

public class ProvideAndRegisterDocumentSetUtilities extends AbstractXdsUtilities {

	
	public ProvideAndRegisterDocumentSetRequestType buildProvideAndRegisterDocumentSetRequest(DocumentEntry documentEntry, String documentPayload, DocumentMetadata documentMetadata, String externalIdForDocumentToReplace) {
		// denne "udpakker" metode er lavet for at kunne lave en buildProvideAndRegisterDocumentSetRequest metode, som ikke arbejder med typen DocumentMetadata. Dette anvendes i "guide til anvendere" som hjælpekode
		return buildProvideAndRegisterDocumentSetRequest(documentEntry, documentPayload, externalIdForDocumentToReplace, documentMetadata.getContentTypeCode());
	}
	
	public ProvideAndRegisterDocumentSetRequestType buildProvideAndRegisterDocumentSetRequest(DocumentEntry documentEntry, String documentPayload, String externalIdForDocumentToReplace, Code contentTypeCode) {

		ProvideAndRegisterDocumentSet provideAndRegisterDocumentSet = new ProvideAndRegisterDocumentSet();

		Document document = new Document(documentEntry, new DataHandler(new ByteArrayDataSource(documentPayload.getBytes(), documentEntry.getMimeType())));
		provideAndRegisterDocumentSet.getDocuments().add(document);

		// Opret SubmissionSet for dokumentet
		SubmissionSet submissionSet = createSubmissionSet(documentEntry.getPatientId(), contentTypeCode, documentEntry.getCreationTime());
		provideAndRegisterDocumentSet.setSubmissionSet(submissionSet);

		// Opret association mellem SubmissionSet og DocumentEntry
		Association association = createAssociation(submissionSet, documentEntry);
		provideAndRegisterDocumentSet.getAssociations().add(association);
		
		// Hvis det er en opdatering af dokumentet, oprettes association mellem det gamle og det nye dokument vha. entryuuid'erne
		if (externalIdForDocumentToReplace != null) {
			Association replacementAssociation = new Association(AssociationType.REPLACE, generateUUID(), documentEntry.getEntryUuid(), externalIdForDocumentToReplace);
			provideAndRegisterDocumentSet.getAssociations().add(replacementAssociation);
		}

		// Transformer request - dette laver core model om til RIM format
		ProvideAndRegisterDocumentSetTransformer registerDocumentSetTransformer = new ProvideAndRegisterDocumentSetTransformer(ebXMLFactory);
		EbXMLProvideAndRegisterDocumentSetRequest30 ebxmlRequest = (EbXMLProvideAndRegisterDocumentSetRequest30) registerDocumentSetTransformer.toEbXML(provideAndRegisterDocumentSet);
		ProvideAndRegisterDocumentSetRequestType provideAndRegisterDocumentSetRequestType = ebxmlRequest.getInternal();
	
		return provideAndRegisterDocumentSetRequestType;
	}

	public Association createAssociation(SubmissionSet submissionSet, DocumentEntry documentEntry) {
		// Opret association mellem SubmissionSet og DocumentEntry
		Association association = new Association();
		association.setAssociationType(AssociationType.HAS_MEMBER);
		association.setEntryUuid(generateUUID());
		association.setSourceUuid(submissionSet.getEntryUuid());
		association.setTargetUuid(documentEntry.getEntryUuid());
		association.setAvailabilityStatus(AvailabilityStatus.APPROVED);
		association.setLabel(AssociationLabel.ORIGINAL);
		return association;
	}
	
	public DocumentEntry createDocumentEntry(DocumentMetadata documentMetadata, String documentEntryUuid, String uniqueId, String logicalId) {
		// denne "udpakker" metode er lavet for at kunne lave en createDocumentEntry metode, som ikke arbejder med typen DocumentMetadata. Dette anvendes i "guide til anvendere" som hjælpekode
		
		String reportTime = (documentMetadata.getReportTime() == null ) ? null : dateTimeFormat.format(documentMetadata.getReportTime()); 
		String serviceStartTime = (documentMetadata.getServiceStartTime() == null ) ? null : dateTimeFormat.format(documentMetadata.getServiceStartTime());
		String serviceStopTime = (documentMetadata.getServiceStopTime() == null ) ? null : dateTimeFormat.format(documentMetadata.getServiceStopTime());
		
		return createDocumentEntry(documentMetadata.getPatientId(), documentMetadata.getOrganisation(), reportTime, serviceStartTime, serviceStopTime, 
				documentMetadata.getClassCode(), documentMetadata.getConfidentialityCode(), documentMetadata.getEventCodes(), documentMetadata.getFormatCode(), documentMetadata.getHealthcareFacilityTypeCode(), 
				documentMetadata.getLanguageCode(), documentMetadata.getMimeType(), documentMetadata.getTitle(), documentMetadata.getTypeCode(), documentMetadata.getPracticeSettingCode(), documentMetadata.getSourcePatientInfo(),   
				documentEntryUuid, uniqueId, logicalId);
	}
	
	public DocumentEntry createDocumentEntry(Code patientId, Code organisation, String creationTime, String serviceStartTime, String serviceStopTime, Code classCode, Code confidentialityCode, List<Code> eventCodes, Code formatCode, Code healthcareFacilityTypeCode, 
			 String languageCode, String mimeType, String title, Code typeCode, Code practiceSettingCode, PatientInfo sourcePatientInfo, String documentEntryUuid, String uniqueId, String logicalId) {
		
		// Opret DocumentEntry for CDA dokumentet
		DocumentEntry documentEntry = new DocumentEntry();
		documentEntry.setEntryUuid(documentEntryUuid);
		documentEntry.setUniqueId(uniqueId);
		documentEntry.setLogicalUuid(logicalId);

		// Patient Identification
		Identifiable patientIdentifiable = null;
		if (patientId != null) {
			AssigningAuthority patientIdAssigningAuthority = new AssigningAuthority(patientId.getSchemeName());
			patientIdentifiable = new Identifiable(patientId.getCode(), patientIdAssigningAuthority);
		}
		documentEntry.setPatientId(patientIdentifiable);
		documentEntry.setSourcePatientId(patientIdentifiable);
		
        if (sourcePatientInfo != null) {
        	documentEntry.setSourcePatientInfo(sourcePatientInfo);	
        }
 
		// Opret author (Organisation)
		AssigningAuthority organisationAssigningAuthority = new AssigningAuthority(organisation.getSchemeName());
		Author author = new Author();
		if (organisation != null && organisation.getCode() != null) {
			String orgDisplayname = (organisation.getDisplayName() != null ? organisation.getDisplayName().getValue() : "");
			Organization authorOrganisation = new Organization(orgDisplayname, organisation.getCode(), organisationAssigningAuthority);
			author.getAuthorInstitution().add(authorOrganisation);
		}
		documentEntry.setAuthor(author);

		// Availability Status (enumeration: APPROVED, SUBMITTED, DEPRECATED)
		documentEntry.setAvailabilityStatus(AvailabilityStatus.APPROVED);

		// Datoer - skal angives i UTC tid
		if (creationTime != null) {
			documentEntry.setCreationTime(creationTime);
		}
		if (serviceStartTime != null) {
			documentEntry.setServiceStartTime(serviceStartTime);
		}
		if (serviceStopTime != null) {
			documentEntry.setServiceStopTime(serviceStopTime);
		}

		if (classCode != null) {
			documentEntry.setClassCode(classCode);
		}
		if (confidentialityCode != null) {
			documentEntry.getConfidentialityCodes().add(confidentialityCode);
		}

		List<Code> eventCodesEntry = documentEntry.getEventCodeList();
		if (eventCodes != null) {
			for (Code eventCode : eventCodes) {
				eventCodesEntry.add(eventCode);
			}
		}
		if (formatCode != null) {
			documentEntry.setFormatCode(formatCode);
		}
		if (healthcareFacilityTypeCode != null) {
			documentEntry.setHealthcareFacilityTypeCode(healthcareFacilityTypeCode);
		}
		if (languageCode != null) {
			documentEntry.setLanguageCode(languageCode);
		}
		if (mimeType != null) {
			documentEntry.setMimeType(mimeType);
		}
		documentEntry.setType(DocumentEntryType.STABLE);
		if (title != null) {
			documentEntry.setTitle(new LocalizedString(title));
		}
		if (typeCode != null) {
			documentEntry.setTypeCode(typeCode);
		}
		if (practiceSettingCode != null) {
			documentEntry.setPracticeSettingCode(practiceSettingCode);
		}
		return documentEntry;
	}

	public SubmissionSet createSubmissionSet(Identifiable patientIdentifiable, Code contentTypeCode, Date submissionTime) {
		String submissionSetUuid = generateId();
		return createSubmissionSet(patientIdentifiable, contentTypeCode, submissionTime, submissionSetUuid, submissionSetUuid, submissionSetUuid);
	}
	public SubmissionSet createSubmissionSet(Identifiable patientIdentifiable, Code contentTypeCode, DateTime submissionTime) {
		String submissionSetUuid = generateId();
		String submissionTimeString = (submissionTime == null ) ? null : submissionTime.toString("yyyyMMddHHmmss");
		return createSubmissionSet(patientIdentifiable, contentTypeCode, submissionTimeString, submissionSetUuid, submissionSetUuid, submissionSetUuid);
	}
	
	public SubmissionSet createSubmissionSet(Identifiable patientIdentifiable, Code contentTypeCode, Date submissionTime, String entryUuid, String uniqueId, String sourceId) {
		String submissionTimeString = (submissionTime == null ) ? null : dateTimeFormat.format(submissionTime);
		return createSubmissionSet(patientIdentifiable, contentTypeCode, submissionTimeString, entryUuid, uniqueId, sourceId);
	}
	
	public SubmissionSet createSubmissionSet(Identifiable patientIdentifiable, Code contentTypeCode, String submissionTime, String entryUuid, String uniqueId, String sourceId) {
		SubmissionSet submissionSet = new SubmissionSet();
		submissionSet.setUniqueId(uniqueId);
		submissionSet.setSourceId(sourceId);
		submissionSet.setEntryUuid(entryUuid);
		submissionSet.setPatientId(patientIdentifiable);
		submissionSet.setContentTypeCode(contentTypeCode);
		submissionSet.setAvailabilityStatus(AvailabilityStatus.APPROVED);

		if (submissionTime != null) {
			submissionSet.setSubmissionTime(submissionTime);
		}
		return submissionSet;
	}

	
	public static DocumentMetadata createAftaleMetadata(String patientCpr, Date creationDate) {
		DocumentMetadata documentMetadata = new DocumentMetadata();
		documentMetadata.setTitle("Dato og tidspunkt for møde mellem patient og sundhedsperson");
		documentMetadata.setPatientId(new Code(patientCpr, new LocalizedString(Codes.CPR_AUTHORITYNAME), Codes.CPR_OID));
		documentMetadata.setReportTime(creationDate);
		documentMetadata.setOrganisation(new Code("123456789", new LocalizedString("Testafdeling iti-42"), Codes.SOR_OID));
		documentMetadata.setClassCode(new Code("001", new LocalizedString("Klinisk rapport"), "1.2.208.184.100.9"));
		documentMetadata.setFormatCode(Codes.FORMAT_CODE_AFTALEDOKUMENT);
		documentMetadata.setHealthcareFacilityTypeCode(new Code("22232009", new LocalizedString("hospital"), "2.16.840.1.113883.6.96"));
		documentMetadata.setPracticeSettingCode(new Code("408443003", new LocalizedString("almen medicin"), "2.16.840.1.113883.6.96"));
		documentMetadata.setSubmissionTime(creationDate);
		documentMetadata.setContentTypeCode(Codes.TYPE_CODE_AFTALEDOKUMENT);
		documentMetadata.setTypeCode(Codes.TYPE_CODE_AFTALEDOKUMENT);
		documentMetadata.setMimeType("text/xml");
		documentMetadata.setLanguageCode("da-DK");
		documentMetadata.setConfidentialityCode(new Code("N", new LocalizedString("N"), "2.16.840.1.113883.5.25"));
		return documentMetadata;
	}
}
