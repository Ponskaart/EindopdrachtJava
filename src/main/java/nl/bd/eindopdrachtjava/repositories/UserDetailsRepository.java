package nl.bd.eindopdrachtjava.repositories;

import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface UserDetailsRepository extends Serializable {

    String getUsername();

    String getPassword();

    //TODO isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired, isEnabled
}
