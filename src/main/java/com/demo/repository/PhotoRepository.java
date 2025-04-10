package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.entity.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    List<Photo> findByAttractionId(Long attractionId);

}
