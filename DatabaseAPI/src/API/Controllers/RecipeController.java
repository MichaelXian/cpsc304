package API.Controllers;

import Database.PostTable;
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

    @GetMapping("/average-rating")
    @ResponseBody
    public String getAverageRating() {
        return RecipeTable.averageRating();
    }

    @GetMapping("/post-comment")
    @ResponseBody
    public String postComment(
            @RequestParam(name="cid") Integer cid,
            @RequestParam(name="rid") Integer rid,
            @RequestParam(name="aid") Integer aid,
            @RequestParam(name="date") Long millis,
            @RequestParam(name="title") String commentTitle,
            @RequestParam(name="rating") Integer rating,
            @RequestParam(name="content") String commentContent
    ) {
        Date date = new Date(millis);
        boolean didPost = PostTable.insert(cid,rid,aid,date,commentTitle,commentContent,rating);
        return didPost ? "Success" : "Failed";
    }

    @GetMapping("/select-recipe")
    @ResponseBody
    public String selectRecipe(
            @RequestParam(name="lo") Integer lo,
            @RequestParam(name="hi") Integer hi
    ) {
        return RecipeTable.listRecipeWRating(lo,hi);
    }

    @GetMapping("/find-recipe-with-time")
    @ResponseBody
    public String findRecipeWTime(
            @RequestParam(name="lo") Integer lo,
            @RequestParam(name="hi") Integer hi
    ) {
        return RecipeTable.listRecipeWTimeInRange(lo, hi);
    }

    @GetMapping("/average-rating-fast")
    @ResponseBody
    public String averageRatingOfFastFood() {
        return RecipeTable.averageRatingOfFastFood();
    }

    @GetMapping("/find-recipe-commented-by-all")
    @ResponseBody
    public String findCommented() {
        return RecipeTable.commented();
    }
}
