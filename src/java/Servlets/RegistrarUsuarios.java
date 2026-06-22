package Servlets;

import Controlador.UsuariosDAO;
import Modelo.Usuarios;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/RegistrarUsuarios")
public class RegistrarUsuarios extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String nombre    = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String tipoDoc   = request.getParameter("tipoDoc");
        String documento = request.getParameter("documento");
        String correo    = request.getParameter("correo");
        String clave     = request.getParameter("clave");
        String fechaNac  = request.getParameter("fechaNacimiento");

        if (nombre    == null || nombre.trim().isEmpty()    ||
            apellidos == null || apellidos.trim().isEmpty() ||
            documento == null || documento.trim().isEmpty() ||
            correo    == null || correo.trim().isEmpty()    ||
            clave     == null || clave.trim().isEmpty()) {

            request.setAttribute("error", "Todos los campos son obligatorios.");
            request.getRequestDispatcher("RegistrarUsuarios.jsp").forward(request, response);
            return;
        }

        if (clave.length() < 6) {
            request.setAttribute("error", "La contraseña debe tener al menos 6 caracteres.");
            request.getRequestDispatcher("RegistrarUsuarios.jsp").forward(request, response);
            return;
        }

        Usuarios u = new Usuarios();
        u.setNombres(nombre.trim());
        u.setApellidos(apellidos.trim());
        u.setIdentificacion(documento.trim());
        u.setCorreo(correo.trim());
        u.setClave(clave);
        u.setDireccion("");
    
        u.setIsActivo(true);
        u.setIdRoles(2);
        u.setIdCiudades(1);

        int idTipoDoc;
        switch (tipoDoc != null ? tipoDoc : "") {
            case "CC":  idTipoDoc = 1; break;
            case "CE":  idTipoDoc = 2; break;
            case "PAS": idTipoDoc = 3; break;
            default:    idTipoDoc = 1;
        }
        u.setIdTipoDocumento(idTipoDoc);

        UsuariosDAO dao = new UsuariosDAO();
        boolean guardado = dao.insertar(u);

        if (guardado) {
            response.sendRedirect("InicioSesion.jsp?msg=registro_ok");
        } else {
            request.setAttribute("error", "No se pudo registrar. El correo o documento ya existe.");
            request.getRequestDispatcher("RegistrarUsuarios.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.sendRedirect("RegistrarUsuarios.jsp");
    }
}
