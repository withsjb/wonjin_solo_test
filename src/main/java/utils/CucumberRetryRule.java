package utils;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.openqa.selenium.WebDriverException;

public class CucumberRetryRule implements TestRule {
    private int retryCount;

    public CucumberRetryRule(int retryCount) {
        this.retryCount = retryCount;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                int attempts = 0;
                Throwable lastException = null;
                // 최대 retryCount 번 재시도 (예: 3번)
                while (attempts <= retryCount) {
                    try {
                        base.evaluate();
                        return; // 정상 실행되면 종료
                    } catch (WebDriverException e) {
                        attempts++;
                        lastException = e;
                        System.out.println(description.getDisplayName() + ": WebDriverException 발생 (시도 " + attempts + "회)");
                        if (attempts > retryCount) {
                            // 3번 이상 재시도하면 예외를 던져서 해당 시나리오를 실패 처리
                            throw e;
                        }
                        // 재시도를 위해 드라이버 초기화나 환경 정리 코드 추가 가능 (필요 시)
                    } catch (Throwable t) {
                        // WebDriverException 이외의 오류는 바로 실패 처리
                        throw t;
                    }
                }
                throw lastException;
            }
        };
    }
}
