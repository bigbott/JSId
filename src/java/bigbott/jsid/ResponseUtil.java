/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigbott.jsid;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletResponse;
import me.jrpc.JrpcServlet;

/**
 *
 * @author Owner
 */
public class ResponseUtil {
        public static void sendResponse(ServletResponse response, String result) {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer;
        try {
            writer = response.getWriter();
            writer.print(result);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(ResponseUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
