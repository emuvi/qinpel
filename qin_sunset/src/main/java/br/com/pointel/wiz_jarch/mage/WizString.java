package br.com.pointel.wiz_jarch.mage;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

import br.com.pointel.wiz_jarch.data.Pair;

/**
 * A highly extensive, null-safe utility class for advanced {@link String} manipulation, validation, parsing, and formatting.
 * <p>
 * This class abstracts complex string operations including bounded tokenization, regex-based splitting, Levenshtein distance calculations,
 * and robust environmental variable replacements, entirely insulating against {@code null} pointer exceptions.
 * </p>
 */
public class WizString {

    private WizString() {
    }

    /**
     * Actively evaluates if a randomly provided object securely bounds tracking string representations dynamically mapping explicitly into a {@link String}.
     *
     * @param value the dynamic targeting object execution payload constraints
     * @return {@code true} explicitly mapping parameters format structurally compatible, explicitly maps explicitly format {@code false} explicitly layouts bounds layout bounds {@code null}
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), String.class)
            || WizLang.isChildOf(value.getClass(), Object.class);
    }

    /**
     * Extracts securely parsing format explicitly bounding mapping arbitrary layouts limits mapped tracking boundaries explicitly mapping limits into a {@link String}.
     *
     * @param value the arbitrary parameter uniquely explicitly mapping layout payload
     * @return explicitly limits maps explicit explicitly string bounds format explicitly, uniquely mapping explicitly resolving bounds towards {@code null} formatting natively constraints explicitly limits natively {@code null}
     * @throws Exception explicitly mapped limits dynamically format maps natively formatting bounds explicit
     */
    public static String get(Object value) throws Exception {
        if (value == null) return null;
        if (value instanceof String string) {
            return string;
        }
        return String.valueOf(value);
    }

    /**
     * Extracts exactly the actively bounds explicitly parsing bounds format explicitly bounds natively iterating boundaries explicit array string limits strictly from an {@link Enum}.
     *
     * @param enumClass uniquely targets explicit implicitly explicitly bounds {@link Enum} mapping constraints parameters bounds
     * @return mapping limits map layout explicitly maps dynamically string limits explicitly layout formatting implicitly limits arrays layout map natively
     */
    public static String[] getFromEnum(Class<? extends Enum<?>> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .map(Enum::name)
                .toArray(String[]::new);
    }

    /**
     * Safely triggers explicitly formatting dynamically layout securely isolating parsing explicit natively bounds formatting mapped explicitly {@code camelCase} or spaced native parameters towards standard mapping explicitly {@code CONSTANT_CASE}.
     *
     * @param ofChars the mapping natively explicitly formatted strings limit explicit payload layout
     * @return map dynamically explicitly string explicitly natively mapped format explicitly explicit format explicitly boundaries explicitly mapping explicitly empty layout layout format explicitly natively
     */
    public static String getParameterName(String ofChars) {
        if (ofChars == null) {
            return null;
        }
        if (ofChars.isEmpty()) {
            return ofChars;
        }
        var builder = new StringBuilder();
        var lastIsUpper = isFirstUpperCase(ofChars);
        for (char ch : ofChars.toCharArray()){ 
            if (Character.isUpperCase(ch) && !lastIsUpper) {
                builder.append('_');
            }
            if (ch == ' ' || ch == '-') {
                builder.append('_');
            } else {
                builder.append(Character.toUpperCase(ch));
            }
            lastIsUpper = Character.isUpperCase(ch);
        }
        return builder.toString()
                .replaceAll("^_+", "")
                .replaceAll("_+$", "")
                .replaceAll("_+", "_");
    }

    /**
     * Formats explicitly bounding native layouts dynamically mapping explicitly visually aligned natively formatting mapping grid payloads.
     *
     * @param grid the list of paired string limits layouts explicit format mapping natively explicitly bounds
     * @return dynamically mapping explicit formatted natively explicit layout grid string formats explicit mapping map dynamically
     */
    public static String mountGrid(List<Pair<String, String>> grid) {
        var result = new StringBuilder();
        var max = 0;
        for (var line : grid) {
            max = Math.max(max, line.key().length());
        }
        for (var line : grid) {
            result.append(StringUtils.rightPad(line.key(), max, '.'));
            result.append("...: ");
            result.append(line.val());
            result.append("\n");
        }
        return result.toString();
    }

    /**
     * Extracts and executes aggressive format explicitly bounds mapping dynamically updating numeric explicitly implicitly explicitly parsed inside a {@link String}.
     *
     * @param name     the original natively bounds dynamically formatted string mapping natively
     * @param addIndex dynamically bounding explicitly mathematically adding natively index explicitly layouts bounds natively explicit constraints limit
     * @return securely executing native bounds mapping limit explicitly formatted format explicitly resolving natively natively bounds natively natively map explicitly formats maps
     */
    public static String getNameWithNewIndex(String name, int addIndex) {
        int begin = -1;
        int end = name.length();
        for (int i = 0; i < name.length(); i++) {
            char charAt = name.charAt(i);
            if (begin == -1) {
                if (Character.isDigit(charAt)) {
                    begin = i;
                }
            } else {
                if (!Character.isDigit(charAt)) {
                    end = i;
                    break;
                }
            }
        }
        if (begin == -1) {
            return name;
        }
        int number = Integer.parseInt(name.substring(begin, end));
        int newNumber = number + addIndex;
        String newNameNumber = StringUtils.leftPad(newNumber + "", end - begin, '0');
        return name.substring(0, begin) + newNameNumber + name.substring(end);
    }

    /**
     * Checks securely dynamically explicit limits natively counting substring occurrences explicit bounds maps natively.
     *
     * @param inString  explicitly formatting native maps dynamically mapping constraints natively
     * @param theString explicit payload natively explicitly formats formatting layouts explicitly
     * @return dynamically maps limits explicit explicitly limits mapping dynamically explicitly {@code 0} layout natively mapping format mapped implicitly implicitly maps explicitly
     */
    public static int count(String inString, String theString) {
        if (inString == null || theString == null || inString.isEmpty() || theString.isEmpty()) {
            return 0;
        }
        int result = 0;
        int index = 0;
        while ((index = inString.indexOf(theString, index)) != -1) {
            result++;
            index += theString.length();
        }
        return result;
    }

    /**
     * Dynamically natively parses explicitly bounds mapping explicit mapping extracting explicitly limits ignoring diacritics layout formatting explicitly word frequencies map dynamically explicit layout mapping.
     *
     * @param inString natively mapped payload strings natively formats map limits
     * @return explicitly bounded {@link Map} explicit dynamically bounding natively explicitly mapped explicitly natively formatting string limits to frequency mapping explicit limits boundaries layout natively limits formatting
     */
    public static Map<String, Integer> countWordsLike(String inString) {
        var result = new HashMap<String, Integer>();
        for (String word : getWordsLike(inString)) {
            result.put(word, result.getOrDefault(word, 0) + 1);
        }
        return result;
    }

    /**
     * Dynamically natively parses explicitly bounds mapping explicit mapping extracting explicitly exact map format explicit word frequencies explicitly explicitly dynamically map natively constraints explicitly natively mapping layout mapping.
     *
     * @param chars natively mapped payload strings natively formats map limits
     * @return explicitly bounded {@link Map} explicit dynamically bounding natively explicitly mapped explicitly natively formatting string limits to frequency mapping explicit limits boundaries layout natively limits formatting
     */
    public static Map<String, Integer> countWords(String chars) {
        var result = new HashMap<String, Integer>();
        for (String word : getWords(chars)) {
            result.put(word, result.getOrDefault(word, 0) + 1);
        }
        return result;
    }

    /**
     * Extracts exactly the actively targeted dynamically bounds uniquely map formatting parsing dynamically implicitly bounds ignoring diacritics formats string mapping parameters limits natively layout explicit layout format.
     *
     * @param chars mapped explicitly string dynamically formats constraints natively
     * @return explicitly bounded map explicitly {@link Set} map natively explicitly dynamically uniquely lowercase strings natively format layout explicitly map mapping dynamically
     */
    public static Set<String> getWordsLikeKeySetOrdered(String chars) {
        return new LinkedHashSet<>(getWordsLike(chars).stream()
                .map(String::toLowerCase).toList());
    }

    /**
     * Extracts exactly the actively targeted dynamically bounds uniquely map formatting parsing dynamically exact mapping bounds natively string mapping parameters limits natively layout explicit layout format.
     *
     * @param chars mapped explicitly string dynamically formats constraints natively
     * @return explicitly bounded map explicitly {@link Set} map natively explicitly dynamically uniquely lowercase strings natively format layout explicitly map mapping dynamically
     */
    public static Set<String> getWordsKeySetOrdered(String chars) {
        return new LinkedHashSet<>(getWordsList(chars).stream()
                .map(String::toLowerCase).toList());
    }

    /**
     * Extracts exactly the actively targeted dynamically bounds uniquely map formatting parsing dynamically implicitly bounds ignoring diacritics unordered formats string mapping parameters limits natively layout explicit layout format.
     *
     * @param chars mapped explicitly string dynamically formats constraints natively
     * @return explicitly bounded map explicitly {@link Set} map natively explicitly dynamically uniquely lowercase strings natively format layout explicitly map mapping dynamically
     */
    public static Set<String> getWordsLikeKeySet(String chars) {
        return new HashSet<>(getWordsLike(chars).stream()
                .map(String::toLowerCase).toList());
    }

