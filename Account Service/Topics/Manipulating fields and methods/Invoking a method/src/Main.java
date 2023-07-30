
import com.sun.tools.javac.Main;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;

class MethodsDemo {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException {
        Main.class.getMethod("hello").invoke(null);
    }

    static void hello() {
        System.out.println("Hello world!");
    }
}
