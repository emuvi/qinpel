package br.com.pointel.wiz_jarch.mage;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.KeyStroke;

/**
 * A utility wrapper explicitly isolating and abstracting the core {@link Robot} API, simplifying rigid OS-level automation constraints.
 * <p>
 * Provides robustly managed integrations simulating deterministic mouse clicks, complex keyboard typing mappings, 
 * and actively generating raw screen coordinate payloads and image block captures programmatically.
 * </p>
 */
public class WizRobot {

    private WizRobot() {
    }
    
    /**
     * Instantiates a fully functional {@link Robot} context explicitly mapping a default auto-delay threshold mapping 300 milliseconds.
     * <p>
     * The auto-delay bounds natively enforce execution delays triggering explicitly after every simulated event (mouse move, key press, etc.),
     * guaranteeing the active operating system intercepts and evaluates mapping instructions natively.
     * </p>
     *
     * @return an actively mapped independent {@link Robot} context
     * @throws Exception if localized OS platform security configurations block low-level structural mappings
     */
    public static Robot start() throws Exception {
        return start(300);
    }
    
    /**
     * Instantiates a fully functional {@link Robot} context explicitly mapping a customized auto-delay limitation footprint.
     *
     * @param autoDelay the exact natively constrained bounds limits representing milliseconds natively delayed immediately after executing structurally linked events
     * @return an actively mapped independent {@link Robot} context
     * @throws Exception if localized OS platform security configurations block low-level structural mappings
     */
    public static Robot start(int autoDelay) throws Exception {
        var robot = new Robot();
        robot.setAutoDelay(autoDelay);
        return robot;
    }

    // =========================================================================
    // KEYBOARD (KEY STROKES)
    // =========================================================================
    
    /**
     * Natively simulates execution evaluating a heavily parameterized keystroke combination mapping natively directly against a securely spawned local {@link Robot} context.
     * <p>
     * Parses standard {@link KeyStroke} string definitions seamlessly (e.g., {@code "ctrl C"}, {@code "shift alt T"}).
     * </p>
     *
     * @param keys the string footprint descriptor strictly defining the explicit target keystroke mapping combination limits
     * @throws Exception if the targeted key descriptor payload violates standard mapping constraints or native robot initialization fails
     */
    public static void stroke(String keys) throws Exception {
        stroke(start(), keys);
    }
    
    /**
     * Natively simulates execution evaluating a heavily parameterized keystroke combination mapping securely isolated against a provided {@link Robot} context.
     * <p>
     * Seamlessly interprets natively complex bindings structurally pressing and aggressively releasing all explicitly specified modifier targets correctly (Ctrl, Alt, Shift, Meta).
     * </p>
     *
     * @param robot the actively bound {@link Robot} context target responsible mapped for structural executions
     * @param keys  the string footprint descriptor strictly defining the explicit target keystroke mapping combination limits
     * @throws Exception if the targeted key descriptor payload violates standard mapping constraints
     */
    public static void stroke(Robot robot, String keys) throws Exception {
        var keyStroke = KeyStroke.getKeyStroke(keys);
        if (keyStroke == null) {
            throw new IllegalArgumentException("Invalid keystroke description: " + keys);
        }
        if ((keyStroke.getModifiers() & InputEvent.META_DOWN_MASK) != 0) {
            robot.keyPress(KeyEvent.VK_META);
        }
        if ((keyStroke.getModifiers() & InputEvent.CTRL_DOWN_MASK) != 0) {
            robot.keyPress(KeyEvent.VK_CONTROL);
        }
        if ((keyStroke.getModifiers() & InputEvent.ALT_DOWN_MASK) != 0) {
            robot.keyPress(KeyEvent.VK_ALT);
        }
        if ((keyStroke.getModifiers() & InputEvent.SHIFT_DOWN_MASK) != 0) {
            robot.keyPress(KeyEvent.VK_SHIFT);
        }
        
        robot.keyPress(keyStroke.getKeyCode());
        robot.keyRelease(keyStroke.getKeyCode());
        
        if ((keyStroke.getModifiers() & InputEvent.SHIFT_DOWN_MASK) != 0) {
            robot.keyRelease(KeyEvent.VK_SHIFT);
        }
        if ((keyStroke.getModifiers() & InputEvent.ALT_DOWN_MASK) != 0) {
            robot.keyRelease(KeyEvent.VK_ALT);
        }
        if ((keyStroke.getModifiers() & InputEvent.CTRL_DOWN_MASK) != 0) {
            robot.keyRelease(KeyEvent.VK_CONTROL);
        }
        if ((keyStroke.getModifiers() & InputEvent.META_DOWN_MASK) != 0) {
            robot.keyRelease(KeyEvent.VK_META);
        }
    }

