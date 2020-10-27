package API.Controllers;

import Database.AccountTable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;

@Controller
public class AccountController {

    @GetMapping("/create-account")
    @ResponseBody
    public String insertAccount(@RequestParam(name="aid") Integer aid,
                                @RequestParam(name="username") String username,
                                @RequestParam(name="first_name") String first_name,
                                @RequestParam(name="last_name") String last_name,
                                @RequestParam(name="password") String password,
                                @RequestParam(name="date_of_birth") Long millis,
                                @RequestParam(name="address") String address) {
        Date date = new Date(millis);
        boolean success = AccountTable.insert(aid, username, first_name, last_name, password, date, address);
        return success ? "Success!" : "Failed";
    }
}
