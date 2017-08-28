package io.ift.automation.testscaffold.apitest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BaseJsonEntityTest {

    private MockPerson source = new MockPerson();
    private MockPerson target= new MockPerson();

    @Before
    public void initTestData(){
        source.setMale("test");
        source.setName("name");
        target.setMale("target");
        target.setName("name");
    }
    @Test
    public void testEquals() throws Exception {
        Assert.assertFalse(source.equals(target));
    }

    @Test
    public void testToString(){
        System.out.println(source.toString());
        System.out.println(target.toString());
    }

    @Test
    public void testEqualIgnoreFields(){
        Assert.assertTrue(source.equalsByPassIgnoreFields(target, "male"));
        Assert.assertFalse(source.equalsByPassIgnoreFields(target));
        Assert.assertTrue(source.equalsByPassIgnoreFields(target, "name", "male"));
    }


    public class MockPerson extends BaseJsonEntity {
        private String male;
        private String name;

        public String getMale() {
            return male;
        }

        public void setMale(String male) {
            this.male = male;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
