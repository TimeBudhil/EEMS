CREATE TABLE IF NOT EXISTS Department (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    city VARCHAR(100),
    annual_budget DECIMAL(15,2)
);

CREATE TABLE IF NOT EXISTS Client (
    id SERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    industry VARCHAR(100),
    primary_contact_name VARCHAR(150),
    primary_contact_phone VARCHAR(50),
    primary_contact_email VARCHAR(150)
);

CREATE TABLE IF NOT EXISTS Employee (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    title VARCHAR(100),
    date_hired DATE,
    salary DECIMAL(12,2),
    department_id INT,
    FOREIGN KEY (department_id) REFERENCES Department(id)
);

CREATE TABLE IF NOT EXISTS Project (
    id SERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    description TEXT,
    start_date DATE,
    end_date DATE,
    total_budget DECIMAL(15,2),
    status VARCHAR(20),
    estimated_duration_hours DECIMAL(8,2),
    CHECK (status IN ('complete', 'active', 'unassigned'))
);

CREATE TABLE IF NOT EXISTS Employee_Project (
    employee_id INT,
    project_id INT,
    hours_allocated DECIMAL(6,2) NOT NULL,
    PRIMARY KEY (employee_id, project_id),
    FOREIGN KEY (employee_id) REFERENCES Employee(id),
    FOREIGN KEY (project_id) REFERENCES Project(id)
);

CREATE TABLE IF NOT EXISTS Client_Project (
    client_id INT,
    project_id INT,
    PRIMARY KEY (client_id, project_id),
    FOREIGN KEY (client_id) REFERENCES Client(id),
    FOREIGN KEY (project_id) REFERENCES Project(id)
);s

CREATE TABLE IF NOT EXISTS Project_Department (
    project_id INT,
    department_id INT, 
    PRIMARY KEY (project_id, department_id),
    FOREIGN KEY (project_id) REFERENCES Project(id),
    FOREIGN KEY (department_id) REFERENCES Department(id),
);