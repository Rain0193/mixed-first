package io.ift.automation.testscaffold.apitest;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ServiceDescriptionTest {

    @Test
    public void testLoadServiceDescriptionFromFile() throws Exception {
        ServiceDescription sd = ServiceDescription.loadServiceDescriptionFromFile("servicedescription/servicedefinition.json");
        assertTrue(sd.getHeaders().size()>0);
        assertTrue(sd.getPathParameters().size()>0);
        assertTrue(sd.getQueryParameters().size()>0);
        assertNotNull(sd);
    }

//    @Test
//    public void testLoadServiceDescription() throws Exception {
//        String sd = FileHelper.readFileToString("servicedescription/loginapi.json");
//        ServiceDescription d = ServiceDescription.loadServiceDescription(sd);
//        assertNotNull(d);
//    }

    @Test
    public void testLoadSD_1(){
        ServiceDescription sd = ServiceDescription.loadServiceDescriptionFromFile("servicedescription/findOrgById.json");
        System.out.println(sd);
    }
}
