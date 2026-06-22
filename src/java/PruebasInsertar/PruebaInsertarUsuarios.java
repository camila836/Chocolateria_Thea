package PruebasInsertar;
 
import Controlador.RolesDAO;
import Controlador.TipoDocumentoDAO;
import Controlador.UsuariosDAO;
import Modelo.Roles;
import Modelo.TipoDocumento;
import Modelo.Usuarios;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Pattern;
 
public class PruebaInsertarUsuarios {
 
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Usuarios u = new Usuarios();
        UsuariosDAO dao = new UsuariosDAO();
        RolesDAO rolesDAO = new RolesDAO();
        TipoDocumentoDAO tipoDocDAO = new TipoDocumentoDAO();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
 
        System.out.println("=== REGISTRO DE NUEVO USUARIO ===");
 
        // ─── Primero los IDs, pedidos por nombre (como en el formulario real) ───
        u.setIdRoles(solicitarIdPorNombreRol(sc, rolesDAO));
        u.setIdTipoDocumento(solicitarIdPorNombreTipoDoc(sc, tipoDocDAO));
        // TODO: cuando exista CiudadesDAO con un método buscarPorNombre(...),
        // reemplazar la línea de abajo por la búsqueda por nombre de ciudad.
        System.out.print("ID Ciudad: ");
        u.setIdCiudades(evaluarEntero(sc));
 
        // ─── Luego los datos personales ───
        u.setNombres(solicitarCadenaNoVacia(sc, "Nombres: "));
        u.setApellidos(solicitarCadenaNoVacia(sc, "Apellidos: "));
        u.setIdentificacion(solicitarCadenaNoVacia(sc, "Identificación: "));
        u.setFechaNacimiento(solicitarFecha(sc, fmt, "Fecha Nacimiento (dd/MM/yyyy): "));
 
        // La fecha de vencimiento de clave NO se pide al usuario.
        // La calcula el sistema (ej: 90 días desde el registro).
        // El control de inactividad/expiración se valida en el módulo de login.
        u.setFechaVencimientoClave(LocalDate.now().plusDays(90));
 
        u.setCorreo(solicitarCorreo(sc, "Correo: "));
        System.out.print("Teléfono: ");
        u.setTelefono(sc.nextLine().trim());
        System.out.print("Dirección: ");
        u.setDireccion(sc.nextLine().trim());
 
        String claveTexto = solicitarCadenaNoVacia(sc, "Clave: ");
        u.setClave(hashSHA256(claveTexto));
 
        u.setAutorizacionTratamientoDatos(solicitarBooleano(sc, "¿Autoriza tratamiento de datos? (S/N): "));
        u.setIsActivo(true); // todo usuario nuevo se crea activo por defecto
 
        System.out.println("\nInsertando usuario en la base de datos...");
        boolean exito = dao.insertar(u);
        System.out.println(exito ? "¡Usuario insertado correctamente!" : "Error: No se pudo insertar el usuario.");
    }
 
    // ─── Búsqueda de IDs por nombre ──────────────────────────────────
    private static int solicitarIdPorNombreRol(Scanner sc, RolesDAO dao) {
        while (true) {
            String nombre = solicitarCadenaNoVacia(sc, "Rol (ej: Administrador, Usuario): ");
            Roles r = dao.buscarPorDescripcion(nombre);
            if (r != null) return r.getIdRoles();
            System.out.println("Ese rol no existe. Verifique el nombre e intente de nuevo.");
        }
    }
 
    private static int solicitarIdPorNombreTipoDoc(Scanner sc, TipoDocumentoDAO dao) {
        while (true) {
            String nombre = solicitarCadenaNoVacia(sc, "Tipo de Documento (ej: Cédula, Pasaporte): ");
            TipoDocumento t = dao.buscarPorDescripcion(nombre);
            if (t != null) return t.getIdTipoDocumento();
            System.out.println("Ese tipo de documento no existe. Verifique el nombre e intente de nuevo.");
        }
    }
 
    // ─── Hash SHA-256 ───────────────────────────────────────────────
    private static String hashSHA256(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(texto.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error al hashear la clave.", e);
        }
    }
 
    // ─── Métodos auxiliares ─────────────────────────────────────────
    private static String solicitarCadenaNoVacia(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = sc.nextLine().trim();
            if (!entrada.isEmpty()) return entrada;
            System.out.println("Este campo es obligatorio. Intente de nuevo.");
        }
    }
 
    private static final Pattern PATRON_CORREO =
            Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");
 
    private static String solicitarCorreo(Scanner sc, String mensaje) {
        while (true) {
            String correo = solicitarCadenaNoVacia(sc, mensaje);
            if (PATRON_CORREO.matcher(correo).matches()) return correo;
            System.out.println("Correo inválido. Use un formato como ejemplo@dominio.com");
        }
    }
 
    private static LocalDate solicitarFecha(Scanner sc, DateTimeFormatter fmt, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return LocalDate.parse(sc.nextLine().trim(), fmt);
            } catch (DateTimeParseException e) {
                System.out.println("Formato inválido, use dd/MM/yyyy.");
            }
        }
    }
 
    private static boolean solicitarBooleano(Scanner sc, String mensaje) {
        System.out.print(mensaje);
        String resp = sc.nextLine().trim();
        return resp.equalsIgnoreCase("S") || resp.equalsIgnoreCase("SI");
    }
 
    private static int evaluarEntero(Scanner sc) {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un número entero válido: ");
            }
        }
    }
}
 