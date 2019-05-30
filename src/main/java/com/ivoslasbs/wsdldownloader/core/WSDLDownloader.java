
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

    /** */
    private static final String SCHEMA = "http://www.w3.org/2001/XMLSchema";

    /** */
    private static final String SCHEMA_LOCATION = "schemaLocation";

    /** */
    private static final String WSDL = "http://schemas.xmlsoap.org/wsdl/";

    /** */
    private static final String WSDL_LOCATION = "location";

    /** */
    private static final String IMPORT = "import";

    /** */
    private static final String INCLUDE = "include";

    /** */
    private String wsdl;

    /** */
    private String xsdPrefix;

    /** */
    private File path;

    /** */
    private final Map<String, String> xsds = new HashMap<String, String>();

   /**
    * 
    * @param wsdl
    * @param xsdPrefix
    * @param path
    */
    public WSDLDownloader(String wsdl, String xsdPrefix, String path) {
	this.wsdl = wsdl;
	this.xsdPrefix = xsdPrefix;
	this.path = new File(path);
    }

    /**
     * 
     */
    public void download() {
	downloadXML(this.wsdl, Boolean.TRUE);
    }

    /**
     * 
     * @param uri
     * @param isWsdl
     */
    private void downloadXML(String uri, boolean isWsdl) {

	try {

	    String fileName;

	    if (isWsdl) {
		fileName = this.xsdPrefix + ".wsdl";
	    } else {
		fileName = this.xsdPrefix + this.getNameByParams(uri) + ".xsd";
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
     * 
     * @param node
     */
    private void processChildElements(final Element node) {

	NodeList nodeList = node.getChildNodes();

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
     * 
     * @param element
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
     * 
     * @param element
     * @param attributeName
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
     * 
     * @param url
     * @return
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
