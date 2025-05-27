package utils.listeners;

import io.qameta.allure.Allure;
import io.qameta.allure.listener.TestLifecycleListener;
import io.qameta.allure.model.TestResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AllureAttachmentListener implements TestLifecycleListener {

    @Override
    public void beforeTestStop(TestResult result) {
        attachScreenshot();
    }

    private void attachScreenshot() {
        try {
            File file = new File("path/to/screenshot.png");
            FileInputStream fileInputStream = new FileInputStream(file);

            Allure.addAttachment("Failure Screenshot", fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

