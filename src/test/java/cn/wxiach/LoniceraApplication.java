package cn.wxiach;

import cn.wxiach.annotation.Get;
import cn.wxiach.annotation.Path;
import cn.wxiach.http.MediaType;

/**
 * @author wxiach 2023/10/13
 */

@Path
public class LoniceraApplication {

    @Get(path = "/index", produces = MediaType.TEXT_PLAIN)
    public String index() {
        return "Hello Lonicera!";
    }

    @Get(path = "/home", produces = MediaType.TEXT_HTML)
    public String home() {
        return "<h1>Hello Lonicera!</h1>";
    }
}
