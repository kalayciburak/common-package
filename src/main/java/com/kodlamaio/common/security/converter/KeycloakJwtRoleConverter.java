package com.kodlamaio.common.security.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class KeycloakJwtRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        return extractRoles(jwt);
    }

    private static Collection<GrantedAuthority> extractRoles(Jwt jwt) {
        Map<String, Object> claims = jwt.getClaims();
        Map<String, Object> realmAccess = (Map<String, Object>) claims.get("realm_access");

        if(realmAccess == null || realmAccess.isEmpty()) return new ArrayList<>();

        List<String> roles = (List<String>) realmAccess.get("roles");
        Collection<GrantedAuthority> result = new ArrayList<>();

        final String ROLE_PREFIX = "ROLE_";
        for (String roleName : roles){
            result.add(new SimpleGrantedAuthority(ROLE_PREFIX + roleName));
        }

        return result;
    }
}
