package com.preAceleracionAlkemy.preAceleracion.repository.specification.dto;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Setter
@Getter
public class MovieDtoSpecification {

    private String title;
    private String genre;
    private String order;
    private Date fechaDeCreacion;

    public MovieDtoSpecification() {
    }

    public MovieDtoSpecification(String title, String genre, String order, Date fechaDeCreacion) {
        this.title = title;
        this.genre = genre;
        this.order = order;
        this.fechaDeCreacion = fechaDeCreacion;
    }

    public boolean isASC() {

        return order.compareToIgnoreCase("ASC") == 0;

    }

    public boolean isDESC() {
        return order.compareToIgnoreCase("DESC") == 0;
    }


}
