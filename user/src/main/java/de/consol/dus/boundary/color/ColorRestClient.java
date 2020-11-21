package de.consol.dus.boundary.color;

/**
 * Client REST-Schnittstelle zu dem anderen Color-Service.
 * Die Schnittstelle ist fault tolerant.
 * @author promyx
 *
 */
@org.eclipse.microprofile.rest.client.inject.RegisterRestClient(configKey = "color-rest")
@javax.ws.rs.Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
@javax.ws.rs.Path("colors")
public interface ColorRestClient {

    /**
     * Fault tolerance:
     * Er probiert es zunächst bis zu 10x mit einer Verzögerung von 1 Sekunde den Aufruf zu
     * erledigen. Danach schlägt der Circuit-Breaker zu und setzt erst einmal 30 Sekunden aus.
     * 
     * @param name
     * @return
     */
    @javax.ws.rs.GET
    @javax.ws.rs.Path("{name}")
    @org.eclipse.microprofile.faulttolerance.Retry(maxRetries = 10, delay = 1, delayUnit = java.time.temporal.ChronoUnit.SECONDS)
    @org.eclipse.microprofile.faulttolerance.CircuitBreaker(requestVolumeThreshold = 4, delay = 30, delayUnit = java.time.temporal.ChronoUnit.SECONDS)
    java.util.Optional<de.consol.dus.boundary.color.response.ColorResponse> getColorByName(
        @javax.ws.rs.PathParam("name") @javax.validation.constraints.NotNull @javax.validation.constraints.Size(max = 255) String name);
}
