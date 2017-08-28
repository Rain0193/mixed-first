package io.ift.automation.commonflows;


public class Constants {
  //start sso 单点登录相关

  public static final int USERCODEMASK = 997;
  public static final String SESSION_USER = "SESSION_USER";
  public static final String SESSION_PRIVILEGE = "SESSION_PRIVILEGE";
  //资产单位属性
  public static final String UNIT = "1001";
  //资产公用放置地
  public static final String ASSETS_PLACE = "1003";
  //资产编号 8位
  public static final int ASSET_CODE_INIT = 10000000;
  //分页显示默认大小
  public static final int DEFAULT_PAGE_SIZE = 20;

  

  public static final String VOUCHER_ASSETS_REGISTER = "Assets_Register";

  public static final String VOUCHER_ASSETS_APPLY = "Assets_Apply";

  public static final String VOUCHER_ASSETS_PANYIN = "Assets_PanYin";

  public static final String VOUCHER_ASSETS_LOSSES = "Assets_Losses";

  public static final String VOUCHER_ASSETS_SCRAP = "Assets_Scrap";

  public static final String VOUCHER_ASSETS_CHANGE = "Assets_Change";

  public static final String VOUCHER_ASSETS_DEPRECIATION = "Assets_Depreciation";

  public static final String ORG_TYPE_BIG_AREA = "大区";

  public static final String ORG_TYPE_AREA = "区域";

  public static final String ORG_TYPE_STORE = "门店";

  public static final String ORG_TYPE_BRANCH = "分行";

  public static final String ORG_TYPE_DEPT = "部门";


  /**
   * 资产APPCode
   */
  public static final String ASSETS_APP_CODE = "AssetManager";
  /**
   * 常量表数据
   */
  public static final String ASSETS_CONSTANTS = "constants";

  /**
   * 资产分类
   */
  public static final String ASSETS_TYPE = "assetsType";


  /**
   * 资产盘亏列表
   */
  public static final String ASSETS_PANKUI_LIST = "assets_pankui_list";

  /**
   * 资产仓库列表
   */
  public static final String ASSETS_CANGKU_LIST = "assets_cangku_list";
  /**
   * 资产appid
   */
  public static final int ASSETS_APP_ID = 16;
  /**
   * 资产信息-查看资产列表权限（有数据范围）
   */
  public static final String ASSETS_LIST = "assets_list";

  /**
   * 查看资产调配记录（有数据范围）
   */
  public static final String DEPLOY_LIST = "deploy_list";
  /**
   * 资产信息-已报废资产列表权限（有数据范围）
   */
  public static final String ASSETS_SCRAPLIST = "assets_scraplist";
  /**
   * 资产信息-未使用资产列表权限
   */
  public static final String ASSETS_UNUSELIST = "assets_unuselist";

  /**
   * 资产信息 - 添加资产权限（有数据范围，对应资产编辑，资产删除权限。数据范围与列表数据范围冲突时，操作权限以最小范围为主，需验证）
   */
  public static final String ASSETS_ADD = "assets_add";
  /**
   * 资产信息-修改价格权限
   */
  public static final String ASSETS_MODIFYPRICE = "assets_modifyprice";

  /**
   * 浏览申请列表的权限
   */
  public static final String VIEW_APPLY_LIST = "view_apply_list";

  /**
   * 查看维修记录
   */
  public static final String VIEW_REPAIR_LIST = "view_repair_list";

  /**
   * 资产批量验收
   */
  public static final String ASSETS_BATCH_CHECKED = "assets_batch_check";

  /**
   * 资产转移
   */
  public static final String ASSETS_TRANSFERS = "assets_transfer";
  /**
   * 跨公司资产转移
   */
  public static final String ASSETS_CROSS_TRANSFERS = "assets_cross_transfer";
  /**
   * 资产设为闲置
   */
  public static final String ASSETS_SET_IDLE = "assets_set_idle";
  /**
   * 资产设为非闲置
   */
  public static final String ASSETS_SET_NONIDLE = "assets_set_nonidle";
  /**
   * 资产闲置列表
   */
  public static final String ASSETS_IDLE_LIST = "assets_idle_list";

  /**
   * 资产闲置审批
   */
  public static final String ASSETS_IDLE_APPROVAL = "assets_idle_approval";

  /**
   * 正常资产批量导出
   */
  public static final String ASSETS_BATCH_EXPORT = "assets_batch_export";

  /**
   * 编辑资产放置地和使用人
   */
  public static final String ASSETS_EDIT_INFO = "assets_edit_info";
  /**
   * 维修审批
   */
  public static final String REPAIR_APPROVAL = "repair_approval";

