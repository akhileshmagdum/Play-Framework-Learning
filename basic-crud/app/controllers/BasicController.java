package controllers;

import models.Employee;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.home.homepage;

import java.util.List;

public class BasicController extends Controller {


    public Result homepage(String fName, String lName) {
        return ok(homepage.render(fName, lName));
    }

    public Result getAllEmployee() {
        List<Employee> employeeList = Employee.employeeFinder.all();
        return ok(Json.toJson(employeeList));
    }
}
