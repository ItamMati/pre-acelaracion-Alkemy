package com.preAceleracionAlkemy.preAceleracion.dto.response;

import com.preAceleracionAlkemy.preAceleracion.entity.CharacterEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterDtoEdit {

    private Long id;

    private String imageUrl;

    private String name;

    private int age;

    private Double weight;

    private String history;
    
    


}