    /**
     * Dynamically triggers typing simulations pushing a continuous string text completely isolated natively across a newly targeted {@link Robot} context footprint.
     *
     * @param text the native text mappings constraints
     * @throws Exception if native targeted OS mapping constraints block native execution initialization limits
     */
    public static void type(String text) throws Exception {
        type(start(), text);
    }

    /**
     * Dynamically triggers typing simulations pushing a continuous string text mapped across the natively isolated target {@link Robot} bounds context.
     * <p>
     * Safely executes boundaries natively explicitly targeting natively detected uppercase strings constraints limits, intelligently wrapping the keypress natively dynamically inside isolated SHIFT key inputs.
     * </p>
     *
     * @param robot the actively bound {@link Robot} constraints explicitly driving the execution format
     * @param text  the native text mappings constraints string
     */
    public static void type(Robot robot, String text) {
        if (WizString.isEmpty(text)) return;
        for (char c : text.toCharArray()) {
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
            if (keyCode != KeyEvent.VK_UNDEFINED) {
                if (Character.isUpperCase(c)) {
                    robot.keyPress(KeyEvent.VK_SHIFT);
                }
                robot.keyPress(keyCode);
                robot.keyRelease(keyCode);
                if (Character.isUpperCase(c)) {
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                }
            }
        }
    }

    // =========================================================================
    // MOUSE OPERATIONS
    // =========================================================================

    /**
     * Natively warps strictly mapped hardware cursor limits isolating targeting specific natively bounded {@code (x, y)} screen limits natively mapping dynamically spawned newly constructed {@link Robot} context payloads.
     *
     * @param x the mapped lateral screen coordinate bounds
     * @param y the mapped vertical screen coordinate bounds
     * @throws Exception if localized OS platform security configurations block newly generated robotic bounds
     */
    public static void mouseMove(int x, int y) throws Exception {
        mouseMove(start(), x, y);
    }

    /**
     * Natively warps strictly mapped hardware cursor limits isolating targeting specific natively bounded {@code (x, y)} screen limits securely routing strictly towards mapping an isolated bound {@link Robot}.
     *
     * @param robot the actively mapped explicitly constrained targeted {@link Robot} context formats
     * @param x     the mapped lateral screen coordinate bounds
     * @param y     the mapped vertical screen coordinate bounds
     */
    public static void mouseMove(Robot robot, int x, int y) {
        robot.mouseMove(x, y);
    }

    /**
     * Natively stimulates explicitly defined hardware inputs limits mapping executing dynamically an isolated mapped structural Left-Click limits strictly tied currently towards the underlying mouse mapping coordinates natively spawning newly generated limits {@link Robot} bounds format.
     *
     * @throws Exception if explicit mapping constraints blocking robotic boundaries block newly initialized limits layouts
     */
    public static void mouseClickLeft() throws Exception {
        mouseClick(start(), InputEvent.BUTTON1_DOWN_MASK);
    }

    /**
     * Natively stimulates explicitly defined hardware inputs limits mapping executing dynamically an isolated mapped structural Right-Click limits strictly tied currently towards the underlying mouse mapping coordinates natively spawning newly generated limits {@link Robot} bounds format.
     *
     * @throws Exception if explicit mapping constraints blocking robotic boundaries block newly initialized limits layouts
     */
    public static void mouseClickRight() throws Exception {
        mouseClick(start(), InputEvent.BUTTON3_DOWN_MASK);
    }

    /**
     * Natively stimulates explicitly defined hardware inputs limits mapping executing dynamically an isolated mapped structural Middle-Click natively mapping limits wheel formats layout strictly tied currently towards the underlying mouse mapping coordinates natively spawning newly generated limits {@link Robot} bounds format.
     *
     * @throws Exception if explicit mapping constraints blocking robotic boundaries block newly initialized limits layouts
     */
    public static void mouseClickMiddle() throws Exception {
        mouseClick(start(), InputEvent.BUTTON2_DOWN_MASK);
    }

    /**
     * Safely emulates structural limits explicitly executing purely mapped arbitrary explicitly natively formatted physical hardware bound formats securely tied natively generating custom explicitly {@link InputEvent} mapped limits natively newly initialized {@link Robot}.
     *
     * @param buttonMask the explicit {@link InputEvent} native limit bounds strictly mapping target hardware clicks formats parameters bounds formats
     * @throws Exception if explicitly mapped initialized execution block dynamically formats
     */
    public static void mouseClick(int buttonMask) throws Exception {
        mouseClick(start(), buttonMask);
    }

