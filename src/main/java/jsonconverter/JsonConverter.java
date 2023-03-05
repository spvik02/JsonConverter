package jsonconverter;

import java.lang.reflect.*;
import java.util.*;

public class JsonConverter {
    private final Map<Class<?>, Class<?>> WRAP_TO_PRIMITIVE = new HashMap<>() {{
        put(Byte.class, Byte.TYPE);
        put(Short.class, Short.TYPE);
        put(Integer.class, Integer.TYPE);
        put(Long.class, Long.TYPE);
        put(Float.class, Float.TYPE);
        put(Double.class, Double.TYPE);
        put(Character.class, Character.TYPE);
        put(Boolean.class, Boolean.TYPE);
    }};
    private final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAP = new HashMap<>() {{
        put(Byte.TYPE, Byte.class);
        put(Short.TYPE, Short.class);
        put(Integer.TYPE, Integer.class);
        put(Long.TYPE, Long.class);
        put(Float.TYPE, Float.class);
        put(Double.TYPE, Double.class);
        put(Character.TYPE, Character.class);
        put(Boolean.TYPE, Boolean.class);
    }};
    private final char CURLY_OPEN_BRACKETS = '{';
    private final char CURLY_CLOSE_BRACKETS = '}';
    private final char SQUARE_OPEN_BRACKETS = '[';
    private final char SQUARE_CLOSE_BRACKETS = ']';
    private final char COLON = ':';
    private final char COMMA = ',';
    private final char DOUBLE_QUOTES = '"';
    private final char DOT = '.';
    private final char REVERSED_SLASH = '\\';

    class Pair{
        private int endPosition;
        private Object value;

        public Pair() {}
        public Pair(int endPosition, Object value) {
            this.endPosition = endPosition;
            this.value = value;
        }
    }

