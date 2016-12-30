package com.jianyujianyu.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by aimreant on 12/4/16.
 */
@Entity
@Table(name = "directory", schema = "filexplorer", catalog = "")
public class DirectoryEntity {
    private int id;
    private String name;
    private DirectoryEntity parentId;
    private UserEntity userByUserId;
    private Timestamp createdAt;
    private Timestamp deletedAt;
    private Collection<DirectoryEntity> children;
    private Collection<LinkEntity> linksById;

    public DirectoryEntity(){

    }

    public DirectoryEntity(String name, UserEntity userByUserId, DirectoryEntity parentId){
        this.name = name;
        if(parentId != null) {
            this.parentId = parentId;
        }
        this.userByUserId = userByUserId;
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
    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

        DirectoryEntity that = (DirectoryEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (deletedAt != null ? !deletedAt.equals(that.deletedAt) : that.deletedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (deletedAt != null ? deletedAt.hashCode() : 0);
        return result;
    }

    @ManyToOne(cascade={CascadeType.REMOVE})
    @JoinColumn(name = "parent_id", referencedColumnName = "id", nullable = true)
    public DirectoryEntity getParentId() {
        return parentId;
    }

    public void setParentId(DirectoryEntity parentId) {
        this.parentId = parentId;
    }


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parentId", cascade={CascadeType.REMOVE})
    public Collection<DirectoryEntity> getChildren() {
        return children;
    }

    public void setChildren(Collection<DirectoryEntity> children) {
        this.children = children;
    }


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "directoryByDirectoryId", cascade={CascadeType.REMOVE})
    public Collection<LinkEntity> getLinksById() {
        return linksById;
    }

    public void setLinksById(Collection<LinkEntity> linksById) {
        this.linksById = linksById;
    }


    @ManyToOne(cascade={CascadeType.MERGE})
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public UserEntity getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(UserEntity userByUserId) {
        this.userByUserId = userByUserId;
    }

}
