package base.utils.csv;

import javax.xml.stream.XMLStreamException;

import base.utils.csv.CsvResult;
import base.utils.csv.FormatForCsv;

public class FormatForCsvDemo {


    public static void main(String[] args) {
        FormatForCsvDemo test = new FormatForCsvDemo();
        test.run();
    }

    private void run() {
        try {
//            CsvResult resultSimple = FormatForCsv.formatXml("<TransactionId>14141414-444a-3g7c-938103284123</TransactionId>");
//            dumpData(resultSimple);
//
//            System.out.println("\r\n---------------------------------------------------------------");
//            CsvResult resultBasic = FormatForCsv.formatXml(BASIC_XML);
//            dumpData(resultBasic);
//
//            System.out.println("\r\n---------------------------------------------------------------");
//            CsvResult resultComplex = FormatForCsv.formatXml(COMPLEX_XML);
//            dumpData(resultComplex);

            System.out.println("\r\n---------------------------------------------------------------");
            dumpData(FormatForCsv.formatXml(XTREME_COMPLEX_XML));

//            System.out.println("\r\n---------------------------------------------------------------");
//            // https://stackoverflow.com/questions/39721630/xml-to-csv-conversion-java
//            dumpData(FormatForCsv.formatXml(SCHOOL_DATA));
//
//            System.out.println("\r\n---------------------------------------------------------------");
//            dumpData(FormatForCsv.formatXml(POSITION_XML));


        } catch (XMLStreamException excep) {
            excep.printStackTrace();
        }
    }

    private static final String COLUMN_FORMAT = "%-25.25s";

    static void dumpData(CsvResult resultSimple) {
        System.out.println(String.format("%-20.20s", resultSimple.getName()));
        String[]headers = resultSimple.getHeaders();
        for(String header : headers) {
            System.out.print(String.format(COLUMN_FORMAT, header));
            System.out.print("  ");
        }
        System.out.println("");


        for (int rowCount = 0; rowCount < resultSimple.size(); rowCount++) {
            for(String header : headers) {
                System.out.print(String.format(COLUMN_FORMAT, resultSimple.getColumn(rowCount, header)));
                System.out.print("  ");
            }
            System.out.println("");
        }
    }

    static final String BASIC_XML =
            "<Members>\r\n" +
                    "    <Member>\r\n" +
                    "        <id>1</id>\r\n" +
                    "        <name>Spouse</name>\r\n" +
                    "    </Member>\r\n" +
                    "    <Member>\r\n" +
                    "        <id>2</id>\r\n" +
                    "        <name>Son 1</name>\r\n" +
                    "    </Member>\r\n" +
                    "    <Member>\r\n" +
                    "        <id>3</id>\r\n" +
                    "        <name>Daughter 1</name>\r\n" +
                    "    </Member>\r\n" +
                    "    <Member>\r\n" +
                    "        <id>4</id>\r\n" +
                    "        <name>Daughter 2</name>\r\n" +
                    "    </Member>\r\n" +
                    "</Members>\r\n";

    static final String COMPLEX_XML =
            "<Members>\r\n" +
                    "    <Member>\r\n" +
                    "        <id>1</id>\r\n" +
                    "        <name>Spouse</name>\r\n" +
                    "            <Contacts>\r\n" +
                    "                <contact>5173359900</contact>\r\n" +
                    "                <contact>5173359922</contact>\r\n"+
                    "           </Contacts>\r\n"+
                    "    </Member>\r\n" +
                    "    <Member>\r\n" +
                    "        <id>2</id>\r\n" +
                    "        <name>Son 1</name>\r\n" +
                    "    </Member>\r\n" +
                    "    <Member>\r\n" +
                    "        <id>3</id>\r\n" +
                    "        <name>Daughter 1</name>\r\n" +
                    "    </Member>\r\n" +
                    "    <Member>\r\n" +
                    "        <id>4</id>\r\n" +
                    "        <name>Daughter 2</name>\r\n" +
                    "    </Member>\r\n" +
                    "</Members>\r\n";

    static final String XTREME_COMPLEX_XML =
            "<AddCustomer active='true'>\r\n"
                    + "    <Header Referral='Agent'>\r\n"
                    + "        <TransactionId>14141414-444a-3g7c-938103284123</TransactionId>\r\n"
                    + "        <TimeStamp>2014-10-05T06:53:30.959-06:00</TimeStamp>\r\n"
                    + "        <FullName>John Adams</FullName>\r\n"
                    + "    </Header>\r\n"
                    + "    <Members>\r\n"
                    + "        <Member><id>1</id><name>Spouse</name></Member>\r\n"
                    + "        <Member><id>2</id><name>Son 1</name></Member>\r\n"
                    + "        <Member><id>3</id><name>Daughter 1</name></Member>\r\n"
                    + "        <Member><id>4</id><name>Daughter 2</name>"
                    + "           <Contacts>"
                    + "              <contact>5173359900</contact><contact>5173359922</contact>"
                    + "           </Contacts>"
                    + "        </Member>\r\n"
                    + "    </Members>\r\n"
                    + "</AddCustomer>";