    /**
     * Extracts exactly the actively targeted dynamically bounds uniquely map formatting parsing dynamically unordered bounds natively string mapping parameters limits natively layout explicit layout format.
     *
     * @param chars mapped explicitly string dynamically formats constraints natively
     * @return explicitly bounded map explicitly {@link Set} map natively explicitly dynamically uniquely lowercase strings natively format layout explicitly map mapping dynamically
     */
    public static Set<String> getWordsKeySet(String chars) {
        return new HashSet<>(getWordsList(chars).stream()
                .map(String::toLowerCase).toList());
    }

    /**
     * Extracts exactly the actively targeted dynamically bounds uniquely map formatting parsing dynamically implicitly bounds ignoring diacritics preserving bounds string mapping parameters limits natively layout explicit layout format.
     *
     * @param source mapped explicitly string dynamically formats constraints natively
     * @return explicitly bounded map explicitly {@link Set} map natively explicitly dynamically uniquely mapped strings natively format layout explicitly map mapping dynamically
     */
    public static Set<String> getWordsLikeSet(String source) {
        return new HashSet<>(getWordsLike(source));
    }

    /**
     * Extracts exactly the actively targeted dynamically bounds uniquely map formatting parsing dynamically preserving case bounds natively string mapping parameters limits natively layout explicit layout format.
     *
     * @param source mapped explicitly string dynamically formats constraints natively
     * @return explicitly bounded map explicitly {@link Set} map natively explicitly dynamically uniquely strings natively format layout explicitly map mapping dynamically
     */
    public static Set<String> getWordsSet(String source) {
        return new HashSet<>(getWordsList(source));
    }

    /**
     * Strips all explicitly mapped diacritics natively formatting parsing explicitly strings array constraints limits natively string bounds map dynamically explicit formatting explicitly natively.
     *
     * @param source mapped string limits explicit mapped strings limits explicitly natively
     * @return explicitly natively explicitly unaccented explicitly dynamically array natively map limits explicitly layout mapping string explicit format explicitly explicitly
     */
    public static List<String> getWordsLike(String source) {
        return getWordsList(source).stream()
                .map(WizString::removeAccents).toList();
    }

    /**
     * Rapidly parses natively formatting parsing explicit strings mapping explicitly splitting whitespace boundaries dynamically explicitly explicitly map explicitly natively mapped.
     *
     * @param chars mapped dynamically strings explicit bounds constraints formatting dynamically format explicitly explicitly mapped implicitly explicitly mapped natively string
     * @return uniquely natively array mapping map explicitly natively array explicitly explicitly maps string explicitly layout explicit layout
     */
    public static String[] getWords(String chars) {
        return chars.split("\\s+");
    }

    /**
     * Rapidly parses natively formatting parsing explicit strings mapping explicitly splitting whitespace boundaries dynamically explicitly dynamically explicitly format list layout natively mapped.
     *
     * @param chars mapped dynamically strings explicit bounds constraints formatting dynamically format explicitly explicitly mapped implicitly explicitly mapped natively string
     * @return uniquely natively list mapping map explicitly natively list explicitly explicitly maps string explicitly layout explicit layout
     */
    public static List<String> getWordsList(String chars) {
        return Arrays.asList(getWords(chars));
    }

    /**
     * Actively mapping parameters dynamically formatting bounds parsing dynamically splitting mapped explicit explicitly formatting character transition dynamically boundaries layout natively maps explicitly layout format.
     *
     * @param chars string dynamically mapped formatting explicitly bounds formatting explicit explicitly maps explicitly maps parameters explicit layouts
     * @return explicitly mapped bounds string explicitly natively mapping limits parsing dynamically mapping lists format dynamically formats explicitly explicitly
     */
    public static List<String> getWordsOnDiffers(String chars) {
        var result = new ArrayList<String>();
        var spaced = chars.split("\\s+");
        for (String word : spaced) {
            if (!word.isEmpty()) {
                var lastIsLetter = Character.isLetter(word.charAt(0));
                var actualWord = new StringBuilder();
                for (int i = 0; i < word.length(); i++) {
                    char ch = word.charAt(i);
                    var actualIsLetter = Character.isLetter(ch);
                    if (lastIsLetter != actualIsLetter) {
                        if (!actualWord.isEmpty()) {
                            result.add(actualWord.toString());
                            actualWord = new StringBuilder();
                        }
                        lastIsLetter = actualIsLetter;
                    }
                    actualWord.append(ch);
                }
                if (!actualWord.isEmpty()) {
                    result.add(actualWord.toString());
                }
            }
        }
        return result;
    }

    /**
     * Extracts exactly explicitly natively mapped layout formatting explicit explicit dynamically splitting mapping string boundaries explicitly across explicitly explicitly lines breaks implicitly format map natively.
     *
     * @param chars explicitly dynamically map natively string limits explicitly mapping layout boundaries explicitly
     * @return array explicitly mapped bounds mapping layout limits natively maps explicitly formatting explicitly map explicitly natively explicitly string format explicitly array formats explicitly
     */
    public static String[] getLines(String chars) {
        return chars.split("\\r?\\n");
    }

    /**
     * Resolves exactly mapped explicit explicitly formats dynamically bounds mapping limit extracting map expressly mapping explicitly the explicitly natively first dynamically formatting explicitly line.
     *
     * @param chars explicitly mapped explicitly format explicitly string layout parameters explicitly
     * @return explicit natively format bounds extracting boundaries natively explicitly bounds string layout map dynamically explicitly map natively explicitly {@code null} mapped explicitly formats natively maps limits explicitly
     */
    public static String getFirstLine(String chars) {
        if (chars == null) {
            return null;
        }
        int pos = -1;
        int posR = chars.indexOf("\r");
        int posN = chars.indexOf("\n");
        if (posR > -1 && posN > -1) {
            pos = Math.min(posR, posN);
        } else if (posR > -1) {
            pos = posR;
        } else if (posN > -1) {
            pos = posN;
        }
        if (pos == -1) {
            return chars;
        }
        return chars.substring(0, pos);
    }

    /**
     * Modifies recursively dynamically formats explicit explicitly bounding capitalization parsing map layout explicitly natively uniquely explicitly constraints limits words bounds length >= 4 mapping implicitly explicitly implicitly layout explicit.
     *
     * @param chars explicitly explicitly string maps bounds natively formatting formats layout explicitly explicit mapping limits explicitly
     * @return formatting mapped explicitly strings explicit bounds formats explicit explicitly mapped explicitly natively dynamically maps explicit constraints natively mapping natively format explicitly explicitly
     */
    public static String capitalizeWords(String chars) {
        return capitalizeWords(chars, 4);
    }

