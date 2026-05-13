package com.fpt.hsf302.jpademo.service;

import com.fpt.hsf302.jpademo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service - xu ly logic nghiep vu (CRUD day du).
 *
 * EntityManager la doi tuong chinh de thao tac voi database:
 * - em.persist(entity)   -> INSERT
 * - em.find(Class, id)   -> SELECT ... WHERE id = ?
 * - em.createQuery(JPQL) -> SELECT tu do
 * - em.merge(entity)     -> UPDATE
 * - em.remove(entity)    -> DELETE
 */
@Service
public class StudentService {

    @PersistenceContext
    private EntityManager em;

    // ===================== CREATE =====================

    /**
     * Them sinh vien moi vao database.
     * SQL: INSERT INTO students (full_name, email, age) VALUES (?, ?, ?)
     */
    @Transactional
    public Student createStudent(String name, String email, int age) {
        Student s = new Student(name, email, age);
        em.persist(s);    // INSERT
        System.out.println("  [INSERT] Saved with ID = " + s.getId());
        return s;  // Tra ve Student (co ID) de su dung tiep
    }

    // ===================== READ =======================

    /**
     * Tim sinh vien theo ID.
     * SQL: SELECT * FROM students WHERE id = ?
     */
    @Transactional(readOnly = true)
    public Student findById(Long id) {
        return em.find(Student.class, id);  // Tra ve Student hoac null
    }

    /**
     * Doc TAT CA sinh vien.
     * JPQL: "SELECT s FROM Student s" (dung ten Entity, KHONG phai ten bang!)
     */
    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return em.createQuery("SELECT s FROM Student s", Student.class)
                .getResultList();
    }

    /**
     * In tat ca sinh vien ra console.
     */
    @Transactional(readOnly = true)
    public void printAll() {
        em.createQuery("SELECT s FROM Student s", Student.class)
                .getResultList()
                .forEach(System.out::println);
    }

    // ===================== UPDATE =====================

    /**
     * Cap nhat thong tin sinh vien theo ID.
     * SQL: UPDATE students SET full_name=?, email=?, age=? WHERE id=?
     *
     * Cach hoat dong:
     * 1. em.find() -> tim sinh vien trong DB
     * 2. Thay doi thuoc tinh (setFullName, setEmail, setAge)
     * 3. Hibernate TU DONG phat hien thay doi va UPDATE khi commit transaction
     */
    @Transactional
    public void updateStudent(Long id, String newName, String newEmail, Integer newAge) {
        Student s = em.find(Student.class, id);  // Tim theo ID
        if (s != null) {
            s.setFullName(newName);   // Thay doi ten
            s.setEmail(newEmail);     // Thay doi email
            s.setAge(newAge);         // Thay doi tuoi
            // KHONG can goi em.merge() hay em.persist()!
            // Hibernate TU DONG UPDATE khi transaction commit
            System.out.println("  [UPDATE] Da cap nhat ID = " + id);
        } else {
            System.out.println("  [UPDATE] Khong tim thay ID = " + id);
        }
    }

    /**
     * Cap nhat bang em.merge() - dung khi doi tuong KHONG nam trong session.
     * SQL: UPDATE students SET ... WHERE id = ?
     */
    @Transactional
    public void mergeStudent(Student student) {
        em.merge(student);  // UPDATE
        System.out.println("  [MERGE] Da cap nhat: " + student.getFullName());
    }

    // ===================== DELETE =====================

    /**
     * Xoa sinh vien theo ID.
     * SQL: DELETE FROM students WHERE id = ?
     *
     * Cach hoat dong:
     * 1. em.find() -> tim sinh vien
     * 2. em.remove() -> xoa khoi DB
     */
    @Transactional
    public void deleteStudent(Long id) {
        Student s = em.find(Student.class, id);  // Tim truoc
        if (s != null) {
            em.remove(s);  // DELETE
            System.out.println("  [DELETE] Da xoa ID = " + id + " (" + s.getFullName() + ")");
        } else {
            System.out.println("  [DELETE] Khong tim thay ID = " + id);
        }
    }

    /**
     * Xoa TAT CA sinh vien.
     * JPQL: DELETE FROM Student
     */
    @Transactional
    public int deleteAll() {
        int count = em.createQuery("DELETE FROM Student").executeUpdate();
        System.out.println("  [DELETE ALL] Da xoa " + count + " sinh vien");
        return count;
    }
}
