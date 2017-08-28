package io.ift.automation.commonflows.oms;

import io.ift.automation.AppName;
import io.ift.automation.commonflows.models.EmployeeTestData;
import io.ift.automation.helpers.database.SpringJdbcTemplateUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by patrick on 15/8/6.
 *
 * @version $Id: OmsHelper.java 1934 2015-10-27 07:47:13Z wuke $
 */


public class OmsHelper {

    /**
     * 根据分摊组织架构ID或者经理
     * @param orgIds
     * @return
     */
    public static List<EmployeeTestData> getManagerByApplyOrdIds(String orgIds) {
        String sql = "select a.id AS orgId,a.orgName,eb.userCode,ISNULL(eb.userNameCn,'暂无负责人') AS userName,p.positionName \n" +
                "\t\tfrom oms..v2_organization_final as a  WITH (NOLOCK) left join oms..v2_organization_final as b   WITH (NOLOCK)\n" +
                "\t\ton ( (a.ID = b.id and  b.orgType in ('分行','区域','部门')) or (a.id !=b.id AND b.orgType in ('门店') and a.orgLongCode like b.orgLongCode+'%')\t\t\t\t\t\t\t\t)\t\n" +
                "\t\tLEFT JOIN oms..T_OMS_EMPLOYEE_BASEINFOR eb WITH(NOLOCK) ON eb.userCode=a.manager\t\t\n" +
                "\t\tLEFT JOIN oms..T_OMS_POSITION p WITH(NOLOCK) ON p.id=eb.positionId \t\t\n" +
                "\t\t\t\t\t\t\t\t\tWHERE b.id IN (?) AND a.status IN ('1')";

        return SpringJdbcTemplateUtils
            .useDataBase(AppName.OMS.getName()).queryForList(sql, EmployeeTestData.class, orgIds);
    }

    /**
     * 根据员工号获取经理
     * @param userCode
     * @return
     */
    public static List<EmployeeTestData> getManagerByUserCode(String userCode){
        String sql="select employeeTable.* from v2_manager_final as employeeTable with(nolock)\n" +
                "where exists (select 1 from t_oms_employee_baseinfor as a with(nolock)\n" +
                "\t\twhere a.orgId = employeeTable.manageOrgId and a.userCode in (?));";
        return SpringJdbcTemplateUtils.useDataBase(AppName.OMS.getName()).queryForList(sql, EmployeeTestData.class, userCode);
    }

    /**
     *
     * @param privilegeUrl
     * @param companyName
     * @param appName
     * @return
     */
    public static List<EmployeeTestData> getEmployeeByPrivilege(String privilegeUrl, String companyName,String appName
            ) {
        String sql = " select  eb.userCode,eb.orgName,isnull(dp.deptId,eb.orgId) as orgId,eb.userName,eb.positionName from oms..v2_employee_final as eb  WITH (NOLOCK)\n" +
                "\t   left join v_current_budget_dept dp with (nolock) on dp.orgId=eb.orgId\n" +
                "\twhere eb.status in ('正式','试用期') AND eb.company in (?) and   \n" +
                "\t\t\texists (select 1 from oms..v2_privilege_final as p  WITH (NOLOCK)\n" +
                "\t\twhere p.userCode = eb.userCode and p.privilegeUrl = ? and p.appcode = ?)\n" +
                "\t\t and  ( ( eb.orgClass = '后台' and dp.deptId is not null) or (eb.orgClass != '后台') )" ;
        return SpringJdbcTemplateUtils.useDataBase(AppName.FEIYONG.getName()).queryForList(sql,
                EmployeeTestData.class,companyName,privilegeUrl,appName);

    }

