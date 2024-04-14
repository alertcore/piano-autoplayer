import java.awt.Robot;
import java.awt.AWTException;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException, AWTException {

        Robot robot = new Robot();

        String sheetstring;
        sheetstring = sheetReader.readFile(args[0]);
        System.out.println(sheetstring);

        processInput(robot, sheetstring, args[1], Integer.parseInt(args[2]), args[0]);
    }

    private static void processInput(Robot robot, String input, String delay, int variance, String filename) throws InterruptedException {

        String[] parts = input.split("\\s+");

        int upper = Integer.parseInt(delay) + variance;
        int lower = Integer.parseInt(delay) - variance;

        System.out.println("Playing " + filename + ".txt of length " + parts.length);

        Thread.sleep(2000);

        int index = 0;
        for (String part : parts) {
            if (part.startsWith("[") && part.endsWith("]")) {
                playChord(robot, part, upper, lower);
            } else {
                playNote(robot, part.toCharArray(), upper, lower);
            }
            System.out.println(index+1 + " of " + parts.length);
            index++;
        }
        System.out.println("A Flawless Performance!");
        Thread.sleep(750);
        System.out.print("\033[H\033[2J");
        System.out.println("exit");
    }

    private static void playChord(Robot robot, String chord, int upper, int lower) throws InterruptedException {

        chord = chord.substring(1, chord.length() - 1);

        long usingDelay = ThreadLocalRandom.current().nextInt(lower, upper);

        for (char ch : chord.toCharArray()) {
            pressKey(robot, ch);
        }

        System.out.println("Press chord [" + chord + "]");
        Thread.sleep(usingDelay);
        //System.out.print(" [" + chord + "] ");
        System.out.println("Wait " + usingDelay + " ms");

        for (char keyInput : chord.toCharArray()) {
            releaseKey(robot, keyInput);
        }
    }

    private static void playNote(Robot robot, char[] keyInputArray, int upper, int lower) throws InterruptedException {

        for (char ch : keyInputArray) {
            boolean isUpperCase = Character.isUpperCase(ch);

            long usingDelay = ThreadLocalRandom.current().nextInt(lower, upper);

            if (isUpperCase) {
                robot.keyPress(KeyEvent.VK_SHIFT);
            }

            int keyCode;

            keyCode = KeyEvent.getExtendedKeyCodeForChar(Character.toLowerCase(ch));
            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);

            System.out.println("Press " + ch);
            Thread.sleep(usingDelay);
            //System.out.print(ch);
            System.out.println("Wait " + usingDelay + " ms");

            if (isUpperCase) {
                robot.keyRelease(KeyEvent.VK_SHIFT);
            }
        }
    }

    private static void pressKey(Robot robot, char keyInput) {

        if (Character.isUpperCase(keyInput)) {
            robot.keyPress(KeyEvent.VK_SHIFT);
        }

        int keyCode = KeyEvent.getExtendedKeyCodeForChar(Character.toLowerCase(keyInput));
        robot.keyPress(keyCode);
    }

    private static void releaseKey(Robot robot, char keyInput) {
        int keyCode = KeyEvent.getExtendedKeyCodeForChar(Character.toLowerCase(keyInput));
        robot.keyRelease(keyCode);

        if (Character.isUpperCase(keyInput)) {
            robot.keyRelease(KeyEvent.VK_SHIFT);
        }
    }
}