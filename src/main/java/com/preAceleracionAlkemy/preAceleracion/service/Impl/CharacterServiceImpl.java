package com.preAceleracionAlkemy.preAceleracion.service.Impl;

import com.preAceleracionAlkemy.preAceleracion.dto.request.CharacterDtoReq;
import com.preAceleracionAlkemy.preAceleracion.mapper.CharacterMapper;
import com.preAceleracionAlkemy.preAceleracion.dto.response.CharacterDtoDetails;
import com.preAceleracionAlkemy.preAceleracion.dto.response.CharacterDtoEdit;

import com.preAceleracionAlkemy.preAceleracion.dto.response.CharacterDtoList;
import com.preAceleracionAlkemy.preAceleracion.dto.response.CharacterDtoRes;
import com.preAceleracionAlkemy.preAceleracion.repository.specification.dto.CharacterDtoSpecification;
import com.preAceleracionAlkemy.preAceleracion.entity.CharacterEntity;
import com.preAceleracionAlkemy.preAceleracion.entity.MovieEntity;

import com.preAceleracionAlkemy.preAceleracion.exception.ParamNotFound;
import com.preAceleracionAlkemy.preAceleracion.repository.CharacterRepository;
import com.preAceleracionAlkemy.preAceleracion.repository.specification.CharacterSpecification;
import com.preAceleracionAlkemy.preAceleracion.service.CharacterService;
import com.preAceleracionAlkemy.preAceleracion.service.MovieService;
import java.util.Arrays;
import java.util.List;

import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.data.jpa.domain.Specification.where;
import org.springframework.stereotype.Service;

@Service
public class CharacterServiceImpl implements CharacterService {

    //Service
    @Autowired
    private MovieService movieService;

    //Repository:
    @Autowired
    private CharacterRepository characterRepository;

    // Mapper:
    @Autowired
    private CharacterMapper characterMapper;

    //Specifications:
    @Autowired
    private CharacterSpecification characterSpecification;

    // == FILTERS ==
    @Override
    public List<CharacterDtoList> getByFilters(String name, Integer age, Double weight, Set<Long> movies) {
        CharacterDtoSpecification characterFilterDto = new CharacterDtoSpecification(name, age, weight, movies);

        List<CharacterEntity> entityList = characterRepository.findAll(
                where((characterSpecification.getByName(characterFilterDto)).
                        and(characterSpecification.getByAge(characterFilterDto).
                                and(characterSpecification.getByWeight(characterFilterDto).
                                        and(characterSpecification.getByMovieId(characterFilterDto))))));

        List<CharacterDtoList> resultDTO = characterMapper.listCharacterEntityToListCharacterDto(entityList);
        return resultDTO;
    }

    @Override
    public CharacterDtoDetails getCharacterDetails(Long id) {
        CharacterEntity dbChar = this.handleFindById(id);
//        CharacterDtoDetails characterDetailsDto = characterMapper.characterEntityToCharacterDetailsDto(dbChar);
        return null;
    }

    // == POST ==
    @Override
    public CharacterDtoRes save(CharacterDtoReq characterDtoReq) {

        CharacterEntity newCharacterEntity = characterMapper.characterDtoReqToEntity(characterDtoReq);

//        movieService.handleFindById(characterDtoReq.getIdMovie()).addCharacterToMovie(newCharacterEntity);

        MovieEntity movie = movieService.handleFindById(characterDtoReq.getIdMovie());
        
        newCharacterEntity.setMovieCharacters((Set<MovieEntity>) movie);

        CharacterEntity savedEntity = characterRepository.save(newCharacterEntity);

        CharacterDtoRes savedChar = characterMapper.characterEntityToCharacterDtoRes(savedEntity);

        return savedChar;
    }

    // == DELETE ==
    @Override
    public void deleteCharacterById(Long id) {

        CharacterEntity exist = handleFindById(id);

        characterRepository.deleteById(exist.getId());
    }

    // == PUT ==
    @Override
    public CharacterDtoEdit editCharacterById(Long id, CharacterDtoDetails charToEdit) {

        CharacterEntity savedChar = this.handleFindById(id);
//        CharacterDtoEdit editedChar = characterMapper.characterEntityToCharacterDtoEdit(characterRepository.save(characterMapper.toDto(savedChar, charToEdit)));

        return null;
    }

    // == ERROR HANDLING ==
    public CharacterEntity handleFindById(Long id) {

        Optional<CharacterEntity> toBeFound = characterRepository.findById(id);

        if (!toBeFound.isPresent()) {
            throw new ParamNotFound("No Character for id: " + id);
        }
        return toBeFound.get();
    }

}
