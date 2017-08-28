package io.ift.automation.helpers;

import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by patrick on 15/8/28.
 */
public class ClassFinderHelperTest {

    @Test
    public void testGetClassListByPackageName() throws Exception {
       List<Class> test=  ClassFinderHelper.getClassListByPackageName("com.dooioo.automation.assertion",true);
        for (Class aClass : test) {
            System.out.println(aClass.getSimpleName());
        }
    }

    @Test
    public void testGetJarClassList() throws Exception{

        List<Class> test=  ClassFinderHelper.getClassListByPackageName("com.google.common",false);
        for (Class aClass : test) {
            System.out.println(aClass.getSimpleName());
        }
    }
}
