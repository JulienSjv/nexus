package com.elea.srv.nexus.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elea.srv.nexus.models.Config;

public interface ConfigRepository extends JpaRepository<Config, Long>{

}
