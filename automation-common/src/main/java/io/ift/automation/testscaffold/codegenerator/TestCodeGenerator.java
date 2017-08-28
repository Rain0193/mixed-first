package io.ift.automation.testscaffold.codegenerator;

import java.io.File;

/**
 * Created by patrick on 15/6/1.
 *
 * @version $Id$
 */


public interface TestCodeGenerator {

    File generateTestCaseData();
    File generateTestCaseCode();
    File generateTestStepCode();

    /**
     * 生成所有的测试代码
     */
    default void generateAllCodes(){

        generateTestStepCode();
        generateTestCaseCode();
        generateTestCaseData();
    }
}
