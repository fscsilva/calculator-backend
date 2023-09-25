package com.silvaindustries.calculatorbackend.service.dto.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

@Jacksonized
@Builder
@Getter
public class UserDTO implements UserDetails {

    @JsonProperty
    private final BigInteger userId;
    @JsonProperty
    private final String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private final String password;
    @JsonProperty
    private final Boolean statusActive;
    @JsonProperty
    private final Double balance;
    @JsonProperty
    private List<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return statusActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return statusActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return statusActive;
    }

    @Override
    public boolean isEnabled() {
        return statusActive;
    }
}
