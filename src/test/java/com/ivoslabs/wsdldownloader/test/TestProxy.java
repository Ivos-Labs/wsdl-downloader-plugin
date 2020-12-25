/**
 * 
 */
package com.ivoslabs.wsdldownloader.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ivoslasbs.wsdldownloader.dto.ProxyConf;

/**
 * Class to test the ProxyConf parser
 * 
 * @since 1.0.0
 * @author www.ivoslabs.com
 *
 */
public class TestProxy {

    private String host = "10.205.54.14";
    private String port = "8080";
    private String usr = "domain/usr";
    private String pwd = "pwd";

    private ProxyConf ProxyConf01 = new ProxyConf(this.usr + ":" + this.pwd + "@" + this.host + ":" + this.port);

    private ProxyConf ProxyConf02 = new ProxyConf(this.usr + "@" + this.host + ":" + this.port);

    private ProxyConf ProxyConf03 = new ProxyConf(this.host + ":" + this.port);

    private ProxyConf ProxyConf04 = new ProxyConf(this.usr + "@" + this.host);

    private ProxyConf ProxyConf05 = new ProxyConf(this.host);

    private String ProxyConfToStr(ProxyConf ProxyConf) {
        return ProxyConf.getProxyUser() + ":" + ProxyConf.getProxyPassword() + "@" + ProxyConf.getProxyHost() + ":" + ProxyConf.getProxyPort();
    }

    @Test
    public void test01() {
        String exp = this.usr + ":" + this.pwd + "@" + this.host + ":" + this.port;
        String act;

        act = this.ProxyConfToStr(this.ProxyConf01);

        assertEquals(exp, act);

    }

    @Test
    public void test02() {
        String exp = this.usr + ":null@" + this.host + ":" + this.port;
        String act;

        act = this.ProxyConfToStr(this.ProxyConf02);

        assertEquals(exp, act);

    }

    @Test
    public void test03() {
        String exp = "null:null@" + this.host + ":" + this.port;
        String act;

        act = this.ProxyConfToStr(this.ProxyConf03);

        assertEquals(exp, act);

    }

    @Test
    public void test04() {
        String exp = this.usr + ":null@" + this.host + ":null";
        String act;

        act = this.ProxyConfToStr(this.ProxyConf04);

        assertEquals(exp, act);

    }

    @Test
    public void test05() {
        String exp = "null:null@" + this.host + ":null";
        String act;

        act = this.ProxyConfToStr(this.ProxyConf05);

        assertEquals(exp, act);

    }

}
