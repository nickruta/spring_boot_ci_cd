package com.sivalabs.jblogger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sivalabs.jblogger.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>
{

}
