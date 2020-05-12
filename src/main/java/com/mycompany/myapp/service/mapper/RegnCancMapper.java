package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.RegnCancDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RegnCanc} and its DTO {@link RegnCancDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface RegnCancMapper extends EntityMapper<RegnCancDTO, RegnCanc> {

    @Mapping(source = "ownerBy.id", target = "ownerById")
    @Mapping(source = "ownerBy.lastName", target = "ownerByLastName")
    RegnCancDTO toDto(RegnCanc regnCanc);

    @Mapping(source = "ownerById", target = "ownerBy")
    RegnCanc toEntity(RegnCancDTO regnCancDTO);

    default RegnCanc fromId(Long id) {
        if (id == null) {
            return null;
        }
        RegnCanc regnCanc = new RegnCanc();
        regnCanc.setId(id);
        return regnCanc;
    }
}
