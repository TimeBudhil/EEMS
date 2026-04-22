package com.mpp.eems.Services;


import java.util.ArrayList;
import java.util.List;

import com.mpp.eems.Domain.Department;
import com.mpp.eems.Domain.Project;
import com.mpp.eems.Repository.DepartmentRepository;

public class DepartmentService extends Services{
    DepartmentRepository deprepo = new DepartmentRepository();

    public Department findDepartment(Department department){
        return deprepo.findDepartmentById(department.getId());
    }

}
