package com.zhongruan.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_news")
public class News {
    @Id
    @GeneratedValue
    private Long id;

    private String title;           // 新闻标题
    private String content;         // 新闻内容
    private String firstPicture;    // 新闻图片的url
    private String flag;            // 新闻状态
    private Integer views;          // 新闻的浏览次数
    private boolean appreciation;   // 新闻的赞赏功能
    private boolean shareStatement; // 新闻的转载许可
    private boolean commentabled;   // 新闻的评论许可
    private boolean published;      // 新闻的发布
    private boolean recommend;      // 新闻是否推荐

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;        // 新闻创建时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;        // 新闻的更新时间

    @ManyToOne
    private Type type;              // 新闻的类型

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>(); // 新闻的标签

    @ManyToOne
    private User user;              // 新闻的作者

    @OneToMany(mappedBy = "news")
    private List<Comment> comments = new ArrayList<>();  // 新闻的评论

    @Transient
    private String tagIds;

    private String description;

    // 将数据准备就绪
    public void init() {
        this.tagIds = tagsToIds(this.tags);
    }

    public String tagsToIds(List<Tag> tags) {
        // [1, 2, 3] ===>  1,2,3,
        // String类的内容不可改变的  StringBuffer内容是可以改变的  StringBuilder 是线程不安全的
        StringBuffer ids = new StringBuffer();
        if (!tags.isEmpty()) {
            for (Tag tag : tags) {
                ids.append(tag.getId());
                ids.append(",");
            }
        }
        return ids.toString();
    }

    public News() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isAppreciation() {
        return appreciation;
    }

    public void setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
    }

    public boolean isShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public boolean isCommentabled() {
        return commentabled;
    }

    public void setCommentabled(boolean commentabled) {
        this.commentabled = commentabled;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentabled=" + commentabled +
                ", published=" + published +
                ", recommend=" + recommend +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", type=" + type +
                ", tags=" + tags +
                ", user=" + user +
                ", comments=" + comments +
                ", tagIds='" + tagIds + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
