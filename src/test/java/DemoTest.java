import org.junit.Test;

import java.io.File;

/**
 * create by afterloe on 2017/10/17
 */
public class DemoTest {

    @Test
    public void testFileIO() {
        System.out.println(File.separator);
        Object flag = "true";
        System.out.println(Boolean.valueOf(flag.toString()).equals(Boolean.TRUE));;
    }
}
