package base.utils;

import java.util.ArrayList;
import java.util.List;

public class SplitByLength {

    public static void main(String[] args) {
        System.out.println("Start: SplitByLength ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        SplitByLength test = new SplitByLength();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        int MAX_LENGTH = 30;
        String text = "callworksbrowser - UiAutomation/readme.txt - Eclipse";
        List<String> strings = new ArrayList<String>();
        int index = 0;
        while (index < text.length()) {
            strings.add(text.substring(index, Math.min(index + MAX_LENGTH,text.length())));
            index += MAX_LENGTH;
        }

        for (String string : strings) {
            System.out.println(string);
        }
    }

}
