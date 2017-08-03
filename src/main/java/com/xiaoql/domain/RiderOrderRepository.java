package com.xiaoql.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

public interface RiderOrderRepository extends JpaRepository<RiderOrder, String>, JpaSpecificationExecutor {

    List<RiderOrder> findByTimeAfter(Date date);

}