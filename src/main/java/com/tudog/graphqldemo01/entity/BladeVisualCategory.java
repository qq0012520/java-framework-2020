package com.tudog.graphqldemo01.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.tudog.graphqldemo01.entity.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BladeVisualCategory extends BaseEntity{

    @OneToMany(mappedBy = "category")
    private List<BladeVisual> bladeVisuals;

    private String categoryKey;

    private String categoryValue;

    @Column(nullable = false)
    private Byte is_deleted = 0; //是否删除,1 是，0 否

    public BladeVisualCategory(Long id){
        setId(id);
    }
}