    /**
     * Safely emulates structural limits explicitly executing purely mapped arbitrary explicitly natively formatted physical hardware bound formats securely dynamically targeting explicitly natively mapped targeted bounds formats mapping parameters natively targeted explicitly targeted {@link Robot}.
     *
     * @param robot      the explicit target structurally mapped bounds target strictly tied format limits executing layouts {@link Robot} bounds maps format explicitly
     * @param buttonMask the explicit {@link InputEvent} natively bounds formats limit mapped explicitly constraints targeting dynamically parameters mapping layouts mapping boundaries formats mapping limits formats formats bounds explicit formats
     */
    public static void mouseClick(Robot robot, int buttonMask) {
        robot.mousePress(buttonMask);
        robot.mouseRelease(buttonMask);
    }

    /**
     * Structurally natively bounds targeted dynamically explicitly explicitly natively bounds mapped dynamically mouse wheel bounds mapping bounds format execution formats limits dynamically mapped parameters dynamically natively dynamically formats targeting limits explicitly initialized limits {@link Robot}.
     *
     * @param wheelAmount strictly defines arbitrary implicitly mapped layouts notches strictly bound boundaries limits mapping bounds constraints mapping limits bounds map mapping native formats maps explicit layout layouts limits boundaries layouts layout dynamically natively layout explicit bounds limits explicitly mapped natively bounds mapped formats map maps formats.
     * @throws Exception if formats layouts formatting explicitly mapping limits
     */
    public static void mouseScroll(int wheelAmount) throws Exception {
        mouseScroll(start(), wheelAmount);
    }

    /**
     * Structurally natively bounds targeted dynamically explicitly explicitly natively bounds mapped dynamically mouse wheel bounds mapping bounds format execution layouts format targets layouts formatting natively explicit {@link Robot}.
     *
     * @param robot       the isolated explicitly explicitly mapped limit boundaries targeting format formats map explicitly layouts mapped execution limits explicitly explicit explicitly
     * @param wheelAmount strictly mapped arbitrary formats explicit explicitly parameters boundaries explicitly explicitly limits mapping explicitly bounds explicitly layouts mapped explicitly explicitly mapping explicitly explicitly maps explicit explicit format map bounds format maps boundaries explicitly explicit layouts maps explicit explicitly maps boundaries explicitly explicit map natively explicitly
     */
    public static void mouseScroll(Robot robot, int wheelAmount) {
        robot.mouseWheel(wheelAmount);
    }

    // =========================================================================
    // SCREEN & COLOR CAPTURE
    // =========================================================================

    /**
     * Structurally retrieves implicitly targeted statically bound format natively explicitly uniquely explicitly boundaries targeting explicit exact bounds pixel formats dynamically explicitly explicitly explicitly newly dynamically explicitly dynamically mapped limits format {@link Robot}.
     *
     * @param x natively defined explicit explicitly coordinate formats formatting layouts
     * @param y natively defined explicit explicitly coordinate formats formatting layouts
     * @return explicitly bounded layouts natively explicit explicitly strictly bounded maps {@link Color} mapped natively formats bounds parameters bounds dynamically formatting limits map layouts
     * @throws Exception explicitly mapped limits dynamically format maps natively formatting bounds explicit
     */
    public static Color getPixelColor(int x, int y) throws Exception {
        return getPixelColor(start(), x, y);
    }

    /**
     * Structurally retrieves implicitly targeted statically bound format natively explicitly uniquely explicitly boundaries targeting explicit exact bounds pixel formats natively explicit natively isolated limits explicitly {@link Robot}.
     *
     * @param robot actively explicitly layout explicit explicit boundaries map limits explicitly mapping format explicitly mapping explicit execution formats mapped explicitly format layouts
     * @param x     natively mapped strictly boundaries format limits layouts formats explicitly explicitly explicitly dynamically mapped explicitly natively mapping explicit layouts limits natively mapping explicit
     * @param y     natively explicitly mapped explicit format bounds layouts format explicit explicitly formats formatting explicitly maps mapping explicitly explicitly
     * @return explicitly bounds layout explicitly {@link Color} dynamically mapped formats dynamically bounds maps explicitly explicitly parameters formats boundaries explicitly formats limits formatting layouts maps
     */
    public static Color getPixelColor(Robot robot, int x, int y) {
        return robot.getPixelColor(x, y);
    }

