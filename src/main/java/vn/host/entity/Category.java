package vn.host.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "categories")

public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryid;
    @Column(name = "categoryname", columnDefinition = "nvarchar(255)")
    private String categoryname;
    @Column(name = "images", columnDefinition = "nvarchar(500)")
    private String images;
}
