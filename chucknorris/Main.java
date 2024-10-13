package chucknorris;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input1 = "";
        boolean isInput1Valid = false;
        boolean isDone = false;
        do {
            System.out.println("Please input operation (encode/decode/exit): ");
            input1 = scanner.nextLine();
            isInput1Valid = (input1.equals("encode") || input1.equals("decode") || input1.equals("exit"));
            if (!isInput1Valid) {
                System.out.printf("There is no '%s' operation.\n", input1);
            }
            if (input1.equals("encode")) {
                System.out.println("Input string: ");
                String input2 = scanner.nextLine();
                System.out.println("Encoded string: ");
                System.out.println(encode(input2));
            }
            if (input1.equals("decode")) {
                String input3 = "";
                boolean isInput3Valid = false;
                Pattern nonZero = Pattern.compile("[^0 ]");
                Matcher nonZeroMatcher = nonZero.matcher(input3);
                do {
                    System.out.println("Input encoded string: ");
                    input3 = scanner.nextLine();
                    boolean isInput3CompValid = !(nonZeroMatcher.find());
                    if (!isInput3CompValid) {
                        System.out.println("Encoded string is not valid. Illegal characters found.");
                        break;
                    }
                    String[] input3Blocks = input3.split(" ");
                    boolean isInput3BlocksEven = input3Blocks.length % 2 == 0;
                    if (!isInput3BlocksEven) {
                        System.out.println("Encoded string is not valid. Illegal number of blocks.");
                        break;
                    }
                    boolean areFirstBlocksValid = true;
                    int firstBlockCounter = 0;
                    for (int i = 0; i < input3Blocks.length; i += 2) {
                        if (input3Blocks[i].length() > 2) {
                            areFirstBlocksValid = false;
                            System.out.println("Encoded string is not valid. " +
                                    "Illegal length of first block of a sequence.");
                            break;
                        }
                    }
                    if (!areFirstBlocksValid) {
                        break;
                    }
                    StringBuilder input3StringBuilder = new StringBuilder();
                    boolean isInput3LengthValid = false;
                    for (int i = 0; i < input3Blocks.length; i++) {
                        if (i % 2 != 0) {
                            input3StringBuilder.append(input3Blocks[i]);
                        }
                    }
                    isInput3LengthValid = input3StringBuilder.length() % 7 == 0;
                    if (!isInput3LengthValid) {
                        System.out.println("Encoded string is not valid. Illegal code length.");
                        break;
                    }
                    isInput3Valid = (isInput3CompValid && isInput3BlocksEven && areFirstBlocksValid
                            && isInput3LengthValid);
                    if (isInput3Valid) {
                        System.out.println("Decoded string: ");
                        System.out.println(decode(input3));
                    }
                } while (!isInput3Valid);
            }
            if (input1.equals("exit")) {
                System.out.println("Bye!");
                isDone = true;
            }
        } while (!isInput1Valid || isDone == false);
    }

    private static String encode(String input) {
        StringBuilder output = new StringBuilder();
        StringBuilder letter = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            letter.append(Integer.toBinaryString(Character.codePointAt(input, i)));
            if (7 > letter.length()) {
                output.append("0".repeat(7 - letter.length()));
            }
            output.append(letter);
            letter.setLength(0);
        }
        return toChuckNorris(output.toString());
    }

    private static String toChuckNorris(String input) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if ('0' == input.charAt(i)) {
                output.append("00");
                output.append(" ");
            } else {
                output.append("0");
                output.append(" ");
            }
            for (int j = i; j < input.length(); j++) {
                if (input.charAt(i) == input.charAt(j)) {
                    output.append("0");
                    if (input.length() - 1 == j) {
                        i = j;
                    }
                } else {
                    output.append(" ");
                    i = j - 1;
                    break;
                }
            }
        }
        return output.toString();
    }

    public static String decode(String input) {
        String[] inputBlocks = input.split(" ");
        String[] binaryBlocks = new String[inputBlocks.length / 2];
        StringBuilder block = new StringBuilder();
        for (int i = 0; i < inputBlocks.length; i += 2) {
            if ("00".equals(inputBlocks[i])) {
                binaryBlocks[i / 2] = block.append("0".repeat(inputBlocks[i + 1].length())).toString();
            }
            if ("0".equals(inputBlocks[i])) {
                binaryBlocks[i / 2] = block.append("1".repeat(inputBlocks[i + 1].length())).toString();
            }
            block.setLength(0);
        }
        for (int i = 0; i < binaryBlocks.length; i++) {
            block.append(binaryBlocks[i]);
        }
        String binaryString = block.toString();
        block.setLength(0);
        char[] charArray = new char[binaryString.length() / 7];
        for (int i = 0; i < binaryString.length(); i += 7) {
            charArray[i / 7] = (char) Integer.parseInt(binaryString.substring(i, i + 7), 2);
        }
        for (int i = 0; i < charArray.length; i++) {
            block.append(charArray[i]);
        }
        return block.toString();
    }
}