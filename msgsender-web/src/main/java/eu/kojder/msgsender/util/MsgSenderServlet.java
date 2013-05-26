package eu.kojder.msgsender.util;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.AbstractApplicationServlet;
import eu.kojder.msgsender.web.MsgSender;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

@WebServlet(urlPatterns = "/*")
public class MsgSenderServlet extends AbstractApplicationServlet {

    @Override
    protected Class<? extends Application> getApplicationClass() throws ClassNotFoundException {
        return MsgSender.class;
    }

    @Override
    protected Application getNewApplication(HttpServletRequest request) throws ServletException {
        try {
            InitialContext ic = new InitialContext();
            return (Application) ic.lookup("java:module/MsgSender");
        } catch (NamingException e) {
            throw new ServletException("Could not access application", e);
        }
    }
}