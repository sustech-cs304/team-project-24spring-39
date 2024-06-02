package com.example.cs304.repository;

import com.example.cs304.entity.Course;
import com.example.cs304.entity.CourseStudent;
import com.example.cs304.response.Response;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface CourseRepository extends JpaRepository<Course, Integer> {

//    @Query

    @Query(value = "SELECT c.id, c.name, c.CID, c.type, c.department, c.credit, c.hours, c.capacity, c.selected, c.location, c.time, p.PID, p.name AS professor_name " +
            "FROM course c " +
            "LEFT JOIN course_professor cp ON c.CID = cp.course_id " +
            "LEFT JOIN professor p ON cp.professor_id = p.PID", nativeQuery = true)
    List<Map<String, Object>> findAllCoursesProfessors();

    // Add course to course_student
    @Modifying
    @Transactional
    @Query(value = "insert into course_student(course_id, student_id, score) values (:courseId, :studentId, :score)", nativeQuery = true)
    void selectCourse(@Param("courseId") String courseId, @Param("studentId") String studentId, @Param("score") int score);

    // delete course from course_student
    @Modifying
    @Query(value = "delete from course_student where course_id = :courseId and student_id = :studentId", nativeQuery = true)
    void dropCourse(@Param("courseId") String courseId, @Param("studentId") String studentId);


    @Query(value = "select s.sid, s.name\n" +
            "from student s left join course_student cs on s.sid = cs.student_id\n" +
            "where cs.course_id = ?;", nativeQuery = true)
    List<Map<String, Object>> findStudentsInCourse(String CID);

    @Query(value = "select * from course where type like '%必修课%';", nativeQuery = true)
    List<Course> findObligatoryCourses();

    @Query(value = "select * from course where type like '%选修课%';", nativeQuery = true)
    List<Course> findElectiveCourses();

    @Query(value = "select * from course where CID in (select course_id from course_student where student_id = :SID and valid = true)", nativeQuery = true)
    List<Course> findSelectedCourses(String SID);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "UPDATE course\n" +
            "SET selected = selected + 1\n" +
            "WHERE CID = :CID", nativeQuery = true)
    void addCourseSelected(String CID);

    @Transactional
    @Modifying
    @Query(value = "insert into course (name, CID, semester, type, department, credit, hours, capacity, location, description, time) " +
            "values (:name, :CID, :semester, :type, :department, :credit, :hours, :capacity, :location, :description, :time)", nativeQuery = true)
    void addCourse(@Param("name") String name, @Param("CID") String CID, @Param("semester") String semester, @Param("type") String type,
                   @Param("department") String department, @Param("credit") Integer credit,  @Param("hours") Integer hours, @Param("capacity") Integer capacity,
                   @Param("location") String location, @Param("description") String description, @Param("time") String time
                   );

    @Transactional
    @Modifying
    @Query(value = "delete from course where CID = :CID", nativeQuery = true)
    void deleteCourse(@Param("CID") String CID);
}