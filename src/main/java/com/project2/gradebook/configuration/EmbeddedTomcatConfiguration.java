package com.project2.gradebook.configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;

@Configuration
public class EmbeddedTomcatConfiguration { 

  @Value("${server.port}")
  private String serverPort;

  @Value("${server.additionalport}")
  private String secondaryPort;

  @Bean
  public TomcatServletWebServerFactory servletContainer() {
	  TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
    Connector[] additionalConnectors = this.additionalConnector();
    if (additionalConnectors != null && additionalConnectors.length > 0) {
      tomcat.addAdditionalTomcatConnectors(additionalConnectors);
    }
   return tomcat;
  }

  private Connector[] additionalConnector() {
    Set<String> defaultPorts = new HashSet<String>();
    defaultPorts.add(this.secondaryPort);
    List<Connector> result = new ArrayList<Connector>();
    for (String port : defaultPorts) {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(Integer.valueOf(port));
        result.add(connector);
    }
    return result.toArray(new Connector[] {});
  }
}