    /**
     *
     * @param companyName
     * @param appCode
     * @param privilegeUrl
     * @param orgIds
     * @return
     */
    public static List<EmployeeTestData> getEmployeeByPrivilegeAndOrgIds( String companyName,String appCode,String privilegeUrl,String orgIds) {
        String sql ="SELECT\n" +
                "\teb.userCode,eb.orgName,\n" +
                "\tisnull(dp.deptId, eb.orgId) AS orgId,\n" +
                "\teb.userName,\n" +
                "\teb.positionName as position\n" +
                "FROM\n" +
                "\toms..v2_employee_final AS eb WITH (NOLOCK)\n" +
                "LEFT JOIN v_current_budget_dept dp ON dp.orgId = eb.orgId\n" +
                "WHERE\n" +
                "\t1 = 1\n" +
                "AND eb.status IN ('正式', '试用期')\n" +
                "AND eb.company IN (?)\n" +
                "AND EXISTS (\n" +
                "\tSELECT\n" +
                "\t\t1\n" +
                "\tFROM\n" +
                "\t\toms..v2_privilege_final AS p WITH (NOLOCK)\n" +
                "\tWHERE\n" +
                "\t\tp.userCode = eb.userCode\n" +
                "\tAND p.privilegeUrl = ?\n" +
                "\tAND p.appcode = ?\n" +
                ")\n" +
                "AND (\n" +
                "\t(\n" +
                "\t\teb.orgClass = '后台'\n" +
                "\t\tAND dp.deptId IS NOT NULL\n" +
                "\t)\n" +
                "\tOR (eb.orgClass != '后台')\n" +
                ") and exists (\n" +
                "select t.id\n" +
                "\tFROM\n" +
                "\t\toms..T_OMS_Organization t\n" +
                "\tINNER JOIN oms..t_oms_organization parent ON t.orgLongCode LIKE parent.orgLongCode + '%'\n" +
                "\tWHERE\n" +
                "\t\tparent.id IN (?)\n" +
                "and t.id=eb.orgId) ";

        return SpringJdbcTemplateUtils.useDataBase(AppName.FEIYONG.getName()).queryForList(sql, EmployeeTestData.class, companyName,
                appCode,privilegeUrl,orgIds);

    }

    /**
     *
     * @param companyName
     * @param appCode
     * @param privilegeUrl
     * @param orgName
     * @return
     */
    public static List<EmployeeTestData> getEmployeeByPrivilegeAndOrgNames(String companyName,String appCode,String privilegeUrl,String orgName) {

        String sql ="SELECT\n" +
                "\teb.userCode,eb.orgName,\n" +
                "\tisnull(dp.deptId, eb.orgId) AS orgId,\n" +
                "\teb.userName,\n" +
                "\teb.positionName as position\n" +
                "FROM\n" +
                "\toms..v2_employee_final AS eb WITH (NOLOCK)\n" +
                "LEFT JOIN v_current_budget_dept dp ON dp.orgId = eb.orgId\n" +
                "WHERE\n" +
                "\t1 = 1\n" +
                "AND eb.status IN ('正式', '试用期')\n" +
                "AND eb.company IN (?)\n" +
                "AND EXISTS (\n" +
                "\tSELECT\n" +
                "\t\t1\n" +
                "\tFROM\n" +
                "\t\toms..v2_privilege_final AS p WITH (NOLOCK)\n" +
                "\tWHERE\n" +
                "\t\tp.userCode = eb.userCode\n" +
                "\tAND p.privilegeUrl = ?\n" +
                "\tAND p.appcode = ?\n" +
                ")\n" +
                "AND (\n" +
                "\t(\n" +
                "\t\teb.orgClass = '后台'\n" +
                "\t\tAND dp.deptId IS NOT NULL\n" +
                "\t)\n" +
                "\tOR (eb.orgClass != '后台')\n" +
                ") and exists (\n" +
                "select t.id\n" +
                "\tFROM\n" +
                "\t\toms..T_OMS_Organization t\n" +
                "\tINNER JOIN oms..t_oms_organization parent ON t.orgLongCode LIKE parent.orgLongCode + '%'\n" +
                "\tWHERE\n" +
                "\t\tparent.orgName IN (?)\n" +
                "and t.id=eb.orgId) ";
        return SpringJdbcTemplateUtils.useDataBase(AppName.FEIYONG.getName()).queryForList(sql, EmployeeTestData.class, companyName,
                privilegeUrl,appCode,orgName);

    }

    /**
     * 
     * @param userCode
     * @return
     */
    public static EmployeeTestData getManagerBySignedAgentCode(String userCode){

        String sql = "  select [userCode],[userName] from [oms].[dbo].[v_employee_baseinfo_sales]\n" +
                "       where positionName like '%分行经理' and status!='离职'\n" +
                "       and [orgCode]=( SELECT [orgCode] FROM [oms].[dbo].[v_employee_baseinfo_sales]\n" +
                "       where [userCode]= ? )";

        return SpringJdbcTemplateUtils.useDataBase(AppName.OMS.getName()).queryForObject(sql,EmployeeTestData.class
        ,userCode);

    }

}
