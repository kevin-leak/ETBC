package club.crabglory.www.etbc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈土拔鼠的日常〉<br>
 * 〈〉
 *
 * @author chen
 * @create 2020/1/6
 */
@Controller
public class ControllerTest {

    @ResponseBody
    @RequestMapping("/getValue")
    public  String getValue(){
        return  "index";
    }
}
