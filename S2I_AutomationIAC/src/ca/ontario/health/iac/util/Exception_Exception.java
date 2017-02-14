
package ca.ontario.health.iac.util;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "Exception", targetNamespace = "http://util.iac.health.ontario.ca/")
public class Exception_Exception
    extends java.lang.Exception
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3885824994804043820L;
	/**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private ca.ontario.health.iac.util.Exception faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public Exception_Exception(String message, ca.ontario.health.iac.util.Exception faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public Exception_Exception(String message, ca.ontario.health.iac.util.Exception faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: ca.ontario.health.iac.util.Exception
     */
    public ca.ontario.health.iac.util.Exception getFaultInfo() {
        return faultInfo;
    }

}