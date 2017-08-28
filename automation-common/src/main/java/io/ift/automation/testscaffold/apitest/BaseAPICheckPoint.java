package io.ift.automation.testscaffold.apitest;

import io.ift.automation.testscaffold.CheckPoint;
import org.springframework.http.ResponseEntity;

/**
 * Created by patrick on 15/6/4.
 *
 * @version $Id: BaseAPICheckPoint.java 1771 2015-08-21 06:02:12Z wuke $
 */


public abstract class BaseAPICheckPoint implements CheckPoint{

    protected ResponseEntity response ;

    public BaseAPICheckPoint() {
    }

    @Override
    public void setContext(Object context) {
        response=(ResponseEntity)context;
    }


}
