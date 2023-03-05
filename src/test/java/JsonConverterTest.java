import jsonconverter.GenericListType;
import jsonconverter.JsonConverter;
import model.Animal;
import model.User;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class JsonConverterTest {
    JsonConverter jsonConverter = new JsonConverter();
    String jsonUser = "{\"name\":\"Dastin\",\"age\":29,\"year\":2000,\"pets\":[{\"collarColor\":\"green\",\"name\":\"Caiery\",\"age\":3},{\"name\":\"Bashi\",\"age\":4},{\"name\":\"Arairie\",\"age\":9}],\"animals\":[{\"name\":\"Arairie\",\"age\":9},{\"name\":\"Bashi\",\"age\":4},{\"collarColor\":\"green\",\"name\":\"Caiery\",\"age\":3}],\"isHappy\":true,\"isMarried\":true,\"category\":\"a\",\"categoryWrapper\":\"C\"}";

    @Nested
    class FromJson{
        String jsonList = "[7,6,5]";
        String jsonSimpleObjectAnimal = "{\"name\":\"Bashi\",\"age\":4}";

        @Test
        void checkFromJsonShouldReturnByteValue16() {
            String json = "16";
            Byte actualValue;
            try {
                actualValue = jsonConverter.fromJson(json, Byte.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            assertThat(actualValue).isEqualTo((byte) 16);
        }
        @Test
        void checkFromJsonShouldReturnByteFromWrap() {
            String json = "16";
            try {
                var actualValue = jsonConverter.fromJson(json, Byte.class);
                assertThat(actualValue).isExactlyInstanceOf(Byte.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Test
        void checkFromJsonShouldReturnByteFromPrimitive() {
            String json = "16";
            try {
                var actualValue = jsonConverter.fromJson(json, byte.class);
                assertThat(actualValue).isExactlyInstanceOf(Byte.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Test
        void checkFromJsonShouldReturnShortValue16() {
            String json = "16";
            try {
                var actualValue = jsonConverter.fromJson(json, Short.class);
                assertThat(actualValue).isEqualTo((short)16);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        @Test
        void checkFromJsonShouldReturnShortFromWrap() {
            String json = "16";
            try {
                var actualValue = jsonConverter.fromJson(json, Short.class);
                assertThat(actualValue).isExactlyInstanceOf(Short.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Test
        void checkFromJsonShouldReturnShortFromPrimitive() {
            String json = "16";
            try {
                var actualValue = jsonConverter.fromJson(json, short.class);
                assertThat(actualValue).isExactlyInstanceOf(Short.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Test
        void checkFromJsonShouldReturnIntegerValue() {
            String json = "16";
            try {
                var actualValue = jsonConverter.fromJson(json, Integer.class);
                assertThat(actualValue).isEqualTo(16);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Test
        void checkFromJsonShouldReturnIntegerFromWrap() {
            String json = "16";
            try {
                var actualValue = jsonConverter.fromJson(json, Integer.class);
                assertThat(actualValue).isExactlyInstanceOf(Integer.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Test
        void checkFromJsonShouldReturnIntegerFromPrimitive() {
            String json = "16";
            try {
                var actualValue = jsonConverter.fromJson(json, int.class);
                assertThat(actualValue).isExactlyInstanceOf(Integer.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Test
        void checkFromJsonShouldReturnLongValue() {
            String json = "16";
            try {
                var actualValue = jsonConverter.fromJson(json, Long.class);
                assertThat(actualValue).isEqualTo(16L);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Test
        void checkFromJsonShouldReturnLongFromWrap() {
            String json = "16";
            try {
                var actualValue = jsonConverter.fromJson(json, Long.class);
                assertThat(actualValue).isExactlyInstanceOf(Long.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Test
        void checkFromJsonShouldReturnLongFromPrimitive() {
            String json = "16";
            try {
                var actualValue = jsonConverter.fromJson(json, long.class);
                assertThat(actualValue).isExactlyInstanceOf(Long.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Test
        void checkFromJsonShouldReturnDoubleValue() {
            String jsonDouble = "7.16";
            Double actualDouble;
            try {
                actualDouble = jsonConverter.fromJson(jsonDouble, Double.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            assertThat(actualDouble).isEqualTo(7.16);
        }
        @Test
        void checkFromJsonShouldReturnDoubleFromWrap() {
            String jsonDouble = "7.16";
            try {
                var actualValue = jsonConverter.fromJson(jsonDouble, Double.class);
                assertThat(actualValue).isExactlyInstanceOf(Double.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Test
        void checkFromJsonShouldReturnDoubleFromPrimitive() {
            String jsonDouble = "7.16";
            try {
                var actualValue = jsonConverter.fromJson(jsonDouble, double.class);
                assertThat(actualValue).isExactlyInstanceOf(Double.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Test
        void checkFromJsonShouldReturnFloatValue() {
            String json = "7.16";
            try {
                var actualValue = jsonConverter.fromJson(json, Float.class);
                assertThat(actualValue).isEqualTo(7.16f);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Test
        void checkFromJsonShouldReturnFloatFromWrap() {
            String json = "7.16";
            try {
                var actualValue = jsonConverter.fromJson(json, Float.class);
                assertThat(actualValue).isExactlyInstanceOf(Float.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Test
        void checkFromJsonShouldReturnFloatFromPrimitive() {
            String json = "7.16";
            try {
                var actualValue = jsonConverter.fromJson(json, float.class);
                assertThat(actualValue).isExactlyInstanceOf(Float.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Test
        void checkFromJsonShouldReturnBooleanValueTrue() {
            String json = "true";
            try {
                var actualValue = jsonConverter.fromJson(json, Boolean.class);
                assertThat(actualValue).isTrue();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Test
        void checkFromJsonShouldReturnBooleanValueFalse() {
            String json = "false";
            try {
                var actualValue = jsonConverter.fromJson(json, Boolean.class);
                assertThat(actualValue).isFalse();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Test
        void checkFromJsonShouldReturnBooleanFromWrap() {
            String json = "true";
            try {
                var actualValue = jsonConverter.fromJson(json, Boolean.class);
                assertThat(actualValue).isExactlyInstanceOf(Boolean.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Test
        void checkFromJsonShouldReturnBooleanFromPrimitive() {
            String json = "true";
            try {
                var actualValue = jsonConverter.fromJson(json, boolean.class);
                assertThat(actualValue).isExactlyInstanceOf(Boolean.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Test
        void checkFromJsonShouldReturnCharacterValue() {
            String json = "\"S\"";
            try {
                var actualValue = jsonConverter.fromJson(json, Character.class);
                assertThat(actualValue).isEqualTo('S');
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Test
        void checkFromJsonShouldReturnCharacterFromWrap() {
            String json = "\"M\"";
            try {
                var actualValue = jsonConverter.fromJson(json, Character.class);
                assertThat(actualValue).isExactlyInstanceOf(Character.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Test
        void checkFromJsonShouldReturnCharacterFromPrimitive() {
            String json = "\"L\"";
            try {
                var actualValue = jsonConverter.fromJson(json, char.class);
                assertThat(actualValue).isExactlyInstanceOf(Character.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Test
        void checkFromJsonShouldReturnStringValue() {
            String json = "\"What a life, what a night\"";
            try {
                var actualValue = jsonConverter.fromJson(json, String.class);
                assertThat(actualValue).isEqualTo("What a life, what a night");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Test
        void checkFromJsonShouldReturnString() {
            String json = "\"What a life, what a night\"";
            try {
                var actualValue = jsonConverter.fromJson(json, String.class);
                assertThat(actualValue).isExactlyInstanceOf(String.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Test
        void checkFromJsonShouldReturnNull() {
            String json = "null";
            try {
                var actualValue = jsonConverter.fromJson(json, String.class);
                assertThat(actualValue).isNull();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Test
        void checkFromJsonShouldReturnArrayWithNull() {
            String jsonListWithNull = "[7,null,5]";
            Integer[] expectedArray = {7, null, 5};
            try {
                var actualValue = jsonConverter.fromJson(jsonListWithNull, Integer[].class);
                assertThat(actualValue).isEqualTo(expectedArray);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Test
        void checkFromJsonShouldReturnIntegerArrayValue(){
            try {
                var actualValue = jsonConverter.fromJson(jsonList, Integer[].class);
                assertThat(actualValue).isEqualTo(new Integer[]{7, 6, 5});
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Test
        void checkFromJsonShouldReturnIntArrayValue(){
            try {
                var actualValue = jsonConverter.fromJson(jsonList, int[].class);
                assertThat(actualValue).isEqualTo(new int[]{7, 6, 5});
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Test
        void checkFromJsonShouldReturnIntegerArray(){
            try {
                var actualValue = jsonConverter.fromJson(jsonList, Integer[].class);
                assertThat(actualValue).isExactlyInstanceOf(Integer[].class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Test
        void checkFromJsonShouldReturnIntListValue(){
            List<Integer> expectedList = new ArrayList<>();
            expectedList.add(7);
            expectedList.add(6);
            expectedList.add(5);
            try {
                Type type =  new GenericListType<List<Integer>>(){}.getType();
                List<Integer> actualValue = jsonConverter.fromJson(jsonList, type);
                assertThat(actualValue).isEqualTo(expectedList);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Test
        void checkFromJsonShouldReturnAnimalValue(){
            Animal expectedAnimal = new Animal("Bashi", 4);
            try {
                var actualValue = jsonConverter.fromJson(jsonSimpleObjectAnimal, Animal.class);
                assertThat(actualValue).isEqualTo(expectedAnimal);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Test
        void checkFromJsonShouldReturnAnimal(){
            try {
                var actualValue = jsonConverter.fromJson(jsonSimpleObjectAnimal, Animal.class);
                assertThat(actualValue).isExactlyInstanceOf(Animal.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Test
        void checkFromJsonShouldReturnUserValue(){
            Animal animal1 = new Animal("Arairie", 9);
            Animal animal2 = new Animal("Bashi", 4);
            Animal animal3 = new Animal("Caiery", 3);
            List<Animal> pets = List.of(animal3, animal2, animal1);
            Animal[] animals = {animal1, animal2, animal3};
            User expectedUser = new User("Dastin", 29, 2000, pets, animals,
                    true, true, 'a', 'C');
            try {
                var actualUser = jsonConverter.fromJson(jsonUser, User.class);
                assertThat(actualUser).isEqualTo(expectedUser);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        @Test
        void checkFromJsonShouldReturnUser(){
            try {
                var actualUser = jsonConverter.fromJson(jsonUser, User.class);
                assertThat(actualUser).isExactlyInstanceOf(User.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Nested
    class ToJson {
        private static Stream<Number> provideIntegerPrimitiveNumbers() {
            return Stream.of(
                    (byte) 16, (short) 16, 16, 16L
            );
        }
        private static Stream<Number> provideFractionalPrimitiveNumbers() {
            return Stream.of(
                    16.0, 16.0f
            );
        }

        @ParameterizedTest
        @MethodSource("provideIntegerPrimitiveNumbers")
        void checkToJsonShouldReturnJsonForIntegerNumber(Number num) {
            String actualJson;
            try {
                actualJson = jsonConverter.toJson(num);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            assertThat(actualJson).isEqualTo("16");
        }
        @ParameterizedTest
        @MethodSource("provideFractionalPrimitiveNumbers")
        void checkToJsonShouldReturnJsonForFractionalNumber(Number num) {
            String actualJson;
            try {
                actualJson = jsonConverter.toJson(num);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            assertThat(actualJson).isEqualTo("16.0");
        }

        @ParameterizedTest
        @CsvSource({"true,true", "false,false"})
        void checkToJsonShouldReturnJsonForBoolean(boolean b, String expectedJson) {
            String actualJson;
            try {
                actualJson = jsonConverter.toJson(b);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            assertThat(actualJson).isEqualTo(expectedJson);
        }

        @Test
        void checkToJsonShouldReturnJsonForChar(){
            String actualJson;
            try {
                actualJson = jsonConverter.toJson('a');
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            assertThat(actualJson).isEqualTo("\"a\"");
        }
        @Test
        void checkToJsonShouldReturnJsonForString(){
            String actualJson;
            try {
                actualJson = jsonConverter.toJson("dinosaur");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            assertThat(actualJson).isEqualTo("\"dinosaur\"");
        }
        @Test
        void checkToJsonShouldReturnJsonForNull(){
            String actualJson;
            try {
                actualJson = jsonConverter.toJson(null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            assertThat(actualJson).isEqualTo("null");
        }

        @Test
        void checkToJsonShouldReturnJsonForIntegerArray(){
            String actualJson;
            try {
                actualJson = jsonConverter.toJson(new Integer[]{1,2,3});
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            assertThat(actualJson).isEqualTo("[1,2,3]");
        }
        @Test
        void checkToJsonShouldReturnJsonForIntegerList(){
            String actualJson;
            try {
                actualJson = jsonConverter.toJson(List.of(1,2,3));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            assertThat(actualJson).isEqualTo("[1,2,3]");
        }

        @Test
        void checkToJsonShouldReturnJsonForAnimal(){
            Animal animal = new Animal("Bashi", 4);
            String actualJson;
            try {
                actualJson = jsonConverter.toJson(animal);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            assertThat(actualJson).isEqualTo("{\"name\":\"Bashi\",\"age\":4}");
        }

        @Test
        void checkFromJsonShouldReturnJsonForUser(){
            String expectedJson = "{\"name\":\"Dastin\",\"age\":29,\"year\":2000,\"pets\":[{\"name\":\"Caiery\",\"age\":3},{\"name\":\"Bashi\",\"age\":4},{\"name\":\"Arairie\",\"age\":9}],\"animals\":[{\"name\":\"Arairie\",\"age\":9},{\"name\":\"Bashi\",\"age\":4},{\"name\":\"Caiery\",\"age\":3}],\"isHappy\":true,\"isMarried\":true,\"category\":\"a\",\"categoryWrapper\":\"C\"}";
            String actualJson;
            Animal animal1 = new Animal("Arairie", 9);
            Animal animal2 = new Animal("Bashi", 4);
            Animal animal3 = new Animal("Caiery", 3);
            List<Animal> pets = List.of(animal3, animal2, animal1);
            Animal[] animals = {animal1, animal2, animal3};
            User user = new User("Dastin", 29, 2000, pets, animals,
                    true, true, 'a', 'C');
            try {

                actualJson = jsonConverter.toJson(user);
                assertThat(actualJson).isEqualTo(expectedJson);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Test
        void checkFromJsonShouldReturnJsonForUserWithNullPets(){
            String expectedJson = "{\"name\":\"Dastin\",\"age\":29,\"year\":2000,\"animals\":[{\"name\":\"Arairie\",\"age\":9},{\"name\":\"Bashi\",\"age\":4},{\"name\":\"Caiery\",\"age\":3}],\"isHappy\":true,\"isMarried\":true,\"category\":\"a\",\"categoryWrapper\":\"C\"}";
            String actualJson;
            Animal animal1 = new Animal("Arairie", 9);
            Animal animal2 = new Animal("Bashi", 4);
            Animal animal3 = new Animal("Caiery", 3);
            Animal[] animals = {animal1, animal2, animal3};
            User user = new User("Dastin", 29, 2000, null, animals,
                    true, true, 'a', 'C');
            try {
                actualJson = jsonConverter.toJson(user);
                assertThat(actualJson).isEqualTo(expectedJson);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
