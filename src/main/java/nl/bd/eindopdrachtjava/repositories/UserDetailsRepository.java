package nl.bd.eindopdrachtjava.repositories;

import java.io.Serializable;

public interface UserDetailsRepository extends Serializable {

    String getUsername();

    String getPassword();

    //TODO isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired, isEnabled
}
