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
import org.openehealth.ipf.commons.ihe.xds.core.metadata.LocalizedString;

public class Codes {

	public static final String SOR_AUTHORITYNAME = "SOR";
	public static final String SOR_OID = "1.2.208.176.1.1";

	public static final String CPR_AUTHORITYNAME = "CPR";
	public static final String CPR_OID = "1.2.208.176.1.2";

	public static final String LOINC_OID = "2.16.840.1.113883.6.1";

	public static final String DK_APD_ROOT_OID = "1.2.208.184.100.10";
	
	public static final String DK_LABSVAR_ROOT_OID =  "1.2.208.184.100.10";

	public static final String LOINC_APD_CODE = "39289-4";
	public static final String LOINC_APD_DISPLAYNAME = "Follow-up (referred to) provider &or specialist, appointment date";

	private static final String LOINC_REP_APD_CODE = "39290-2";
	private static final String LOINC_REP_APD_DISPLAYNAME = "Repeterende dato og tidspunkt for møde mellem patient og sundhedsperson";

	private static final String LOINC_PHMR_CODE = "53576-5";
	private static final String LOINC_PHMR_DISPLAYNAME = "Personal Health Monitoring Report";

	private static final String LOINC_STAMKORT_CODE = "52460-3";
	private static final String LOINC_STAMKORT_DISPLAYNAME = "Patient Information";

	public static final String LABSVAR_DISPLAY = "LABORATORY REPORT.TOTAL";
	public static final String LABSVAR_CODE = "11502-2";
	public static final String LABSVAR_SCHEME = "2.16.840.1.113883.6.1";

	
	public static final Code TYPE_CODE_AFTALEDOKUMENT = new Code(LOINC_APD_CODE, new LocalizedString(LOINC_APD_DISPLAYNAME), LOINC_OID);
	public static final Code TYPE_CODE_REPETERENDE_AFTALEDOKUMENT = new Code(LOINC_REP_APD_CODE, new LocalizedString(LOINC_REP_APD_DISPLAYNAME), LOINC_OID);
	public static final Code TYPE_CODE_PHMRDOKUMENT = new Code(LOINC_PHMR_CODE, new LocalizedString(LOINC_PHMR_DISPLAYNAME), LOINC_OID);
	public static final Code TYPE_CODE_STAMKORT = new Code(LOINC_STAMKORT_CODE, new LocalizedString(LOINC_STAMKORT_DISPLAYNAME), LOINC_OID);
	public static final Code TYPE_CODE_LABSVAR = new Code(LABSVAR_CODE, new LocalizedString(LABSVAR_DISPLAY), LABSVAR_SCHEME);
	
	
	public static final Code FORMAT_CODE_STAMKORT = new Code("DK FSK Schema", new LocalizedString("DK FSK Schema"), "urn:ad:dk:medcom:fsk:full");
	public static final Code FORMAT_CODE_AFTALEDOKUMENT = new Code("urn:ad:dk:medcom:appointmentsummary:full", new LocalizedString("DK Appointment Summary Document schema"), Codes.DK_APD_ROOT_OID);
	public static final Code FORMAT_CODE_LABSVAR = new Code("urn:ad:dk:medcom:labreports:svareksponeringsservice", new LocalizedString("Laboratoriesvar (samling af svar)"), Codes.DK_LABSVAR_ROOT_OID);

}
