package com.example.cs304.repository;

import com.example.cs304.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface ForumRepository extends JpaRepository<Post, Integer> {

    @Query(value = "select p.id, s.name as author, s.avatar, p.posting_time, p.title, p.content, f.filepath, p.major_category, p.course_category, count(l.id) as likes\n" +
            "from post p left join student s on p.author_id = s.sid\n" +
            "left join file f on p.file_id = f.id\n" +
            "left join `like` l on p.id = l.post_id\n" +
            "group by p.id;", nativeQuery = true)
    List<Map<String, Object>> getAllPost();

    @Query(value = "insert into post (author_id, title, content, file_id, major_category, course_category) " +
            "values (:author_id, :title, :content, :file_id, :major_category, :course_category)", nativeQuery = true)
    void posting(String author_id, String title, String content, int file_id, String major_category, String course_category);

//    insert into `like` (post_id, author_id) values (1, '20180001');
    @Query(value = "insert into `like` (post_id, author_id) values (:post_id, :author_id)", nativeQuery = true)
    void like(int post_id, String author_id);

//    delete from post where id = 1;
    @Query(value = "delete from post where id = :id", nativeQuery = true)
    void deletePost(int id);

//    insert into reply (post_id, author_id, content, file_id) values (1, '20180001', 'content', 1);
    @Query(value = "insert into reply (post_id, author_id, content, file_id) values (:post_id, :author_id, :content, :file_id)", nativeQuery = true)
    void comment(int post_id, String author_id, String content, int file_id);
}
