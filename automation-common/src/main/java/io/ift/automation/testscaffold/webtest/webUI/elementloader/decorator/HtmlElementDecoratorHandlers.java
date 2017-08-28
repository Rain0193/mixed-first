package io.ift.automation.testscaffold.webtest.webUI.elementloader.decorator;

import io.ift.automation.testscaffold.webtest.webUI.annotations.webpage.PreconditionElement;
import io.ift.automation.testscaffold.webtest.webUI.annotations.webpage.RepeatableOptions;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.HtmlElement;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.NgRepeatableElement;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.UploadElement;
import org.openqa.selenium.By;

import java.lang.reflect.Field;

/**
 * Created by patrick on 15/9/30.
 */

//put into context
public class HtmlElementDecoratorHandlers {

    public static <T extends HtmlElement> void  handlerHtmlInstance(T htmlElementInstance,
                                                                     String elementName,Field field){
        handleNgRepeatableElement(htmlElementInstance, elementName, field);
        handleUploadElement(htmlElementInstance,field);
    }

    private static <T extends HtmlElement>  void handleUploadElement(T htmlElementInstance,Field field) {
        if(field.getType().equals(UploadElement.class)){
            PreconditionElement o = field.getAnnotation(PreconditionElement.class);
            if(o!=null){
                htmlElementInstance.setPreconditionActionName(o.actionName());
                htmlElementInstance.setPreconditionElementXpathLocator(o.elementXpathLocator());
            }

        }
    }

    private static <T extends HtmlElement> void handleNgRepeatableElement(T htmlElementInstance, String elementName, Field field) {
        if(field.getType().equals(NgRepeatableElement.class)){
            RepeatableOptions o = field.getAnnotation(RepeatableOptions.class);
            if(o==null) throw new RuntimeException(elementName+"is NgRepeatableElement,must have RepeatableOptions annotation");
            ((NgRepeatableElement)htmlElementInstance).setInitCount(o.initCount());
            ((NgRepeatableElement)htmlElementInstance).setRepeatTriggerXpathLocator(o.repeatTriggerXpathLocator());
            ((NgRepeatableElement)htmlElementInstance).setOffsetXPathForRepeatableRoot(o.offsetXPathForRepeatableRoot());
        }
    }


}
