package com.lovable_clone_microservices.account_service.mapper;


import com.lovable_clone_microservices.account_service.dto.auth.SignUpRequest;
import com.lovable_clone_microservices.account_service.dto.auth.UserProfileResponse;
import com.lovable_clone_microservices.account_service.entity.User;
import com.lovable_clone_microservices.common_library.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUserEntity(SignUpRequest signUpRequest);

    @Mapping(target = "userId", source = "id")
    UserProfileResponse toUserProfileResponse(User user);

    UserDto toUserDto(User user);
}
