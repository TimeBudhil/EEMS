package com.mpp.eems.Controller;

import java.time.LocalDate;

import com.mpp.eems.Domain.Client;
import com.mpp.eems.Domain.Department;
import com.mpp.eems.Domain.Employee;
import com.mpp.eems.Domain.Project;
import com.mpp.eems.Domain.Status;
import com.mpp.eems.Repository.ClientProjectRepository;
import com.mpp.eems.Repository.ClientRepository;
import com.mpp.eems.Repository.DatabaseInitializer;
import com.mpp.eems.Repository.DepartmentRepository;
import com.mpp.eems.Repository.EmployeeProjectRepository;
import com.mpp.eems.Repository.EmployeeRepository;
import com.mpp.eems.Repository.ProjectDepartmentRepository;
import com.mpp.eems.Repository.ProjectRepository;

/**
 * Initialize and demonstrate correct use of REPOSITORY functions
 * allows population and initializing of DB
 */
public class Controller {
    // ── Repositories ──────────────────────────────────────────────────────────
    static DepartmentRepository    deptRepo    = new DepartmentRepository();
    static EmployeeRepository      empRepo     = new EmployeeRepository();
    static ProjectRepository       projRepo    = new ProjectRepository();
    static ClientRepository        clientRepo  = new ClientRepository();

    static EmployeeProjectRepository    empProjRepo  = new EmployeeProjectRepository();
    static ClientProjectRepository      cliProjRepo  = new ClientProjectRepository();
    static ProjectDepartmentRepository  projDeptRepo = new ProjectDepartmentRepository();

