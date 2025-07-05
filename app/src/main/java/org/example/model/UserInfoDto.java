package org.example.model;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entities.UserInfo;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoDto extends UserInfo {
    
//    Snake case strategy changes the name from user_name to userName and vice versa
    private String firstName;
    
    private String lastName;
    
    private String phoneNumber;

    private String email;
}
