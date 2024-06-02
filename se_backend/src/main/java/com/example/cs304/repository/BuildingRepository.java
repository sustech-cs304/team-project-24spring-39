package com.example.cs304.repository;

import com.example.cs304.entity.Building;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Integer>{

    @Modifying
    @Transactional
    @Query(value = "insert into building (name, status) values (:name, :status)", nativeQuery = true)
    void addBuilding(@Param("name") String name, @Param("status") String status);

    @Modifying
    @Transactional
    @Query(value = "delete from building where name = :name", nativeQuery = true)
    void deleteBuilding(@Param("name") String name);

    @Modifying
    @Transactional
    @Query(value = "update building set capacity = capacity + :capacity where name = :place", nativeQuery = true)
    void addBuidingCapacity(@Param("capacity") int capacity, @Param("place") String place);

    @Modifying
    @Transactional
    @Query(value = "update building set status = :status where name = :name", nativeQuery = true)
    void updateBuildingStatus(@Param("name") String name, @Param("status") String status);

}
