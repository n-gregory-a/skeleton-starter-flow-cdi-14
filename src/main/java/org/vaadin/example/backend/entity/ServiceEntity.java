package org.vaadin.example.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServiceEntity {

    private int id;

    private String name;

    private String address;

    private String version;

    private ServiceStatus status;

}