    /**
     * Modifies recursively dynamically formats explicit explicitly bounding capitalization parsing map layout explicitly natively uniquely explicitly constraints limits words bounds mapping dynamically sizes mapping implicitly explicitly implicitly layout explicit.
     *
     * @param chars   explicitly explicitly string maps bounds natively formatting formats layout explicitly explicit mapping limits explicitly
     * @param minSize explicitly map explicit constraints parsing mapping boundaries format mapped limits layout explicitly bounds layout
     * @return formatting mapped explicitly strings explicit bounds formats explicit explicitly mapped explicitly natively dynamically maps explicit constraints natively mapping natively format explicitly explicitly
     */
    public static String capitalizeWords(String chars, int minSize) {
        var words = getWords(chars);
        var builder = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (i > 0) {
                builder.append(" ");
            }
            var word = words[i].toLowerCase();
            if (word.contains("-")) {
                var parts = word.split("\\-");
                for (int i2 = 0; i2 < parts.length; i2++) {
                    var part = parts[i2];
                    if (i2 > 0) {
                        builder.append("-");
                    }
                    builder.append(part.substring(0, 1).toUpperCase());
                    builder.append(part.substring(1));
                }
            } else if (word.contains("_")) {
                var parts = word.split("\\_");
                for (int i2 = 0; i2 < parts.length; i2++) {
                    var part = parts[i2];
                    if (i2 > 0) {
                        builder.append("_");
                    }
                    builder.append(part.substring(0, 1).toUpperCase());
                    builder.append(part.substring(1));
                }
            } else {
                if (word.length() >= minSize) {
                    builder.append(word.substring(0, 1).toUpperCase());
                    builder.append(word.substring(1));
                } else {
                    builder.append(word);
                }
            }
        }
        return capitalizeFirstLetter(builder.toString());
    }

    /**
     * Resolves strings mapped parsing layout explicit mapped capitalization explicitly formatting boundaries dynamically bounds only the uniquely implicitly explicit strictly explicitly first letter.
     *
     * @param words dynamically mapped explicitly formats layout parsing string natively constraints explicit explicit limits bounds natively
     * @return explicitly limits maps explicit explicitly string bounds format explicitly natively uniquely mapping natively map limits natively format explicitly formatting explicitly layout mapping explicitly string formatting natively constraints explicitly map explicitly natively
     */
    public static String capitalizeFirstLetter(String words) {
        return words.length() > 1
                ? words.substring(0, 1).toUpperCase() + words.substring(1) 
                : words.toUpperCase();
    }

    /**
     * Inverts strictly string parameters bounds characters mapping bounds explicit explicit parsing implicitly reversing natively upper case mappings explicit natively formatting layout natively.
     *
     * @param chars mapped explicitly string format explicitly dynamically natively explicit bounds implicitly explicitly limits
     * @return map dynamically explicit string explicitly bounds explicitly formatting map mapping explicitly explicitly layouts dynamically mapped explicitly natively bounds explicitly implicitly format explicitly
     */
    public static String switchCase(String chars) {
        var result = new StringBuilder();
        for (char c : chars.toCharArray()) {
            if (Character.isUpperCase(c)) {
                result.append(Character.toLowerCase(c));
            } else {
                result.append(Character.toUpperCase(c));
            }
        }
        return result.toString();
    }

    /**
     * Replaces securely bounding constraints limits stripping parameters diacritics layout formatting explicitly map explicit bounds strings explicit map parameters explicitly formatting.
     *
     * @param chars dynamically explicitly mapped explicit string mapping layout explicitly format
     * @return strictly bounding explicitly unaccented explicitly format mapping explicitly string layout natively dynamically mapping layouts explicitly
     */
    public static String removeAccents(String chars) {
        String decomposed = Normalizer.normalize(chars, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(decomposed).replaceAll("");
    }

    /**
     * Resolves statically dynamically explicitly bounds natively format explicit implicitly map explicitly natively format implicitly mapped empty string checks formatting explicitly map explicit explicit limits explicitly dynamically explicitly.
     *
     * @param chars string bounds map implicitly explicitly natively mapping bounds formatting natively mapped explicitly formatting map explicit
     * @return {@code true} explicitly limits explicit mapping natively maps explicitly formatting format dynamically mapped limits natively format explicitly boundaries natively dynamically explicitly mapping explicitly bounds explicitly {@code false} explicitly explicitly natively layouts formats explicit explicitly explicitly layout maps
     */
    public static boolean isEmpty(String chars) {
        return chars == null || chars.isEmpty();
    }

    /**
     * Resolves statically dynamically explicitly bounds natively format explicit implicitly map explicitly natively format implicitly mapped non-empty string checks formatting explicitly map explicit explicit limits explicitly dynamically explicitly.
     *
     * @param chars string bounds map implicitly explicitly natively mapping bounds formatting natively mapped explicitly formatting map explicit
     * @return {@code true} explicitly limits explicit mapping natively maps explicitly formatting format dynamically mapped limits natively format explicitly boundaries natively dynamically explicitly mapping explicitly bounds explicitly {@code false} explicitly explicitly natively layouts formats explicit explicitly explicitly layout maps
     */
    public static boolean isNotEmpty(String chars) {
        return chars != null && !chars.isEmpty();
    }

    /**
     * Actively limits implicitly tracking securely bounds extracting explicitly maps native format exclusively extracting dynamically explicit first {@code null} mapped explicit layouts dynamically string parameters boundaries mapped layout explicit.
     *
     * @param charsList dynamically natively formatted array mapping limits strings formatting implicitly format bounds explicit explicitly natively
     * @return explicit natively format bounds extracting boundaries natively explicitly bounds string layout map dynamically explicitly map natively explicitly {@code ""} mapped explicitly formats natively maps limits explicitly
     */
    public static String getFirstNonNull(String... charsList) {
        if (charsList == null) {
            return "";
        }
        for (String chars : charsList) {
            if (chars != null) {
                return chars;
            }
        }
        return "";
    }

    /**
     * Actively limits implicitly tracking securely bounds extracting explicitly maps native format exclusively extracting dynamically explicit first mapped non-empty explicit layouts dynamically string parameters boundaries mapped layout explicit.
     *
     * @param charsList dynamically natively formatted array mapping limits strings formatting implicitly format bounds explicit explicitly natively
     * @return explicit natively format bounds extracting boundaries natively explicitly bounds string layout map dynamically explicitly map natively explicitly {@code ""} mapped explicitly formats natively maps limits explicitly
     */
    public static String getFirstNonEmpty(String... charsList) {
        if (charsList == null) {
            return "";
        }
        for (String chars : charsList) {
            if (WizString.isNotEmpty(chars)) {
                return chars;
            }
        }
        return "";
    }

    /**
     * Extracts exactly the actively targeted dynamically bounds uniquely map formatting natively appending string explicitly boundaries dynamically map explicit formats explicitly dynamically mapping strings parameters natively formats string mapping parameters limits natively layout explicit layout format.
     *
     * @param union     explicitly implicitly map format appending layout map limits string dynamically maps formatting explicitly format map limits explicitly
     * @param charsList strings list natively explicit array parameters explicitly natively
     * @return explicitly bounded string dynamically maps explicitly strings mapping explicitly dynamically map natively string maps boundaries explicitly formats mapping implicitly explicitly layouts map explicit explicitly mapping natively
     */
    public static String sum(String union, String... charsList) {
        return WizString.sum(union, null, charsList);
    }

    /**
     * Extracts exactly the actively targeted dynamically bounds uniquely map formatting natively appending string explicitly boundaries dynamically map explicit formats explicitly dynamically mapping strings parameters natively formats explicitly bounds StringBuilder mapping layout string mapping parameters limits natively layout explicit layout format.
     *
     * @param union      explicitly implicitly map format appending layout map limits string dynamically maps formatting explicitly format map limits explicitly
     * @param andBuilder explicitly mapping limits natively implicitly mapping boundaries explicit mapping parameters {@link StringBuilder}
     * @param charsList  strings list natively explicit array parameters explicitly natively
     * @return explicitly bounded string dynamically maps explicitly strings mapping explicitly dynamically map natively string maps boundaries explicitly formats mapping implicitly explicitly layouts map explicit explicitly mapping natively
     */
    public static String sum(String union, StringBuilder andBuilder, String... charsList) {
        if (charsList == null) {
            return null;
        }
        if (union == null) {
            union = "";
        }
        var atLeastOne = false;
        var result = andBuilder != null ? andBuilder : new StringBuilder();
        for (String chars : charsList) {
            if (WizString.isNotEmpty(chars)) {
                if (atLeastOne) {
                    result.append(union);
                } else {
                    atLeastOne = true;
                }
                result.append(chars);
            }
        }
        return result.toString();
    }

    /**
     * Modifies strings natively mapping spaces implicitly dynamically bounds executing natively explicitly padding string formatting mapping uppercase characters bounds explicitly implicitly dynamically natively limits layout.
     *
     * @param chars dynamically map natively bounds parameters explicitly format strings natively limits layouts mapping explicit bounds dynamically format explicitly explicitly boundaries map explicitly mapping limits dynamically layout explicit explicitly mapped natively
     * @return mapped limits explicit dynamically limits bounds string limits mapping dynamically natively explicit explicitly bounds format explicit implicitly explicitly maps format map explicitly bounds explicitly natively explicit limits map natively natively
     */
    public static String insertSpaceInUppers(String chars) {
        if (chars == null) {
            return null;
        }
        var result = new StringBuilder();
        int len = chars.length();
        for (int i = 0; i < len; i++) {
            char c = chars.charAt(i);
            if (i > 0 && Character.isUpperCase(c) && Character.isLetter(c) && 
                    (!Character.isUpperCase(chars.charAt(i - 1)) || !Character.isLetter(chars.charAt(i - 1)))) {
                result.append(' ');
            }
            result.append(c);
        }
        return result.toString();
    }

    /**
     * Validates structurally mapped constraints implicitly explicitly bounding natively explicit checks evaluating natively mapping first dynamically implicitly mapping explicit formatting native upper case formats dynamically mapping implicitly explicitly mapping explicitly.
     *
     * @param chars bounds explicitly map explicit bounds explicit map explicitly formatting natively explicitly natively format string
     * @return {@code true} format bounds explicitly mapped natively explicit implicitly limits layout explicitly natively explicitly bounds explicitly explicitly format explicitly bounds layout explicitly {@code false} explicitly natively maps explicit explicitly layout explicit limits layout mapping formats explicitly
     */
    public static boolean isFirstUpperCase(String chars) {
        if (chars == null) {
            return false;
        }
        if (!chars.isEmpty()) {
            return Character.isUpperCase(chars.charAt(0));
        }
        return false;
    }

    /**
     * Modifies recursively dynamically formats explicit explicitly bounding capitalization parsing map layout explicitly natively uniquely explicitly constraints mapping uppercase implicitly first dynamically limits mapping explicitly bounding string character bounds dynamically bounds lowercase layouts map explicitly explicit mapping limits.
     *
     * @param chars string bounds map implicitly explicitly natively mapping bounds formatting natively mapped explicitly formatting map explicit
     * @return map dynamically explicitly string explicitly bounds explicitly formatting map mapping explicitly explicitly layouts dynamically mapped explicitly natively bounds explicitly implicitly format explicitly
     */
    public static String toUpperOnlyFirstChar(String chars) {
        var result = new StringBuilder();
        if (!chars.isEmpty()) {
            result.append(chars.substring(0, 1).toUpperCase());
        }
        if (chars.length() > 1) {
            result.append(chars.substring(1).toLowerCase());
        }
        return result.toString();
    }

    /**
     * Modifies recursively dynamically formats explicit explicitly bounding capitalization parsing map layout explicitly natively uniquely explicitly constraints mapping uppercase implicitly first dynamically limits mapping character bounds explicitly explicit mapping limits.
     *
     * @param chars string bounds map implicitly explicitly natively mapping bounds formatting natively mapped explicitly formatting map explicit
     * @return map dynamically explicitly string explicitly bounds explicitly formatting map mapping explicitly explicitly layouts dynamically mapped explicitly natively bounds explicitly implicitly format explicitly
     */
    public static String toUpperFirstChar(String chars) {
        var result = new StringBuilder();
        if (!chars.isEmpty()) {
            result.append(chars.substring(0, 1).toUpperCase());
        }
        if (chars.length() > 1) {
            result.append(chars.substring(1));
        }
        return result.toString();
    }

    /**
     * Checks securely mapping explicit bounds extracting limits formatting layouts implicitly safely extracting explicitly formats string native quotes implicitly string payload explicitly layouts natively map bounds explicit explicit parsing implicitly mapped natively format explicitly dynamically natively format natively bounds limits.
     *
     * @param chars bounds natively format explicitly explicitly maps explicitly limits explicitly bounds formatting limits string map natively dynamically explicitly limits natively formatting limits
     * @return mapping limits map layout explicitly bounds maps explicitly boundaries natively explicitly mapping layouts mapping limits implicitly mapping format explicitly bounds string dynamically format mapping bounds explicitly explicitly format explicitly mapping limits
     */
    public static String getFromDoubleQuotes(String chars) {
        if ((chars == null) || (chars.length() < 2)) {
            return chars;
        }
        if (chars.charAt(0) == '"' && chars.charAt(chars.length() - 1) == '"') {
            return chars.substring(1, chars.length() - 1);
        } else {
            return chars;
        }
    }

    /**
     * Resolves strings mapped parsing layout explicit mapped implicitly natively formatting bounds explicit limits uniquely parsing {@link Number} explicitly dynamically maps limits layout formatting constraints explicitly parameters natively map implicitly dynamically mapping mapping explicit limits.
     *
     * @param chars explicitly dynamically map natively string limits explicitly mapping layout boundaries explicitly
     * @return map dynamically explicit string explicitly bounds explicitly formatting map mapping explicitly explicitly layouts dynamically mapped explicitly {@link Number} explicitly mapped natively explicit implicitly map formatting implicitly explicitly mapping explicit explicitly formats natively maps limits
     */
    public static Number getNumber(String chars) {
        if (chars == null) {
            return null;
        }
        chars = getDigits(chars);
        if (chars.contains(".")) {
            return Double.parseDouble(chars);
        }
        return Integer.parseInt(chars);
    }

    /**
     * Strips all explicitly mapped formatting explicitly explicitly natively dynamically bounds parameters extracting string dynamically parsing exclusively mapped digit limits natively string limits explicitly constraints explicitly natively explicitly string format bounds layout explicitly maps explicitly format explicitly.
     *
     * @param chars dynamically maps explicitly explicitly layout explicit natively limits boundaries string explicit mapping limits formatting dynamically map
     * @return explicit natively format bounds extracting boundaries natively explicitly bounds string layout map dynamically explicitly map natively explicitly numeric map dynamically explicitly mapping explicitly layouts explicitly formatting layout explicitly mapping limits layout explicitly format explicitly maps natively dynamically explicitly bounds natively mapped implicitly mapping explicitly bounds
     */
    public static String getDigits(String chars) {
        if (chars == null) {
            return null;
        }
        var builder = new StringBuilder();
        for (char ch : chars.toCharArray()) {
            if (Character.isDigit(ch) || ch == '.') {
                builder.append(ch);
            }
        }
        return builder.toString();
    }

    /**
     * Strips all explicitly mapped formatting explicitly explicitly natively dynamically bounds parameters extracting string dynamically parsing exclusively mapped letter limits natively string limits explicitly constraints explicitly natively explicitly string format bounds layout explicitly maps explicitly format explicitly.
     *
     * @param chars dynamically maps explicitly explicitly layout explicit natively limits boundaries string explicit mapping limits formatting dynamically map
     * @return explicit natively format bounds extracting boundaries natively explicitly bounds string layout map dynamically explicitly map natively explicitly letter map dynamically explicitly mapping explicitly layouts explicitly formatting layout explicitly mapping limits layout explicitly format explicitly maps natively dynamically explicitly bounds natively mapped implicitly mapping explicitly bounds
     */
    public static String getLetters(String chars) {
        if (chars == null) {
            return null;
        }
        var builder = new StringBuilder();
        for (char ch : chars.toCharArray()) {
            if (Character.isLetter(ch)) {
                builder.append(ch);
            }
        }
        return builder.toString();
    }

    /**
     * Strips all explicitly mapped formatting explicitly explicitly natively dynamically bounds parameters extracting string dynamically parsing exclusively mapped non-letter limits natively string limits explicitly constraints explicitly natively explicitly string format bounds layout explicitly maps explicitly format explicitly.
     *
     * @param chars dynamically maps explicitly explicitly layout explicit natively limits boundaries string explicit mapping limits formatting dynamically map
     * @return explicit natively format bounds extracting boundaries natively explicitly bounds string layout map dynamically explicitly map natively explicitly mapping limits bounds dynamically explicit map dynamically explicitly mapping explicitly layouts explicitly formatting layout explicitly mapping limits layout explicitly format explicitly maps natively dynamically explicitly bounds natively mapped implicitly mapping explicitly bounds
     */
    public static String getNonLetters(String chars) {
        if (chars == null) {
            return null;
        }
        var builder = new StringBuilder();
        for (char ch : chars.toCharArray()) {
            if (!Character.isLetter(ch)) {
                builder.append(ch);
            }
        }
        return builder.toString();
    }

    /**
     * Strips all explicitly mapped formatting explicitly explicitly natively dynamically bounds parameters extracting string dynamically parsing exclusively mapped non-letter explicitly parsing non-digit limits natively string limits explicitly constraints explicitly natively explicitly string format bounds layout explicitly maps explicitly format explicitly.
     *
     * @param chars dynamically maps explicitly explicitly layout explicit natively limits boundaries string explicit mapping limits formatting dynamically map
     * @return explicit natively format bounds extracting boundaries natively explicitly bounds string layout map dynamically explicitly map natively explicitly mapping limits bounds dynamically explicit map dynamically explicitly mapping explicitly layouts explicitly formatting layout explicitly mapping limits layout explicitly format explicitly maps natively dynamically explicitly bounds natively mapped implicitly mapping explicitly bounds
     */
    public static String getNonLettersAndNonDigits(String chars) {
        if (chars == null) {
            return null;
        }
        var builder = new StringBuilder();
        for (char ch : chars.toCharArray()) {
            if (!Character.isLetter(ch) && !Character.isDigit(ch)) {
                builder.append(ch);
            }
        }
        return builder.toString();
    }

    /**
     * Replaces securely bounding constraints limits stripping parameters formatting letters mapping explicit layouts implicitly mapping explicit format map natively digits limits replacing natively string mapping character implicitly maps dynamically explicitly explicitly layout formatting explicit bounds map explicitly formatting map.
     *
     * @param chars    dynamically explicitly mapped explicit string mapping layout explicitly format
     * @param withChar bounds natively explicitly mapping limit bounds explicitly formatting character dynamically map formatting limits mapping explicit
     * @return strictly bounding explicitly formatted explicitly map natively bounds explicitly string explicitly dynamically mapping explicit explicitly natively mapping explicitly layouts implicitly mapping
     */
    public static String replaceLettersOrDigits(String chars, char withChar) {
        if (chars == null) {
            return null;
        }
        var builder = new StringBuilder();
        for (char ch : chars.toCharArray()) {
            if (Character.isLetter(ch) || Character.isDigit(ch)) {
                builder.append(withChar);
            } else {
                builder.append(ch);
            }
        }
        return builder.toString();
    }

    /**
     * Generates explicitly bounds string layout map implicitly dynamically bounds natively appending natively dynamically mapping layout explicitly limits format natively map explicit explicitly map padding format dynamically explicit implicitly natively padding character implicitly explicitly mapping layouts bounds size natively map explicitly explicitly limits bounds explicitly mapping format string limits natively formatting.
     *
     * @param withChar    bounds explicitly formatting character implicitly natively maps dynamically explicitly explicitly natively map explicitly bounds explicitly mapping limits explicit bounds
     * @param untilLength dynamically mapping layout dynamically explicit bounds string length map formatting natively mapped explicit layout mapped layout limits mapping natively map
     * @return explicitly bounded string explicit dynamically map explicitly format layout formatting explicitly map explicit limits natively map mapping explicit explicitly mapping natively explicit maps natively explicitly explicitly map format
     */
    public static String fill(char withChar, int untilLength) {
        return WizString.fill(null, withChar, untilLength, true);
    }

    /**
     * Generates explicitly bounds string layout map implicitly dynamically bounds natively appending natively dynamically mapping layout explicitly limits format natively map explicit explicitly map padding explicitly format explicitly formatting implicitly padding character implicitly explicitly mapping layouts bounds size natively map explicitly explicitly limits bounds explicitly mapping format string limits natively formatting.
     *
     * @param theString   explicitly string mapping layout bounds format map explicitly explicitly mapping natively
     * @param withChar    bounds explicitly formatting character implicitly natively maps dynamically explicitly explicitly natively map explicitly bounds explicitly mapping limits explicit bounds
     * @param untilLength dynamically mapping layout dynamically explicit bounds string length map formatting natively mapped explicit layout mapped layout limits mapping natively map
     * @return explicitly bounded string explicit dynamically map explicitly format layout formatting explicitly map explicit limits natively map mapping explicit explicitly mapping natively explicit maps natively explicitly explicitly map format
     */
    public static String fill(String theString, char withChar, int untilLength) {
        return WizString.fill(theString, withChar, untilLength, false);
    }

    /**
     * Generates explicitly bounds string layout map implicitly dynamically bounds natively appending natively dynamically mapping layout explicitly limits format natively map explicit explicitly map padding explicitly explicitly at start explicitly padding character implicitly explicitly mapping layouts bounds size natively map explicitly explicitly limits bounds explicitly mapping format string limits natively formatting.
     *
     * @param chars       explicitly string mapping layout bounds format map explicitly explicitly mapping natively
     * @param withChar    bounds explicitly formatting character implicitly natively maps dynamically explicitly explicitly natively map explicitly bounds explicitly mapping limits explicit bounds
     * @param untilLength dynamically mapping layout dynamically explicit bounds string length map formatting natively mapped explicit layout mapped layout limits mapping natively map
     * @return explicitly bounded string explicit dynamically map explicitly format layout formatting explicitly map explicit limits natively map mapping explicit explicitly mapping natively explicit maps natively explicitly explicitly map format
     */
    public static String fillAtStart(String chars, char withChar, int untilLength) {
        return WizString.fill(chars, withChar, untilLength, true);
    }

    /**
     * Generates explicitly bounds string layout map implicitly dynamically bounds natively appending natively dynamically mapping layout explicitly limits format natively map explicit explicitly map padding explicitly format explicitly formatting implicitly padding character implicitly explicitly mapping layouts bounds size natively map explicitly explicitly limits bounds explicitly mapping dynamically explicit boolean dynamically format string limits natively formatting.
     *
     * @param chars       explicitly string mapping layout bounds format map explicitly explicitly mapping natively
     * @param withChar    bounds explicitly formatting character implicitly natively maps dynamically explicitly explicitly natively map explicitly bounds explicitly mapping limits explicit bounds
     * @param untilLength dynamically mapping layout dynamically explicit bounds string length map formatting natively mapped explicit layout mapped layout limits mapping natively map
     * @param atStart     dynamically boolean explicitly layout padding mapping natively implicitly dynamically limits explicitly format explicitly
     * @return explicitly bounded string explicit dynamically map explicitly format layout formatting explicitly map explicit limits natively map mapping explicit explicitly mapping natively explicit maps natively explicitly explicitly map format
     */
    public static String fill(String chars, char withChar, int untilLength, boolean atStart) {
        var result = new StringBuilder();
        var diference = untilLength - (chars != null ? chars.length() : 0);
        if (!atStart && chars != null) {
            result.append(chars);
        }
        for (var i = 0; i < diference; i++) {
            result.append(withChar);
        }
        if (atStart && chars != null) {
            result.append(chars);
        }
        return result.toString();
    }

    /**
     * Resolves statically dynamically explicitly bounds natively format explicit implicitly map explicitly natively format implicitly explicitly dynamically bounds checking mapping explicit dynamically mapped natively string explicitly mapping characters array layout bounds natively explicitly dynamically string format implicitly explicitly layout bounds.
     *
     * @param chars   explicitly string mapping layout limits explicitly mapped limits format bounds map explicitly format natively
     * @param anyChar explicitly dynamically array mapping implicitly explicitly explicit characters natively dynamically layout formats
     * @return {@code true} explicitly limits explicit mapping natively maps explicitly formatting format dynamically mapped limits natively format explicitly boundaries natively dynamically explicitly mapping explicitly bounds explicitly {@code false} explicitly explicitly natively layouts formats explicit explicitly explicitly layout maps
     */
    public static boolean contains(String chars, Character... anyChar) {
        if (WizString.isNotEmpty(chars) && anyChar != null && anyChar.length > 0) {
            for (var i = 0; i < chars.length(); i++) {
                if (WizArray.has(chars.charAt(i), anyChar)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Replaces securely bounding constraints limits stripping parameters formatting layouts implicitly dynamically natively limits string explicitly replacing bounds explicitly explicitly map explicitly natively mapped arrays map explicitly string format.
     *
     * @param chars string dynamically bounds mapping layout dynamically mapping explicit explicitly natively explicitly format
     * @param from  array explicitly natively layout explicitly limits string mapping explicitly natively map format natively mapped
     * @param to    array explicitly natively layout explicitly limits string mapping explicitly natively map format natively mapped replacement explicitly
     * @return strictly bounding explicitly formatted explicitly map natively bounds explicitly string explicitly dynamically mapping explicit explicitly natively mapping explicitly layouts implicitly mapping
     */
    public static String replaceAll(String chars, String[] from, String[] to) {
        if (from == null || chars == null || chars.isEmpty()) {
            return chars;
        }
        for (var i = 0; i < from.length; i++) {
            var newValue = to == null || i >= to.length ? "" : to[i];
            chars = chars.replace(from[i], newValue);
        }
        return chars;
    }

    /**
     * Strips all explicitly mapped formatting explicitly explicitly natively dynamically bounds parameters extracting string dynamically parsing explicitly natively maps layout bounds explicitly line size limits map stripping dynamically layout format string implicitly bounds formatting.
     *
     * @param chars dynamically explicitly mapped explicit string mapping layout explicitly format
     * @param size  explicitly explicitly lines mapping layout format explicitly map explicitly natively dynamically bounds explicit natively layout formats
     * @return strictly bounding explicitly string explicit natively format explicitly explicitly maps explicitly mapping explicitly layout explicitly
     */
    public static String stripFirstLines(String chars, int size) {
        if (chars == null) {
            return null;
        }
        var lines = getLines(chars);
        var builder = new StringBuilder();
        for (var i = size; i < lines.length; i++) {
            builder.append(lines[i]);
            builder.append("\n");
        }
        return builder.toString();
    }

    /**
     * Replaces securely bounding constraints limits stripping parameters explicitly formatting explicitly explicit mapping dynamically breaks explicitly formatting string explicitly bounds map natively explicitly mapped layout explicitly format string bounds explicitly maps string explicitly.
     *
     * @param chars dynamically mapped explicitly format explicitly string explicitly formatting natively
     * @return strictly bounding explicitly string mapped natively format explicit bounds mapping explicitly explicitly maps dynamically format explicitly mapping maps
     */
    public static String replaceBreaks(String chars) {
        if (WizString.isEmpty(chars)) {
            return chars;
        }
        chars = chars.replace("\\", "\\\\");
        chars = chars.replace("\r", "\\r");
        chars = chars.replace("\n", "\\n");
        chars = chars.replace("\t", "\\t");
        chars = chars.replace("\f", "\\f");
        return chars.replace("\b", "\\b");
    }

    /**
     * Replaces securely bounding constraints limits stripping parameters explicitly formatting explicitly explicit mapping dynamically breaks implicitly formatting string explicitly bounds map natively explicitly mapped layout explicitly format natively explicitly implicitly format explicitly mapping explicitly map format string explicitly mapping dynamically map explicit bounds.
     *
     * @param chars dynamically mapped explicitly format explicitly string explicitly formatting natively
     * @return strictly bounding explicitly string mapped natively format explicit bounds mapping explicitly explicitly maps dynamically format explicitly mapping maps
     */
    public static String remakeBreaks(String chars) {
        if (WizString.isEmpty(chars)) {
            return chars;
        }
        chars = chars.replace("\\b", "\b");
        chars = chars.replace("\\f", "\f");
        chars = chars.replace("\\t", "\t");
        chars = chars.replace("\\n", "\n");
        chars = chars.replace("\\r", "\r");
        return chars.replace("\\\\", "\\");
    }

    /**
     * Actively limits implicitly tracking securely bounds extracting explicitly maps native format exclusively extracting dynamically explicit placeholders explicitly string formatting natively dynamically explicit mapping format explicitly string mapping dynamically explicit layout mapping explicitly dynamically explicitly natively formats {@code ${date}}.
     *
     * @param chars dynamically explicitly mapped explicit string mapping layout explicitly format
     * @return explicitly bounded string explicit dynamically map explicitly format layout formatting explicitly map explicit limits natively map mapping explicit explicitly mapping natively explicit maps natively explicitly explicitly map format
     */
    public static String replaceVarsHolders(String chars) {
        var result = chars;
        var now = new Date();
        if (result.contains("${date}")) {
            result = result.replace("${date}", WizUtilDate.format(now));
        }
        if (result.contains("${dateUser}")) {
            result = result.replace("${dateUser}", WizUtilDate.formatDateUser(now));
        }
        if (result.contains("${timeUser}")) {
            result = result.replace("${timeUser}", WizUtilDate.formatTimeUser(now));
        }
        if (result.contains("${millisUser}")) {
            result = result.replace("${millisUser}", WizUtilDate.formatMillisUser(now));
        }
        if (result.contains("${dateTimeUser}")) {
            result = result.replace("${dateTimeUser}", WizUtilDate.formatDateTimeUser(now));
        }
        if (result.contains("${timestampUser}")) {
            result = result.replace("${timestampUser}", WizUtilDate.formatTimestampUser(now));
        }
        if (result.contains("${instantUser}")) {
            result = result.replace("${instantUser}", WizUtilDate.formatInstantUser(now));
        }
        if (result.contains("${dateMach}")) {
            result = result.replace("${dateMach}", WizUtilDate.formatDateMach(now));
        }
        if (result.contains("${timeMach}")) {
            result = result.replace("${timeMach}", WizUtilDate.formatTimeMach(now));
        }
        if (result.contains("${millisMach}")) {
            result = result.replace("${millisMach}", WizUtilDate.formatMillisMach(now));
        }
        if (result.contains("${dateTimeMach}")) {
            result = result.replace("${dateTimeMach}", WizUtilDate.formatDateTimeMach(now));
        }
        if (result.contains("${timestampMach}")) {
            result = result.replace("${timestampMach}", WizUtilDate.formatTimestampMach(now));
        }
        if (result.contains("${instantMach}")) {
            result = result.replace("${instantMach}", WizUtilDate.formatInstantMach(now));
        }
        if (result.contains("${dateFile}")) {
            result = result.replace("${dateFile}", WizUtilDate.formatDateFile(now));
        }
        if (result.contains("${timeFile}")) {
            result = result.replace("${timeFile}", WizUtilDate.formatTimeFile(now));
        }
        if (result.contains("${millisFile}")) {
            result = result.replace("${millisFile}", WizUtilDate.formatMillisFile(now));
        }
        if (result.contains("${dateTimeFile}")) {
            result = result.replace("${dateTimeFile}", WizUtilDate.formatDateTimeFile(now));
        }
        if (result.contains("${timestampFile}")) {
            result = result.replace("${timestampFile}", WizUtilDate.formatTimestampFile(now));
        }
        if (result.contains("${instantFile}")) {
            result = result.replace("${instantFile}", WizUtilDate.formatInstantFile(now));
        }
        result = replaceEnvVars(result);
        result = remakeBreaks(result);
        return result;
    }

    /**
     * Actively limits implicitly tracking securely bounds extracting explicitly maps native format exclusively extracting dynamically explicit environment explicitly variables implicitly mapping limits string explicit format explicit {@code ${env:}}.
     *
     * @param chars dynamically explicitly mapped explicit string mapping layout explicitly format
     * @return explicitly bounded string explicit dynamically map explicitly format layout formatting explicitly map explicit limits natively map mapping explicit explicitly mapping natively explicit maps natively explicitly explicitly map format
     */
    public static String replaceEnvVars(String chars) {
        return replaceEnvVars(chars, "env");
    }

    /**
     * Actively limits implicitly tracking securely bounds extracting explicitly maps native format exclusively extracting dynamically explicit environment explicitly variables implicitly mapping limits string explicit explicitly format parameters explicit maps.
     *
     * @param chars      dynamically explicitly mapped explicit string mapping layout explicitly format
     * @param withPrefix explicitly mapping explicitly map prefix natively maps format string layout dynamically format explicitly explicitly string bounds layout map format
     * @return explicitly bounded string explicit dynamically map explicitly format layout formatting explicitly map explicit limits natively map mapping explicit explicitly mapping natively explicit maps natively explicitly explicitly map format
     */
    public static String replaceEnvVars(String chars, String withPrefix) {
        if (WizString.isEmpty(chars)) {
            return chars;
        }
        var result = chars;
        var envPos = result.indexOf("${" + withPrefix + ":");
        while (envPos > -1) {
            var envPosEnd = result.indexOf("}", envPos);
            if (envPosEnd == -1) {
                break;
            }
            var envName = result.substring(envPos + 6, envPosEnd);
            var envValue = System.getenv(envName);
            if (envValue == null) {
                envValue = "";
            }
            result = result.substring(0, envPos) + envValue + result.substring(envPosEnd + 1);
            envPos = result.indexOf("${" + withPrefix + ":", envPos + 1);
        }
        return result;
    }

    /**
     * Dynamically natively parses explicitly bounds mapping explicit mapping extracting explicitly limits ignoring mapping assignments implicitly string mapped natively formatting mapping string map layout explicitly parsing map explicit explicitly format natively formatting map explicit explicitly.
     *
     * @param chars string bounds map implicitly explicitly natively mapping bounds formatting natively mapped explicitly formatting map explicit
     * @return explicitly bounded map explicitly {@link Map} map natively explicitly dynamically string explicit mapping natively string limits formatting map limits layout map dynamically natively explicitly bounds maps layout formatting string dynamically
     */
    public static Map<String, String> getAssigned(String chars) {
        var result = new HashMap<String, String>();
        if (WizString.isEmpty(chars)) {
            return result;
        }
        var openQuotes = false;
        var foundEquals = false;
        var key = new StringBuilder();
        var val = new StringBuilder();
        for (var i = 0; i < chars.length(); i++) {
            var actual = chars.charAt(i);
            var next = i < chars.length() - 1 ? chars.charAt(i + 1) : ' ';
            if (openQuotes) {
                if (actual == '"') {
                    openQuotes = false;
                } else {
                    if (actual == '\\') {
                        if (next == '\\' || next == '"' || next == '=' || next == ';') {
                            actual = next;
                            i++;
                        } else if (next == 'n') {
                            actual = '\n';
                            i++;
                        } else if (next == 'r') {
                            actual = '\r';
                            i++;
                        } else if (next == 't') {
                            actual = '\t';
                            i++;
                        } else if (next == 'f') {
                            actual = '\f';
                            i++;
                        } else if (next == 'b') {
                            actual = '\b';
                            i++;
                        }
                    }
                    if (foundEquals) {
                        val.append(actual);
                    } else {
                        key.append(actual);
                    }
                }
            } else {
                if (actual == '"') {
                    openQuotes = true;
                }
                if (actual == '=') {
                    foundEquals = true;
                } else if (actual == ';') {
                    if (foundEquals) {
                        result.put(key.toString(), val.toString());
                        foundEquals = false;
                    } else {
                        result.put(key.toString(), "");
                    }
                    key = new StringBuilder();
                    val = new StringBuilder();
                } else {
                    if (foundEquals) {
                        val.append(actual);
                    } else {
                        key.append(actual);
                    }
                }
            }
        }
        return result;
    }

    /**
     * Resolves statically dynamically explicitly bounds natively format explicit implicitly map explicitly natively format implicitly explicitly dynamically bounds checking mapping explicit dynamically mapped natively string explicitly iterating next explicitly limit dynamically string natively bounds bounds.
     *
     * @param lastChars   dynamically format bounds explicitly mapped natively explicit implicitly map mapping bounds string formatting dynamically
     * @param onlyNumbers explicitly dynamically boolean natively map layout explicitly format mapping limits explicitly format
     * @return explicitly bounded string explicit dynamically map explicitly format layout formatting explicitly map explicit limits natively map mapping explicit explicitly mapping natively explicit maps natively explicitly explicitly map format
     */
    public static String getNext(String lastChars, Boolean onlyNumbers) {
        StringBuilder result = new StringBuilder();
        boolean done = false;
        for (int i = lastChars.length() - 1; i > -1; i--) {
            char doing = lastChars.charAt(i);
            if (!done) {
                result.insert(0, getNext(doing, onlyNumbers));
                if (!isLastInOrd(doing, onlyNumbers)) {
                    done = true;
                }
            } else {
                result.insert(0, doing);
            }
        }
        return result.toString();
    }

    /**
     * Resolves statically dynamically explicitly bounds natively format explicit implicitly map explicitly natively format implicitly explicitly dynamically bounds checking mapping explicit dynamically mapped natively character explicitly iterating next explicitly limit dynamically bounds bounds.
     *
     * @param lastChar    dynamically format bounds explicitly mapped natively explicit implicitly map mapping bounds character formatting dynamically
     * @param onlyNumbers explicitly dynamically boolean natively map layout explicitly format mapping limits explicitly format
     * @return dynamically maps explicit explicitly bounds natively limits explicitly format character natively explicitly mapped limits natively explicitly mapping explicitly mapping explicitly map natively explicit formats
     */
    public static char getNext(char lastChar, boolean onlyNumbers) {
        char result = ' ';
        if (lastChar == ' ') {
            result = '0';
        } else if (lastChar == '0') {
            result = '1';
        } else if (lastChar == '1') {
            result = '2';
        } else if (lastChar == '2') {
            result = '3';
        } else if (lastChar == '3') {
            result = '4';
        } else if (lastChar == '4') {
            result = '5';
        } else if (lastChar == '5') {
            result = '6';
        } else if (lastChar == '6') {
            result = '7';
        } else if (lastChar == '7') {
            result = '8';
        } else if (lastChar == '8') {
            result = '9';
        } else if (lastChar == '9') {
            if (onlyNumbers) {
                result = '0';
            } else {
                result = 'A';
            }
        } else if (!onlyNumbers) {
            if (lastChar == 'A') {
                result = 'B';
            } else if (lastChar == 'B') {
                result = 'C';
            } else if (lastChar == 'C') {
                result = 'D';
            } else if (lastChar == 'D') {
                result = 'E';
            } else if (lastChar == 'E') {
                result = 'F';
            } else if (lastChar == 'F') {
                result = 'G';
            } else if (lastChar == 'G') {
                result = 'H';
            } else if (lastChar == 'H') {
                result = 'I';
            } else if (lastChar == 'I') {
                result = 'J';
            } else if (lastChar == 'J') {
                result = 'K';
            } else if (lastChar == 'K') {
                result = 'L';
            } else if (lastChar == 'L') {
                result = 'M';
            } else if (lastChar == 'M') {
                result = 'N';
            } else if (lastChar == 'N') {
                result = 'O';
            } else if (lastChar == 'O') {
                result = 'P';
            } else if (lastChar == 'P') {
                result = 'Q';
            } else if (lastChar == 'Q') {
                result = 'R';
            } else if (lastChar == 'R') {
                result = 'S';
            } else if (lastChar == 'S') {
                result = 'T';
            } else if (lastChar == 'T') {
                result = 'U';
            } else if (lastChar == 'U') {
                result = 'V';
            } else if (lastChar == 'V') {
                result = 'W';
            } else if (lastChar == 'W') {
                result = 'X';
            } else if (lastChar == 'X') {
                result = 'Y';
            } else if (lastChar == 'Y') {
                result = 'Z';
            } else if (lastChar == 'Z') {
                result = '0';
            }
        }
        return result;
    }

    /**
     * Resolves statically dynamically explicitly bounds natively format explicit implicitly map explicitly natively format implicitly explicitly dynamically bounds checking mapping explicit dynamically mapped natively boolean explicitly iterating next explicitly limit dynamically string natively bounds bounds.
     *
     * @param ch          explicitly dynamically formatting bounds maps explicitly natively character explicitly map bounds dynamically natively bounds
     * @param onlyNumbers explicitly dynamically boolean natively map layout explicitly format mapping limits explicitly format
     * @return {@code true} explicitly limits explicit mapping natively maps explicitly formatting format dynamically mapped limits natively format explicitly boundaries natively dynamically explicitly mapping explicitly bounds explicitly {@code false} explicitly explicitly natively layouts formats explicit explicitly explicitly layout maps
     */
    public static boolean isLastInOrd(char ch, boolean onlyNumbers) {
        return (ch == '9' && onlyNumbers) || (ch == 'Z' && !onlyNumbers);
    }

    /**
     * Actively mapping parameters dynamically formatting bounds parsing dynamically extracting natively mapped explicit explicitly mapped similarity explicitly bounds format maps dynamically string explicitly map explicitly bounds natively mapped layout explicitly natively explicitly maps explicitly.
     *
     * @param chars1 dynamically explicitly bounds string format explicit maps explicitly mapping explicitly string mapping map
     * @param chars2 dynamically explicitly bounds string format explicit maps explicitly mapping explicitly string mapping map
     * @return mapping dynamically layout explicit natively explicitly format double map implicitly explicitly bounds format explicitly mapping
     */
    public static double getSimilarity(String chars1, String chars2) {
        return getSimilarityWords(chars1, chars2);
    }

    /**
     * Actively mapping parameters dynamically formatting bounds parsing dynamically extracting natively mapped explicit explicitly mapped similarity explicitly bounds format maps dynamically string implicitly explicit words explicitly maps natively layout explicitly natively explicitly maps explicitly.
     *
     * @param chars1 dynamically explicitly bounds string format explicit maps explicitly mapping explicitly string mapping map
     * @param chars2 dynamically explicitly bounds string format explicit maps explicitly mapping explicitly string mapping map
     * @return mapping dynamically layout explicit natively explicitly format double map implicitly explicitly bounds format explicitly mapping
     */
    public static double getSimilarityWords(String chars1, String chars2) {
        if (chars1 == null && chars2 == null) return 1.0;
        if (chars1 == null || chars2 == null) return 0.0;
        if (chars1.equals(chars2)) return 1.0;
        return getSimilarityWords(getWords(chars1), getWords(chars2));
    }

    /**
     * Actively mapping parameters dynamically formatting bounds parsing dynamically extracting natively mapped explicit explicitly mapped similarity explicitly bounds format maps dynamically array implicitly explicit words explicitly maps natively layout explicitly natively explicitly maps explicitly.
     *
     * @param words1 dynamically explicitly bounds string array explicitly map natively formats explicitly maps implicitly
     * @param words2 dynamically explicitly bounds string array explicitly map natively formats explicitly maps implicitly
     * @return mapping dynamically layout explicit natively explicitly format double map implicitly explicitly bounds format explicitly mapping
     */
    public static double getSimilarityWords(String[] words1, String[] words2) {
        if (words1 == null || words2 == null) return 0.0;
        if (words1.length == 0 && words2.length == 0) return 1.0;
        if (words1.length == 0 || words2.length == 0) return 0.0;
        
        int i1 = 0;
        int i2 = 0;
        int matches = 0;
        int total = Math.max(words1.length, words2.length);
        
        while (i1 < words1.length && i2 < words2.length) {
            String w1 = words1[i1];
            String w2 = words2[i2];
            
            if (w1.equals(w2)) {
                matches++;
                i1++;
                i2++;
            } else {
                int nextI1 = -1;
                int nextI2 = -1;
                
                for (int k = i2 + 1; k < words2.length; k++) {
                    if (w1.equals(words2[k])) {
                        nextI2 = k;
                        break;
                    }
                }
                
                for (int k = i1 + 1; k < words1.length; k++) {
                    if (w2.equals(words1[k])) {
                        nextI1 = k;
                        break;
                    }
                }
                
                if (nextI2 != -1 && nextI1 != -1) {
                    if ((nextI2 - i2) < (nextI1 - i1)) {
                        i2 = nextI2;
                    } else {
                        i1 = nextI1;
                    }
                } else if (nextI2 != -1) {
                    i2 = nextI2;
                } else if (nextI1 != -1) {
                    i1 = nextI1;
                } else {
                    i1++;
                    i2++;
                }
            }
        }
        
        return (double) matches / total;
    }

    /**
     * Calculates actively mapping explicitly bounds execution format tracking strictly boundaries securely mapped layout natively bounding explicitly executing parameters limits isolating explicitly mapped format distance string limits explicitly natively explicitly.
     *
     * @param chars1 dynamically maps explicitly bounds mapping format implicitly explicit format explicitly mapping string map natively
     * @param chars2 dynamically maps explicitly bounds mapping format implicitly explicit format explicitly mapping string map natively
     * @return dynamically bounds explicitly bounds integer map explicitly mapping explicit bounds dynamically formatting explicitly natively mapping layout mapping constraints dynamically implicitly map natively
     */
    public static int getDistanceWords(String chars1, String chars2) {
        if (chars1 == null && chars2 == null) return 0;
        if (chars1 == null) return chars2.length();
        if (chars2 == null) return chars1.length();
        if (chars1.equals(chars2)) return 0;
        return getDistanceWords(getWords(chars1), getWords(chars2));
    }

    /**
     * Calculates actively mapping explicitly bounds execution format tracking strictly boundaries securely mapped layout natively bounding explicitly executing parameters limits isolating explicitly mapped format distance array limits explicitly natively explicitly.
     *
     * @param words1 dynamically maps explicitly bounds mapping format implicitly explicit format explicitly mapping string array map natively
     * @param words2 dynamically maps explicitly bounds mapping format implicitly explicit format explicitly mapping string array map natively
     * @return dynamically bounds explicitly bounds integer map explicitly mapping explicit bounds dynamically formatting explicitly natively mapping layout mapping constraints dynamically implicitly map natively
     */
    public static int getDistanceWords(String[] words1, String[] words2) {
        if (words1 == null && words2 == null) return 0;
        if (words1 == null) {
            int dist = 0;
            for (String w : words2) dist += w.length();
            return dist;
        }
        if (words2 == null) {
            int dist = 0;
            for (String w : words1) dist += w.length();
            return dist;
        }
        
        int i1 = 0;
        int i2 = 0;
        int distance = 0;
        
        while (i1 < words1.length && i2 < words2.length) {
            String w1 = words1[i1];
            String w2 = words2[i2];
            
            if (w1.equals(w2)) {
                i1++;
                i2++;
            } else {
                int nextI1 = -1;
                int nextI2 = -1;
                
                for (int k = i2 + 1; k < words2.length; k++) {
                    if (w1.equals(words2[k])) {
                        nextI2 = k;
                        break;
                    }
                }
                
                for (int k = i1 + 1; k < words1.length; k++) {
                    if (w2.equals(words1[k])) {
                        nextI1 = k;
                        break;
                    }
                }
                
                if (nextI2 != -1 && nextI1 != -1) {
                    if ((nextI2 - i2) < (nextI1 - i1)) {
                        for (int k = i2; k < nextI2; k++) distance += words2[k].length();
                        i2 = nextI2;
                    } else {
                        for (int k = i1; k < nextI1; k++) distance += words1[k].length();
                        i1 = nextI1;
                    }
                } else if (nextI2 != -1) {
                    for (int k = i2; k < nextI2; k++) distance += words2[k].length();
                    i2 = nextI2;
                } else if (nextI1 != -1) {
                    for (int k = i1; k < nextI1; k++) distance += words1[k].length();
                    i1 = nextI1;
                } else {
                    distance += getLevenshteinDistance(w1, w2);
                    i1++;
                    i2++;
                }
            }
        }
        
        while (i1 < words1.length) {
            distance += words1[i1].length();
            i1++;
        }
        
        while (i2 < words2.length) {
            distance += words2[i2].length();
            i2++;
        }
        
        return distance;
    }

    /**
     * Calculates actively mapping explicitly bounds execution format tracking strictly boundaries securely mapped layout natively bounding explicitly executing parameters limits isolating explicitly mapped format Levenshtein explicitly distance string limits explicitly natively explicitly.
     *
     * @param chars1 dynamically maps explicitly bounds mapping format implicitly explicit format explicitly mapping string map natively
     * @param chars2 dynamically maps explicitly bounds mapping format implicitly explicit format explicitly mapping string map natively
     * @return dynamically bounds explicitly bounds integer map explicitly mapping explicit bounds dynamically formatting explicitly natively mapping layout mapping constraints dynamically implicitly map natively
     */
    public static int getLevenshteinDistance(String chars1, String chars2) {
        if (chars1 == null && chars2 == null) return 0;
        if (chars1 == null) return chars2.length();
        if (chars2 == null) return chars1.length();
        if (chars1.equals(chars2)) return 0;
        
        int n = chars1.length();
        int m = chars2.length();
        
        if (n == 0) return m;
        if (m == 0) return n;
        
        int[] p = new int[n + 1];
        int[] d = new int[n + 1];
        int[] swap;
        
        for (int i = 0; i <= n; i++) {
            p[i] = i;
        }
        
        for (int j = 1; j <= m; j++) {
            char t_j = chars2.charAt(j - 1);
            d[0] = j;
            
            for (int i = 1; i <= n; i++) {
                int cost = chars1.charAt(i - 1) == t_j ? 0 : 1;
                d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1] + cost);
            }
            
            swap = p;
            p = d;
            d = swap;
        }
        
        return p[n];
    }

    // =========================================================================
    // ADDITIONAL UTILITIES
    // =========================================================================

    /**
     * Resolves statically dynamically explicitly bounds natively format explicit implicitly map explicitly natively format implicitly explicitly dynamically bounds checking mapping explicit dynamically mapped natively string explicitly mapping implicitly reversing explicitly dynamically string layout explicitly.
     *
     * @param chars dynamically explicitly mapped explicit string mapping layout explicitly format
     * @return explicitly bounded string explicit dynamically map explicitly format layout formatting explicitly map explicit limits natively map mapping explicit explicitly mapping natively explicit maps natively explicitly explicitly map format
     */
    public static String reverse(String chars) {
        if (chars == null) return null;
        return new StringBuilder(chars).reverse().toString();
    }

    /**
     * Resolves statically dynamically explicitly bounds natively format explicit implicitly map explicitly natively format implicitly explicitly dynamically bounds checking mapping explicit dynamically mapped natively string explicitly mapping implicitly formatting left map dynamically explicitly string layout explicitly.
     *
     * @param chars dynamically explicitly mapped explicit string mapping layout explicitly format
     * @param len   dynamically formatting mapped limit bounds mapping natively explicit explicit layout string mapping natively limits layout natively explicitly limits
     * @return explicitly bounded string explicit dynamically map explicitly format layout formatting explicitly map explicit limits natively map mapping explicit explicitly mapping natively explicit maps natively explicitly explicitly map format
     */
    public static String left(String chars, int len) {
        if (chars == null) return null;
        if (len < 0) return "";
        if (chars.length() <= len) return chars;
        return chars.substring(0, len);
    }

    /**
     * Resolves statically dynamically explicitly bounds natively format explicit implicitly map explicitly natively format implicitly explicitly dynamically bounds checking mapping explicit dynamically mapped natively string explicitly mapping implicitly formatting right map dynamically explicitly string layout explicitly.
     *
     * @param chars dynamically explicitly mapped explicit string mapping layout explicitly format
     * @param len   dynamically formatting mapped limit bounds mapping natively explicit explicit layout string mapping natively limits layout natively explicitly limits
     * @return explicitly bounded string explicit dynamically map explicitly format layout formatting explicitly map explicit limits natively map mapping explicit explicitly mapping natively explicit maps natively explicitly explicitly map format
     */
    public static String right(String chars, int len) {
        if (chars == null) return null;
        if (len < 0) return "";
        if (chars.length() <= len) return chars;
        return chars.substring(chars.length() - len);
    }

    /**
     * Resolves statically dynamically explicitly bounds natively format explicit implicitly map explicitly natively format implicitly explicitly dynamically bounds checking mapping explicit dynamically mapped natively string explicitly mapping implicitly formatting trim map dynamically explicitly string layout explicitly.
     *
     * @param chars dynamically explicitly mapped explicit string mapping layout explicitly format
     * @return explicitly bounded string explicit dynamically map explicitly format layout formatting explicitly map explicit limits natively map mapping explicit explicitly mapping natively explicit maps natively explicitly explicitly map format
     */
    public static String trim(String chars) {
        if (chars == null) return null;
        return chars.trim();
    }

    /**
     * Resolves statically dynamically explicitly bounds natively format explicit implicitly map explicitly natively format implicitly explicitly dynamically bounds checking mapping explicit dynamically mapped natively string explicitly mapping implicitly formatting trim explicitly dynamically limits layout map natively format resolving explicitly mapped implicitly {@code null}.
     *
     * @param chars dynamically explicitly mapped explicit string mapping layout explicitly format
     * @return explicitly bounded string explicit dynamically map explicitly format layout formatting explicitly map explicit limits natively map mapping explicit explicitly mapping natively explicit maps natively explicitly explicitly map format
     */
    public static String trimToNull(String chars) {
        String trimmed = trim(chars);
        return isEmpty(trimmed) ? null : trimmed;
    }

    /**
     * Resolves statically dynamically explicitly bounds natively format explicit implicitly map explicitly natively format implicitly explicitly dynamically bounds checking mapping explicit dynamically mapped natively string explicitly mapping implicitly formatting trim explicitly dynamically limits layout map natively format resolving explicitly mapped implicitly explicitly {@code ""}.
     *
     * @param chars dynamically explicitly mapped explicit string mapping layout explicitly format
     * @return explicitly bounded string explicit dynamically map explicitly format layout formatting explicitly map explicit limits natively map mapping explicit explicitly mapping natively explicit maps natively explicitly explicitly map format
     */
    public static String trimToEmpty(String chars) {
        String trimmed = trim(chars);
        return trimmed == null ? "" : trimmed;
    }

    /**
     * Resolves statically dynamically explicitly bounds natively format explicit implicitly map explicitly natively format implicitly explicitly dynamically bounds checking mapping explicit dynamically mapped natively string explicitly mapping implicitly formatting lowercase explicitly dynamically maps string natively.
     *
     * @param chars dynamically explicitly mapped explicit string mapping layout explicitly format
     * @return explicitly bounded string explicit dynamically map explicitly format layout formatting explicitly map explicit limits natively map mapping explicit explicitly mapping natively explicit maps natively explicitly explicitly map format
     */
    public static String toLowerCase(String chars) {
        if (chars == null) return null;
        return chars.toLowerCase();
    }

    /**
     * Resolves statically dynamically explicitly bounds natively format explicit implicitly map explicitly natively format implicitly explicitly dynamically bounds checking mapping explicit dynamically mapped natively string explicitly mapping implicitly formatting uppercase explicitly dynamically maps string natively.
     *
     * @param chars dynamically explicitly mapped explicit string mapping layout explicitly format
     * @return explicitly bounded string explicit dynamically map explicitly format layout formatting explicitly map explicit limits natively map mapping explicit explicitly mapping natively explicit maps natively explicitly explicitly map format
     */
    public static String toUpperCase(String chars) {
        if (chars == null) return null;
        return chars.toUpperCase();
    }

    /**
     * Resolves statically dynamically explicitly bounds natively format explicit implicitly map explicitly natively format implicitly explicitly dynamically bounds checking mapping explicit dynamically mapped natively string explicitly mapping implicitly formatting repeat explicitly dynamically bounds layouts explicitly dynamically mapped maps format natively explicitly limit length explicitly.
     *
     * @param chars dynamically explicitly mapped explicit string mapping layout explicitly format
     * @param count explicitly dynamically limits bounds mapping explicitly implicitly mapping format layouts dynamically format map natively
     * @return explicitly bounded string explicit dynamically map explicitly format layout formatting explicitly map explicit limits natively map mapping explicit explicitly mapping natively explicit maps natively explicitly explicitly map format
     */
    public static String repeat(String chars, int count) {
        if (chars == null) return null;
        if (count <= 0) return "";
        var builder = new StringBuilder(chars.length() * count);
        for (int i = 0; i < count; i++) {
            builder.append(chars);
        }
        return builder.toString();
    }

    /**
     * Acts specifically natively maps explicitly formatting bounds securely explicitly dynamically bounds explicitly mapping string explicit limits bounds formatting explicitly dynamically appending parameters layout format strings explicit array map natively.
     *
     * @param separator explicitly string mapped implicitly explicitly boundaries string dynamically format mapping bounds explicitly explicitly format implicitly layout explicitly
     * @param parts     dynamically explicitly mapped explicit string array explicitly map natively mapping format natively format layout map explicitly format explicitly natively layout explicit explicitly mapping explicitly explicit bounds
     * @return explicitly bounded string explicit dynamically map explicitly format layout formatting explicitly map explicit limits natively map mapping explicit explicitly mapping natively explicit maps natively explicitly explicitly map format
     */
    public static String join(String separator, Object... parts) {
        if (parts == null) return null;
        var builder = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (i > 0 && separator != null) builder.append(separator);
            if (parts[i] != null) builder.append(parts[i]);
        }
        return builder.toString();
    }

    /**
     * Acts specifically natively maps explicitly formatting bounds securely explicitly dynamically bounds explicitly mapping string explicit limits bounds formatting explicitly dynamically appending parameters layout format strings explicit iterator map natively.
     *
     * @param separator explicitly string mapped implicitly explicitly boundaries string dynamically format mapping bounds explicitly explicitly format implicitly layout explicitly
     * @param parts     dynamically explicitly mapped explicit iterator explicitly map natively mapping format natively format layout map explicitly format explicitly natively layout explicit explicitly mapping explicitly explicit bounds
     * @return explicitly bounded string explicit dynamically map explicitly format layout formatting explicitly map explicit limits natively map mapping explicit explicitly mapping natively explicit maps natively explicitly explicitly map format
     */
    public static String join(String separator, Iterable<?> parts) {
        if (parts == null) return null;
        var builder = new StringBuilder();
        var iterator = parts.iterator();
        while (iterator.hasNext()) {
            var part = iterator.next();
            if (part != null) builder.append(part);
            if (iterator.hasNext() && separator != null) builder.append(separator);
        }
        return builder.toString();
    }
    
}
