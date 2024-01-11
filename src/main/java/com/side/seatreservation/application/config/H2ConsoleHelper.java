package com.side.seatreservation.application.config;

import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
//@Profile("local")
public class H2ConsoleHelper {
    private Server webServer;
    @Value("${webclient.h2-console-port}")
    Integer h2ConsolePort;


    @EventListener(ContextRefreshedEvent.class)
    public void start() throws java.sql.SQLException {
        log.info(String.format("starting h2 console at port %s", h2ConsolePort));
        this.webServer = org.h2.tools.Server.createWebServer("-webPort", h2ConsolePort.toString(), "-tcpAllowOthers");
        this.webServer.start();
    }

    @EventListener(ContextClosedEvent.class)
    public void stop() {
        log.info("stopping h2 console at port 8078");
        this.webServer.stop();
    }

}