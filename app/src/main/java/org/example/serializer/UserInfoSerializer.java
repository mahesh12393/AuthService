package org.example.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import org.example.model.UserInfoDto;


public class UserInfoSerializer implements Serializer<UserInfoDto> {

    public ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String arg0, UserInfoDto arg1){
        byte[] val = null;

        try{
            val = objectMapper.writeValueAsBytes(arg1); // converting the userinfo dto details as string and then to bytes
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return val;
    }
}