    public <T> T fromJson(String json, Class<T> type)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        T object;
        Pair pair = parseJson(json, 0, type);
        object = (T) pair.value;
        return object;
    }

    public <T> T fromJson(String json, Type type)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        T object;
        Pair pair = parseJson(json, 0, type);
        object = (T) pair.value;
        return object;
    }

    private Object createValue(String valueString, Type type)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Object object;
        Class<?> cls = (Class<?>) type;

        if(isNumber(cls)){
            Class<?> typePrimitive = WRAP_TO_PRIMITIVE.getOrDefault(cls, cls);
            Class<?> typeWrap = PRIMITIVE_TO_WRAP.getOrDefault(cls, cls);

            object = typeWrap.getConstructor(typePrimitive).newInstance((byte)0);
            Method valueOfString = typeWrap.getDeclaredMethod("valueOf", String.class);
            object = valueOfString.invoke(object, valueString);
        } else if (boolean.class.isAssignableFrom(cls) || Boolean.class.isAssignableFrom(cls)) {
            object = Boolean.class.getConstructor(boolean.class).newInstance(false);
            Method valueOfString = Boolean.class.getDeclaredMethod("valueOf", String.class);
            object = valueOfString.invoke(object,valueString);
        } else if (String.class.isAssignableFrom(cls)) {
            object = cls.getConstructor(String.class).newInstance(valueString);
        } else if(Character.class.isAssignableFrom(cls) ||char.class.isAssignableFrom(cls)){
            object = Character.class.getConstructor(char.class).newInstance('0');
            Method valueOfString = Character.class.getDeclaredMethod("valueOf", char.class);
            object = valueOfString.invoke(object, valueString.charAt(0));
        } else {
            throw new java.lang.UnsupportedOperationException("Not supported yet. The value is " +  valueString);
        }
        return object;
    }

    private Pair parseJson(String json, int position,  Type type)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        Pair pair = null;

        while (position<json.length()){
            char c = json.charAt(position);

            if (c == '{') {
                pair = parseJsonObject(json, position, type);
                break;
            } else if (c == '[') {
                pair = parseJsonArray(json, position, type);
                break;
            } else if (c == '"') {
                pair = parseJsonString(json, position, type);
                break;
            } else if (c == 't' || c == 'f') {
                pair = parseJsonBoolean(json, position, type);
                break;
            } else if (c == 'n') {
                pair = parseJsonNull(json, position);
                break;
            } else if (Character.isDigit(c)) {
                pair = parseJsonNumber(json, position, type);
                break;
            }else position++;
        }
        return pair;
    }
    private Pair parseJsonObject(String json, int positionStart, Type type)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> cls = (Class<?>) type;
        Object object = cls.getDeclaredConstructor().newInstance();
        Map<Field, Object> fieldValueMap = new HashMap<>();
        Field[] fields = getFields(cls);

        positionStart++;
        while (positionStart<json.length()){
            char c = json.charAt(positionStart);

            if (c == DOUBLE_QUOTES) {
                Pair pair;
                positionStart++;
                int positionEnd = json.indexOf(DOUBLE_QUOTES, positionStart);
                String fieldName = json.substring(positionStart, positionEnd);
                positionStart = positionEnd + 2;
                Optional<Field> currentField = Arrays.stream(fields)
                        .filter(field -> fieldName.equals(field.getName()))
                        .findFirst();
                if(currentField.isPresent()){
                    if(Collection.class.isAssignableFrom(currentField.get().getType())){
                        Class<?> subCls = (Class<?>) ((ParameterizedType) currentField.get().getGenericType()).getActualTypeArguments()[0];
                        pair = parseJson(json, positionStart, subCls);
                    } else{
                        pair = parseJson(json, positionStart, currentField.get().getType());
                    }
                    fieldValueMap.put(currentField.get(), pair.value);
                } else {
                    pair = parseJson(json, positionStart, null);
                }
                positionStart = pair.endPosition;
            }else if(c == CURLY_CLOSE_BRACKETS){
                break;
            }else positionStart++;
        }

        fieldValueMap.forEach((field, fieldValue) ->{
            if(field.trySetAccessible()){
                try {
                    field.set(object, fieldValue);
                } catch (IllegalAccessException e) {
                    System.out.println(object + " is " + fieldValue + " of " + fieldValue.getClass());
                    throw new RuntimeException(e);
                }
            }
        });
        return new Pair(++positionStart, object);
    }

    private Pair parseJsonArray(String json, int position, Type type)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        if(type instanceof ParameterizedType){
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Class<?> cls = (Class<?>) parameterizedType.getRawType();
            Type subType = parameterizedType.getActualTypeArguments()[0];

            if(List.class.isAssignableFrom(cls)){
                if (subType instanceof Class<?>){
                    Class<?> subCls = (Class<?>) subType;
                    List<Object> list;
                    Pair pair;

                    if(cls.isInterface()){
                        list = new ArrayList<>();
                    }else {
                        list = (List<Object>) cls.getDeclaredConstructor().newInstance();
                    }

                    position++;
                    while (position<json.length()){
                        char c = json.charAt(position);
                        if (c == SQUARE_CLOSE_BRACKETS) {
                            break;
                        }else{
                            pair = parseJson(json, position, subCls);
                            list.add(pair.value);
                            position = pair.endPosition;
                        }
                    }
                    return new Pair(position, list);
                }else {
                    throw new UnsupportedOperationException("Not supported yet.");
                }
            }
        }else if(type instanceof Class<?>){
            Class<?> cls = (Class<?>) type;
            Class<?> subCls;
            Pair pair;
            List<Object> list = new ArrayList<>();

            if(cls.isArray()){
                subCls = cls.getComponentType();
            }else {
                subCls = cls;
                System.out.println("here we are it's list");
            }

            position++;
            while (position<json.length()){
                char c = json.charAt(position);
                if (c == SQUARE_CLOSE_BRACKETS) {
                    break;
                }else{
                    pair = parseJson(json, position, subCls);
                    list.add(pair.value);
                    position = pair.endPosition;
                }
            }
            if(cls.isArray()){
                Object array = Array.newInstance(subCls, list.size());
                for (int i = 0; i < list.size(); i++) {
                    Array.set(array, i, list.get(i));
                }
                return new Pair(position, array);
            }
            return new Pair(position, list);
        }
        else{
            throw new UnsupportedOperationException("Not supported yet.");
        }
        return new Pair();
    }

    private Pair parseJsonString(String json, int positionStart, Type type)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        int positionEnd = ++positionStart;
        while (positionEnd<json.length()){
            char c = json.charAt(positionEnd);
            if ( c == REVERSED_SLASH) {
                positionEnd++;
            }else if (c == DOUBLE_QUOTES) {
                break;
            }
            positionEnd++;
        }
        if(type == null) {
            return new Pair(++positionEnd, null);
        }
        String valueString = json.substring(positionStart, positionEnd);
        Object value = createValue(valueString, type);
        return new Pair(++positionEnd, value);
    }
    private Pair parseJsonBoolean(String json, int positionStart, Type type)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        int positionEnd;

        if (json.charAt(positionStart) == 't') {
            positionEnd = positionStart + 4;
        } else {
            positionEnd = positionStart + 5;
        }
        String valueString = json.substring(positionStart, positionEnd);
        Object value = createValue(valueString, type);
        return new Pair(positionEnd, value);
    }
    private Pair parseJsonNull(String json, int positionStart) {
        int positionEnd = positionStart + 4;
        String valueString = json.substring(positionStart, positionEnd);
        if("null".equals(valueString)) {
            return new Pair(positionEnd, null);
        } else {
            throw new IllegalArgumentException("Probably there should be a null literal here, but the wrong format was used");
        }
    }
    private Pair parseJsonNumber(String json, int positionStart, Type type)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        int positionEnd = positionStart;
        while (positionEnd<json.length()){
            char c = json.charAt(positionEnd);
            if (Character.isDigit(c) || c == DOT) {
                positionEnd++;
            }else break;
        }
        String valueString = json.substring(positionStart, positionEnd);
        Object value = createValue(valueString, type);
        return new Pair(positionEnd, value);
    }

    private Field[] getFields(Class<?> cls){
        Class<?> subclass = cls;
        List<Field> fieldList = new ArrayList<>();
        while (subclass.getSuperclass() != null){
            fieldList.addAll(List.of(subclass.getDeclaredFields()));
            subclass = subclass.getSuperclass();
        }
        return fieldList.toArray(Field[]::new);
    }

    private String createJsonFromString(String value){
        return "\"" + value + "\"";
    }
    private String createJsonFromCharacter(Character value){
        return "\"" + value + "\"";
    }
    private String createJsonFromNum(Number value){
        return value.toString();
    }
    private String createJsonFromBoolean(Boolean value){
        return value.toString();
    }

    private void createJsonValue(Object obj, StringBuilder jsonValueBuilder) throws IllegalAccessException {
        if (obj == null) {
            jsonValueBuilder.append("null");
            return;
        }
        Class<?> cls = obj.getClass();

        if(isString(cls)){
            jsonValueBuilder.append(createJsonFromString((String)obj));
        }else if(isCharacter(cls)){
            jsonValueBuilder.append(createJsonFromCharacter((Character)obj));
        } else if (isBoolean(cls)) {
            jsonValueBuilder.append(createJsonFromBoolean((Boolean) obj));
        } else if(isNumber(cls)){
            jsonValueBuilder.append(createJsonFromNum((Number) obj));
        }else if (cls.isArray()){
            Object[] containedValues = (Object[])obj;

            jsonValueBuilder.append(SQUARE_OPEN_BRACKETS);
            for (Object containedValue : containedValues) {
                createJsonValue(containedValue, jsonValueBuilder);
                jsonValueBuilder.append(COMMA);
            }
            jsonValueBuilder.deleteCharAt(jsonValueBuilder.length() - 1);
            jsonValueBuilder.append(SQUARE_CLOSE_BRACKETS);
        } else if(List.class.isAssignableFrom(cls)){
            List<?> containedValues = (List<?>) obj;

            jsonValueBuilder.append(SQUARE_OPEN_BRACKETS);
            for (Object containedValue : containedValues) {
                createJsonValue(containedValue, jsonValueBuilder);
                jsonValueBuilder.append(COMMA);
            }
            jsonValueBuilder.deleteCharAt(jsonValueBuilder.length() - 1);
            jsonValueBuilder.append(SQUARE_CLOSE_BRACKETS);
        } else if(Collection.class.isAssignableFrom(cls) || Map.class.isAssignableFrom(cls)){
            throw new UnsupportedOperationException("At the moment this collection or map is unsupported");
        } else if (cls.getPackage().getName().startsWith("java")){
            throw new UnsupportedOperationException("At the moment this class is unsupported");
        } else{
            jsonValueBuilder.append(CURLY_OPEN_BRACKETS);
            Field[] fields = getFields(obj.getClass());
            for(Field field : fields){
                if(field.trySetAccessible()){
                    if(field.get(obj) == null){
                        continue;
                    }
                    jsonValueBuilder.append(prepareFieldName(field));

                    createJsonValue(field.get(obj), jsonValueBuilder);
                    jsonValueBuilder.append(COMMA);
                }
            }
            jsonValueBuilder.deleteCharAt(jsonValueBuilder.length() - 1);
            jsonValueBuilder.append(CURLY_CLOSE_BRACKETS);
        }
    }

    public String toJson(Object obj) throws IllegalAccessException {
        StringBuilder jsonValueBuilder = new StringBuilder();
        createJsonValue(obj, jsonValueBuilder);
        return jsonValueBuilder.toString();
    }

    private boolean isNumberOrBoolean(Class<?> type){
        return Number.class.isAssignableFrom(type)
                || Boolean.class.isAssignableFrom(type)
                || type.isPrimitive() && !char.class.isAssignableFrom(type);
    }

    private boolean isNumber(Class<?> type){
        return Number.class.isAssignableFrom(type)
                || type.isPrimitive()
                && !char.class.isAssignableFrom(type)
                && !boolean.class.isAssignableFrom(type);
    }
    private boolean isBoolean(Class<?> type){
        return Boolean.class.isAssignableFrom(type)
                || boolean.class.isAssignableFrom(type);
    }

    private boolean isStringOrCharacter(Class<?> type){
        return String.class.isAssignableFrom(type)
                || Character.class.isAssignableFrom(type)
                || char.class.isAssignableFrom(type);
    }
    private boolean isString(Class<?> type){
        return String.class.isAssignableFrom(type);
    }
    private boolean isCharacter(Class<?> type){
        return Character.class.isAssignableFrom(type)
                || char.class.isAssignableFrom(type);
    }

    private String prepareFieldName(Field field){
        return "\"" + field.getName() + "\":";
    }
}
