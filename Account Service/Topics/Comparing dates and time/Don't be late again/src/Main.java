import java.time.LocalTime;
import java.util.Scanner;
import java.util.stream.IntStream;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int times = Integer.parseInt(scanner.nextLine());

        String[] input = IntStream.range(0, times)
                .mapToObj(i -> scanner.nextLine())
                .toArray(String[]::new);

        IntStream.range(0, times)
                .mapToObj(i -> input[i].split(" "))
                .filter(items -> LocalTime.parse(items[1]).isAfter(LocalTime.of(20, 0)))
                .map(items -> items[0])
                .forEach(System.out::println);
    }
}
