import com.jayway.restassured.response.Response;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

/**
 * Created by jennyhui on 12/9/15.
 */

public class WeatherDEMO implements JavaSamplerClient {

    private final String URL = "http://api.openweathermap.org/data/2.5/weather?q=ShangHai,cn&appid=2de143494c0b295cca9337e1e96b00e0";
    private RequestData request;


    /**
     * Do any initialization required by this client.
     *
     * @param javaSamplerContext
     */
    public void setupTest(JavaSamplerContext javaSamplerContext) {
        request = new RequestData();
        request.setMethod("GET");
        request.setBody("");
        request.setUrl(URL);
    }

    /**
     * Perform a single sample for each iteration.
     *
     * @param javaSamplerContext
     * @return
     */
    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {
        SampleResult jmeter_result = new SampleResult();
        jmeter_result.setSamplerData(request.toString());
        jmeter_result.sampleStart();
        Response response = RestAssuredClientUtil.executeHttp(request);
//        response.prettyPrint();
        jmeter_result.sampleEnd();
        jmeter_result.setSuccessful(response.getStatusCode() == 200);
        jmeter_result.setResponseData(response.prettyPrint(), "utf-8");
//        jmeter_result.setDataType(SampleResult.TEXT);
        return jmeter_result;

    }

    public void teardownTest(JavaSamplerContext javaSamplerContext) {

    }

    /**
     * Provide a list of parameters which this test supports.
     *
     * @return have fun !
     */
    public Arguments getDefaultParameters() {
        Arguments arguments = new Arguments();
        arguments.addArgument("JennyHui", "18");
        arguments.addArgument("Amoy", "China");
        return arguments;
    }

    public static void main(String[] args) {
        WeatherDEMO test = new WeatherDEMO();
        JavaSamplerContext context = new JavaSamplerContext(test.getDefaultParameters());
        test.setupTest(context);
        test.runTest(context);
        test.teardownTest(context);
        System.exit(0);
    }
}
