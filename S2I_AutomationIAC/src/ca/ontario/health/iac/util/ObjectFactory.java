
package ca.ontario.health.iac.util;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ca.ontario.health.iac.util package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Exception_QNAME = new QName("http://util.iac.health.ontario.ca/", "Exception");
    private final static QName _CleanHealthNumber_QNAME = new QName("http://util.iac.health.ontario.ca/", "CleanHealthNumber");
    private final static QName _CleanHealthNumberResponse_QNAME = new QName("http://util.iac.health.ontario.ca/", "CleanHealthNumberResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ca.ontario.health.iac.util
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CleanHealthNumber }
     * 
     */
    public CleanHealthNumber createCleanHealthNumber() {
        return new CleanHealthNumber();
    }

    /**
     * Create an instance of {@link CleanHealthNumberResponse }
     * 
     */
    public CleanHealthNumberResponse createCleanHealthNumberResponse() {
        return new CleanHealthNumberResponse();
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://util.iac.health.ontario.ca/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CleanHealthNumber }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://util.iac.health.ontario.ca/", name = "CleanHealthNumber")
    public JAXBElement<CleanHealthNumber> createCleanHealthNumber(CleanHealthNumber value) {
        return new JAXBElement<CleanHealthNumber>(_CleanHealthNumber_QNAME, CleanHealthNumber.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CleanHealthNumberResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://util.iac.health.ontario.ca/", name = "CleanHealthNumberResponse")
    public JAXBElement<CleanHealthNumberResponse> createCleanHealthNumberResponse(CleanHealthNumberResponse value) {
        return new JAXBElement<CleanHealthNumberResponse>(_CleanHealthNumberResponse_QNAME, CleanHealthNumberResponse.class, null, value);
    }

}
