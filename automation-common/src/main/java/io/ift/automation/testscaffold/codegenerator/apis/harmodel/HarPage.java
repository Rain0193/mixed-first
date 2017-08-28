package io.ift.automation.testscaffold.codegenerator.apis.harmodel;

public class HarPage {

    private String startedDateTime;
    private String title;
    private String id;
    private HarPageTimings harPageTimings;
    private String _URL;
    private int _loadTime;
    private int _TTFB;
    private int _bytesOut;
    private int _bytesOutDoc;
    private int _connections;
    private int _requests;
    private int _requestsDoc;
    private int _responses_200;
    private int _responses_404;
    private int _responses_other;
    private int _render;
    private int _fullyLoaded;
    private int _cached;
    private int _docTime;
    private int _domTime;
    private int _score_cache;
    private int _score_cdn;
    private int _score_gzip;
    private int _score_cookies;
    //public int _score_keep-alive;
    private int _score_minify;
    private int _score_combine;
    private int _score_compress;
    private int _score_etags;
    private int _gzip_total;
    private int _gzip_savings;
    private int _minify_total;
    private int _minify_savings;
    private int _image_total;
    private int _image_savings;
    private int _optimization_checked;
    private int _aft;
    private int _domElements;
    private String _pageSpeedVersion;
    private String _title;
    private int _titleTime;
    private int _loadEventStart;
    private int _loadEventEnd;
    private int _domContentLoadedEventStart;
    private int _domContentLoadedEventEnd;
    private int _lastVisualChange;
    private String _browser_name;
    private String _browser_version;
    private int _server_count;
    private int _server_rtt;
    private String _base_page_cdn;
    private int _adult_site;
    private int _fixed_viewport;
    private int _score_progressive_jpeg;
    private int _firstPaint;
    private double _docCPUms;
    private double _fullyLoadedCPUms;
    private int _docCPUpct;
    private int _date;
    private int _SpeedIndex;
    private int _visualComplete;
    private int _run;
    private int _effectiveBps;
    private int _effectiveBpsDoc;

    public String getStartedDateTime() {
        return startedDateTime;
    }

