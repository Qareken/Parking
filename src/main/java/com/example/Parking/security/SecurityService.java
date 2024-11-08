package com.example.Parking.security;

import com.example.Parking.dto.AuthResponse;
import com.example.Parking.dto.RefreshTokenRequest;
import com.example.Parking.dto.RefreshTokenResponse;
import com.example.Parking.dto.UserDTO;
import com.example.Parking.entity.RefreshToken;
import com.example.Parking.entity.Users;
import com.example.Parking.entity.enumConstants.RoleType;
import com.example.Parking.exception.RefreshTokenException;
import com.example.Parking.repository.UserRepository;
import com.example.Parking.security.jwt.JwtUtils;
import com.example.Parking.service.impl.RefreshTokenService;
import com.example.Parking.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final UserServiceImpl usersService;
    private final UserRepository userRepository;



    public AuthResponse authenticateUser(UserDTO userDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getPhoneNumber(), userDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        System.out.println(roles);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        String token = jwtUtils.generateJwtToken(userDetails);
        log.info(token+"token");
        return AuthResponse.builder().id(userDetails.getId()).token(token).refreshToken(refreshToken.getToken())
                .name(userDetails.getUsername()).roles(roles).build();
    }
    public UserDTO registerAdmin(UserDTO userDTO){
        return usersService.saveAdmin(userDTO);
    }
    public UserDTO register(UserDTO userDTO){

        return usersService.save(userDTO);
    }
    public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String requestRefresh= refreshTokenRequest.getRefreshToken();
        return refreshTokenService.findByRefreshToken(requestRefresh).map(refreshTokenService::checkRefreshToken)
                .map(RefreshToken::getUserId)
                .map(userid->{
                    Users tokenOwner = userRepository.findById(userid).orElseThrow(()->new RefreshTokenException("Exception trying to get token for userId: {}"+ userid));
                    String token = jwtUtils.generateTokenFromUserName(tokenOwner.getName());
                    return new RefreshTokenResponse(token, refreshTokenService.createRefreshToken(userid).getToken());
                }).orElseThrow(()->new RefreshTokenException(requestRefresh,"Refresh token not found"));
    }

    public void logout(){
        var currentPrincipal = getAuthentication().getPrincipal();
        if(currentPrincipal instanceof AppUserDetails userDetails){
            Long userId= userDetails.getId();
            refreshTokenService.deleteByUserId(userId);
        }
    }
    public Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
