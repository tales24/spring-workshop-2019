package de.inmediasp.springws.zoo.animals;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface MonkeyRepository extends JpaRepository<Monkey, Long> {
}
