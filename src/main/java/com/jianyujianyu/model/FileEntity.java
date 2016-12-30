package com.jianyujianyu.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by aimreant on 12/4/16.
 */
@Entity
@Table(name = "file", schema = "filexplorer", catalog = "")
public class FileEntity {
    private int id;
    private String hash;
    private long size;
    private Timestamp createdAt;
    private Timestamp deletedAt;
    private Collection<LinkEntity> linksById;

    public FileEntity(){

    }

    public FileEntity(String hash, long size){
        this.hash = hash;
        this.size = size;
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
    @Column(name = "hash", nullable = false, length = 200)
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Basic
    @Column(name = "size", nullable = false)
    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
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

        FileEntity that = (FileEntity) o;

        if (id != that.id) return false;
        if (size != that.size) return false;
        if (hash != null ? !hash.equals(that.hash) : that.hash != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (deletedAt != null ? !deletedAt.equals(that.deletedAt) : that.deletedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (hash != null ? hash.hashCode() : 0);
        result = 31 * result + (int) (size ^ (size >>> 32));
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (deletedAt != null ? deletedAt.hashCode() : 0);
        return result;
    }


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "fileByFileId", cascade={CascadeType.REMOVE}, orphanRemoval = true)
    public Collection<LinkEntity> getLinksById() {
        return linksById;
    }

    public void setLinksById(Collection<LinkEntity> linksById) {
        this.linksById = linksById;
    }


}
