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

import java.util.Date;

public class CdaMetadata {

	public CdaMetadata(){}
	
	public Code classCode;
	
	public Code formatCode;
	
	public Code healthcareFacilityTypeCode;
	
	public Code practiceSettingCode;

	public Date submissionTime;

	public String getMimeType() {
		return "text/xml";
	}

	public Code getClassCode() {
		return classCode;
	}

	public void setClassCode(Code classCode) {
		this.classCode = classCode;
	}

	public Code getFormatCode() {
		return formatCode;
	}

	public void setFormatCode(Code formatCode) {
		this.formatCode = formatCode;
	}

	public Code getHealthcareFacilityTypeCode() {
		return healthcareFacilityTypeCode;
	}

	public void setHealthcareFacilityTypeCode(Code healthcareFacilityTypeCode) {
		this.healthcareFacilityTypeCode = healthcareFacilityTypeCode;
	}

	public Date getSubmissionTime() {
		return submissionTime;
	}

	public void setSubmissionTime(Date submissionTime) {
		this.submissionTime = submissionTime;
	}
	
	public Code getPracticeSettingCode() {
		return practiceSettingCode;
	}

	public void setPracticeSettingCode(Code practiceSettingCode) {
		this.practiceSettingCode = practiceSettingCode;
	}
}
