package controllers;

import models.Employee;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
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

    public Result saveEmployee(Http.Request request) {
        Employee employee = Json.fromJson(request.body().asJson(), Employee.class);
        employee.save();
        return ok("Employee saved");
    }

    public Result getEmployeeById(Long id) {
        return ok(Json.toJson(Employee.employeeFinder.ref(id)));
    }

    public Result updateEmployee(Http.Request request) {
        Employee employee = Json.fromJson(request.body().asJson(), Employee.class);
        Employee existingEmployee = Employee.employeeFinder.ref(employee.getId());
        existingEmployee.setDesignation(employee.getDesignation());
        existingEmployee.setFullName(employee.getFullName());
        existingEmployee.update();
        return ok("Employee updated");
    }

    public Result deleteEmployee(Long id) {
        Employee existingEmployee = Employee.employeeFinder.ref(id);
        existingEmployee.delete();
        return ok("Employee deleted");
    }
}
