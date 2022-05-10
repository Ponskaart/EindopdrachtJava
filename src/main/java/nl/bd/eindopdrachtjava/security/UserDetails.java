//package nl.bd.eindopdrachtjava.security;
//
//import nl.bd.eindopdrachtjava.models.entities.User;
//
//import nl.bd.eindopdrachtjava.models.enums.UserRole;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.*;
//
//public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
//
//    private User user;
//
//    public UserDetails(User user) {
//        this.user = user;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        UserRole role = user.getRole();
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//
//            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
//
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return user.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return user.getUsername();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return user.isEnabled();
//    }
//
//}