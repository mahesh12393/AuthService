package org.example.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.io.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import org.example.model.UserInfoDto;

import java.io.OutputStream;

public class UserInfoSerializer implements Serializer<UserInfoDto> {

//    @Override

    public ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String arg0, UserInfoDto arg1){
        byte[] val = null;

        try{
            val = objectMapper.writeValueAsString(arg1).getBytes(); // converting the userinfo dto details as string and then to bytes
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return val;
    }
}