  /**
   * 资产盘点
   */
  public static final String ASSET_INVENTORY = "asset_inventory";
  /**
   * 资产盘点列表
   */
  public static final String ASSET_INVENTORY_LIST = "asset_inventory_list";
  /**
   * 助理申请资产报修
   */
  public static final String REPAIR_ASSISTANT_APPLY = "repair_assistant_apply";


  /**
   * 浏览报废列表的权限
   */
  public static final String VIEW_SCRAP_LIST = "view_scrap_list";
  /**
   * 资产信息-回收成未使用权限
   */
  public static final String ASSETS_RECOVER = "assets_recover";

  /**
   * 资产实物验收权限（有数据范围）
   */
  public static final String ASSETS_REALCHECK = "assets_realcheck";
  /**
   * 资产财务验收权限
   */
  public static final String ASSETS_FINANCIALCHECK = "assets_financialcheck";
  //查看待验收资产
  public static final String VIEW_ASSETS_CHECKLIST = "view_assets_check_list";
  /**
   * 资产申请-添加申请权限（有数据范围）
   */
  public static final String APPLY_ADD = "apply_add";
  /**
   * 申请审批
   */
  public static final String APPLY_APPROVAL = "apply_approval";
  /**
   * 申请时财务审批
   */
  public static final String APPLY_FINANCIAL_CHECKED0 = "apply_financial_check0";
  /**
   * 申请时财务确认
   */
  public static final String APPLY_FINANCIAL_CHECKED = "apply_financial_check";

  /**
   * 行政确认
   */
  public static final String APPLY_XINGZHENG_CHECKED = "apply_xingzheng_checked";

  /**
   * 资产编码打印
   */
  public static final String PRINT_ASSET_CODE = "print_asset_code";
  /**
   * 申请行政审批
   */
  public static final String APPLY_ADAPPROVAL = "apply_adapproval";
  /**
   * 助理确认
   */
  public static final String APPLY_ASSISTANT_CHECKED = "apply_assistant_check";
  /**
   * 采购部门审批
   */
  public static final String APPLY_PURCHASE_APPROVAL = "apply_purchase_approval";

  /**
   * 资产报废 - 验收部门验收权限
   */
  public static final String SCRAP_CHECKED = "scrap_checked";

  /**
   * 资产报废 - 申请报废权限（有数据范围）
   */
  public static final String SCRAP_ADD = "scrap_add";
  /**
   * 资产报废-审批权限（有数据范围，经理审批）
   */
  public static final String SCRAP_APPROVAL = "scrap_approval";
  /**
   * 资产报废-财务审批权限
   */
  public static final String SCRAP_FAPPROVAL = "scrap_fapproval";
  /**
   * 资产报废-行政审批权限
   */
  public static final String SCRAP_ADAPPROVAL = "scrap_adapproval";
  /**
   * 系统设置-分类管理权限
   */
  public static final String SYSTEM_CLASSIFICATION = "system_classification";
  /**
   * 系统设置-资产分类属性管理权限
   */
  public static final String SYSTEM_CLASSPROPERTY = "system_classproperty";
  /**
   * 系统设置-放置地管理权限
   */
  public static final String SYSTEM_PLACE = "system_place";

  /**
   * 资产报表-汇总列表查看权限（有数据范围）
   */
  public static final String REPORT_LIST = "report_list";
  /**
   * 资产报表-明细列表查看权限
   */
  public static final String REPORT_DETAIL = "report_detail";
  //维修商管理
  public static final String ASSETS_MANAGER_SERVICE = "assets_manager_service";

  //预算部门管理
  public static final String DEPT_MANAGE = "dept_manage";
  /**
   * 条形码打印权限 （无数据范围）
   */
  public static final String BARCODE_PRINT = "barcode_print";

  /**
   * 维修财务审批节点
   */
  public static final String FINANCIAL_REPAIR_APPROVAL = "financial_repair_approval";
  /**
   * 调配权限
   */
  public static final String TIAOPEI = "tiaopei";

  public static final int SCOPE_ALL = 100;
  public static final int SCOPE_CURRENT_ORGANAZITION = 10;
  public static final int SCOPE_SELF = 1;
  public static final int SCOPE_NONE = -1;
  public static final int SCOPE_CURRENT_ORG_TREE = 50;
  /**
   * 默认Assigneee 权限
   */
  public static final String DEFAULT_ASSIGNEES_URL = "'defaultUrl'";

  /**
   * 资产分类缩写的最大长度
   */
  public static final int assetsClassShortNameLength = 4;

  /**
   * 德佑
   */
  public static final String ASSET_CODE_domain_PREFIX = "DY";
  /**
   * 德融
   */
  public static final String ASSET_CODE_DERONG_PREFIX = "DR";


