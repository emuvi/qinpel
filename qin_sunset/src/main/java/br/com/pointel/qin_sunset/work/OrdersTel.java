package br.com.pointel.qin_sunset.work;

import br.com.pointel.qin_sunset.core.WayToRun;
import jakarta.servlet.ServletException;

public class OrdersTel {

    public static String telPing(WayToRun wayToRun) throws ServletException {
        try {
            return "pong";
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

}
