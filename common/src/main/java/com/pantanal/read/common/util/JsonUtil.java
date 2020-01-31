package com.pantanal.read.common.util;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class JsonUtil {

    private static ObjectMapper objectMapper;
    private static final String EXCLUDE = "EXCLUDE";
    private static final String INCLUDE = "INCLUDE";
    //yyyy-MM-dd'T'HH:mm:ss.SSSZ
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @JsonFilter("EXCLUDE")
    interface Exclude {
    }

    @JsonFilter("INCLUDE")
    interface Include {
    }

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.setDateFormat(new SimpleDateFormat(DATE_TIME_FORMAT));
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ISO_LOCAL_TIME));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ISO_LOCAL_TIME));
        objectMapper.registerModule(javaTimeModule).registerModule(new ParameterNamesModule());
    }


    /**
     * @return
     */
    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /**
     * @param dateTimeFormatter
     * @return
     */
    public static ObjectMapper getObjectMapper(String dateTimeFormatter) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.setDateFormat(new SimpleDateFormat(dateTimeFormatter));
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormatter)));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ISO_LOCAL_TIME));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(dateTimeFormatter)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ISO_LOCAL_TIME));
        objectMapper.registerModule(javaTimeModule).registerModule(new ParameterNamesModule());
        return objectMapper;
    }

    /**
     * @param object
     * @return
     */
    public static String objectToString(Object object) {
        return objectToString(getObjectMapper(), object);
    }

    /**
     * @param objectMapper
     * @param object
     * @return
     */
    public static String objectToString(ObjectMapper objectMapper, Object object) {
        if (object == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error("objectToString error! object:{}", object, e);
            return null;
        }
    }

    /**
     * @param object
     * @param exludeProperty
     * @param <T>
     * @return
     */
    public static <T extends JsonNode> T objectToJson(ObjectMapper objectMapper, Object object, String... exludeProperty) {
        if (object == null) {
            return null;
        }
        try {
            if (exludeProperty != null) {
                return filter(objectMapper.copy(), object.getClass(), exludeProperty, null).valueToTree(object);
            } else {
                return objectMapper.valueToTree(object);
            }
        } catch (Exception e) {
            log.error("objectToTree error! object:{}", object, e);
            return null;
        }
    }


    /**
     * @param object
     * @param exludeProperty
     * @param <T>
     * @return
     */
    public static <T extends JsonNode> T objectToJson(Object object, String... exludeProperty) {
        return objectToJson(getObjectMapper(), object, exludeProperty);
    }


    /**
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T stringToObject(String json, Class<T> type) {
        if (json == null) {
            return null;
        }
        try {
            return objectMapper.readValue(json, type);
        } catch (Exception e) {
            try {
                json = JSONObject.fromObject(json, JSONHelper.getDefaultConfig()).toString();
                return objectMapper.readValue(json, type);
            } catch (Exception e1) {
                log.error("stringToObject Class error! class:{}, json:{}", type, json, e);
                log.error("stringToObject Class error! class:{}, json:{}", type, json, e1);
                return null;
            }
        }
    }

    /**
     * @param json
     * @return
     */
    public static JsonNode stringToJson(String json) {
        if (json == null) {
            return null;
        }
        try {
            return objectMapper.readTree(json);
        } catch (Exception e) {
            log.error("stringToTree to JsonNode error! json:{}", json, e);
            return null;
        }
    }


    /**
     * @param n
     * @param valueType
     * @param <T>
     * @return
     */
    public static <T> T jsonToObject(TreeNode n, Class<T> valueType) {
        if (n == null) {
            return null;
        }
        try {
            return objectMapper.treeToValue(n, valueType);
        } catch (Exception e) {
            log.error("treeToObject error! object:{}", n, e);
            return null;
        }
    }


    public static ObjectNode objectNode() {
        return objectMapper.createObjectNode();
    }


    public static ArrayNode arrayNode() {
        return objectMapper.createArrayNode();
    }

    /**
     * @param clazz
     * @param exclude property names join in ','
     * @param include property names join in ','
     * @return
     */
    private static ObjectMapper filter(ObjectMapper objectMapper, Class<?> clazz, String[] exclude, String[] include) {
        if (clazz == null) {
            return objectMapper;
        }
        if (include != null && include.length > 0) {
            objectMapper.setFilterProvider(new SimpleFilterProvider().addFilter(INCLUDE,
                    SimpleBeanPropertyFilter.filterOutAllExcept(include)));
            objectMapper.addMixIn(clazz, Include.class);
        }
        if (exclude != null && exclude.length > 0) {
            objectMapper.setFilterProvider(new SimpleFilterProvider().addFilter(EXCLUDE,
                    SimpleBeanPropertyFilter.serializeAllExcept(exclude)));
            objectMapper.addMixIn(clazz, Exclude.class);
        }
        return objectMapper;
    }


    /**
     * 处理json格式中的双引号，把双引号替换为单引号，以便能正常解析json字符串
     *
     * @param json
     * @return
     */
    public static String handlerDoubleQuotationInJson(String json) {
        String result = json;
        Pattern p = Pattern.compile("[^{\\[:,]\\\"[^}\\]:,]");
        Matcher m = p.matcher(json);
        boolean isReturn = true;
        while (m.find()) {
            if (m.group().startsWith("\"\"")) {
                result = result.substring(0, m.start()) + result.substring(m.start(), m.end()).replaceFirst("\\\"\\\"", "\"'") + result.substring(m.end());
            } else {
                result = result.substring(0, m.start()) + result.substring(m.start(), m.end()).replaceFirst("\\\"", "'") + result.substring(m.end());
            }
            isReturn = false;
        }
        if (isReturn) {
            return result;
        } else {
            return handlerDoubleQuotationInJson(result);
        }
    }

    public static void main(String[] args) {

        File file = new File("/1469988c761c000.txt");

        String s = "[{\"text\":\"封面\",\"pageno\":\"1\"},{\"text\":\"版权页\",\"pageno\":\"3\"},{\"text\":\"再版前言\",\"pageno\":\"4\"},{\"text\":\"前言\",\"pageno\":\"6\"},{\"text\":\"目录\",\"pageno\":\"8\"},{\"text\":\"Part One 成长教育\",\"pageno\":\"12\",\"children\":[{\"text\":\"Unit 1 Excellence Is Not An Act,But A Habit\",\"pageno\":\"13\"},{\"text\":\"Unit 2 Rich Dad,Poor Dad\",\"pageno\":\"16\"},{\"text\":\"Unit 3 Healthy Diet Means Better School Performance\",\"pageno\":\"19\"},{\"text\":\"Unit 4 Feed Your Mind\",\"pageno\":\"22\"},{\"text\":\"Unit 5 You're Not Special\",\"pageno\":\"25\"},{\"text\":\"Unit 6 Unhappier Marriages For Kids of Divorce?\",\"pageno\":\"29\"},{\"text\":\"Unit 7 No One Can Be Perfect\",\"pageno\":\"32\"},{\"text\":\"Unit 8 What Is The Equation For A Happy,Healthy Teen?\",\"pageno\":\"35\"},{\"text\":\"Unit 9 The Language Of Music\",\"pageno\":\"38\"},{\"text\":\"Unit 10 Learning:A Lifelong Career\",\"pageno\":\"41\"},{\"text\":\"Unit 11 Ten Golden Rules To Young People From Bill Gates\",\"pageno\":\"44\"},{\"text\":\"Unit 12 Knowledge And Virtue\",\"pageno\":\"47\"},{\"text\":\"Unit 13 Great Expense On Private Tutors By Asian Parents\",\"pageno\":\"50\"},{\"text\":\"Unit 14 Moms Pass On Experience Without Even Trying\",\"pageno\":\"53\"},{\"text\":\"Unit 15 Children From Poor Families Are More Likely To Eat Junk Food\",\"pageno\":\"56\"},{\"text\":\"Unit 16 If I Rest,I Rust\",\"pageno\":\"58\"},{\"text\":\"Unit 17 Top Parenting Info:Be Consistent\",\"pageno\":\"61\"},{\"text\":\"Unit 18 Three Days To See\",\"pageno\":\"64\"}]},{\"text\":\"Part Two 财富金融\",\"pageno\":\"68\",\"children\":[{\"text\":\"Unit 1 No Global Recession But Risks Rise\",\"pageno\":\"69\"},{\"text\":\"Unit 2 The US Dollar Will Lose Dominance By\",\"pageno\":\"72\"},{\"text\":\"Unit 3 $ 7.5 Million:The Threshold For \"Rich\"\",\"pageno\":\"76\"},{\"text\":\"Unit 4 A Third Of British Can Never Pay Back\",\"pageno\":\"79\"},{\"text\":\"Unit 5 The Rich Grew Richer After The Global Recession\",\"pageno\":\"82\"},{\"text\":\"Unit 6 The “Herding Effect” Makes Bargain Hunters Sign Up To Groupon\",\"pageno\":\"85\"},{\"text\":\"Unit 7 The Problems Brought To Singapore By Foreign Wealthy\",\"pageno\":\"89\"},{\"text\":\"Unit 8 All For One And One For All\",\"pageno\":\"92\"},{\"text\":\"Unit 9 Learn How To Manage Money\",\"pageno\":\"95\"},{\"text\":\"Unit 10 Eat The Rich?\",\"pageno\":\"98\"},{\"text\":\"Unit 11 Friends' Relationship May Cost You In Business\",\"pageno\":\"101\"},{\"text\":\"Unit 12 How To Come Up With A Good Brand Name\",\"pageno\":\"104\"},{\"text\":\"Unit 13 Asian,New National Interest\",\"pageno\":\"108\"},{\"text\":\"Unit 14 BRICS Bid For More Say In IMF\",\"pageno\":\"111\"}]},{\"text\":\"Part Three 异域风采\",\"pageno\":\"114\",\"children\":[{\"text\":\"Unit 1 Loving France\",\"pageno\":\"115\"},{\"text\":\"Unit 2 Elysee Palace\",\"pageno\":\"118\"},{\"text\":\"Unit 3 Converting Taxi Into A Hotel\",\"pageno\":\"121\"},{\"text\":\"Unit 4 British Pub Etiquette\",\"pageno\":\"124\"},{\"text\":\"Unit 5 Serving A Sunday Feast\",\"pageno\":\"127\"},{\"text\":\"Unit 6 A White Horse-Hair Wig In Court\",\"pageno\":\"130\"},{\"text\":\"Unit 7 Freshers Week In UK\",\"pageno\":\"133\"},{\"text\":\"Unit 8 Happy Fourth Of July!\",\"pageno\":\"136\"},{\"text\":\"Unit 9 American Superstitions\",\"pageno\":\"139\"},{\"text\":\"Unit 10 Customer Service\",\"pageno\":\"142\"},{\"text\":\"Unit 11 Raising Children In America\",\"pageno\":\"145\"},{\"text\":\"Unit 12 Venetian Vacation\",\"pageno\":\"149\"},{\"text\":\"Unit 13 Do As The Romans Did?\",\"pageno\":\"152\"},{\"text\":\"Unit 14 Bhutan:Hidden Lands Of Happiness\",\"pageno\":\"155\"},{\"text\":\"Unit 15 A Little Country With Pretty\",\"pageno\":\"159\"},{\"text\":\"Unit 16 Neanderthals Were Sophisticated\",\"pageno\":\"162\"},{\"text\":\"Unit 17 Chinese Etiquette\",\"pageno\":\"165\"},{\"text\":\"Unit 18 How Neanderthals Fought Disease\",\"pageno\":\"169\"},{\"text\":\"Unit 19 Don't BeAn April Fool!\",\"pageno\":\"172\"},{\"text\":\"Unit 20 What Is Christmas About?\",\"pageno\":\"174\"},{\"text\":\"Unit 21 The Origin Of Christmas\",\"pageno\":\"178\"},{\"text\":\"Unit 22 Father's Day\",\"pageno\":\"181\"},{\"text\":\"Unit 23 Greek Is land Shows Signs Of Volcanic Unrest\",\"pageno\":\"183\"},{\"text\":\"Unit 24 Gentle Butterfly Battle\",\"pageno\":\"186\"}]},{\"text\":\"Part Four 涓涓真情\",\"pageno\":\"190\",\"children\":[{\"text\":\"Unit 1 Please Do Not Snuff Out The Candle\",\"pageno\":\"191\"},{\"text\":\"Unit 2 Juliette Drouet To Victor Hugo\",\"pageno\":\"194\"},{\"text\":\"Unit 3 The Nails And The Fence\",\"pageno\":\"196\"},{\"text\":\"Unit 4 George Washington To His Wife\",\"pageno\":\"198\"},{\"text\":\"Unit 5 Shelley To Elizabeth Hitchhiker\",\"pageno\":\"200\"},{\"text\":\"Unit 6 Napoleon To Josephine\",\"pageno\":\"204\"},{\"text\":\"Unit 7 Wise Enough To Seethe Little Surprise In Life\",\"pageno\":\"207\"},{\"text\":\"Unit 8 Children's Presents\",\"pageno\":\"211\"},{\"text\":\"Unit 9 Give But Do Not Expect In Love\",\"pageno\":\"214\"},{\"text\":\"Unit 10 Bettine Brentano To Goethe\",\"pageno\":\"216\"},{\"text\":\"Unit 11 A Box Full Of Kisses\",\"pageno\":\"219\"},{\"text\":\"Unit 12 Wild Flowers\",\"pageno\":\"222\"},{\"text\":\"Unit 13 Back Home\",\"pageno\":\"225\"},{\"text\":\"Unit 14 Roses For Rose\",\"pageno\":\"228\"},{\"text\":\"Unit 15 The Value Of Mother's Love\",\"pageno\":\"231\"},{\"text\":\"Unit 16 The Color Of Love\",\"pageno\":\"233\"},{\"text\":\"Unit 17 World Of Smiles\",\"pageno\":\"236\"},{\"text\":\"Unit 18 It's Just Where I Am\",\"pageno\":\"239\"},{\"text\":\"Unit 19 Love Is Everything\",\"pageno\":\"243\"},{\"text\":\"Unit 20 Running In The Rain\",\"pageno\":\"246\"},{\"text\":\"Unit 21 One Girl Changed My Life\",\"pageno\":\"250\"},{\"text\":\"Unit 22 A Woman's Tears\",\"pageno\":\"254\"},{\"text\":\"Unit 23 Send The Peace Of Goodness To Others\",\"pageno\":\"257\"},{\"text\":\"Unit 24 You Can'tBe Replaced\",\"pageno\":\"259\"}]},{\"text\":\"Part Five 灵智心语\",\"pageno\":\"262\",\"children\":[{\"text\":\"Unit 1 The Art Of Forgiveness\",\"pageno\":\"263\"},{\"text\":\"Unit 2 I Want! I Do! I Get!\",\"pageno\":\"266\"},{\"text\":\"Unit 3 On Motes And Beams\",\"pageno\":\"269\"},{\"text\":\"Unit 4 Persistence Pays\",\"pageno\":\"272\"},{\"text\":\"Unit 5 Just For Today\",\"pageno\":\"275\"},{\"text\":\"Unit 6 If I Had My Life To Live Over...\",\"pageno\":\"278\"},{\"text\":\"Unit 7 The Road Of Life\",\"pageno\":\"280\"},{\"text\":\"Unit 8 Get A Thorough Understanding Of Oneself\",\"pageno\":\"282\"},{\"text\":\"Unit 9 People In Your Life\",\"pageno\":\"285\"},{\"text\":\"Unit 10 No Pain No Success\",\"pageno\":\"288\"},{\"text\":\"Unit 11 The Love Of Beauty\",\"pageno\":\"291\"},{\"text\":\"Unit 12 It Matters To This One\",\"pageno\":\"294\"},{\"text\":\"Unit 13 Catch The Star That Will Take You To Your Dreams\",\"pageno\":\"297\"},{\"text\":\"Unit 14 Autumn—The Harvest Season\",\"pageno\":\"299\"},{\"text\":\"Unit 15 Youth\",\"pageno\":\"302\"},{\"text\":\"Unit 16 You Have Only One Life\",\"pageno\":\"305\"},{\"text\":\"Unit 17 Ambition\",\"pageno\":\"308\"},{\"text\":\"Unit 18 There Is One Day\",\"pageno\":\"312\"},{\"text\":\"Unit 19 Excuse\",\"pageno\":\"315\"},{\"text\":\"Unit 20 If I Were A Boy Again\",\"pageno\":\"318\"},{\"text\":\"Unit 21 My Love\",\"pageno\":\"321\"},{\"text\":\"Unit 22 What Will Matter?\",\"pageno\":\"324\"},{\"text\":\"Unit 23 Future Can Be Anything\",\"pageno\":\"327\"},{\"text\":\"Unit 24 Wisdom In Lose And Get\",\"pageno\":\"330\"},{\"text\":\"Unit 25 Random Thoughts On The Window\",\"pageno\":\"333\"},{\"text\":\"Unit 26 Never Give Up\",\"pageno\":\"336\"},{\"text\":\"Unit 27 Enjoy Loneliness\",\"pageno\":\"339\"},{\"text\":\"Unit 28 Home On The Way\",\"pageno\":\"343\"},{\"text\":\"Unit 29 Air And Opportunity\",\"pageno\":\"346\"},{\"text\":\"Unit 30 Love Your Life\",\"pageno\":\"348\"},{\"text\":\"Unit 31 What I Have Lived For\",\"pageno\":\"351\"},{\"text\":\"Unit 32 Companionship Of Books\",\"pageno\":\"354\"},{\"text\":\"Unit 33 Have Faith,And Expect The Best\",\"pageno\":\"357\"},{\"text\":\"Unit 34 Try To Enjoy Every Moment Of Your Life\",\"pageno\":\"359\"},{\"text\":\"Unit 35 It All Depends On What You're Listening for\",\"pageno\":\"362\"},{\"text\":\"Unit 36 The Secret To Success\",\"pageno\":\"365\"},{\"text\":\"Unit 37 Who You MetIn The Those Beautiful Moments\",\"pageno\":\"368\"},{\"text\":\"Unit 38 Life's A Cafeteria\",\"pageno\":\"371\"},{\"text\":\"Unit 39 Hope Is Right There Above Your Head\",\"pageno\":\"373\"},{\"text\":\"Unit 40 Gifts From The Heart\",\"pageno\":\"375\"}]},{\"text\":\"封底\",\"pageno\":\"378\"}]";
        try {
            s = FileUtils.readFileToString(file, "utf-8");
            //s = "{\"text\":\"\"DUST HATH CLOSED HELEN＇S EYE\"\",\"pageno\":\"46\"}";
            System.out.println(s);
            System.out.println(handlerDoubleQuotationInJson(s));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //s = s.replaceAll("[^{\\[:,]\\\"[^}\\]:,]","'");

    }
}
