package com.fileservice.repository;

import com.fileservice.entity.File;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileRepository extends ReactiveCassandraRepository<File, UUID> {

}