    public void setStartedDateTime(String startedDateTime) {
        this.startedDateTime = startedDateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HarPageTimings getHarPageTimings() {
        return harPageTimings;
    }

    public void setHarPageTimings(HarPageTimings harPageTimings) {
        this.harPageTimings = harPageTimings;
    }

    public String get_URL() {
        return _URL;
    }

    public void set_URL(String _URL) {
        this._URL = _URL;
    }

    public int get_loadTime() {
        return _loadTime;
    }

    public void set_loadTime(int _loadTime) {
        this._loadTime = _loadTime;
    }

    public int get_TTFB() {
        return _TTFB;
    }

    public void set_TTFB(int _TTFB) {
        this._TTFB = _TTFB;
    }

    public int get_bytesOut() {
        return _bytesOut;
    }

    public void set_bytesOut(int _bytesOut) {
        this._bytesOut = _bytesOut;
    }

    public int get_bytesOutDoc() {
        return _bytesOutDoc;
    }

    public void set_bytesOutDoc(int _bytesOutDoc) {
        this._bytesOutDoc = _bytesOutDoc;
    }

    public int get_connections() {
        return _connections;
    }

    public void set_connections(int _connections) {
        this._connections = _connections;
    }

    public int get_requests() {
        return _requests;
    }

    public void set_requests(int _requests) {
        this._requests = _requests;
    }

    public int get_requestsDoc() {
        return _requestsDoc;
    }

    public void set_requestsDoc(int _requestsDoc) {
        this._requestsDoc = _requestsDoc;
    }

    public int get_responses_200() {
        return _responses_200;
    }

    public void set_responses_200(int _responses_200) {
        this._responses_200 = _responses_200;
    }

    public int get_responses_404() {
        return _responses_404;
    }

    public void set_responses_404(int _responses_404) {
        this._responses_404 = _responses_404;
    }

    public int get_responses_other() {
        return _responses_other;
    }

    public void set_responses_other(int _responses_other) {
        this._responses_other = _responses_other;
    }

    public int get_render() {
        return _render;
    }

    public void set_render(int _render) {
        this._render = _render;
    }

    public int get_fullyLoaded() {
        return _fullyLoaded;
    }

    public void set_fullyLoaded(int _fullyLoaded) {
        this._fullyLoaded = _fullyLoaded;
    }

    public int get_cached() {
        return _cached;
    }

    public void set_cached(int _cached) {
        this._cached = _cached;
    }

    public int get_docTime() {
        return _docTime;
    }

    public void set_docTime(int _docTime) {
        this._docTime = _docTime;
    }

    public int get_domTime() {
        return _domTime;
    }

    public void set_domTime(int _domTime) {
        this._domTime = _domTime;
    }

    public int get_score_cache() {
        return _score_cache;
    }

    public void set_score_cache(int _score_cache) {
        this._score_cache = _score_cache;
    }

    public int get_score_cdn() {
        return _score_cdn;
    }

    public void set_score_cdn(int _score_cdn) {
        this._score_cdn = _score_cdn;
    }

    public int get_score_gzip() {
        return _score_gzip;
    }

    public void set_score_gzip(int _score_gzip) {
        this._score_gzip = _score_gzip;
    }

    public int get_score_cookies() {
        return _score_cookies;
    }

    public void set_score_cookies(int _score_cookies) {
        this._score_cookies = _score_cookies;
    }

    public int get_score_minify() {
        return _score_minify;
    }

    public void set_score_minify(int _score_minify) {
        this._score_minify = _score_minify;
    }

    public int get_score_combine() {
        return _score_combine;
    }

    public void set_score_combine(int _score_combine) {
        this._score_combine = _score_combine;
    }

    public int get_score_compress() {
        return _score_compress;
    }

    public void set_score_compress(int _score_compress) {
        this._score_compress = _score_compress;
    }

    public int get_score_etags() {
        return _score_etags;
    }

    public void set_score_etags(int _score_etags) {
        this._score_etags = _score_etags;
    }

    public int get_gzip_total() {
        return _gzip_total;
    }

    public void set_gzip_total(int _gzip_total) {
        this._gzip_total = _gzip_total;
    }

    public int get_gzip_savings() {
        return _gzip_savings;
    }

    public void set_gzip_savings(int _gzip_savings) {
        this._gzip_savings = _gzip_savings;
    }

    public int get_minify_total() {
        return _minify_total;
    }

    public void set_minify_total(int _minify_total) {
        this._minify_total = _minify_total;
    }

    public int get_minify_savings() {
        return _minify_savings;
    }

    public void set_minify_savings(int _minify_savings) {
        this._minify_savings = _minify_savings;
    }

    public int get_image_total() {
        return _image_total;
    }

    public void set_image_total(int _image_total) {
        this._image_total = _image_total;
    }

    public int get_image_savings() {
        return _image_savings;
    }

    public void set_image_savings(int _image_savings) {
        this._image_savings = _image_savings;
    }

    public int get_optimization_checked() {
        return _optimization_checked;
    }

    public void set_optimization_checked(int _optimization_checked) {
        this._optimization_checked = _optimization_checked;
    }

    public int get_aft() {
        return _aft;
    }

    public void set_aft(int _aft) {
        this._aft = _aft;
    }

    public int get_domElements() {
        return _domElements;
    }

    public void set_domElements(int _domElements) {
        this._domElements = _domElements;
    }

    public String get_pageSpeedVersion() {
        return _pageSpeedVersion;
    }

    public void set_pageSpeedVersion(String _pageSpeedVersion) {
        this._pageSpeedVersion = _pageSpeedVersion;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public int get_titleTime() {
        return _titleTime;
    }

    public void set_titleTime(int _titleTime) {
        this._titleTime = _titleTime;
    }

    public int get_loadEventStart() {
        return _loadEventStart;
    }

    public void set_loadEventStart(int _loadEventStart) {
        this._loadEventStart = _loadEventStart;
    }

    public int get_loadEventEnd() {
        return _loadEventEnd;
    }

    public void set_loadEventEnd(int _loadEventEnd) {
        this._loadEventEnd = _loadEventEnd;
    }

    public int get_domContentLoadedEventStart() {
        return _domContentLoadedEventStart;
    }

    public void set_domContentLoadedEventStart(int _domContentLoadedEventStart) {
        this._domContentLoadedEventStart = _domContentLoadedEventStart;
    }

    public int get_domContentLoadedEventEnd() {
        return _domContentLoadedEventEnd;
    }

    public void set_domContentLoadedEventEnd(int _domContentLoadedEventEnd) {
        this._domContentLoadedEventEnd = _domContentLoadedEventEnd;
    }

    public int get_lastVisualChange() {
        return _lastVisualChange;
    }

    public void set_lastVisualChange(int _lastVisualChange) {
        this._lastVisualChange = _lastVisualChange;
    }

    public String get_browser_name() {
        return _browser_name;
    }

    public void set_browser_name(String _browser_name) {
        this._browser_name = _browser_name;
    }

    public String get_browser_version() {
        return _browser_version;
    }

    public void set_browser_version(String _browser_version) {
        this._browser_version = _browser_version;
    }

    public int get_server_count() {
        return _server_count;
    }

    public void set_server_count(int _server_count) {
        this._server_count = _server_count;
    }

    public int get_server_rtt() {
        return _server_rtt;
    }

    public void set_server_rtt(int _server_rtt) {
        this._server_rtt = _server_rtt;
    }

    public String get_base_page_cdn() {
        return _base_page_cdn;
    }

    public void set_base_page_cdn(String _base_page_cdn) {
        this._base_page_cdn = _base_page_cdn;
    }

    public int get_adult_site() {
        return _adult_site;
    }

    public void set_adult_site(int _adult_site) {
        this._adult_site = _adult_site;
    }

    public int get_fixed_viewport() {
        return _fixed_viewport;
    }

    public void set_fixed_viewport(int _fixed_viewport) {
        this._fixed_viewport = _fixed_viewport;
    }

    public int get_score_progressive_jpeg() {
        return _score_progressive_jpeg;
    }

    public void set_score_progressive_jpeg(int _score_progressive_jpeg) {
        this._score_progressive_jpeg = _score_progressive_jpeg;
    }

    public int get_firstPaint() {
        return _firstPaint;
    }

    public void set_firstPaint(int _firstPaint) {
        this._firstPaint = _firstPaint;
    }

    public double get_docCPUms() {
        return _docCPUms;
    }

    public void set_docCPUms(double _docCPUms) {
        this._docCPUms = _docCPUms;
    }

    public double get_fullyLoadedCPUms() {
        return _fullyLoadedCPUms;
    }

    public void set_fullyLoadedCPUms(double _fullyLoadedCPUms) {
        this._fullyLoadedCPUms = _fullyLoadedCPUms;
    }

    public int get_docCPUpct() {
        return _docCPUpct;
    }

    public void set_docCPUpct(int _docCPUpct) {
        this._docCPUpct = _docCPUpct;
    }

    public int get_date() {
        return _date;
    }

    public void set_date(int _date) {
        this._date = _date;
    }

    public int get_SpeedIndex() {
        return _SpeedIndex;
    }

    public void set_SpeedIndex(int _SpeedIndex) {
        this._SpeedIndex = _SpeedIndex;
    }

    public int get_visualComplete() {
        return _visualComplete;
    }

    public void set_visualComplete(int _visualComplete) {
        this._visualComplete = _visualComplete;
    }

    public int get_run() {
        return _run;
    }

    public void set_run(int _run) {
        this._run = _run;
    }

    public int get_effectiveBps() {
        return _effectiveBps;
    }

    public void set_effectiveBps(int _effectiveBps) {
        this._effectiveBps = _effectiveBps;
    }

    public int get_effectiveBpsDoc() {
        return _effectiveBpsDoc;
    }

    public void set_effectiveBpsDoc(int _effectiveBpsDoc) {
        this._effectiveBpsDoc = _effectiveBpsDoc;
    }

    @Override
    public String toString() {
        return "HarPage{" +
                "startedDateTime='" + startedDateTime + '\'' +
                ", title='" + title + '\'' +
                ", id='" + id + '\'' +
                ", harPageTimings=" + harPageTimings +
                ", _URL='" + _URL + '\'' +
                ", _loadTime=" + _loadTime +
                ", _TTFB=" + _TTFB +
                ", _bytesOut=" + _bytesOut +
                ", _bytesOutDoc=" + _bytesOutDoc +
                ", _connections=" + _connections +
                ", _requests=" + _requests +
                ", _requestsDoc=" + _requestsDoc +
                ", _responses_200=" + _responses_200 +
                ", _responses_404=" + _responses_404 +
                ", _responses_other=" + _responses_other +
                ", _render=" + _render +
                ", _fullyLoaded=" + _fullyLoaded +
                ", _cached=" + _cached +
                ", _docTime=" + _docTime +
                ", _domTime=" + _domTime +
                ", _score_cache=" + _score_cache +
                ", _score_cdn=" + _score_cdn +
                ", _score_gzip=" + _score_gzip +
                ", _score_cookies=" + _score_cookies +
                ", _score_minify=" + _score_minify +
                ", _score_combine=" + _score_combine +
                ", _score_compress=" + _score_compress +
                ", _score_etags=" + _score_etags +
                ", _gzip_total=" + _gzip_total +
                ", _gzip_savings=" + _gzip_savings +
                ", _minify_total=" + _minify_total +
                ", _minify_savings=" + _minify_savings +
                ", _image_total=" + _image_total +
                ", _image_savings=" + _image_savings +
                ", _optimization_checked=" + _optimization_checked +
                ", _aft=" + _aft +
                ", _domElements=" + _domElements +
                ", _pageSpeedVersion='" + _pageSpeedVersion + '\'' +
                ", _title='" + _title + '\'' +
                ", _titleTime=" + _titleTime +
                ", _loadEventStart=" + _loadEventStart +
                ", _loadEventEnd=" + _loadEventEnd +
                ", _domContentLoadedEventStart=" + _domContentLoadedEventStart +
                ", _domContentLoadedEventEnd=" + _domContentLoadedEventEnd +
                ", _lastVisualChange=" + _lastVisualChange +
                ", _browser_name='" + _browser_name + '\'' +
                ", _browser_version='" + _browser_version + '\'' +
                ", _server_count=" + _server_count +
                ", _server_rtt=" + _server_rtt +
                ", _base_page_cdn='" + _base_page_cdn + '\'' +
                ", _adult_site=" + _adult_site +
                ", _fixed_viewport=" + _fixed_viewport +
                ", _score_progressive_jpeg=" + _score_progressive_jpeg +
                ", _firstPaint=" + _firstPaint +
                ", _docCPUms=" + _docCPUms +
                ", _fullyLoadedCPUms=" + _fullyLoadedCPUms +
                ", _docCPUpct=" + _docCPUpct +
                ", _date=" + _date +
                ", _SpeedIndex=" + _SpeedIndex +
                ", _visualComplete=" + _visualComplete +
                ", _run=" + _run +
                ", _effectiveBps=" + _effectiveBps +
                ", _effectiveBpsDoc=" + _effectiveBpsDoc +
                '}';
    }
}
