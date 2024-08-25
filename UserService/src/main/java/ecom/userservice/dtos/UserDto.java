package ecom.userservice.dtos;

import ecom.userservice.models.Role;
import ecom.userservice.models.User;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private String name;
    private String email;
    private List<String> roles;

    public static UserDto from(User user){
        if(user==null) return null;
        UserDto userDto = new UserDto();
        userDto.setName(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles().stream().map(Role::getName).toList());
        return userDto;
    }
}
