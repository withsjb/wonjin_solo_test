package runner;

import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;
// import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;
// import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;
import io.cucumber.junit.CucumberOptions;

/**
 * 아래 옵션들은 Cucumber를 사용하기 위한 최소 옵션이다. <br>
 * features - 시나리오 파일의 경로 <br>
 * glue - 시나리오가 실행될 때 각 operation을 수행하는 클래스의 폴더명 <br>
 * tags - 실행할 시나리오의 구분자 <br>
 *
 * Runner Class를 최초의 시작 클래스로 판단하면 된다.
 * */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/main/features/"},
        glue = "stepdefinitions",
        tags = "@test",
        publish = true,
        plugin = {"pretty", "html:target/cucumber-reports"}
)
public class Runner {
}
