package com.fpt.hsf302.jpademo;

import com.fpt.hsf302.jpademo.entity.Student;
import com.fpt.hsf302.jpademo.service.StudentService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test CRUD cua StudentService.
 *
 * @SpringBootTest = khoi dong Spring Boot de test
 * @Transactional  = moi test ROLLBACK sau khi xong => khong anh huong test khac
 */
@SpringBootTest
@Transactional
public class StudentTest {

    @Autowired
    private StudentService studentService;

    @PersistenceContext
    private EntityManager entityManager;

    // ===================== TEST CREATE =====================

    @Test
    public void testCreateStudent() {
        // 1. Them sinh vien
        Student created = studentService.createStudent("Nguyen Van A", "a@fpt.edu.vn", 20);

        // 2. Flush de dam bao ghi xuong DB
        entityManager.flush();
        entityManager.clear();

        // 3. Tim lai bang ID thuc (khong hardcode ID=1)
        Student student = entityManager.find(Student.class, created.getId());

        // 4. Kiem tra
        assertNotNull(student);
        assertEquals("Nguyen Van A", student.getFullName());
        assertEquals("a@fpt.edu.vn", student.getEmail());
        assertEquals(20, student.getAge());

        System.out.println("testCreateStudent PASSED!");
    }

    // ===================== TEST READ =====================

    @Test
    public void testFindById() {
        // 1. Them sinh vien, lay ID thuc
        Student created = studentService.createStudent("Le Van B", "b@fpt.edu.vn", 21);
        Long realId = created.getId();

        // 2. Tim theo ID thuc
        Student found = studentService.findById(realId);

        // 3. Kiem tra
        assertNotNull(found);
        assertEquals("Le Van B", found.getFullName());

        System.out.println("testFindById PASSED!");
    }

    @Test
    public void testFindAll() {
        // 1. Dem so sinh vien hien tai (co the co tu CommandLineRunner)
        int beforeCount = studentService.findAll().size();

        // 2. Them 3 sinh vien
        studentService.createStudent("SV 1", "sv1_test@fpt.edu.vn", 20);
        studentService.createStudent("SV 2", "sv2_test@fpt.edu.vn", 21);
        studentService.createStudent("SV 3", "sv3_test@fpt.edu.vn", 22);

        // 3. Kiem tra tang dung 3
        List<Student> students = studentService.findAll();
        assertEquals(beforeCount + 3, students.size());

        System.out.println("testFindAll PASSED!");
    }

    // ===================== TEST UPDATE =====================

    @Test
    public void testUpdateStudent() {
        // 1. Them sinh vien, lay ID thuc
        Student created = studentService.createStudent("Nguyen Van A", "a@fpt.edu.vn", 20);
        Long realId = created.getId();

        // 2. Cap nhat
        studentService.updateStudent(realId, "Nguyen Van A Updated", "a.updated@fpt.edu.vn", 22);

        // 3. Flush va clear cache
        entityManager.flush();
        entityManager.clear();

        // 4. Tim lai va kiem tra
        Student student = entityManager.find(Student.class, realId);
        assertNotNull(student);
        assertEquals("Nguyen Van A Updated", student.getFullName());
        assertEquals("a.updated@fpt.edu.vn", student.getEmail());
        assertEquals(22, student.getAge());

        System.out.println("testUpdateStudent PASSED!");
    }

    // ===================== TEST DELETE =====================

    @Test
    public void testDeleteStudent() {
        // 1. Them 2 sinh vien
        Student sv1 = studentService.createStudent("SV Xoa", "xoa_test@fpt.edu.vn", 20);
        Student sv2 = studentService.createStudent("SV Giu", "giu_test@fpt.edu.vn", 21);

        // 2. Xoa sinh vien dau tien
        studentService.deleteStudent(sv1.getId());

        // 3. Flush va clear
        entityManager.flush();
        entityManager.clear();

        // 4. Kiem tra
        Student deleted = entityManager.find(Student.class, sv1.getId());
        Student kept = entityManager.find(Student.class, sv2.getId());

        assertNull(deleted);        // Da xoa => null
        assertNotNull(kept);        // Van con
        assertEquals("SV Giu", kept.getFullName());

        System.out.println("testDeleteStudent PASSED!");
    }

    // ===================== TEST NOT FOUND =====================

    @Test
    public void testFindByIdNotFound() {
        Student notFound = studentService.findById(999L);
        assertNull(notFound);

        System.out.println("testFindByIdNotFound PASSED!");
    }
}
