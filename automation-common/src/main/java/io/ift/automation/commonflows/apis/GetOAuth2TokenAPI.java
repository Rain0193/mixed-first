package io.ift.automation.commonflows.apis;

import io.ift.automation.helpers.RestTemplateClientHelper;
import io.ift.automation.testscaffold.apitest.RequestData;
import io.ift.automation.testscaffold.apitest.baseapi.BaseWebService;

/**
 * 获取OAuth2 Token的API
 * Created by patrick on 15/4/24.
 *
 * @version $Id$
 */

@SuppressWarnings("unchecked")
public class GetOAuth2TokenAPI extends BaseWebService {
    private final static String SERVICEDESCRIPTION_PATH = "servicedescription/oauth2/getOAuth2Token.json";

    public GetOAuth2TokenAPI(RequestData data) {
        super(SERVICEDESCRIPTION_PATH, data);
    }

    public GetOAuth2TokenAPI(RestTemplateClientHelper client, RequestData data) {
        super(client, SERVICEDESCRIPTION_PATH, data);
    }

}
