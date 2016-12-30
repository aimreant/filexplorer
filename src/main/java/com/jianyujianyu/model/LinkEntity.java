package com.jianyujianyu.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by aimreant on 12/4/16.
 */
@Entity
@Table(name = "link", schema = "filexplorer", catalog = "")
public class LinkEntity {
    private int id;
    private String filename;
    private Timestamp createdAt;
    private Timestamp deletedAt;
    private FileEntity fileByFileId;
    private UserEntity userByUserId;
    private DirectoryEntity directoryByDirectoryId;
    private Collection<LogEntity> logsById;

    public LinkEntity(){

    }

    public LinkEntity(
            String filename,
            DirectoryEntity directoryByDirectoryId,
            FileEntity fileByFileId,
            UserEntity userByUserId
    ){
        this.filename = filename;
        this.directoryByDirectoryId = directoryByDirectoryId;
        this.fileByFileId = fileByFileId;
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
    @Column(name = "filename", nullable = false, length = 50)
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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

        LinkEntity that = (LinkEntity) o;

        if (id != that.id) return false;
        if (filename != null ? !filename.equals(that.filename) : that.filename != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (deletedAt != null ? !deletedAt.equals(that.deletedAt) : that.deletedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (filename != null ? filename.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (deletedAt != null ? deletedAt.hashCode() : 0);
        return result;
    }

    @ManyToOne(cascade={CascadeType.REMOVE})
    @JoinColumn(name = "directory_id", referencedColumnName = "id", nullable = false)
    public DirectoryEntity getDirectoryByDirectoryId() {
        return directoryByDirectoryId;
    }

    public void setDirectoryByDirectoryId(DirectoryEntity directoryByDirectoryId) {
        this.directoryByDirectoryId = directoryByDirectoryId;
    }

    @ManyToOne(cascade={CascadeType.MERGE})
    @JoinColumn(name = "file_id", referencedColumnName = "id", nullable = false)
    public FileEntity getFileByFileId() {
        return fileByFileId;
    }

    public void setFileByFileId(FileEntity fileByFileId) {
        this.fileByFileId = fileByFileId;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public UserEntity getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(UserEntity userByUserId) {
        this.userByUserId = userByUserId;
    }



    @OneToMany(fetch = FetchType.EAGER, mappedBy = "linkByLinkId", cascade={CascadeType.REMOVE})
    public Collection<LogEntity> getLogsById() {
        return logsById;
    }

    public void setLogsById(Collection<LogEntity> logsById) {
        this.logsById = logsById;
    }
}
