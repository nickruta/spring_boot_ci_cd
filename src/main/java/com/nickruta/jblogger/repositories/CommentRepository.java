package com.nickruta.jblogger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nickruta.jblogger.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>
{

}
