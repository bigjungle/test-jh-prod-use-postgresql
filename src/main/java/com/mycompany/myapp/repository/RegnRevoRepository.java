package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.RegnRevo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the RegnRevo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegnRevoRepository extends JpaRepository<RegnRevo, Long>, JpaSpecificationExecutor<RegnRevo> {

    @Query("select regnRevo from RegnRevo regnRevo where regnRevo.punishPerson.login = ?#{principal.username}")
    List<RegnRevo> findByPunishPersonIsCurrentUser();

}
