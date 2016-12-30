package com.jianyujianyu.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by aimreant on 12/4/16.
 */
@Entity
@Table(name = "log", schema = "filexplorer", catalog = "")
public class LogEntity {
    private int id;
    private String operation;
    private Timestamp createdAt;
    private LinkEntity linkByLinkId;
    private UserEntity userByUserId;

    public LogEntity(){

    }

    public LogEntity(String operation, LinkEntity linkByLinkId, UserEntity userByUserId){
        this.operation = operation;
        this.linkByLinkId = linkByLinkId;
        this.userByUserId = userByUserId;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "operation", nullable = false, length = 20)
    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Basic
    @Column(name = "created_at", nullable = false)
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogEntity logEntity = (LogEntity) o;

        if (id != logEntity.id) return false;
        if (operation != null ? !operation.equals(logEntity.operation) : logEntity.operation != null) return false;
        if (createdAt != null ? !createdAt.equals(logEntity.createdAt) : logEntity.createdAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (operation != null ? operation.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }

    @ManyToOne(cascade={CascadeType.REMOVE})
    @JoinColumn(name = "link_id", referencedColumnName = "id", nullable = true)
    public LinkEntity getLinkByLinkId() {
        return linkByLinkId;
    }

    public void setLinkByLinkId(LinkEntity linkByLinkId) {
        this.linkByLinkId = linkByLinkId;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public UserEntity getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(UserEntity userByUserId) {
        this.userByUserId = userByUserId;
    }
}
