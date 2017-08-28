package io.ift.automation.testscaffold.codegenerator.apis.harmodel;

public class HarPageTimings {

    private int onLoad;
    private int onContentLoad;
    private int _startRender;

    public int getOnLoad() {
        return onLoad;
    }

    public void setOnLoad(int onLoad) {
        this.onLoad = onLoad;
    }

    public int getOnContentLoad() {
        return onContentLoad;
    }

    public void setOnContentLoad(int onContentLoad) {
        this.onContentLoad = onContentLoad;
    }

    public int get_startRender() {
        return _startRender;
    }

    public void set_startRender(int _startRender) {
        this._startRender = _startRender;
    }

    @Override
    public String toString() {
        return "HarPageTimings{" +
                "onLoad=" + onLoad +
                ", onContentLoad=" + onContentLoad +
                ", _startRender=" + _startRender +
                '}';
    }
}
