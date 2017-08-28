package io.ift.automation.testscaffold.codegenerator.apis.harmodel;

public class HarEntry {

    private String connection;
    private String pageref;
    private String startedDateTime;
    private int time;
    private HarRequest request;
    private HarResponse response;
    private HarCache harCache;
    private HarTimings harTimings;
    private String _ip_addr;
    private String _method;
    private String _host;
    private String _url;
    private String _responseCode;
    private String _load_ms;
    private String _ttfb_ms;
    private String _load_start;
    private String _bytesOut;
    private String _expires;
    private String _contentType;
    private String _contentEncoding;
    private String _type;
    private String _socket;
    private String _score_cache;
    private String _score_cdn;
    private String _score_gzip;
    private String _score_cookies;
    //public String _score_keep-alive;
    private String _score_minify;
    private String _score_combine;
    private String _score_compress;
    private String _score_etags;
    private String _is_secure;
    private String _dns_ms;
    private int _connect_ms;
    private String _ssl_ms;
    private String _gzip_total;
    private String _gzip_save;
    private String _minify_total;
    private String _minify_save;
    private String _image_total;
    private String _image_save;
    private String _cache_time;
    private String _dns_start;
    private String _dns_end;
    private String _server_count;
    private String _server_rtt;
    private String _client_port;
    private String _jpeg_scan_count;
    private String _full_url;
    private int _score_progressive_jpeg;
    private int _load_end;
    private String _ttfb_start;
    private int _ttfb_end;
    private int _download_start;
    private int _download_end;
    private int _download_ms;
    private String _all_start;
    private int _all_end;
    private int _all_ms;
    private int _index;
    private int _number;

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getPageref() {
        return pageref;
    }

    public void setPageref(String pageref) {
        this.pageref = pageref;
    }

    public String getStartedDateTime() {
        return startedDateTime;
    }

