package uz.smart_ai.smart_ai.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import uz.smart_ai.smart_ai.dto.UserDTO;
import uz.smart_ai.smart_ai.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO(User user);

    User userDTOToUser(UserDTO userDTO);
}
