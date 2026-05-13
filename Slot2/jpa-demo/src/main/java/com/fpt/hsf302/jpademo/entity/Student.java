package com.fpt.hsf302.jpademo.entity;

import jakarta.persistence.*;

/**
 * Entity Student - anh xa voi bang "students" trong H2 database.
 */
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(unique = true)
    private String email;

    private Integer age;

    // Constructor mac dinh - Hibernate bat buoc phai co
    public Student() {}

    public Student(String fullName, String email, Integer age) {
        this.fullName = fullName;
        this.email = email;
        this.age = age;
    }

    // Getters
    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public Integer getAge() { return age; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setAge(Integer age) { this.age = age; }

    @Override
    public String toString() {
        return "Student{id=" + id + ", fullName='" + fullName + "', email='" + email + "', age=" + age + "}";
    }
}
