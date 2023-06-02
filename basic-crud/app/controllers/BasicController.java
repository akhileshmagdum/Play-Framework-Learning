package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class BasicController extends Controller {

    public Result homepage() {
        return ok("Welcome Home!");
    }
}
