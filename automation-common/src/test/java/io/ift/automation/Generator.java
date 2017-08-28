package io.ift.automation;

import io.ift.automation.testscaffold.codegenerator.APICodesGenerator;

/**
 * Created by patrick on 15/9/2.
 */
public class Generator {

    public static void main(String[] args) {

        APICodesGenerator
                .nameAPIName("MobileKyPrivateAPI").nameAPICategory("Mobile/ky")
                .openAPIURL("http://open.dooioo.org/rest/docs?module=%E5%AE%A2%E6%BA%90API&api=%E5%AE%A2%E6%88%B7-%E8%8E%B7%E5%8F%96%E5%AE%A2%E6%88%B7%E5%88%97%E8%A1%A8&version=v3")
                .generateAllCodes();
    }
}
