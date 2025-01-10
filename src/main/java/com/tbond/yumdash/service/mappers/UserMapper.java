package com.tbond.yumdash.service.mappers;

import com.tbond.yumdash.domain.User;
import com.tbond.yumdash.dto.user.UserResponseDto;
import com.tbond.yumdash.repository.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {
    @Mapping(target = "id", source = "reference")
    User toUser(UserEntity userEntity);

    UserResponseDto toUserResponseDto(User user);

    List<User> toUserList(List<UserEntity> list);

    List<UserResponseDto> toUserResponseDtoList(List<User> list);
}
