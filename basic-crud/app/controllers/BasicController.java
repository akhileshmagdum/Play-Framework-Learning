package controllers;

import play.mvc.Controller;
import play.mvc.Result;
// A view needs to be imported manually
import views.html.home.homepage;

public class BasicController extends Controller {

    public Result homepage(String fName, String lName) {
        return ok(homepage.render(fName, lName));
    }
}
