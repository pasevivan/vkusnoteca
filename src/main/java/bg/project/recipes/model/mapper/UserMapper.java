package bg.project.recipes.model.mapper;

import bg.project.recipes.model.dto.user.UserDTO;
import bg.project.recipes.model.dto.user.UserRegisterDTO;
import bg.project.recipes.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "active", constant = "true")
    UserEntity userDtoToUserEntity(UserRegisterDTO userRegisterDTO);

    UserDTO userEntityToUserDTO(UserEntity userEntity);
}
