import com.uncodia.openaispigot.OpenAiCodeCompletionTask;
import com.uncodia.openaispigot.UnirestUtil;
import org.testng.annotations.Test;

public class JunitClassTest {
    @Test
    public void testMethod() {
        String output=UnirestUtil.openAICompletions("Say Hello");
//        assert(output!=null);
        System.out.println(output);

    }
}
