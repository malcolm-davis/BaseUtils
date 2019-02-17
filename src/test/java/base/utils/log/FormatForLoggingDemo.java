package base.utils.log;

import javax.xml.stream.XMLStreamException;

import base.utils.FormatForLogging;

public class FormatForLoggingDemo {

    public static void main(String[] args) {
        System.out.println("Start: FormatForLoggingDemo ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        FormatForLoggingDemo test = new FormatForLoggingDemo();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis() - start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        try {
            System.out.println(FormatForLogging.formatXml(INPUT_XML));
            System.out.println(FormatForLogging.formatXml("<TransactionId>14141414-444a-3g7c-938103284123</TransactionId>"));
        } catch (XMLStreamException excep) {
            excep.printStackTrace();
        }
    }

    static final String INPUT_XML =
            "<AddCustomer>\r\n"
                    + "    <Header>\r\n"
                    + "        <TransactionId>14141414-444a-3g7c-938103284123</TransactionId>\r\n"
                    + "        <TimeStamp>2014-10-05T06:53:30.959-06:00</TimeStamp>\r\n"
                    + "        <FullName>John Adams</FullName>\r\n"
                    + "    </Header>\r\n"
                    + "    <Referral>Agent</Referral>\r\n"
                    + "    <Members>\r\n"
                    + "       <Member><id>1</id><name>Spouse</name></Member>\r\n"
                    + "       <Member><id>2</id><name>Son 1</name></Member>\r\n"
                    + "       <Member><id>3</id><name>Daughter 1</name></Member>\r\n"
                    + "       <Member><id>4</id><name>Daughter 2</name>"
                    + "           <Contacts>"
                    + "              <contact>5173359900</contact><contact>5173359922</contact>"
                    + "           </Contacts>"
                    + "       </Member>\r\n"
                    + "    </Members>\r\n"
                    + "</AddCustomer>";
}
