package com.novianto.challange5.repository;

import com.novianto.challange5.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    @Query(value = "SELECT u FROM User u WHERE u.id = :id")
    public User getByIdUser(@Param("id") UUID id);

    @Query(value = "SELECT u FROM users u WHERE id = :id", nativeQuery = true)
    public Object getByIdNative(@Param("id") UUID id);

    @Query(value = "select u from User u where u.username like :nameParam")
    public Page<User> getByLikeUsername(@Param("nameParam") String nameParam, Pageable pageable);

    @Query(value = "select u from User u")
    public Page<User> getALlPage(Pageable pageable);

    @Query(value = "SELECT u FROM User u WHERE u.username = :username AND u.emailAddress = :emailAddress", nativeQuery = false)
    public User findUserByUsernameAndEmailWithQuery(@Param("username") String username, @Param("emailAddress") String emailAddress);

    Page<User> findByUsernameAndEmailAddress(String username, String emailAddress, Pageable pageable);

    @Query("select count(u) from User u where u.username = ?1")
    long countByUsername(String username);

    long count();

    @Query("select sum(u.id) from User u")
    long sumUser();
}