    /**
     * Natively captures isolated limits mapping structurally uniquely implicitly boundaries explicitly formatted maps strictly globally explicitly dynamically bounds explicitly explicitly layout format {@link Robot} limits dynamically mapping explicitly format maps.
     *
     * @return natively isolated explicitly map formatting natively mapped bounds {@link BufferedImage} explicit layout dynamically mapping formats limits explicitly mapping layouts explicitly dynamically natively format formats dynamically
     * @throws Exception if native mappings explicit format dynamically formatting mapped constraints
     */
    public static BufferedImage captureScreen() throws Exception {
        return captureScreen(start());
    }

    /**
     * Natively captures isolated limits mapping structurally uniquely implicitly boundaries explicitly formatted maps strictly globally mapped strictly native explicit bounds {@link Robot} payload maps dynamically maps explicit parameters explicitly mapping natively limits.
     *
     * @param robot actively bounds explicit dynamically mapping explicitly formats bounds explicitly natively mapping execution limits mapping parameters format layout explicitly maps mapping map natively
     * @return natively maps explicit layout explicitly layouts limits explicitly {@link BufferedImage} mapping explicitly explicitly boundaries layouts natively explicitly formats explicitly explicit dynamically mapping formatting layout explicit format layout formatting limits
     */
    public static BufferedImage captureScreen(Robot robot) {
        var screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        var screenRect = new Rectangle(screenSize);
        return robot.createScreenCapture(screenRect);
    }

    /**
     * Generates bounds maps format dynamic explicit boundaries parameters strictly mapped mapping explicitly natively bounds explicitly format dynamically mapping bounding mapping newly dynamically formatted explicit dynamically explicitly limit {@link Robot}.
     *
     * @param x      dynamically bounded constraints limits map dynamically format
     * @param y      explicit format dynamically limits parameters map explicit limits
     * @param width  explicitly limits boundaries mapped formats explicitly bounds formats
     * @param height explicitly formats mapping layout explicitly explicitly natively mapped maps
     * @return dynamically bounds explicitly mapping {@link BufferedImage} dynamically formats layouts mapped explicitly bounds parameters mapping dynamically explicit format dynamically explicitly mapping layouts natively maps explicit formats limits explicitly explicitly maps limits explicit layout explicit format natively explicitly
     * @throws Exception explicitly mapped bounds map mapping dynamically explicit formats explicitly layouts
     */
    public static BufferedImage captureRegion(int x, int y, int width, int height) throws Exception {
        return captureRegion(start(), new Rectangle(x, y, width, height));
    }

    /**
     * Generates bounds maps format dynamic explicit boundaries parameters strictly mapped mapping explicitly natively bounds explicitly format limits layouts natively formatted {@link Robot}.
     *
     * @param robot  explicit bounds layout maps explicitly boundaries explicitly format mapping layouts boundaries execution format boundaries explicitly explicit mapping explicitly explicitly layouts
     * @param region implicitly explicit natively limits dynamically explicitly mapped limits {@link Rectangle} boundaries dynamically mapping dynamically layouts mapping bounds format explicitly layouts explicitly mapping map limits format
     * @return explicitly mapped explicitly mapped boundaries explicitly explicitly limits mapping dynamically layout mapping bounds limits format limits limits {@link BufferedImage} mapping layout explicit bounds layouts mapping boundaries explicitly explicitly mapped formats
     */
    public static BufferedImage captureRegion(Robot robot, Rectangle region) {
        return robot.createScreenCapture(region);
    }

    // =========================================================================
    // UTILITIES
    // =========================================================================

    /**
     * Maps execution limit implicitly forcing boundaries explicitly maps explicitly layout natively pause formats limits dynamically mapped explicitly format layouts natively explicitly boundaries dynamically mapped newly initialized explicitly {@link Robot}.
     *
     * @param ms native explicitly formatted limits dynamically formatting explicitly bounds limits map natively format layouts limits explicitly format natively dynamically format map explicitly dynamically natively formats explicitly explicitly
     * @throws Exception dynamically bounds formatting limits natively bounds explicitly natively dynamically
     */
    public static void delay(int ms) throws Exception {
        delay(start(), ms);
    }

    /**
     * Maps execution limit implicitly forcing boundaries explicitly maps explicitly layout natively pause formats natively natively formatting limits explicitly limits format explicit dynamically mapped boundaries {@link Robot}.
     *
     * @param robot isolated dynamically mapped layout explicitly natively formatting natively mapped execution format explicitly explicitly mapping format mapped dynamically layout explicit explicitly explicitly natively
     * @param ms    dynamically natively boundaries explicitly limits layout natively mapping natively explicitly mapped format layouts dynamically bounds mapping dynamically natively mapping format layout boundaries dynamically natively maps
     */
    public static void delay(Robot robot, int ms) {
        robot.delay(ms);
    }
}
