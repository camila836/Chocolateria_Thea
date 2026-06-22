package Servlets;



import Controlador.UsuariosDAO;
import Modelo.Usuarios;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/InicioSesion")
public class InicioSesion extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String correo = request.getParameter("Correo");
        String clave  = request.getParameter("clave");

        if (correo == null || correo.trim().isEmpty()
                || clave == null || clave.trim().isEmpty()) {
            request.setAttribute("error", "Por favor completa todos los campos.");
            request.getRequestDispatcher("InicioSesion.jsp").forward(request, response);
            return;
        }

        UsuariosDAO dao = new UsuariosDAO();
        String nombreUsuario = dao.validarLogin(correo.trim(), clave);

        if (nombreUsuario != null) {
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogueado", correo.trim());
            session.setAttribute("nombreUsuario",   nombreUsuario);
            session.setMaxInactiveInterval(1800);
            response.sendRedirect("index.jsp");
        } else {
            request.setAttribute("error", "Correo o contraseña incorrectos. Inténtalo de nuevo.");
            request.getRequestDispatcher("InicioSesion.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("InicioSesion.jsp");
    }
}
