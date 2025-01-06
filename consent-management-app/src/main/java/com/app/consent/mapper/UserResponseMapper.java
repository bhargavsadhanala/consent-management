package com.app.consent.mapper;

import com.app.consent.dto.UserResponseDTO;
import com.app.consent.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserResponseMapper {

    public UserResponseDTO fromUser(User user) {
        return new UserResponseDTO(
                user.getUserId(),
                user.getUsername(),
                user.getGender(),
                user.getStatus(),
                user.getDateModified()
        );
    }
}
