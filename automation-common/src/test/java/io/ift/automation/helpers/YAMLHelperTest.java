package io.ift.automation.helpers;

import io.ift.automation.testscaffold.webtest.webUI.UIdescription.ElementDescription;
import com.google.common.collect.Maps;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

public class YAMLHelperTest {

    @Test
    public void testParse() throws Exception {
        Map<String,Map<String,String>> map = Maps.newHashMap();
            map= YAMLHelper.parse("pages/baiduhomepage.yaml", HashMap.class);
        assertNotNull(map);
    }

    @Test
    public void testParse_AsMap() throws Exception {
        Map map=YAMLHelper.parseAsMap("pages/baiduhomepage3.yaml");
        assertNotNull(map);
    }

    @Test
    public void testParse_AsClassMap() throws Exception {
        Map map=YAMLHelper.parseAsClassMap("pages/baiduhomepage3.yaml", ElementDescription.class);
        assertNotNull(map);
    }

    @Test
    public void testParse_ElementDescription() throws Exception {
        ElementDescription description= YAMLHelper.parse("pages/baiduhomepage2.yaml", ElementDescription.class);
        System.out.println(description);
        assertNotNull(description);
    }


    @Test
    public void testParseAsList() throws Exception {
        List<ElementDescription> description= YAMLHelper.parseAsList("pages/baiduhomepage2.yaml", ElementDescription.class);
        System.out.println(description);
        assertNotNull(description);
    }

    @Test
       public void testParse_AsClassMap_Map() throws Exception {
        Map<String,ElementDescription> l = YAMLHelper.parseAsMap("pages/baiduhomepage4.yaml", ElementDescription.class,"name");
        assertNotNull(l);
    }
}