package redirector;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Webhook extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public Properties sysProp;
	protected final Log actionLogger;
	/**
     * @see HttpServlet#HttpServlet()
     */
    public Webhook() {
        super();
		this.actionLogger = LogFactory.getLog("ACTION");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execMain(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execMain(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void execMain(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {

			// URLキーの抽出
			String uri = request.getRequestURI();

			this.actionLogger.error("REDIRECTOR LP uri : [" + uri + "] ");

			// split url
			String[] arrUri = uri.split("/");
			//if( !"LineWebhook".equals(arrUri[1]) ) {
			//	getServletContext().getRequestDispatcher("/error.htm").forward(request, response);
			//	return;
			//}

			// get bot basic id
			String BASIC_ID = arrUri[2];

			// get page name
			//String fileName = arrUri[4];

			request.setAttribute("BASIC_ID", BASIC_ID);

			getServletContext().getRequestDispatcher("/Webhook.htm").forward(request, response);
			return;

		} catch (Exception e) {
			e.printStackTrace();
			getServletContext().getRequestDispatcher("/error.htm").forward(request, response);
		}
	}

	// リクエスト引数取得
	private String getParam(HttpServletRequest req, String str) {
		String retValue = "";
		String[] array = req.getParameterValues(str);
		if ( array == null  ) {
			retValue = "";

		} else if ( array != null && array.length > 1 ) {
			String buf = new String();
			for (String s: array) {
				if ( buf.length() > 0 ) {
					buf += ",";
				}
				buf += s;
			}
			retValue = buf.toString();

		} else {
			retValue = array[0];
		}
		return retValue;
	}
	private int getParamInt(HttpServletRequest req, String str) {
		int retValue = 0;
		try {
			String[] array = req.getParameterValues(str);
			retValue = Integer.parseInt(array[0]);
		} catch (Exception e) {}
		return retValue;
	}

}

