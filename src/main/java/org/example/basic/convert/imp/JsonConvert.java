package org.example.basic.convert.imp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.basic.convert.inf.IConvert;

public class JsonConvert implements IConvert<Object> {

    private static ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public Object convert(String src, Class<?> tClazz) {
        if (tClazz == String.class || tClazz == char[].class) {
            return src;
        }
        Object pObj;
        try {
            pObj = MAPPER.readValue(src, tClazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return pObj;
    }

    @Override
    public Class<?> defaultConvertGenClazz(Class<?> tarClazz) {
        return tarClazz;
    }
}
