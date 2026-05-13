package com.fpt.hsf302.jpademo;

import com.fpt.hsf302.jpademo.entity.Student;
import com.fpt.hsf302.jpademo.service.StudentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * HSF302 - Slot 1: Spring Boot + JPA + H2 In-Memory Demo
 * FPT University - Software Engineering
 *
 * Demo day du CRUD: Create, Read, Update, Delete
 *
 * Khi chay xong, vao http://localhost:8080/h2-console de xem database!
 * JDBC URL: jdbc:h2:mem:testdb | User: sa | Pass: (trong)
 */
@SpringBootApplication
public class JpaDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaDemoApplication.class, args);
    }

    @Bean
    CommandLineRunner demo(StudentService service) {
        return args -> {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("  HSF302 - DEMO CRUD (H2 In-Memory Database)");
            System.out.println("=".repeat(60));

            // ===================================================
            // 1. CREATE - Them du lieu (em.persist)
            // ===================================================
            System.out.println("\n>>> 1. CREATE - Them 5 sinh vien\n");

            service.createStudent("Tran Van Thuan", "thuantv@fpt.edu.vn", 20);
            service.createStudent("Nguyen Van An", "annv@fpt.edu.vn", 21);
            service.createStudent("Le Thi Binh", "binhlt@fpt.edu.vn", 19);
            service.createStudent("Pham Van Cuong", "cuongpv@fpt.edu.vn", 22);
            service.createStudent("Hoang Thi Dung", "dunght@fpt.edu.vn", 20);

            // ===================================================
            // 2. READ - Doc du lieu
            // ===================================================
            System.out.println("\n>>> 2. READ - Doc tat ca sinh vien (findAll)\n");
            service.printAll();

            System.out.println("\n>>> 2b. READ - Tim sinh vien ID = 1 (findById)\n");
            Student found = service.findById(1L);
            System.out.println("  Ket qua: " + found);

            System.out.println("\n>>> 2c. READ - Tim sinh vien ID = 99 (khong ton tai)\n");
            Student notFound = service.findById(99L);
            System.out.println("  Ket qua: " + notFound);  // null

            // ===================================================
            // 3. UPDATE - Cap nhat du lieu
            // ===================================================
            System.out.println("\n>>> 3. UPDATE - Doi ten sinh vien ID=1 thanh 'Tran Thuan Updated'\n");
            service.updateStudent(1L, "Tran Thuan Updated", "thuan_new@fpt.edu.vn", 25);

            System.out.println("\n  Kiem tra lai sau khi UPDATE:");
            Student updated = service.findById(1L);
            System.out.println("  " + updated);

            // ===================================================
            // 4. DELETE - Xoa du lieu
            // ===================================================
            System.out.println("\n>>> 4. DELETE - Xoa sinh vien ID = 4\n");
            service.deleteStudent(4L);

            System.out.println("\n>>> 4b. DELETE - Thu xoa ID = 99 (khong ton tai)\n");
            service.deleteStudent(99L);

            // ===================================================
            // 5. KET QUA CUOI CUNG
            // ===================================================
            System.out.println("\n>>> 5. DANH SACH CUOI CUNG (sau UPDATE va DELETE)\n");
            List<Student> remaining = service.findAll();
            remaining.forEach(s -> System.out.println("  " + s));
            System.out.println("\n  Tong so sinh vien con lai: " + remaining.size());

            // ===================================================
            System.out.println("\n" + "=".repeat(60));
            System.out.println("  DEMO CRUD HOAN TAT!");
            System.out.println("  Vao http://localhost:8080/h2-console de xem database");
            System.out.println("  JDBC URL: jdbc:h2:mem:testdb | User: sa | Pass: (trong)");
            System.out.println("=".repeat(60) + "\n");
        };
    }
}
