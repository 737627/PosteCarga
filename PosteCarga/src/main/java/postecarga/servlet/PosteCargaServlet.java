package postecarga.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/PosteCargaServlet")
public class PosteCargaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor del servlet PosteCargaServlet.
     */
    public PosteCargaServlet() {
        super();
    }

    /**
     * Maneja el método HTTP <code>GET</code>.
     * @param request solicitud del servlet
     * @param response respuesta del servlet
     * @throws ServletException si ocurre un error específico del servlet
     * @throws IOException si ocurre un error de E/S
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Maneja el método HTTP <code>POST</code>.
     * @param request solicitud del servlet
     * @param response respuesta del servlet
     * @throws ServletException si ocurre un error específico del servlet
     * @throws IOException si ocurre un error de E/S
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Procesa las solicitudes para los métodos HTTP <code>GET</code> y <code>POST</code>.
     * @param request solicitud del servlet
     * @param response respuesta del servlet
     * @throws ServletException si ocurre un error específico del servlet
     * @throws IOException si ocurre un error de E/S
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PosteCargaServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PosteCargaServlet en " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Devuelve una breve descripción del servlet.
     * @return una cadena de texto con la descripción del servlet
     */
    @Override
    public String getServletInfo() {
        return "Breve descripción";
    }
}
