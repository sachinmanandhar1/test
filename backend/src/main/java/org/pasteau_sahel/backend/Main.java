package org.pasteau_sahel.backend;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.servlet.ServletContainer;
import org.jboss.weld.environment.servlet.Listener;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) throws Exception {
        // JPA EntityManagerFactory
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pasteau-sahel-pu");

        // Jetty Server
        Server server = new Server(8080);
        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath("/");
        webAppContext.setResourceBase("src/main/webapp");

        // CDI
        webAppContext.addEventListener(new Listener());

        // JAX-RS (Jersey)
        ServletHolder jerseyServlet = new ServletHolder(new ServletContainer());
        jerseyServlet.setInitParameter("javax.ws.rs.Application", org.pasteau_sahel.backend.rest.RestApplication.class.getName());
        webAppContext.addServlet(jerseyServlet, "/api/*");

        // Make EntityManagerFactory available to the application
        webAppContext.getServletContext().setAttribute("entityManagerFactory", emf);

        server.setHandler(webAppContext);
        server.start();
        server.join();
    }
}