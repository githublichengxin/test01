package sudo.lcx.springbootsecurityfuxi01.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lichengxin
 */
@RestController
public class MyController {

    @RequestMapping("login")
    public String loginReturn() {
        return "Hello Security!";
    }

    @RequestMapping("/admin/test")
    public String adminTest() {
        return "admin test";
    }

    @RequestMapping("/user/test")
    public String userTest() {
        return "user test";
    }
}