  /**
   * 增加仓库新增权限
   */
  public static final String WAREHOUSE_ADD = "warehouse_add";

  /**
   * 增加仓库新增权限
   */
  public static final String ASSET_LOSE = "asset_lose";

  /**
   * 增加资产禁用,相当于删除功能
   */
  public static final String ASSET_DISABLE = "asset_disable";

  /**
   * 登记资产批量删除
   */
  public static final String BATCH_ASSETS_DELETE = "batch_assets_delete";
  /**
   * 登记资产批量编辑
   */
  public static final String BATCH_ASSETS_EDIT = "batch_assets_edit";

  /**
   * 登记资产批量导出财务确认
   */
  public static final String BATCH_EXPORT_FINANCE_CONFIRM = "batch_export_finance_confirm";

  /**
   * 登记资产批量导入财务确认
   */
  public static final String BATCH_IMPORT_FINANCE_CONFIRM = "batch_import_finance_confirm";

  /**
   * 批量行政确认
   */
  public static final String BATCH_XINGZHENG_CHECKED = "batch_xingzheng_checked";

  /**
   * 资产转移记录
   */
  public static final String ASSET_TRANSFERLIST = "assets_transfer_lists";


  /**
   * 行政审批
   */
  public static final String APPLY_ADMINISTRATION_APPROVAL = "apply_administration_approval";

  /**
   * 新增折旧/关账
   */
  public static final String FINANCE_PERIOD_ADD = "finance_period_add";

  /**
   * 资产凭证
   */
  public static final String ASSETS_VOUCHER = "assets_voucher";

  /**
   * 财务关账
   */
  public static final String FINANCE_CLOSING = "finance_closing";
  /**
   * 修改资产财务信息
   */
  public static final String ASSETS_EDIT_FINANCE_INFO = "assets_edit_finance_info";

  /**
   * 资产管理员参看待确认列表
   */
  public static final String ASSET_CHECKED_LIST_ADMIN = "asset_checked_list_admin";

  /**
   * 资产转移调拨管理员查看审批列表
   */
  public static final String
      ASSET_TRANSFER_APPROVAL_LIST_ADMIN =
      "asset_transfer_approval_list_admin";

  /**
   * 资产编辑放置地
   */
  public static final String ASSETS_EDIT_PLACE_INFO = "assets_edit_place_info";

  /**
   * 资产编辑供应商信息
   */
  public static final String ASSETS_SUPPLIER_INFO = "assets_edit_supplier_info";
  /**
   * 查看资产财务信息变更记录
   */
  public static final String ASSETS_VIEW_FINANCE_INFO_CHANGES = "assets_view_finance_info_changes";

  /**
   * 资产门店暂停列表
   */
  public static final String ASSET_STORE_PAUSE_LIST = "asset_store_pause_list";
  /**
   * 暂停门店资产列表
   */
  public static final String PAUSED_ASSETS_LIST = "paused_assets_list";
  /**
   * 暂停门店资产列表导出
   */
  public static final String PAUSED_ASSETS_EXPORT = "paused_assets_export";


  /**
   * 门店暂停新增
   */
  public static final String ASSET_STORE_PAUSE_ADD = "asset_store_pause_add";

  /**
   * 门店暂停启用
   */
  public static final String ASSET_STORE_PAUSE_ENABLE = "asset_store_pause_enable";

  /**
   * 小数部分最大小数
   */
  public static final int MAX_FRACTION_SCALE = 0x2;

  /**
   * 日期格式 yyyy-MM
   */
  public static final String DATE_YYYY_MM_FORMAT = "yyyy-MM";
  /**
   * /** 日期格式 yyyy-MM-dd
   */
  public static final String DATE_YYYY_MM_DD_FORMAT = "yyyy-MM-dd";
  /**
   * 日期格式 yyyy-MM-dd HH:mm:ss
   */
  public static final String DATE_YMD_HH_MM_SS_FORMAT = "yyyy-MM-dd HH:mm:ss";
  /**
   * 测试环境IP
   */
  public static final String TEST_IP = "10.8.1.200";
  /**
   * 集成环境IP
   */
  public static final String INTEGRATION_IP = "192.168.3.46";
  /**
   * 生产环境IP
   */
  public static final String PRODUCTION_IP = "192.168.0.167";
  /**
   * ME环境IP
   */
  public static final String PREVIEW_IP = "192.168.7.108";
  /**
   * 可运行IP集合
   */
  public static final String
      CAN_RUN_IP =
      TEST_IP + "," + INTEGRATION_IP + "," + PRODUCTION_IP + "," + PREVIEW_IP;
}
