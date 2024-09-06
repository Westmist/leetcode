package org.example.basic.convert;

import org.example.basic.convert.ano.Convert;
import org.example.basic.convert.ano.Params;
import org.example.basic.convert.inf.IConvert;

public class ConvertUtil {

    public static Object[] parse(Class<?>[] types, Params sp) {
        Object[] params = new Object[types.length];
        Convert[] ps = sp.pc();
        for (int i = 0; i < types.length; i++) {
            params[i] = parse(types[i], ps[i]);
        }
        return params;
    }

    public static Object parse(Class<?> type, Convert anoC) {
        IConvert<?> iConvert = ConvertFactory.COVERTS.get(anoC.convert());
        Class<?> genTypeClazz = anoC.type() == Void.class ? iConvert.defaultConvertGenClazz(type) : anoC.type();
        return iConvert.convert(anoC.value(), genTypeClazz);
    }

}
