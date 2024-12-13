package com.tbond.yumdash.service.mappers;

import com.tbond.yumdash.domain.User;
import com.tbond.yumdash.dto.user.UserResponseDto;
import com.tbond.yumdash.repository.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {
    @Mapping(target = "id", source = "reference")
    User toUser(UserEntity userEntity);

    @Mapping(target = "fullName", source = "fullName")
    UserResponseDto toUserResponseDto(User user);
}
