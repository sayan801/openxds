/**
 * The MIT License
 *
 * Original work sponsored and donated by National Board of e-Health (SDS), Denmark
 * (http://sundhedsdatastyrelsen.dk)
 *
 * Copyright (C) 2016 National Board of e-Health (SDS), Denmark (http://sundhedsdatastyrelsen.dk)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.openhealthtools.openxds.utilities;

import org.openehealth.ipf.commons.ihe.xds.core.metadata.Code;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.PatientInfo;

import java.util.Date;
import java.util.List;

public class DocumentMetadata extends CdaMetadata {

	public Code patientId;

	public Date reportTime;
	
	public Date serviceStartTime;	

	public Date serviceStopTime;	

	public Code organisation;

	public Code typeCode;
	
	public List<Code> eventCodes;
	
	public String languageCode;
	
	public String title;
	
	public Code contentTypeCode;
	
	public String mimeType;
	
	private Code confidentialityCode;
	
	private PatientInfo sourcePatientInfo;
	
	@Override
	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	
	public Code getPatientId() {
		return patientId;
	}

	public void setPatientId(Code patientId) {
		this.patientId = patientId;
	}

	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	public Code getOrganisation() {
		return organisation;
	}

	public void setOrganisation(Code organisation) {
		this.organisation = organisation;
	}

	public List<Code> getEventCodes() {
		return eventCodes;
	}

	public void setEventCodes(List<Code> eventCodes) {
		this.eventCodes = eventCodes;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Code getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(Code typeCode) {
		this.typeCode = typeCode;
	}

	public Code getContentTypeCode() {
		return contentTypeCode;
	}

	public void setContentTypeCode(Code contentTypeCode) {
		this.contentTypeCode = contentTypeCode;
	}

	public Code getConfidentialityCode() {
		return confidentialityCode;
	}

	public void setConfidentialityCode(Code confidentialityCode) {
		this.confidentialityCode = confidentialityCode;
	}
	
	public Date getServiceStartTime() {
		return serviceStartTime;
	}

	public void setServiceStartTime(Date serviceStartTime) {
		this.serviceStartTime = serviceStartTime;
	}

	public void setServiceStopTime(Date serviceStopTime) {
		this.serviceStopTime = serviceStopTime;
	}

	public Date getServiceStopTime() {
		return this.serviceStopTime;
	}

	public PatientInfo getSourcePatientInfo() {
		return sourcePatientInfo;
	}

	public void setSourcePatientInfo(PatientInfo sourcePatientInfo) {
		this.sourcePatientInfo = sourcePatientInfo;
	}

}
