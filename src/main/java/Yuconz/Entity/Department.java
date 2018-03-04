package Yuconz.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Department
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String name;

    @OneToMany
    private List<Section> sections;
}
