package API.Controllers;

import Database.RecipeTable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;

@Controller
public class RecipeController {

    @GetMapping("/create-recipe")
    @ResponseBody
    public String insertAccount(@RequestParam(name="rid") Integer rid,
                                @RequestParam(name="name") String name,
                                @RequestParam(name="content") String content,
                                @RequestParam(name="cooking_time") Integer cooking_time,
                                @RequestParam(name="rating") Integer rating,
                                @RequestParam(name="typename") String typename,
                                @RequestParam(name="aid") Integer aid,
                                @RequestParam(name="date") Long millis) {
        Date date = new Date(millis);
        boolean success = RecipeTable.insert(rid, name, content, cooking_time, rating, typename, aid, date);
        return success ? "Success!" : "Failed";
    }

}
