CREATE TABLE Department (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    city VARCHAR(100),
    annual_budget DECIMAL(15,2)
);

CREATE TABLE Client (
    id SERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    industry VARCHAR(100),
    primary_contact_name VARCHAR(150),
    primary_contact_phone VARCHAR(50),
    primary_contact_email VARCHAR(150)
);

CREATE TABLE Employee (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    title VARCHAR(100),
    date_hired DATE,
    salary DECIMAL(12,2),
    department_id INT,
    FOREIGN KEY (department_id) REFERENCES Department(id)
);

CREATE TABLE Project (
    id SERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    description TEXT,
    start_date DATE,
    end_date DATE,
    total_budget DECIMAL(15,2),
    status VARCHAR(20),
    CHECK (status IN ('complete', 'active', 'unassigned'))
);

CREATE TABLE Employee_Project (
    employee_id INT,
    project_id INT,
    PRIMARY KEY (employee_id, project_id),
    FOREIGN KEY (employee_id) REFERENCES Employee(id),
    FOREIGN KEY (project_id) REFERENCES Project(id)
);

CREATE TABLE Client_Project (
    client_id INT,
    project_id INT,
    PRIMARY KEY (client_id, project_id),
    FOREIGN KEY (client_id) REFERENCES Client(id),
    FOREIGN KEY (project_id) REFERENCES Project(id)
);