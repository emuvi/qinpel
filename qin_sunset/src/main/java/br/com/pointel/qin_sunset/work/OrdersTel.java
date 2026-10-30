package br.com.pointel.qin_sunset.work;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.pointel.qin_sunset.core.WayToRun;
import br.com.pointel.qin_sunset.hook.ServesTel;
import br.com.pointel.wiz_jarch.mage.WizInstant;
import jakarta.servlet.ServletException;
import java.time.Instant;
import java.time.ZoneOffset;

public class OrdersTel {

    public static String telPing(WayToRun wayToRun) throws ServletException {
        try {
            return "pong";
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    public static String telLive(WayToRun wayToRun, String uid) throws ServletException {
        String lived = Instant.now().atOffset(ZoneOffset.UTC).format(WizInstant.formatDateTimeMach);
        try (var connection = ServesTel.connectDatabase()) {
            createTelUidTable(connection);
            try (var statement = connection.prepareStatement("""
                    INSERT INTO tel_uid (uid, lived)
                    VALUES (?, ?)
                    ON CONFLICT(uid) DO UPDATE SET lived = ?
                    """)) {
                statement.setString(1, uid);
                statement.setString(2, lived);
                statement.setString(3, lived);
                statement.executeUpdate();
            }
            return "lived";
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private static void createTelUidTable(Connection connection) throws SQLException {
        try (var statement = connection.createStatement()) {
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS tel_uid (
                        uid TEXT,
                        cluster TEXT,
                        name TEXT,
                        lived TEXT,
                        PRIMARY KEY (uid)
                    )
                    """);
        }
    }

}
