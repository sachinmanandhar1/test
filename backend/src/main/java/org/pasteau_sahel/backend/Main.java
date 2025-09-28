package org.pasteau_sahel.backend;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.servlet.ServletContainer;
import org.jboss.weld.environment.servlet.Listener;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        // Database configuration
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(String.format("jdbc:postgresql://%s:%s/%s",
                System.getenv("DB_HOST"),
                System.getenv("DB_PORT"),
                System.getenv("DB_NAME")));
        hikariConfig.setUsername(System.getenv("DB_USER"));
        hikariConfig.setPassword(System.getenv("DB_PASSWORD"));
        hikariConfig.setDriverClassName("org.postgresql.Driver");
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        // JPA EntityManagerFactory
        Map<String, Object> props = new HashMap<>();
        props.put("javax.persistence.nonJtaDataSource", dataSource);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pasteau-sahel-pu", props);

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