package com.github.marschall.storedprocedureexamples;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

  /**
   * Explicitly mapped to named stored procedure {@code User.plus1IO} in the {@link EntityManager}
   *
   * @see User
   */
  @Procedure(name = "User.plus1")
  Integer plus1BackedByOtherNamedStoredProcedure(Integer arg);

  /**
   * Directly map the method to the stored procedure in the database (to avoid the annotation madness on your domain
   * classes).
   */
  @Procedure
  Integer plus1inout(Integer arg);

}