package com.Microservice_based_document_repository.Gateway.repository;
import com.Microservice_based_document_repository.Gateway.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Representation of methods that are being used to connect ot database
 */
@Service
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Checks database for user with given name
     * @param name of user to be found
     * @return User model form database
     */
    Optional<User> findUserByName(String name);

    /**
     * Changes username
     * @param name new name of user to be changed
     * @param Id user id whom name is to be changed
     * @return int 1 if name was to be changed successfully or 0 if error occurred
     */
    @Modifying
    @Query("UPDATE User " +
            "SET name = :name " +
            "WHERE Id = :Id")

    int changeName(@Param("name") String name,
                   @Param("Id") Long Id);

    /**
     * Changes user password
     * @param password new password to be changed
     * @param Id user id whom password is to be changed
     * @return int 1 if name was to be changed successfully or 0 if error occurred
     */
    @Modifying
    @Query("UPDATE User " +
            "SET password = :password " +
            "WHERE Id = :Id")

    int changePassword(@Param("password") String password,
                   @Param("Id") Long Id);

    /**
     * Gets all users saved in database
     * @return list of all users saved in database
     */
    List<User> findAll();
}
