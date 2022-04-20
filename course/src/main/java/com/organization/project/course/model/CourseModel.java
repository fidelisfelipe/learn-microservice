package com.organization.project.course.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.organization.project.course.model.enums.CourseLevel;
import com.organization.project.course.model.enums.CourseStatus;
import lombok.Data;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)//para não serializar campos nulos
@Entity
@Table(name="TB_COURSES")
public class CourseModel implements Serializable {
    private static final long serialVersionUID = 1L;
//USED in MYSQL GENERATED
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID courseId;

    @Column(nullable = false, length = 150)
    private String name;
    @Column(nullable = false, length = 250)
    private String description;
    @Column
    private String imageUrl;
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss'Z'")
    private LocalDateTime creationDate;
    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss'Z'")
    private LocalDateTime lastUpdateDate;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseStatus courseStatus;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseLevel courseLevel;
    @Column(nullable = false)
    private UUID userInstructor;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)//cascade = CascadeType.ALL, orphanRemoval = true // realiza deleção item a item
    @Fetch(FetchMode.SUBSELECT)
    //@OnDelete(action = OnDeleteAction.CASCADE)//responsabilidade é passada para o banco de dados
    private Set<ModuleModel> moduleList;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private Set<CourseUserModel> courseUserList;

    public CourseUserModel convertToCourseUserModel(UUID userID){
        return new CourseUserModel(null, this, userID);
    }
}
