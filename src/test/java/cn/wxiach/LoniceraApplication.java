package cn.wxiach;

import cn.wxiach.annotation.Get;
import cn.wxiach.annotation.Path;

/**
 * @author wxiach 2023/10/13
 */

@Path
public class LoniceraApplication {

    @Get(path = "/index")
    public String index() {
        return "Hello Lonicera!";
    }
}
