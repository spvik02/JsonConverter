package jsonconverter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericListType<T> {
    private Type type;

    public GenericListType(){
        ParameterizedType parameterized = (ParameterizedType) getClass().getGenericSuperclass();
        this.type = parameterized.getActualTypeArguments()[0];
    }
    public Type getType(){
        return type;
    }
}
