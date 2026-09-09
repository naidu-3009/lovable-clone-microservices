package com.lovable_clone_microservices.account_service.services.implementation;

//import com.projectlove.lovable_clone.Services.AuthService;
//import com.projectlove.lovable_clone.dto.auth.AuthResponse;
//import com.projectlove.lovable_clone.dto.auth.LoginRequest;
//import com.projectlove.lovable_clone.dto.auth.SignUpRequest;
//import com.projectlove.lovable_clone.entity.User;
//import com.projectlove.lovable_clone.error.BadRequestException;
//import com.projectlove.lovable_clone.mapper.UserMapper;
//import com.projectlove.lovable_clone.repository.UserRepository;
//import com.projectlove.lovable_clone.security.AuthUtil;
import com.lovable_clone_microservices.account_service.dto.auth.AuthResponse;
import com.lovable_clone_microservices.account_service.dto.auth.LoginRequest;
import com.lovable_clone_microservices.account_service.dto.auth.SignUpRequest;
import com.lovable_clone_microservices.account_service.entity.User;
import com.lovable_clone_microservices.account_service.mapper.UserMapper;
import com.lovable_clone_microservices.account_service.repository.UserRepository;
import com.lovable_clone_microservices.account_service.services.AuthService;
import com.lovable_clone_microservices.common_library.dto.UserDto;
import com.lovable_clone_microservices.common_library.error.BadRequestException;
import com.lovable_clone_microservices.common_library.security.AuthUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class AuthServiceImpl implements AuthService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    AuthUtil authUtil;
    AuthenticationManager authenticationManager;


    @Override
    public AuthResponse signup(SignUpRequest request) {
        userRepository.findByUsername(request.username()).ifPresent(user -> {
            throw new BadRequestException("user already exists with user name:"+ request.username());
        });
        User user=userMapper.toUserEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);

        String token=authUtil.generateAccessToken(userMapper.toUserDto(user));
        return new AuthResponse(token,userMapper.toUserProfileResponse(user));

        //we are getting username+password +name from user => using thse we are generating jwt and returning
        //it to the user btw we are sending userprofileresponse btw


    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(),request.password())
        );

        User user=(User)authentication.getPrincipal();
        String token=authUtil.generateAccessToken(userMapper.toUserDto(user));
        return new AuthResponse(token,userMapper.toUserProfileResponse(user));


    }

}
