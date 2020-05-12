package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.RegnCanc;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the RegnCanc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegnCancRepository extends JpaRepository<RegnCanc, Long>, JpaSpecificationExecutor<RegnCanc> {

    @Query("select regnCanc from RegnCanc regnCanc where regnCanc.ownerBy.login = ?#{principal.username}")
    List<RegnCanc> findByOwnerByIsCurrentUser();

}
