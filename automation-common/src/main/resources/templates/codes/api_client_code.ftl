<#if package_name?exists>
package com.Domain.automation.apis.${package_name};
<#else>
package com.Domain.automation.apis;
</#if>

import com.Domain.automation.helpers.RestTemplateClientHelper;
import com.Domain.automation.testscaffold.apitest.RequestData;
import com.Domain.automation.testscaffold.apitest.baseapi.BaseWebService;

public class ${api_name} extends BaseWebService {

    private final static String SERVICEDESCRIPTION_PATH = "${service_description_path}";

    public ${api_name}(RequestData data) {
        super(SERVICEDESCRIPTION_PATH, data);
    }

    public ${api_name}(RestTemplateClientHelper client, RequestData data) {
        super(client, SERVICEDESCRIPTION_PATH, data);
    }

}
