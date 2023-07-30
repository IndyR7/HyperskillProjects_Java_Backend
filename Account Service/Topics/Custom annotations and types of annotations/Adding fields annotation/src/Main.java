
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
@interface Version {
    String value();
}

@Version(value = "1.0")
class TestClass {
    public void myMethod() {
        // some code
    }
}