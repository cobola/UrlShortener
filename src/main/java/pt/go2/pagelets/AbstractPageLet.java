/**
 * 
 */
package pt.go2.pagelets;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;

/**
 * @author vilaca
 * 
 */
public abstract class AbstractPageLet {

	abstract byte[] main(final HttpExchange exchange) throws IOException;

	abstract public int getResponseCode();

	abstract public String getMimeType();

	private int responseSize;
	
	final public boolean execute(final HttpExchange exchange)
			throws IOException {

		final byte[] buffer = main(exchange);

		if (buffer == null)
			return false;

		final OutputStream os = exchange.getResponseBody();

		exchange.getResponseHeaders().set("Content-Type", getMimeType());
		exchange.sendResponseHeaders(getResponseCode(), buffer.length);

		responseSize = buffer.length;
		
		os.write(buffer);
		os.flush();
		os.close();

		return true;
	}

	public int getResponseSize() {
		return responseSize;
	}

}