    public void setStartedDateTime(String startedDateTime) {
        this.startedDateTime = startedDateTime;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public HarRequest getRequest() {
        return request;
    }

    public void setRequest(HarRequest request) {
        this.request = request;
    }

    public HarResponse getResponse() {
        return response;
    }

    public void setResponse(HarResponse response) {
        this.response = response;
    }

    public HarCache getHarCache() {
        return harCache;
    }

    public void setHarCache(HarCache harCache) {
        this.harCache = harCache;
    }

    public HarTimings getHarTimings() {
        return harTimings;
    }

    public void setHarTimings(HarTimings harTimings) {
        this.harTimings = harTimings;
    }

    public String get_ip_addr() {
        return _ip_addr;
    }

    public void set_ip_addr(String _ip_addr) {
        this._ip_addr = _ip_addr;
    }

    public String get_method() {
        return _method;
    }

    public void set_method(String _method) {
        this._method = _method;
    }

    public String get_host() {
        return _host;
    }

    public void set_host(String _host) {
        this._host = _host;
    }

    public String get_url() {
        return _url;
    }

    public void set_url(String _url) {
        this._url = _url;
    }

    public String get_responseCode() {
        return _responseCode;
    }

    public void set_responseCode(String _responseCode) {
        this._responseCode = _responseCode;
    }

    public String get_load_ms() {
        return _load_ms;
    }

    public void set_load_ms(String _load_ms) {
        this._load_ms = _load_ms;
    }

    public String get_ttfb_ms() {
        return _ttfb_ms;
    }

    public void set_ttfb_ms(String _ttfb_ms) {
        this._ttfb_ms = _ttfb_ms;
    }

    public String get_load_start() {
        return _load_start;
    }

    public void set_load_start(String _load_start) {
        this._load_start = _load_start;
    }

    public String get_bytesOut() {
        return _bytesOut;
    }

    public void set_bytesOut(String _bytesOut) {
        this._bytesOut = _bytesOut;
    }

    public String get_expires() {
        return _expires;
    }

    public void set_expires(String _expires) {
        this._expires = _expires;
    }

    public String get_contentType() {
        return _contentType;
    }

    public void set_contentType(String _contentType) {
        this._contentType = _contentType;
    }

    public String get_contentEncoding() {
        return _contentEncoding;
    }

    public void set_contentEncoding(String _contentEncoding) {
        this._contentEncoding = _contentEncoding;
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public String get_socket() {
        return _socket;
    }

    public void set_socket(String _socket) {
        this._socket = _socket;
    }

    public String get_score_cache() {
        return _score_cache;
    }

    public void set_score_cache(String _score_cache) {
        this._score_cache = _score_cache;
    }

    public String get_score_cdn() {
        return _score_cdn;
    }

    public void set_score_cdn(String _score_cdn) {
        this._score_cdn = _score_cdn;
    }

    public String get_score_gzip() {
        return _score_gzip;
    }

    public void set_score_gzip(String _score_gzip) {
        this._score_gzip = _score_gzip;
    }

    public String get_score_cookies() {
        return _score_cookies;
    }

    public void set_score_cookies(String _score_cookies) {
        this._score_cookies = _score_cookies;
    }

    public String get_score_minify() {
        return _score_minify;
    }

    public void set_score_minify(String _score_minify) {
        this._score_minify = _score_minify;
    }

    public String get_score_combine() {
        return _score_combine;
    }

    public void set_score_combine(String _score_combine) {
        this._score_combine = _score_combine;
    }

    public String get_score_compress() {
        return _score_compress;
    }

    public void set_score_compress(String _score_compress) {
        this._score_compress = _score_compress;
    }

    public String get_score_etags() {
        return _score_etags;
    }

    public void set_score_etags(String _score_etags) {
        this._score_etags = _score_etags;
    }

    public String get_is_secure() {
        return _is_secure;
    }

    public void set_is_secure(String _is_secure) {
        this._is_secure = _is_secure;
    }

    public String get_dns_ms() {
        return _dns_ms;
    }

    public void set_dns_ms(String _dns_ms) {
        this._dns_ms = _dns_ms;
    }

    public int get_connect_ms() {
        return _connect_ms;
    }

    public void set_connect_ms(int _connect_ms) {
        this._connect_ms = _connect_ms;
    }

    public String get_ssl_ms() {
        return _ssl_ms;
    }

    public void set_ssl_ms(String _ssl_ms) {
        this._ssl_ms = _ssl_ms;
    }

    public String get_gzip_total() {
        return _gzip_total;
    }

    public void set_gzip_total(String _gzip_total) {
        this._gzip_total = _gzip_total;
    }

    public String get_gzip_save() {
        return _gzip_save;
    }

    public void set_gzip_save(String _gzip_save) {
        this._gzip_save = _gzip_save;
    }

    public String get_minify_total() {
        return _minify_total;
    }

    public void set_minify_total(String _minify_total) {
        this._minify_total = _minify_total;
    }

    public String get_minify_save() {
        return _minify_save;
    }

    public void set_minify_save(String _minify_save) {
        this._minify_save = _minify_save;
    }

    public String get_image_total() {
        return _image_total;
    }

    public void set_image_total(String _image_total) {
        this._image_total = _image_total;
    }

    public String get_image_save() {
        return _image_save;
    }

    public void set_image_save(String _image_save) {
        this._image_save = _image_save;
    }

    public String get_cache_time() {
        return _cache_time;
    }

    public void set_cache_time(String _cache_time) {
        this._cache_time = _cache_time;
    }

    public String get_dns_start() {
        return _dns_start;
    }

    public void set_dns_start(String _dns_start) {
        this._dns_start = _dns_start;
    }

    public String get_dns_end() {
        return _dns_end;
    }

    public void set_dns_end(String _dns_end) {
        this._dns_end = _dns_end;
    }

    public String get_server_count() {
        return _server_count;
    }

    public void set_server_count(String _server_count) {
        this._server_count = _server_count;
    }

    public String get_server_rtt() {
        return _server_rtt;
    }

    public void set_server_rtt(String _server_rtt) {
        this._server_rtt = _server_rtt;
    }

    public String get_client_port() {
        return _client_port;
    }

    public void set_client_port(String _client_port) {
        this._client_port = _client_port;
    }

    public String get_jpeg_scan_count() {
        return _jpeg_scan_count;
    }

    public void set_jpeg_scan_count(String _jpeg_scan_count) {
        this._jpeg_scan_count = _jpeg_scan_count;
    }

    public String get_full_url() {
        return _full_url;
    }

    public void set_full_url(String _full_url) {
        this._full_url = _full_url;
    }

    public int get_score_progressive_jpeg() {
        return _score_progressive_jpeg;
    }

    public void set_score_progressive_jpeg(int _score_progressive_jpeg) {
        this._score_progressive_jpeg = _score_progressive_jpeg;
    }

    public int get_load_end() {
        return _load_end;
    }

    public void set_load_end(int _load_end) {
        this._load_end = _load_end;
    }

    public String get_ttfb_start() {
        return _ttfb_start;
    }

    public void set_ttfb_start(String _ttfb_start) {
        this._ttfb_start = _ttfb_start;
    }

    public int get_ttfb_end() {
        return _ttfb_end;
    }

    public void set_ttfb_end(int _ttfb_end) {
        this._ttfb_end = _ttfb_end;
    }

    public int get_download_start() {
        return _download_start;
    }

    public void set_download_start(int _download_start) {
        this._download_start = _download_start;
    }

    public int get_download_end() {
        return _download_end;
    }

    public void set_download_end(int _download_end) {
        this._download_end = _download_end;
    }

    public int get_download_ms() {
        return _download_ms;
    }

    public void set_download_ms(int _download_ms) {
        this._download_ms = _download_ms;
    }

    public String get_all_start() {
        return _all_start;
    }

    public void set_all_start(String _all_start) {
        this._all_start = _all_start;
    }

    public int get_all_end() {
        return _all_end;
    }

    public void set_all_end(int _all_end) {
        this._all_end = _all_end;
    }

    public int get_all_ms() {
        return _all_ms;
    }

    public void set_all_ms(int _all_ms) {
        this._all_ms = _all_ms;
    }

    public int get_index() {
        return _index;
    }

    public void set_index(int _index) {
        this._index = _index;
    }

    public int get_number() {
        return _number;
    }

    public void set_number(int _number) {
        this._number = _number;
    }

    @Override
    public String toString() {
        return "HarEntry{" +
                "connection='" + connection + '\'' +
                ", pageref='" + pageref + '\'' +
                ", startedDateTime='" + startedDateTime + '\'' +
                ", time=" + time +
                ", request=" + request +
                ", response=" + response +
                ", harCache=" + harCache +
                ", harTimings=" + harTimings +
                ", _ip_addr='" + _ip_addr + '\'' +
                ", _method='" + _method + '\'' +
                ", _host='" + _host + '\'' +
                ", _url='" + _url + '\'' +
                ", _responseCode='" + _responseCode + '\'' +
                ", _load_ms='" + _load_ms + '\'' +
                ", _ttfb_ms='" + _ttfb_ms + '\'' +
                ", _load_start='" + _load_start + '\'' +
                ", _bytesOut='" + _bytesOut + '\'' +
                ", _expires='" + _expires + '\'' +
                ", _contentType='" + _contentType + '\'' +
                ", _contentEncoding='" + _contentEncoding + '\'' +
                ", _type='" + _type + '\'' +
                ", _socket='" + _socket + '\'' +
                ", _score_cache='" + _score_cache + '\'' +
                ", _score_cdn='" + _score_cdn + '\'' +
                ", _score_gzip='" + _score_gzip + '\'' +
                ", _score_cookies='" + _score_cookies + '\'' +
                ", _score_minify='" + _score_minify + '\'' +
                ", _score_combine='" + _score_combine + '\'' +
                ", _score_compress='" + _score_compress + '\'' +
                ", _score_etags='" + _score_etags + '\'' +
                ", _is_secure='" + _is_secure + '\'' +
                ", _dns_ms='" + _dns_ms + '\'' +
                ", _connect_ms=" + _connect_ms +
                ", _ssl_ms='" + _ssl_ms + '\'' +
                ", _gzip_total='" + _gzip_total + '\'' +
                ", _gzip_save='" + _gzip_save + '\'' +
                ", _minify_total='" + _minify_total + '\'' +
                ", _minify_save='" + _minify_save + '\'' +
                ", _image_total='" + _image_total + '\'' +
                ", _image_save='" + _image_save + '\'' +
                ", _cache_time='" + _cache_time + '\'' +
                ", _dns_start='" + _dns_start + '\'' +
                ", _dns_end='" + _dns_end + '\'' +
                ", _server_count='" + _server_count + '\'' +
                ", _server_rtt='" + _server_rtt + '\'' +
                ", _client_port='" + _client_port + '\'' +
                ", _jpeg_scan_count='" + _jpeg_scan_count + '\'' +
                ", _full_url='" + _full_url + '\'' +
                ", _score_progressive_jpeg=" + _score_progressive_jpeg +
                ", _load_end=" + _load_end +
                ", _ttfb_start='" + _ttfb_start + '\'' +
                ", _ttfb_end=" + _ttfb_end +
                ", _download_start=" + _download_start +
                ", _download_end=" + _download_end +
                ", _download_ms=" + _download_ms +
                ", _all_start='" + _all_start + '\'' +
                ", _all_end=" + _all_end +
                ", _all_ms=" + _all_ms +
                ", _index=" + _index +
                ", _number=" + _number +
                '}';
    }
}
