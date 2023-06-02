package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class BasicController extends Controller {

    public Result homepage(String fName, String lName) {
        return ok("Welcome Home! \n User -> " + fName + " " + lName);
    }
}
