package io.ift.automation.commonflows.oms;

import io.ift.automation.commonflows.Constants;
import io.ift.automation.commonflows.models.EmployeeTestData;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by patrick on 15/8/24.
 */
public class OmsHelperTest {

    @Test
    public void testGetManagerByApplyOrdIds() throws Exception {
        List<EmployeeTestData> employees = OmsHelper.getManagerByApplyOrdIds("20241");
        System.out.println(employees);
    }

    @Test
    public void testGetManagerByApplyOrdIds1() throws Exception {
        List<EmployeeTestData> employees = OmsHelper.getEmployeeByPrivilege("20241", "bd", "erd");
        System.out.println(employees);
    }


    @Test
    public void testGetEmployeeByPrivilege() throws Exception {
        List<EmployeeTestData> employees = OmsHelper.getEmployeeByPrivilege(Constants.SCRAP_ADAPPROVAL,"德佑", "AssetManager");
        System.out.println(employees);
    }


    @Test
    public void testGetEmployeeByPrivilegeAndOrgIds() throws Exception {
        List<EmployeeTestData> employees = OmsHelper.getEmployeeByPrivilegeAndOrgIds("德佑","AssetManager",
                Constants.SCRAP_CHECKED,"20045");
        System.out.println(employees);
    }

    @Test
    public void testGetEmployeeByPrivilegeAndOrgNames() throws Exception {
        List<EmployeeTestData> employees = OmsHelper.getEmployeeByPrivilegeAndOrgNames("德佑",
                "AssetManager", Constants.SCRAP_CHECKED,"IT1组").subList(0,1);
        System.out.println(employees);
    }
}
