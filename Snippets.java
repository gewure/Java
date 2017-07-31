
public class Snippets {
	
/* this can be used to save raw-byte-data into a file. e.g. a PDF you download */
public void rawBinaryDataToFileDownload(String url, String fileToWrite) {
	URL obj = new URL(url);
	HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
	con.setRequestMethod("GET");
	con.setRequestProperty("User-Agent", USER_AGENT);
	//con.setRequestProperty("X-Cookie", "token=" + token);

	InputStream is = con.getInputStream();
	FileOutputStream fos = new FileOutputStream(new File(fileToWrite));  
	int inByte;
	while((inByte = is.read()) != -1)
	     fos.write(inByte);
	is.close();
	fos.close();
	con.disconnect();
	}
	
/**
 * Sets value in osiXml on attribute (localized by tree path) to newValue.
 * 
 * @param osiXml
 *           Xml-File
 * @param treePath
 *            path of leaf (attribute) in XML
 * @param newValue
 *            Value to set on leaf (attribute)
 *            
 * @throws SAXException
 * @throws IOException
 * @throws ParserConfigurationException
 * @throws XPathExpressionException
 * @throws TransformerFactoryConfigurationError
 * @throws TransformerException
 */
private void editXML(File osiXml, String treePath, String newValue)
		throws TEException, ParserConfigurationException, SAXException, IOException, XPathExpressionException, TransformerFactoryConfigurationError, TransformerException {
	DocumentBuilderFactory instance = DocumentBuilderFactory.newInstance();
	instance.setNamespaceAware(true);
	DocumentBuilder builder = instance.newDocumentBuilder();
	Document doc = builder.parse(osiXml);
	XPathFactory instance2 = XPathFactory.newInstance();
	XPath newXPath = instance2.newXPath();
	XPathExpression expr = newXPath.compile(treePath);
	Object evaluate = expr.evaluate(doc, XPathConstants.NODE);
	Node n = (Node) evaluate;
	n.setTextContent(newValue);

	Transformer transformer = TransformerFactory.newInstance().newTransformer();
	transformer.setOutputProperty(OutputKeys.INDENT, "yes");

	// initialize StreamResult with File object to save to file
	StreamResult result = new StreamResult(new StringWriter());
	DOMSource source = new DOMSource(doc);
	transformer.transform(source, result);

	String xmlString = result.getWriter().toString();
	if (osiXml.exists()) {
		osiXml.delete();
		osiXml.createNewFile();
	}
	FileWriter fw = new FileWriter(osiXml);
	fw.write(xmlString);
	fw.close();
}
}
