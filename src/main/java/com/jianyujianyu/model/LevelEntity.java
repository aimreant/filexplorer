package com.jianyujianyu.model;

import javax.persistence.*;

/**
 * Created by aimreant on 12/4/16.
 */
@Entity
@Table(name = "level", schema = "filexplorer", catalog = "")
public class LevelEntity {
    private int id;
    private Long spaceAllow;

    public LevelEntity(){}

    public LevelEntity(int id, Long spaceAllow){
        this.id = id;
        this.spaceAllow = spaceAllow;
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
    @Column(name = "space_allow", nullable = true)
    public Long getSpaceAllow() {
        return spaceAllow;
    }

    public void setSpaceAllow(Long spaceAllow) {
        this.spaceAllow = spaceAllow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LevelEntity that = (LevelEntity) o;

        if (id != that.id) return false;
        if (spaceAllow != null ? !spaceAllow.equals(that.spaceAllow) : that.spaceAllow != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (spaceAllow != null ? spaceAllow.hashCode() : 0);
        return result;
    }
}