    static final String SCHOOL_DATA = "<?xml version=\"1.0\"?>\r\n" +
            "<school id=\"100\" name=\"WGen School\">\r\n" +
            "\r\n" +
            "    <grade id=\"1\">\r\n" +
            "        <classroom id=\"101\" name=\"Mrs. Jones' Math Class\">\r\n" +
            "            <teachers>"+
            "               <teacher id=\"10100000001\" first_name=\"Barbara\" last_name=\"Jones\"/>\r\n" +
            "            </teachers>"+
            "            <students>"+
            "            <student id=\"10100000010\" first_name=\"Michael\" last_name=\"Gil\"/>\r\n" +
            "            <student id=\"10100000011\" first_name=\"Kimberly\" last_name=\"Gutierrez\"/>\r\n" +
            "            <student id=\"10100000013\" first_name=\"Toby\" last_name=\"Mercado\"/>\r\n" +
            "            <student id=\"10100000014\" first_name=\"Lizzie\" last_name=\"Garcia\"/>\r\n" +
            "            <student id=\"10100000015\" first_name=\"Alex\" last_name=\"Cruz\"/>\r\n" +
            "            </students>"+
            "        </classroom>\r\n" +
            "\r\n" +
            "\r\n" +
            "        <classroom id=\"102\" name=\"Mr. Smith's PhysEd Class\">\r\n" +
            "            <teachers>"+
            "            <teacher id=\"10200000001\" first_name=\"Arthur\" last_name=\"Smith\"/>\r\n" +
            "            <teacher id=\"10200000011\" first_name=\"John\" last_name=\"Patterson\"/>\r\n" +
            "            </teachers>"+
            "            <students>"+
            "            <student id=\"10200000010\" first_name=\"Nathaniel\" last_name=\"Smith\"/>\r\n" +
            "            <student id=\"10200000011\" first_name=\"Brandon\" last_name=\"McCrancy\"/>\r\n" +
            "            <student id=\"10200000012\" first_name=\"Elizabeth\" last_name=\"Marco\"/>\r\n" +
            "            <student id=\"10200000013\" first_name=\"Erica\" last_name=\"Lanni\"/>\r\n" +
            "            <student id=\"10200000014\" first_name=\"Michael\" last_name=\"Flores\"/>\r\n" +
            "            <student id=\"10200000015\" first_name=\"Jasmin\" last_name=\"Hill\"/>\r\n" +
            "            <student id=\"10200000016\" first_name=\"Brittany\" last_name=\"Perez\"/>\r\n" +
            "            <student id=\"10200000017\" first_name=\"William\" last_name=\"Hiram\"/>\r\n" +
            "            <student id=\"10200000018\" first_name=\"Alexis\" last_name=\"Reginald\"/>\r\n" +
            "            <student id=\"10200000019\" first_name=\"Matthew\" last_name=\"Gayle\"/>\r\n" +
            "            </students>"+
            "        </classroom>\r\n" +
            "\r\n" +
            "        <classroom id=\"103\" name=\"Brian's Homeroom\">\r\n" +
            "            <teachers>"+
            "            <teacher id=\"10300000001\" first_name=\"Brian\" last_name=\"O'Donnell\"/>\r\n" +
            "            </teachers>"+
            "        </classroom>\r\n" +
            "    </grade>\r\n" +
            "</school>";

    private static final String POSITION_XML = "            <Position>\r\n" +
            "                <recordingMode>ALWAYS</recordingMode>\r\n" +
            "                <positionNumber>10</positionNumber>\r\n" +
            "                <description>Detroit Ten</description>\r\n" +
            "                <extension>110</extension>\r\n" +
            "                <defaultUser>DETROIT-10</defaultUser>\r\n" +
            "                <Workstation>\r\n" +
            "                    <location>detroit</location>\r\n" +
            "                    <ipAddress>10.0.1.10</ipAddress>\r\n" +
            "                    <macAddress>48:4d:7e:ef:f2:a6</macAddress>\r\n" +
            "                    <SwitchPorts>\r\n" +
            "                        <SwitchPort>\r\n" +
            "                            <networkSwitch>switch-detroit-2</networkSwitch>\r\n" +
            "                            <port>9</port>\r\n" +
            "                        </SwitchPort>\r\n" +
            "                    </SwitchPorts>\r\n" +
            "                </Workstation>\r\n" +
            "                <Phone callWaiting=\"false\" dnd=\"false\" allowTransfer=\"true\">\r\n" +
            "                    <location>detroit</location>\r\n" +
            "                    <ipAddress>10.0.1.110</ipAddress>\r\n" +
            "                    <macAddress>64:16:7f:90:e9:43</macAddress>\r\n" +
            "                    <SwitchPorts>\r\n" +
            "                        <SwitchPort>\r\n" +
            "                            <networkSwitch>switch-detroit-2</networkSwitch>\r\n" +
            "                            <port>10</port>\r\n" +
            "                        </SwitchPort>\r\n" +
            "                    </SwitchPorts>\r\n" +
            "                    <type>POLYCOM_VVX_410</type>\r\n" +
            "                    <headsetMode>GENERIC</headsetMode>\r\n" +
            "                    <DIDs>\r\n" +
            "                        <DID>\r\n" +
            "                            <number>5550110</number>\r\n" +
            "                        </DID>\r\n" +
            "                    </DIDs>\r\n" +
            "                    <ringToneGroup>detroit</ringToneGroup>\r\n" +
            "                    <AIUInput>\r\n" +
            "                        <aiu>aiu-detroit-10</aiu>\r\n" +
            "                    </AIUInput>\r\n" +
            "                </Phone>\r\n" +
            "            </Position>";
}
