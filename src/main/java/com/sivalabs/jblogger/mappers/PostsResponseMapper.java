package com.nickruta.jblogger.mappers;

import com.nickruta.jblogger.config.ApplicationProperties;
import com.nickruta.jblogger.domain.CommentDTO;
import com.nickruta.jblogger.domain.PostDTO;
import com.nickruta.jblogger.domain.PostsResponse;
import com.nickruta.jblogger.entities.Comment;
import com.nickruta.jblogger.entities.Post;
import com.nickruta.jblogger.entities.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class PostsResponseMapper {

    private final ApplicationProperties applicationProperties;

    public PostsResponseMapper(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public PostsResponse map(Page<Post> postsPage) {
        PostsResponse postsResponse = new PostsResponse();
        postsResponse.setCurrentPageNo(postsPage.getNumber()+1);
        postsResponse.setTotalPages(postsPage.getTotalPages());
        postsResponse.setTotalPosts(postsPage.getNumberOfElements());
        postsResponse.setHasNextPage(postsPage.hasNext());
        postsResponse.setHasPreviousPage(postsPage.hasPrevious());
        postsResponse.setPosts(postsPage.getContent().stream().map(this::map).collect(Collectors.toList()));
        return postsResponse;
    }

    public List<PostDTO> map(List<Post> postList) {
        return postList.stream().map(this::map).collect(Collectors.toList());
    }

    public PostDTO map(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .url(post.getUrl())
                .content(post.getContent())
                .shortDescription(post.getShortDescription())
                .createdByUserName(post.getCreatedBy().getName())
                .createdOn(post.getCreatedOn())
                .updatedOn(post.getUpdatedOn())
                .viewCount(post.getViewCount())
                .tags(post.getTags().stream().map(Tag::getLabel).collect(Collectors.toList()))
                .comments(post.getComments().stream().map(this::map).collect(Collectors.toList()))
                .facebookShareLink(getFacebookShareLink(post))
                .twitterShareLink(getTwitterShareLink(post))
                .linkedInShareLink(getLinkedInShareLink(post))
                .build();
    }

    public CommentDTO map(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .name(comment.getName())
                .email(comment.getEmail())
                .content(comment.getContent())
                .createdOn(comment.getCreatedOn())
                .updatedOn(comment.getUpdatedOn())

                .build();
    }

    private String getTwitterShareLink(Post post)
    {
        return applicationProperties.getTwitterShareUrl() +encode(post.getTitle())+" "+getURLWithContextPath()+"/"+post.getUrl();
    }

    private String getFacebookShareLink(Post post)
    {
        return applicationProperties.getFacebookShareUrl() +"u="+getURLWithContextPath()+"/"+post.getUrl()+"&t="+encode(post.getTitle());
    }

    private String getLinkedInShareLink(Post post)
    {
        return applicationProperties.getLinkedinShareUrl() +"title="+encode(post.getTitle())+"&url="+getURLWithContextPath()+"/"+post.getUrl();
    }

    //TODO; make it dynamic
    private static String getURLWithContextPath()
    {
        return "http://localhost:8080";
    }

    private static String encode(String str)
    {
        try
        {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e)
        {
            log.error(e.getMessage(),e);
            return "";
        }
    }

}
