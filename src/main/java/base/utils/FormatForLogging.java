package base.utils;

import java.io.StringReader;
import java.util.Map;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * FormatForLogging flattens out structures like XML text to single line for logging.
 *
 * Note: Code copied from https://github.com/elasolutions/elaUtils.
 * elaUtils has FOSS Apache Licensing.
 *
 * @author Malcolm G. Davis
 * @version 1.0
 */
public class FormatForLogging {

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
     * Formats XML document for logging by flattening out the XML structure.
     *
     * @param xml a {@link java.lang.String} object.
     * @throws javax.xml.stream.XMLStreamException
     * String
     * @return a {@link java.lang.String} object.
     */
    public static String formatXml(String xml) throws XMLStreamException {
        if (xml == null) {
            return "[null]";
        }

        if (InternalString.isBlank(xml)) {
            return "[empty]";
        }

        StringBuilder output = new StringBuilder();

        formatXmlAppendToBuilder(xml, output);

        return output.toString();
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
    public static void formatXmlAppendToBuilder(String xml,
            StringBuilder output) throws FactoryConfigurationError,
    XMLStreamException {
        StringReader xmlReader = new StringReader(xml);

        XMLInputFactory factory = XMLInputFactory.newInstance();

        factory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, Boolean.FALSE);

        XMLStreamReader reader = factory.createXMLStreamReader(xmlReader);
        String lastTag = null;
        int count = 0;
        int startEnd = 0;
        while (reader.hasNext()) {
            int event = reader.next();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    lastTag = reader.getLocalName();
                    if (startEnd > 0) {
                        output.append("[");
                        startEnd = 0;
                    } else if (count > 0) {
                        output.append(", ");
                    }
                    output.append(lastTag + "=");
                    count++;
                    startEnd++;
                    break;

                case XMLStreamConstants.CHARACTERS:
                    output.append(reader.getText().trim());
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    if (lastTag != null && !lastTag.equals(reader.getLocalName())) {
                        output.append("]");
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
    }

}