    public static void main(String[] args) throws Exception {
        DatabaseInitializer.resetDatabase();  // ← wipes everything
        DatabaseInitializer.initialize();     // ← recreates schema

        // ── 1. CREATE DEPARTMENTS ─────────────────────────────────────────────
        Department engineering  = deptRepo.addDepartment(new Department(0, "Engineering",  "New York",    920000.00, null));
        Department marketing    = deptRepo.addDepartment(new Department(0, "Marketing",    "Chicago",     450000.00, null));
        Department design       = deptRepo.addDepartment(new Department(0, "Design",       "Austin",      380000.00, null));
        Department hr           = deptRepo.addDepartment(new Department(0, "HR",           "Dallas",      300000.00, null));
        Department operations   = deptRepo.addDepartment(new Department(0, "Operations",   "Seattle",     510000.00, null));

        System.out.println("✔ Departments created");

        // ── 2. CREATE EMPLOYEES ───────────────────────────────────────────────

        // Engineering (6)
        Employee alice   = empRepo.add(new Employee(0, "Alice",   "Chen",     "Senior Engineer",  LocalDate.of(2019, 3, 10), 110000, engineering, null));
        Employee bob     = empRepo.add(new Employee(0, "Bob",     "Martin",   "Backend Engineer", LocalDate.of(2020, 7, 1),  95000,  engineering, null));
        Employee carol   = empRepo.add(new Employee(0, "Carol",   "Smith",    "DevOps Engineer",  LocalDate.of(2021, 1, 15), 98000,  engineering, null));
        Employee david   = empRepo.add(new Employee(0, "David",   "Lee",      "Frontend Engineer",LocalDate.of(2022, 4, 20), 92000,  engineering, null));
        Employee eve     = empRepo.add(new Employee(0, "Eve",     "Johnson",  "QA Engineer",      LocalDate.of(2020, 9, 5),  88000,  engineering, null));
        Employee frank   = empRepo.add(new Employee(0, "Frank",   "Wang",     "Tech Lead",        LocalDate.of(2018, 6, 12), 125000, engineering, null));

        // Marketing (5)
        Employee grace   = empRepo.add(new Employee(0, "Grace",   "Kim",      "Marketing Manager",LocalDate.of(2019, 2, 28), 85000,  marketing, null));
        Employee henry   = empRepo.add(new Employee(0, "Henry",   "Davis",    "Content Strategist",LocalDate.of(2021, 5, 3), 72000,  marketing, null));
        Employee isla    = empRepo.add(new Employee(0, "Isla",    "Brown",    "SEO Specialist",   LocalDate.of(2022, 8, 17), 68000,  marketing, null));
        Employee jack    = empRepo.add(new Employee(0, "Jack",    "Wilson",   "Paid Ads Manager", LocalDate.of(2020, 11, 9), 78000,  marketing, null));
        Employee karen   = empRepo.add(new Employee(0, "Karen",   "Taylor",   "Brand Strategist", LocalDate.of(2019, 7, 22), 80000,  marketing, null));

        // Design (4)
        Employee liam    = empRepo.add(new Employee(0, "Liam",    "Anderson", "UX Designer",      LocalDate.of(2021, 3, 14), 90000,  design, null));
        Employee mia     = empRepo.add(new Employee(0, "Mia",     "Thomas",   "UI Designer",      LocalDate.of(2022, 1, 25), 86000,  design, null));
        Employee noah    = empRepo.add(new Employee(0, "Noah",    "Jackson",  "Graphic Designer", LocalDate.of(2020, 6, 30), 75000,  design, null));
        Employee olivia  = empRepo.add(new Employee(0, "Olivia",  "White",    "Design Lead",      LocalDate.of(2018, 10, 8), 105000, design, null));

        // HR (3)
        Employee peter   = empRepo.add(new Employee(0, "Peter",   "Harris",   "HR Manager",       LocalDate.of(2017, 4, 19), 82000,  hr, null));
        Employee quinn   = empRepo.add(new Employee(0, "Quinn",   "Martin",   "Recruiter",        LocalDate.of(2021, 9, 11), 65000,  hr, null));
        Employee rachel  = empRepo.add(new Employee(0, "Rachel",  "Garcia",   "HR Generalist",    LocalDate.of(2022, 2, 7),  63000,  hr, null));

        // Operations (5)
        Employee sam     = empRepo.add(new Employee(0, "Sam",     "Martinez", "Ops Manager",      LocalDate.of(2018, 8, 23), 95000,  operations, null));
        Employee tina    = empRepo.add(new Employee(0, "Tina",    "Robinson", "Logistics Lead",   LocalDate.of(2020, 3, 17), 78000,  operations, null));
        Employee umar    = empRepo.add(new Employee(0, "Umar",    "Clark",    "Process Analyst",  LocalDate.of(2021, 7, 29), 72000,  operations, null));
        Employee vera    = empRepo.add(new Employee(0, "Vera",    "Rodriguez","Supply Chain Spec",LocalDate.of(2022, 5, 4),  70000,  operations, null));
        Employee will    = empRepo.add(new Employee(0, "Will",    "Lewis",    "Operations Analyst",LocalDate.of(2019, 12, 1),76000,  operations, null));

        System.out.println("✔ Employees created");

        // ── 3. CREATE PROJECTS ────────────────────────────────────────────────
        Project p1 = projRepo.addProject(new Project(0, "Platform Redesign",         "...", LocalDate.of(2024,1,1),  LocalDate.of(2024,12,31), 500,  500000, Status.ACTIVE,     null, null));
        Project p2 = projRepo.addProject(new Project(0, "Mobile App Launch",         "...", LocalDate.of(2024,3,1),  LocalDate.of(2024,9,30),  320,  320000, Status.ACTIVE,     null, null));
        Project p3 = projRepo.addProject(new Project(0, "Brand Refresh",             "...", LocalDate.of(2024,2,1),  LocalDate.of(2024,6,30),  150,  150000, Status.COMPLETE,   null, null));
        Project p4 = projRepo.addProject(new Project(0, "Data Pipeline Upgrade",     "...", LocalDate.of(2024,4,1),  LocalDate.of(2024,10,31), 280,  280000, Status.ACTIVE,     null, null));
        Project p5 = projRepo.addProject(new Project(0, "HR Portal",                 "...", LocalDate.of(2024,5,1),  LocalDate.of(2024,11,30), 120,  120000, Status.UNASSIGNED, null, null));
        Project p6 = projRepo.addProject(new Project(0, "Client Analytics Dashboard","...", LocalDate.of(2024,1,15), LocalDate.of(2024,8,15),  210,  210000, Status.ACTIVE,     null, null));
        Project p7 = projRepo.addProject(new Project(0, "Supply Chain Automation",   "...", LocalDate.of(2024,6,1),  LocalDate.of(2025,3,31),  390,  390000, Status.ACTIVE,     null, null));

        // After seeding, update p2 to end soon (within 30 days):
        p2 = new Project(p2.getId(), p2.getName(), p2.getDescription(),
            p2.getStartDate(), LocalDate.now().plusDays(20),
            p2.getEstimatedDurationHours(), p2.getTotalBudget(), p2.getStatus(), null, null);
        projRepo.modifyProject(p2);

        // p7 ends in ~200 days:
        p7 = new Project(p7.getId(), p7.getName(), p7.getDescription(),
            p7.getStartDate(), LocalDate.now().plusDays(200),
            p7.getEstimatedDurationHours(), p7.getTotalBudget(), p7.getStatus(), null, null);
        projRepo.modifyProject(p7);
        System.out.println("✔ Projects created");

        // ── 4. CREATE CLIENTS ─────────────────────────────────────────────────
        Client c1 = clientRepo.addClient(new Client(0, "Apex Solutions",    "Technology",   "James Reed",    "555-0101", "james@apex.com",    null));
        Client c2 = clientRepo.addClient(new Client(0, "BlueSky Media",     "Marketing",    "Sara Nguyen",   "555-0202", "sara@bluesky.com",  null));
        Client c3 = clientRepo.addClient(new Client(0, "CoreLogic Inc",     "Finance",      "Tom Briggs",    "555-0303", "tom@corelogic.com", null));
        Client c4 = clientRepo.addClient(new Client(0, "Delta Retail Group","Retail",       "Amy Fowler",    "555-0404", "amy@delta.com",     null));

        System.out.println("✔ Clients created");

        // ── 5. LINK EMPLOYEES → PROJECTS ─────────────────────────────────────
        // Platform Redesign  — engineering + design heavy
        empProjRepo.linkEmployee(p1.getId(), alice.getId());
        empProjRepo.linkEmployee(p1.getId(), bob.getId());
        empProjRepo.linkEmployee(p1.getId(), frank.getId());
        empProjRepo.linkEmployee(p1.getId(), liam.getId());
        empProjRepo.linkEmployee(p1.getId(), olivia.getId());

        // Mobile App Launch — engineering + design
        empProjRepo.linkEmployee(p2.getId(), carol.getId());
        empProjRepo.linkEmployee(p2.getId(), david.getId());
        empProjRepo.linkEmployee(p2.getId(), eve.getId());
        empProjRepo.linkEmployee(p2.getId(), mia.getId());
        empProjRepo.linkEmployee(p2.getId(), noah.getId());

        // Brand Refresh — marketing + design
        empProjRepo.linkEmployee(p3.getId(), grace.getId());
        empProjRepo.linkEmployee(p3.getId(), henry.getId());
        empProjRepo.linkEmployee(p3.getId(), karen.getId());
        empProjRepo.linkEmployee(p3.getId(), liam.getId());
        empProjRepo.linkEmployee(p3.getId(), olivia.getId());

        // Data Pipeline Upgrade — engineering + ops
        empProjRepo.linkEmployee(p4.getId(), alice.getId());
        empProjRepo.linkEmployee(p4.getId(), frank.getId());
        empProjRepo.linkEmployee(p4.getId(), sam.getId());
        empProjRepo.linkEmployee(p4.getId(), tina.getId());

        // HR Portal — hr + engineering
        empProjRepo.linkEmployee(p5.getId(), peter.getId());
        empProjRepo.linkEmployee(p5.getId(), quinn.getId());
        empProjRepo.linkEmployee(p5.getId(), bob.getId());
        empProjRepo.linkEmployee(p5.getId(), carol.getId());

        // Client Analytics Dashboard — engineering + marketing
        empProjRepo.linkEmployee(p6.getId(), frank.getId());
        empProjRepo.linkEmployee(p6.getId(), eve.getId());
        empProjRepo.linkEmployee(p6.getId(), jack.getId());
        empProjRepo.linkEmployee(p6.getId(), isla.getId());

        // Supply Chain Automation — ops + engineering
        empProjRepo.linkEmployee(p7.getId(), sam.getId());
        empProjRepo.linkEmployee(p7.getId(), umar.getId());
        empProjRepo.linkEmployee(p7.getId(), vera.getId());
        empProjRepo.linkEmployee(p7.getId(), will.getId());
        empProjRepo.linkEmployee(p7.getId(), alice.getId());

        // Platform Redesign (500h total)
        empProjRepo.assignProjectToEmployee(alice.getId(),  p1.getId(), 150);
        empProjRepo.assignProjectToEmployee(bob.getId(),    p1.getId(), 100);
        empProjRepo.assignProjectToEmployee(frank.getId(),  p1.getId(), 150);
        empProjRepo.assignProjectToEmployee(liam.getId(),   p1.getId(), 50);
        empProjRepo.assignProjectToEmployee(olivia.getId(), p1.getId(), 50);

        // Mobile App Launch (320h total)
        empProjRepo.assignProjectToEmployee(carol.getId(),  p2.getId(), 80);
        empProjRepo.assignProjectToEmployee(david.getId(),  p2.getId(), 80);
        empProjRepo.assignProjectToEmployee(eve.getId(),    p2.getId(), 60);
        empProjRepo.assignProjectToEmployee(mia.getId(),    p2.getId(), 60);
        empProjRepo.assignProjectToEmployee(noah.getId(),   p2.getId(), 40);

        // Brand Refresh (150h total)
        empProjRepo.assignProjectToEmployee(grace.getId(),  p3.getId(), 40);
        empProjRepo.assignProjectToEmployee(henry.getId(),  p3.getId(), 30);
        empProjRepo.assignProjectToEmployee(karen.getId(),  p3.getId(), 30);
        empProjRepo.assignProjectToEmployee(liam.getId(),   p3.getId(), 25);
        empProjRepo.assignProjectToEmployee(olivia.getId(), p3.getId(), 25);

        // Data Pipeline Upgrade (280h total)
        empProjRepo.assignProjectToEmployee(alice.getId(),  p4.getId(), 100);
        empProjRepo.assignProjectToEmployee(frank.getId(),  p4.getId(), 80);
        empProjRepo.assignProjectToEmployee(sam.getId(),    p4.getId(), 60);
        empProjRepo.assignProjectToEmployee(tina.getId(),   p4.getId(), 40);

        // HR Portal (120h total)
        empProjRepo.assignProjectToEmployee(peter.getId(),  p5.getId(), 40);
        empProjRepo.assignProjectToEmployee(quinn.getId(),  p5.getId(), 30);
        empProjRepo.assignProjectToEmployee(bob.getId(),    p5.getId(), 30);
        empProjRepo.assignProjectToEmployee(carol.getId(),  p5.getId(), 20);

        // Client Analytics Dashboard (210h total)
        empProjRepo.assignProjectToEmployee(frank.getId(),  p6.getId(), 70);
        empProjRepo.assignProjectToEmployee(eve.getId(),    p6.getId(), 60);
        empProjRepo.assignProjectToEmployee(jack.getId(),   p6.getId(), 50);
        empProjRepo.assignProjectToEmployee(isla.getId(),   p6.getId(), 30);

        // Supply Chain Automation (390h total)
        empProjRepo.assignProjectToEmployee(sam.getId(),    p7.getId(), 100);
        empProjRepo.assignProjectToEmployee(umar.getId(),   p7.getId(), 80);
        empProjRepo.assignProjectToEmployee(vera.getId(),   p7.getId(), 80);
        empProjRepo.assignProjectToEmployee(will.getId(),   p7.getId(), 70);
        empProjRepo.assignProjectToEmployee(alice.getId(),  p7.getId(), 60);

        System.out.println("✔ Employee-Project links created");

        // ── 6. LINK DEPARTMENTS → PROJECTS ───────────────────────────────────
        projDeptRepo.linkDepartment(p1.getId(), engineering.getId());
        projDeptRepo.linkDepartment(p1.getId(), design.getId());

        projDeptRepo.linkDepartment(p2.getId(), engineering.getId());
        projDeptRepo.linkDepartment(p2.getId(), design.getId());

        projDeptRepo.linkDepartment(p3.getId(), marketing.getId());
        projDeptRepo.linkDepartment(p3.getId(), design.getId());

        projDeptRepo.linkDepartment(p4.getId(), engineering.getId());
        projDeptRepo.linkDepartment(p4.getId(), operations.getId());

        projDeptRepo.linkDepartment(p5.getId(), hr.getId());
        projDeptRepo.linkDepartment(p5.getId(), engineering.getId());

        projDeptRepo.linkDepartment(p6.getId(), engineering.getId());
        projDeptRepo.linkDepartment(p6.getId(), marketing.getId());

        projDeptRepo.linkDepartment(p7.getId(), operations.getId());
        projDeptRepo.linkDepartment(p7.getId(), engineering.getId());

        System.out.println("✔ Project-Department links created");

        // ── 7. LINK CLIENTS → PROJECTS ───────────────────────────────────────
        cliProjRepo.linkClient(p1.getId(), c1.getId());  // Apex — Platform Redesign
        cliProjRepo.linkClient(p2.getId(), c1.getId());  // Apex — Mobile App
        cliProjRepo.linkClient(p3.getId(), c2.getId());  // BlueSky — Brand Refresh
        cliProjRepo.linkClient(p4.getId(), c3.getId());  // CoreLogic — Data Pipeline
        cliProjRepo.linkClient(p6.getId(), c3.getId());  // CoreLogic — Analytics Dashboard
        cliProjRepo.linkClient(p6.getId(), c4.getId());  // Delta — Analytics Dashboard
        cliProjRepo.linkClient(p7.getId(), c4.getId());  // Delta — Supply Chain

        System.out.println("✔ Client-Project links created");

        // ── 8. VERIFY — sample reads ──────────────────────────────────────────
        System.out.println("\n── Verification ─────────────────────────────────");

        System.out.println("\nProjects for Alice (Engineering):");
        empProjRepo.findProjectIdsByEmployee(alice.getId())
                   .forEach(id -> System.out.println("  project id: " + id));

        System.out.println("\nEmployees on Platform Redesign:");
        empProjRepo.findEmployeeIdsByProject(p1.getId())
                   .forEach(id -> System.out.println("  employee id: " + id));

        System.out.println("\nDepartments on Data Pipeline Upgrade:");
        projDeptRepo.findDepartmentIdsByProject(p4.getId())
                    .forEach(id -> System.out.println("  department id: " + id));

        System.out.println("\nClients on Analytics Dashboard:");
        cliProjRepo.findClientIdsByProject(p6.getId())
                   .forEach(id -> System.out.println("  client id: " + id));

        System.out.println("\nProjects for CoreLogic:");
        cliProjRepo.findProjectIdsByClient(c3.getId())
                   .forEach(id -> System.out.println("  project id: " + id));
    }

}
