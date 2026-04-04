package app;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CircuitBuilder {
    private final ObjectMapper mapper;

    public CircuitBuilder() {
        this.mapper = new ObjectMapper();
    }
}
