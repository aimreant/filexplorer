package com.jianyujianyu.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by aimreant on 12/4/16.
 */
@Entity
@Table(name = "user", schema = "filexplorer", catalog = "")
public class UserEntity {
    private int id;
    private String username;
    private String password;
    private long spaceUsage;
    private Timestamp createdAt;
    private Timestamp deletedAt;
    private Collection<DirectoryEntity> directorysById;
    private Collection<LinkEntity> linksById;
    private Collection<LogEntity> logsById;
    private LevelEntity levelByLevelId;

    public UserEntity(){

    }

    public UserEntity(String username, String password, LevelEntity levelByLevelId){
        this.username = username;
        this.password = password;
        this.levelByLevelId = levelByLevelId;
        this.spaceUsage = 0;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username", nullable = false, length = 20)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 100)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "space_usage", nullable = false)
    public long getSpaceUsage() {
        return spaceUsage;
    }

    public void setSpaceUsage(long spaceUsage) {
        this.spaceUsage = spaceUsage;
    }

    @Basic
    @Column(name = "created_at", nullable = false)
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "deleted_at", nullable = true)
    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (id != that.id) return false;
        if (spaceUsage != that.spaceUsage) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (deletedAt != null ? !deletedAt.equals(that.deletedAt) : that.deletedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (int) (spaceUsage ^ (spaceUsage >>> 32));
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (deletedAt != null ? deletedAt.hashCode() : 0);
        return result;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userByUserId", cascade = CascadeType.REMOVE)
    public Collection<LinkEntity> getLinksById() {
        return linksById;
    }

    public void setLinksById(Collection<LinkEntity> linksById) {
        this.linksById = linksById;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userByUserId", cascade = CascadeType.REMOVE)
    public Collection<DirectoryEntity> getDirectorysById() {
        return directorysById;
    }

    public void setDirectorysById(Collection<DirectoryEntity> directorysById) {
        this.directorysById = directorysById;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userByUserId", cascade = CascadeType.ALL)
    public Collection<LogEntity> getLogsById() {
        return logsById;
    }

    public void setLogsById(Collection<LogEntity> logsById) {
        this.logsById = logsById;
    }

    @ManyToOne
    @JoinColumn(name = "level_id", referencedColumnName = "id", nullable = false)
    public LevelEntity getLevelByLevelId() {
        return levelByLevelId;
    }

    public void setLevelByLevelId(LevelEntity levelByLevelId) {
        this.levelByLevelId = levelByLevelId;
    }
}
