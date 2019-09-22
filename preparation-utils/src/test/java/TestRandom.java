import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/*
 * Created on 2015年6月1日
 */

/**
 * 测试 file:/dev/random, file:/dev/urandom 是否有间隔问题.
 * 
 * 问题原因: weblogic 第一次启动正常, 之后的启动缓慢, 需要等待多时(若隔一段时间启动则没有这个问题).
 * <p>
 * 解决办法：
 * <p>
 * 永久：oracle 说修改 $JAVA_HOME/jre/lib/security/java.security 文件，
 * 		替换securerandom.source=file:/dev/random 为 securerandom.source=file:/dev/urandom。
 * 		对所有使用JVM的应用生效。
 * 		（这个永久的方法，这里面有个问题，就是设置时候实际应该设置为securerandom.source=file:/dev/./urandom，否则不生效）</ol>
 * <p>
 * 临时：修改startWeblogic.sh文件，JAVA_OPTIONS="${SAVE_JAVA_OPTIONS} -Djava.security.egd=file:/dev/./urandom"</ol>
 * </li>
 * 
 * 测试该类<br>
 * java -Djava.security.egd=file:/dev/random TestRandom<br>
 * java -Djava.security.egd=file:/dev/urandom TestRandom<br>
 * java -Djava.security.egd=file:/dev/./urandom TestRandom<br>
 * 
 * @author rough
 */
public class TestRandom {
    public static void main(String[] args) {
        try {
            System.out.println("Begin to get SecureRandom Instance.");
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            System.out.println("SR is ready for use....");
            System.out.println("Next double is :" + sr.nextDouble());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
