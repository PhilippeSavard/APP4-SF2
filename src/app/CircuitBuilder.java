package app;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CircuitBuilder {
    private final ObjectMapper mapper; // ObjectMapper = utile pour lecture de JsonNode

    public CircuitBuilder() {
        this.mapper = new ObjectMapper();
    }
}
