<<<<<<< HEAD
CREATE TABLE IF NOT EXISTS Department (
    id SERIAL PRIMARY KEY,
=======
CREATE TABLE Department (
    id INT PRIMARY KEY,
>>>>>>> 366db5ae6d5f65618702d353ed4cdad7686b91c9
    name VARCHAR(100) NOT NULL,
    city VARCHAR(100),
    annual_budget DECIMAL(15,2)
);

<<<<<<< HEAD
CREATE TABLE IF NOT EXISTS Client (
    id SERIAL PRIMARY KEY,
=======
CREATE TABLE Client (
    id INT PRIMARY KEY,
>>>>>>> 366db5ae6d5f65618702d353ed4cdad7686b91c9
    name VARCHAR(150) NOT NULL,
    industry VARCHAR(100),
    primary_contact_name VARCHAR(150),
    primary_contact_phone VARCHAR(50),
    primary_contact_email VARCHAR(150)
);

<<<<<<< HEAD
CREATE TABLE IF NOT EXISTS Employee (
    id SERIAL PRIMARY KEY,
=======
CREATE TABLE Employee (
    id INT SERIAL PRIMARY KEY,
>>>>>>> 366db5ae6d5f65618702d353ed4cdad7686b91c9
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    title VARCHAR(100),
    date_hired DATE,
    salary DECIMAL(12,2),
    department_id INT,
    FOREIGN KEY (department_id) REFERENCES Department(id)
);

<<<<<<< HEAD
CREATE TABLE IF NOT EXISTS Project (
    id SERIAL PRIMARY KEY,
=======
CREATE TABLE Project (
    id INT PRIMARY KEY,
>>>>>>> 366db5ae6d5f65618702d353ed4cdad7686b91c9
    name VARCHAR(150) NOT NULL,
    description TEXT,
    start_date DATE,
    end_date DATE,
    total_budget DECIMAL(15,2),
    status VARCHAR(20),
    CHECK (status IN ('complete', 'active', 'unassigned'))
);

CREATE TABLE IF NOT EXISTS Employee_Project (
    employee_id INT,
    project_id INT,
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