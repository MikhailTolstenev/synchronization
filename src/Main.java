import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.*;

public class Main {
    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        String[] texts = new String[1000];
//        for (int i = 0; i < texts.length; i++) {
//            texts[i] = generateRoute("RLRFR", 100);
//        }

        final ExecutorService threadPool = Executors.newFixedThreadPool(1000);
//        for (String text : texts) {
        for (int j = 0; j < 1000; j++) {
            Runnable call = () -> {
                String text = generateRoute("RLRFR", 100);
                Integer rNumber = 0;
                for (int i = 0; i < text.length(); i++) {
                    if (text.charAt(i) == 'R') {
                        rNumber++;
                    }
                }
                synchronized (rNumber) {
                    if (sizeToFreq.containsKey(rNumber) == false) {
                        sizeToFreq.put(rNumber, 1);
                    } else {
                        sizeToFreq.put(rNumber, sizeToFreq.get(rNumber) + 1);
                    }
                }
            };
            Future<Objects> future = (Future<Objects>) threadPool.submit(call);
        }
        Integer maxValue = 0;
        for (Integer key : sizeToFreq.keySet()) {
            if (maxValue == 0 || sizeToFreq.get(key) > sizeToFreq.get(maxValue)) {
                maxValue = key;
            }
        }
        System.out.println("Самое частое количество повторений " + maxValue + "  (встретилось " + sizeToFreq.get(maxValue) + "  раз)\n Другие размеры ");

        for (Integer key1 : sizeToFreq.keySet()) {
            System.out.println("- " + key1 + " (" + sizeToFreq.get(key1) + " раз)");
        }

    }
}








