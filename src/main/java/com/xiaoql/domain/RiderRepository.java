package com.xiaoql.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RiderRepository extends JpaRepository<Rider, String> {

    List<Rider> findByLoginName(String loginName);
}