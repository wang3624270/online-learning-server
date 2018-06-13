package org.octopus.auth.sdjpa_dao;

import org.octopus.auth.jpa_model.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityUsersJpaDao extends JpaRepository<SysUser, String> {

}
