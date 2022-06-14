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

import org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml30.RetrieveDocumentSetRequestType;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.activation.DataHandler;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class RetrieveDocumentSetUtilities {


	public RetrieveDocumentSetRequestType createRetrieveDocumentSetRequestType(String documentId, String homeCommunityId, String repositoryId) {
		List<String> documentIds = new LinkedList<>();
		documentIds.add(documentId);
		return createRetrieveDocumentSetRequestType(documentIds, homeCommunityId, repositoryId);
	}

	public RetrieveDocumentSetRequestType createRetrieveDocumentSetRequestType(List<String> documentIds, String homeCommunityId, String repositoryId) {
		RetrieveDocumentSetRequestType retrieveDocumentSetRequestType = new RetrieveDocumentSetRequestType();

		for (Iterator<String> iterator = documentIds.iterator(); iterator.hasNext();) {
			RetrieveDocumentSetRequestType.DocumentRequest documentRequest = new RetrieveDocumentSetRequestType.DocumentRequest();
			documentRequest.setRepositoryUniqueId(repositoryId);
			documentRequest.setHomeCommunityId(homeCommunityId);
			documentRequest.setDocumentUniqueId(iterator.next());
			retrieveDocumentSetRequestType.getDocumentRequest().add(documentRequest);
		}
		return retrieveDocumentSetRequestType;
	}

	public Document getXmlDocumentFromDataHandler(DataHandler documentDataHandler) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		builder = factory.newDocumentBuilder();
		Document doc = builder.parse(new InputSource(documentDataHandler.getInputStream()));
		return doc;
	}

	public String getDocumentFromDataHandler(DataHandler documentDataHandler) throws IOException {

		InputStream inputStream = documentDataHandler.getInputStream();
		StringBuilder textBuilder = new StringBuilder();
		try (Reader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			int c = 0;
			while ((c = reader.read()) != -1) {
				textBuilder.append((char) c);
			}
		}
		return textBuilder.toString();
	}
}
