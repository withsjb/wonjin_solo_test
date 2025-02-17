package runner;

import io.cucumber.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
// import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;
// import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;
import io.cucumber.junit.CucumberOptions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static utils.AndroidManager.copyJsonFile;

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
        plugin = {"pretty", "html:target/cucumber-reports", "json:target/cucumber.json"}

)
public class Runner {
    static String dateOnly = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    private static final String CUCUMBER_JSON = "/cucumber.json";
    private static final String CUCUMBER_DAY_JSON = "/target_"+ dateOnly +"/cucumber_" + dateOnly + ".json"; // 날짜만 포함된 결과 파일

    @AfterClass
    public static void uploadReport(){
        try{
            File dayFolder = new File("target/target_" + dateOnly);
            if (!dayFolder.exists()) {
                dayFolder.mkdirs(); // 폴더가 없으면 생성
                System.out.println("폴더가 없어서 생성되었습니다: " + dayFolder.getAbsolutePath());
                File dayFile = new File(CUCUMBER_DAY_JSON);

                if (dayFile.exists()) {
                    // 날짜가 같은 파일이 존재하면 기존 파일 삭제 후 새로운 파일로 교체
                    System.out.println("같은 날짜의 파일이 존재하여 덮어씁니다.");
                    dayFile.delete(); // 기존 파일 삭제
                }

                copyJsonFile(CUCUMBER_JSON, CUCUMBER_DAY_JSON);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
