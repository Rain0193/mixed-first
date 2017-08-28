package io.ift.automation.testscaffold.codegenerator.webui;

import io.ift.automation.helpers.StringHelper;

/**
 * Created by patrick on 15/6/1.
 *
 * @version $Id$
 * @author patrick
 */
public class WebTestAtomicAction implements Comparable<WebTestAtomicAction> {
    private WebElementDescription element;
    // find by annotation value
//    private String branchActionDescription;
    //find by annotation value
//    private String checkPointName;
    private int seq;
//    private String flowName;
    private String elementAndActionPair;

    /**
     * <p>Constructor for WebTestAtomicAction.</p>
     *
     * @param element a {@link WebElementDescription} object.
     * @param seqAndElementAction a {@link java.lang.String} object.
     */
    public WebTestAtomicAction(WebElementDescription element,String seqAndElementAction) {

        this.element = element;
        setSeqAndElementAction(seqAndElementAction);
    }

    /**
     * <p>Getter for the field <code>element</code>.</p>
     *
     * @return a {@link WebElementDescription} object.
     */
    public WebElementDescription getElement() {
        return element;
    }

    /**
     * <p>Setter for the field <code>element</code>.</p>
     *
     * @param element a {@link WebElementDescription} object.
     */
    public void setElement(WebElementDescription element) {
        this.element = element;
    }



    /** {@inheritDoc} */
    @Override
    public int compareTo(WebTestAtomicAction o) {
        return this.seq-o.seq;
    }


    /**
     * <p>Getter for the field <code>seq</code>.</p>
     *
     * @return a int.
     */
    public int getSeq() {
        return Integer.parseInt(elementAndActionPair.split(" ")[0]);
    }

    /**
     * <p>Setter for the field <code>seq</code>.</p>
     *
     * @param seq a int.
     */
    public void setSeq(int seq) {
        this.seq = seq;
    }

    /**
     * <p>Getter for the field <code>elementAndActionPair</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getElementAndActionPair() {
        return elementAndActionPair;
    }

    /**
     * <p>Setter for the field <code>elementAndActionPair</code>.</p>
     *
     * @param elementAndActionPair a {@link java.lang.String} object.
     */
    public void setElementAndActionPair(String elementAndActionPair) {

        this.elementAndActionPair = elementAndActionPair;
    }

    /**
     * <p>setSeqAndElementAction.</p>
     *
     * @param seqAndElementAction a {@link java.lang.String} object.
     */
    public void setSeqAndElementAction(String seqAndElementAction){
        String[] pair=seqAndElementAction.replace(StringHelper.SPACE, "-").split("-");
        if(pair.length>1){
            this.seq=Double.valueOf(pair[0]).intValue();
            this.elementAndActionPair=element.getVariableName()+StringHelper.SPACE+pair[1];
        }else{
            this.seq=Double.valueOf(pair[0]).intValue();
            this.elementAndActionPair=element.getVariableName();
        }
    }


    /** {@inheritDoc} */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WebTestAtomicAction that = (WebTestAtomicAction) o;

        if (seq != that.seq) return false;
        if (element != null ? !element.equals(that.element) : that.element != null) return false;
        return !(elementAndActionPair != null ? !elementAndActionPair.equals(that.elementAndActionPair) : that.elementAndActionPair != null);

    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        int result = element != null ? element.hashCode() : 0;
        result = 31 * result + seq;
        result = 31 * result + (elementAndActionPair != null ? elementAndActionPair.hashCode() : 0);
        return result;
    }
}
