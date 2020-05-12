package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.RegnRevoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RegnRevo} and its DTO {@link RegnRevoDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface RegnRevoMapper extends EntityMapper<RegnRevoDTO, RegnRevo> {

    @Mapping(source = "punishPerson.id", target = "punishPersonId")
    @Mapping(source = "punishPerson.lastName", target = "punishPersonLastName")
    RegnRevoDTO toDto(RegnRevo regnRevo);

    @Mapping(source = "punishPersonId", target = "punishPerson")
    RegnRevo toEntity(RegnRevoDTO regnRevoDTO);

    default RegnRevo fromId(Long id) {
        if (id == null) {
            return null;
        }
        RegnRevo regnRevo = new RegnRevo();
        regnRevo.setId(id);
        return regnRevo;
    }
}
