//192.168.0.160
  10.8.1.8
## Get All Roles 
```sql
SELECT   a.userCode, d .applicationCode AS appCode, c.privilegeUrl, b.dataPrivilege, a.companyId
FROM      T_OMS_EMPLOYEE_ROLE AS a WITH (nolock) INNER JOIN
                T_OMS_ROLE_PRIVILEGE AS b WITH (nolock) ON a.roleId = b.roleId INNER JOIN
                T_OMS_APPLICATION_PRIVILEGE AS c WITH (nolock) ON b.privilegeId = c.ID INNER JOIN
                T_OMS_APPLICATION AS d WITH (nolock) ON c.appId = d .ID
UNION
SELECT   PO.userCode, A.applicationCode AS appCode, p.privilegeUrl, rp.dataPrivilege, PR.companyId
FROM      T_OMS_POSITION_ROLE PR WITH (nolock) INNER JOIN
                T_OMS_EMPLOYEE_POSITION AS PO ON PR.positionId = PO.positionId INNER JOIN
                T_OMS_POSITION AS POS WITH (NOLOCK) ON PO.positionId = POS.id INNER JOIN
                T_OMS_ORGANIZATION org WITH (NOLOCK) ON org.id = po.orgId INNER JOIN
                T_OMS_COMPANY c WITH (nolock) ON org.company = c.companyShortName AND pr.companyId = c.id INNER JOIN
                T_OMS_ROLE_PRIVILEGE AS RP WITH (nolock) ON pr.roleId = rp.roleId INNER JOIN
                T_OMS_APPLICATION_PRIVILEGE AS P WITH (nolock) ON RP.privilegeId = p.id INNER JOIN
                T_OMS_APPLICATION AS A WITH (nolock) ON p.appId = a.id
WHERE   POS.status = 1
UNION
SELECT   EP.userCode, A.applicationCode AS appCode, p.privilegeUrl, rp.dataPrivilege, O.companyId
FROM      T_OMS_ORGANIZATION_ROLE AS O WITH (nolock) INNER JOIN
                T_OMS_EMPLOYEE_POSITION AS EP WITH (NOLOCK) ON O.ORGID = EP.orgId INNER JOIN
                T_OMS_POSITION AS POS WITH (NOLOCK) ON EP.positionId = POS.id INNER JOIN
                T_OMS_ROLE_PRIVILEGE AS RP WITH (nolock) ON O.roleId = rp.roleId INNER JOIN
                T_OMS_APPLICATION_PRIVILEGE AS P WITH (nolock) ON RP.privilegeId = p.id INNER JOIN
                T_OMS_APPLICATION AS A WITH (nolock) ON p.appId = a.id
WHERE   POS.status = 1
UNION
/* 权限增加职等职级配置部分 by chenzhenkun*/ SELECT EP.userCode, A.applicationCode AS appCode, p.privilegeUrl, 
                rp.dataPrivilege, O.companyId
FROM      T_OMS_TITLE_ROLE O WITH (nolock) INNER JOIN
                T_OMS_EMPLOYEE_BASEINFOR AS EP WITH (NOLOCK) ON O.levelId = EP.levelId INNER JOIN
                T_OMS_COMPANY c WITH (nolock) ON EP.company = c.companyShortName AND o.companyId = c.id INNER JOIN
                T_OMS_ROLE_PRIVILEGE AS RP WITH (nolock) ON o.roleId = rp.roleId INNER JOIN
                T_OMS_APPLICATION_PRIVILEGE AS P WITH (nolock) ON RP.privilegeId = p.id INNER JOIN
                T_OMS_APPLICATION AS A WITH (nolock) ON p.appId = a.id
WHERE   O.status = 1
UNION
/* 权限增加组织树配置部分 by chenzhenkun*/ SELECT EP.userCode, A.applicationCode AS appCode, p.privilegeUrl, 
                rp.dataPrivilege, O.companyId
FROM      (SELECT   id AS orgId, longCodes.roleId, longCodes.companyId
                 FROM      T_OMS_ORGANIZATION org WITH (nolock),
                                     (SELECT   orgLongCode, tree.roleId, tree.companyId
                                      FROM      T_OMS_ORGANIZATION org1 WITH (nolock) INNER JOIN
                                                      T_OMS_ORGANIZATION_TREE_ROLE tree WITH (nolock) ON org1.id = tree.orgId
                                      WHERE   tree.status = 1) longCodes
                 WHERE   LEFT(org.orgLongCode, len(longCodes.orgLongCode)) = longCodes.orgLongCode) AS O INNER JOIN
                T_OMS_EMPLOYEE_POSITION AS EP WITH (NOLOCK) ON O.orgId = EP.orgId INNER JOIN
                T_OMS_POSITION AS POS WITH (NOLOCK) ON EP.positionId = POS.id INNER JOIN
                T_OMS_ROLE_PRIVILEGE AS RP WITH (nolock) ON O.roleId = rp.roleId INNER JOIN
                T_OMS_APPLICATION_PRIVILEGE AS P WITH (nolock) ON RP.privilegeId = p.id INNER JOIN
                T_OMS_APPLICATION AS A WITH (nolock) ON p.appId = a.id
WHERE   POS.status = 1
UNION
/* 权限增加（组织树+岗位) by -- lxh*/ SELECT ep.userCode, A.applicationCode AS appCode, p.privilegeUrl, rp.dataPrivilege, 
                tree.companyId
FROM      T_OMS_ORGANIZATION org WITH (nolock) INNER JOIN
                T_OMS_ORGANIZATION org1 WITH (nolock) ON org.orgLongCode LIKE org1.orgLongCode + '%' INNER JOIN
                T_OMS_ORGANIZATION_TREE_POSITION_ROLE tree WITH (nolock) ON org1.id = tree.orgId INNER JOIN
                T_OMS_EMPLOYEE_POSITION ep WITH (nolock) ON org.id = ep.orgId AND ep.positionId = tree.positionId INNER JOIN
                T_OMS_POSITION pos WITH (nolock) ON pos.id = ep.positionId INNER JOIN
                T_OMS_ROLE_PRIVILEGE rp WITH (nolock) ON tree.roleId = rp.roleId INNER JOIN
                T_OMS_APPLICATION_PRIVILEGE p WITH (nolock) ON p.id = rp.privilegeId INNER JOIN
                T_OMS_APPLICATION a WITH (nolock) ON a.id = p.appId
WHERE   tree.status = 1 AND pos.status = 1
```

## Get Position
```sql
-- get scrap_add employ
select distinct opr.positionId,p.positionName,p.positionDesc from T_OMS_ROLE_PRIVILEGE rp, T_OMS_APPLICATION_PRIVILEGE ap
,T_OMS_POSITION_ROLE opr,T_OMS_EMPLOYEE_POSITION ep,T_OMS_POSITION p 
where rp.privilegeId=ap.id
and ap.privilegeUrl='scrap_add'
and opr.roleId=rp.roleId
and ep.positionId=opr.positionId
and p.id=ep.positionId;
--and ep.userCode=98301;
-- get all scrap_add role
```





