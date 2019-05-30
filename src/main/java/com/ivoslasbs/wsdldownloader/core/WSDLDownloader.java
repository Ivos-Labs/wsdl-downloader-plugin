
package com.ivoslasbs.wsdldownloader.core;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * @author www.ivoslabs.com
 *
 */
public class WSDLDownloader {

    /** The constant http://www.w3.org/2001/XMLSchema */
    private static final String SCHEMA = "http://www.w3.org/2001/XMLSchema";

    /** The constant schemaLocation */
    private static final String SCHEMA_LOCATION = "schemaLocation";

    /** The constant http://schemas.xmlsoap.org/wsdl/ */
    private static final String WSDL = "http://schemas.xmlsoap.org/wsdl/";

    /** The constant location */
    private static final String WSDL_LOCATION = "location";

    /** The constant import */
    private static final String IMPORT = "import";

    /** The constant include */
    private static final String INCLUDE = "include";

    /** The wsdl */
    private String wsdl;

    /** The prefix */
    private String prefix;

    /** The path */
    private File path;

    /** Th xsds */
    private final Map<String, String> xsds = new HashMap<String, String>();

    /**
     * Create a WSDLDownloader
     * 
     * @param wsdl   WSDL url to download
     * @param prefix String to use as prefix in the downloaded files
     * @param path   directory where will saved the downloaded files
     */
    public WSDLDownloader(String wsdl, String prefix, String path) {
	this.wsdl = wsdl;
	this.prefix = prefix;
	this.path = new File(path);
    }

    /**
     * download wsdl and their resources
     */
    public void download() {
	downloadXML(this.wsdl, Boolean.TRUE);
    }

    /**
     * download a file and their resources
     * 
     * @param uri    file url to download
     * @param isWsdl indicates if is wsdl
     */
    private void downloadXML(String uri, boolean isWsdl) {

	try {

	    String fileName;

	    if (isWsdl) {
		fileName = this.prefix + ".wsdl";
	    } else {
		fileName = this.prefix + this.getNameByParams(uri) + ".xsd";
	    }

	    this.xsds.put(uri, fileName);

	    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	    documentBuilderFactory.setNamespaceAware(Boolean.TRUE);

	    Document document = documentBuilderFactory.newDocumentBuilder().parse(uri);

	    processChildElements(document.getDocumentElement());

	    Transformer transformer = TransformerFactory.newInstance().newTransformer();

	    transformer.transform(new DOMSource(document), new StreamResult(new File(this.path, fileName)));

	} catch (Exception e) {
	    throw new RuntimeException(e);
	}

    }

    /**
     * Process all child nodes of a Element
     * 
     * @param element Element to process
     */
    private void processChildElements(Element element) {

	NodeList nodeList = element.getChildNodes();

	//
	IntFunction<Node> toNode = nodeList::item;
	//
	Predicate<Node> isElement = Element.class::isInstance;
	//
	Function<Node, Element> toElement = Element.class::cast;
	//
	IntStream.range(0, nodeList.getLength()).mapToObj(toNode).filter(isElement).map(toElement).forEach(this::processElement);

    }

    /**
     * Process an element
     * 
     * @param element Element to process
     */
    private void processElement(Element element) {

	if (element.getNamespaceURI() != null) {
	    if (element.getNamespaceURI().equals(SCHEMA) && element.getLocalName().equals(IMPORT)) {
		this.updateElement(element, SCHEMA_LOCATION);
	    }
	    if (element.getNamespaceURI().equals(SCHEMA) && element.getLocalName().equals(INCLUDE)) {
		this.updateElement(element, SCHEMA_LOCATION);
	    } else if (element.getNamespaceURI().equals(WSDL) && element.getLocalName().equals(IMPORT)) {
		this.updateElement(element, WSDL_LOCATION);
	    } else {
		processChildElements(element);
	    }
	}

    }

    /**
     * Update remote urls to local urls in an element
     * 
     * @param element       Element ot update
     * @param attributeName attribute name with url to download
     */
    private void updateElement(Element element, String attributeName) {
	String uri = element.getAttribute(attributeName);
	if (!this.xsds.containsKey(uri)) {
	    this.downloadXML(uri, Boolean.FALSE);
	}
	// get local name generated at downloadXML
	element.setAttribute(attributeName, this.xsds.get(uri));
    }

    /**
     * Build a string using first param and value from a url
     * 
     * @param url
     * @return builded String
     */
    private String getNameByParams(String url) {

	String name = null;
	String query[] = url.split("\\?");

	if (query.length > 1) {
	    String auxUrl[] = query[1].split("=");
	    if (auxUrl.length > 1) {
		name = "_" + auxUrl[0] + "_" + auxUrl[1];
	    }
	}

	return name;
    }

}
