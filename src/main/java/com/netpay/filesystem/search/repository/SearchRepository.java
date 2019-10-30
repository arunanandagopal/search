package com.netpay.filesystem.search.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.netpay.filesystem.search.model.Directory;

import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<Directory, String> {

	@Query(value = "SELECT * FROM Directory WHERE  path @ (CAST(:pathToSearch AS ltxtquery))", nativeQuery = true)
	List<Directory> findByPath(@Param("pathToSearch") String pathToSearch);

	@Modifying
	@Transactional
	@Query(value = "INSERT INTO Directory  VALUES  (CAST(:destinationPath AS ltree))", nativeQuery = true)
	void createFileSystem(@Param("destinationPath") String destinationPath);

}
