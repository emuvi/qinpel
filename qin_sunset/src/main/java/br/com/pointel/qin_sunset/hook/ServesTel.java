package br.com.pointel.qin_sunset.hook;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import br.com.pointel.qin_sunset.work.OrdersTel;
import br.com.pointel.qin_sunset.work.Runner;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ServesTel {

    private static final String DATABASE_URL = "jdbc:sqlite:serves_tel.sdb";

    private ServesTel() {}

    public static void init(ServletContextHandler context) throws SQLException {
        try (var connection = connectDatabase()) {
        }

        context.addServlet(new ServletHolder(new HttpServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                var wayToRun = Runner.getWayToRun(req);
                if (wayToRun == null) {
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server does not have a way to run");
                    return;
                }
                if (!Boolean.TRUE.equals(wayToRun.airWays.setup.servesTel)) {
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Server does not allow Tel access");
                    return;
                }
                resp.setContentType("text/plain");
                resp.getWriter().print(OrdersTel.telPing(wayToRun));
            }
        }), "/tel/ping");

        context.addServlet(new ServletHolder(new HttpServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                var wayToRun = Runner.getWayToRun(req);
                if (wayToRun == null) {
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server does not have a way to run");
                    return;
                }
                if (!Boolean.TRUE.equals(wayToRun.airWays.setup.servesTel)) {
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Server does not allow Tel access");
                    return;
                }
                resp.setContentType("text/plain");
                resp.getWriter().print(OrdersTel.telLive(wayToRun, req.getParameter("uid")));
            }
        }), "/tel/live");
    }

    public static Connection connectDatabase() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }
}
