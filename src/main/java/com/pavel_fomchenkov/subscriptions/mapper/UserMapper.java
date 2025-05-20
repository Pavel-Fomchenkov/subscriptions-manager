package com.pavel_fomchenkov.subscriptions.mapper;

import com.pavel_fomchenkov.subscriptions.dto.UserDTO;
import com.pavel_fomchenkov.subscriptions.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @Named("mapToUserDTO")
    UserDTO mapToUserDTO(User user);

    @Named("mapDTOToUser")
    User mapDTOToUser(UserDTO userDTO);
}
