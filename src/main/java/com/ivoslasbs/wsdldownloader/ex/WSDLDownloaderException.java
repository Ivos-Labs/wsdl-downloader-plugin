/**
 * 
 */
package com.ivoslasbs.wsdldownloader.ex;

/**
 * Exceptions that can be thrown during the normal operation
 * 
 * @since 1.0.0
 * @author imperezivan
 *
 */
public class WSDLDownloaderException extends RuntimeException {

    /** The serialVersionUID */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new runtime exception with the specified cause and a detail message of <tt>(cause==null ? null : cause.toString())</tt> (which typically contains the class and detail message of <tt>cause</tt>). This constructor is useful for runtime exceptions that are little more than wrappers for other throwables.
     *
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt> value is permitted, and indicates that the cause is nonexistent or unknown.)
     * @since 1.0.0
     * @author imperezivan
     */
    public WSDLDownloaderException(Throwable cause) {
        super(cause);
    }
}
