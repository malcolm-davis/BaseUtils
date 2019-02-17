package base.utils.csv;

import java.io.StringReader;
import java.util.Map;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import base.utils.*;

/**
 * FormatForCsv flattens out structures like XML text to single line for csv.
 *
 * Note: Code copied from https://github.com/elasolutions/elaUtils.
 * elaUtils has FOSS Apache Licensing.
 *
 * @author Malcolm G. Davis
 * @version 1.0
 */
public class FormatForCsv2 {

    /**
     * Formats a map for logging.
     *
     * @param map a {@link java.util.Map} object.
     * @return a {@link java.lang.String} object.
     */
    public static String formatMap(Map<String, Object> map) {
        StringBuilder str = new StringBuilder();
        formatMapAppendBuilder(map, str);
        return str.toString();
    }

    /**
     * Formats a map for logging and appending it to a StringBuilder.
     *
     * @param map a {@link java.util.Map} object.
     * @param str
     * void
     */
    public static void formatMapAppendBuilder(Map<String, Object> map,
            StringBuilder str) {
        if (map == null) {
            str.append("null=null");
            return ;
        }
        int cnt = 0;
        for (String key : map.keySet()) {
            if (cnt > 0) {
                str.append(", ");
            }
            str.append(key + "=" + map.get(key));
            cnt++;
        }
    }

    /**
     * Formats XML document for csv by flattening out the XML structure.
     *
     * @param xml a {@link java.lang.String} object.
     * @throws javax.xml.stream.XMLStreamException
     * String
     * @return a {@link java.lang.String} object.
     */
    public static CsvResult formatXml(String xml) throws XMLStreamException {
        if (xml == null) {
            return CsvResult.newResults();
        }

        if (InternalString.isBlank(xml)) {
            return CsvResult.newResults();
        }

        return formatXmlAppendToBuilder(xml);
    }


    /**
     * Formats XML document for logging by flattening out the XML structure and appending it to a StringBuilder.
     *
     * @param xml a {@link java.lang.String} object.
     * @param output a {@link java.lang.StringBuilder} object.
     * @throws javax.xml.stream.FactoryConfigurationError if any.
     * @throws javax.xml.stream.XMLStreamException
     * void
     */
    public static CsvResult formatXmlAppendToBuilder(String xml) throws FactoryConfigurationError, XMLStreamException {

        StringReader xmlReader = new StringReader(xml);

        XMLInputFactory factory = XMLInputFactory.newInstance();

        factory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, Boolean.FALSE);

        XMLStreamReader reader = factory.createXMLStreamReader(xmlReader);

        CsvResult results = CsvResult.newResults();

        // Map<String,String>values = new
        String lastTag = null;
        int count = 0;
        int startEnd = 0;
        while (reader.hasNext()) {
            int event = reader.next();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    if (lastTag != null && lastTag.equals(reader.getLocalName())) {
                        // This corresponds to list of items
                        // Example: Contacts=[contact=5173359900, contact=5173359922]
                    }

                    lastTag = reader.getLocalName();
                    // System.out.println("START_ELEMENT:"+lastTag +"-"+startEnd);
                    if (startEnd > 0) {
                        results = results.addChild(CsvResult.newResults());
                        results.setName(lastTag);
                        // output.append("[");
                        startEnd = 0;
                    } else if (count > 0) {
                        // output.append(", ");
                    }

                    if(reader.getAttributeCount()>0) {
                        for( int cnt=0; cnt<reader.getAttributeCount(); cnt++) {
                            String attribute = reader.getAttributeName(cnt).toString();
                            System.out.println("\t"+attribute +"="+reader.getAttributeValue(cnt));
                            results.addColumnValue(attribute, reader.getAttributeValue(cnt));
                        }
                    }

                    // output.append(lastTag + "=");
                    count++;
                    startEnd++;
                    break;

                case XMLStreamConstants.CHARACTERS:
                    System.out.println(lastTag + "="+reader.getText().trim());

                    String column = lastTag;
                    if(!results.isRoot()) {
                        column = results.getParent().getName()+":"+lastTag;
                    }
                    System.out.println("\t"+column+ "="+reader.getText().trim());
                    results.addColumnValue(column, reader.getText().trim());
                    // output.append(reader.getText().trim());
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    if (lastTag != null && !lastTag.equals(reader.getLocalName())) {
                        // output.append("]");
                        results = results.getParent();
                    } else {
                        startEnd--;
                    }
                    break;

                case XMLStreamConstants.START_DOCUMENT:
                    break;

                default:
                    break;

            }
        }
        return results;
    }

}
