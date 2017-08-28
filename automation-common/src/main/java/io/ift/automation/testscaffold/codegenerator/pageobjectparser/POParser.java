package io.ift.automation.testscaffold.codegenerator.pageobjectparser;

/**
 * Created by patrick on 15/7/13.
 *
 * @version $Id: POParser.java 1771 2015-08-21 06:02:12Z wuke $
 */
public interface POParser {

    void parseButtons();
    void parseLinks();
    void parseSelectList();
    void parseRadio();
    void parseCheckBox();
    default void parseOthers(){
        //do nothing
    }

    void parseTextArea();

    void parseInputBox();
    default void parse(){
        parseButtons();
        parseLinks();
        parseRadio();
        parseCheckBox();
        parseInputBox();
        parseSelectList();
        parseTextArea();
        parseOthers();
    }
}
