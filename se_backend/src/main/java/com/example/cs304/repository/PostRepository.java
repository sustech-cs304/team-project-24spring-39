package com.example.cs304.repository;

import com.example.cs304.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface PostRepository extends JpaRepository<Post, Integer> {
//    @Query("SELECT p FROM Post p WHERE (:authorId is null or p.author.sid = :authorId) and (:majorTag is null or p.majorCategory = :majorTag) and (:courseTag is null or p.courseCategory = :courseTag)")
//List<Post> findByCondition(String authorId, String majorTag, String courseTag);
  @Query(value = """
        SELECT p.id as post_id, p.title, p.content, p.major_category, p.course_category, p.author_id, p.posting_time, a.name,
        JSON_ARRAYAGG(JSON_OBJECT(  'reply_author_id', r.author_id ,'reply_author' ,s.name , 'reply_content', r.content  )) as replies,
        JSON_ARRAYAGG(JSON_OBJECT(  'file_name' ,f.name , 'file_path', f.filepath  )) as files
        from post p
        left join file f on p.id = f.post_id
        left join student a on p.author_id = a.SID
        left join reply r on p.id = r.post_id
        left join student s on r.author_id = s.SID
        where (:authorId is null or p.author_id = :authorId) and (:majorTag is null or p.major_category = :majorTag) and (:courseTag is null or p.course_category = :courseTag)
        group by p.id""", nativeQuery = true)
List<Map<String,Object>> findByCondition(String authorId, String majorTag, String courseTag);
}